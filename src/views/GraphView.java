package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import global_data.Options;
import graph.*;
import utility.Box;

public class GraphView extends JPanel {

	/**
	 * Generated By Eclipse
	 */
	private static final long serialVersionUID = -208575025484602711L;

	private final Graph graph_;

	// private int grid_size_ = 10;
	private int node_width_ = 3;
	private boolean view_grid_ = true;

	private final HashMap< Node, Box > box_for_node_;
	private final HashMap< Edge, Box > box_for_edge_;

	// COLORS:
	private final Color background_color_ = new Color( 220, 220, 220 );
	private final Color grid_color_ = new Color( 180, 180, 180 );
	private final Color edge_color_ = Color.BLACK;
	private final Color ghost_edge_color_ = new Color( 120, 120, 120 );

	// Edge Geometry:
	private final double arrow_length_coeff_ = 1;

	public GraphView( Graph g ) {
		graph_ = g;
		box_for_node_ = new HashMap< Node, Box >();
		box_for_edge_ = new HashMap< Edge, Box >();
	}

	public void paint( Graphics g ) {
		Graphics2D g2D = (Graphics2D) g;
		drawBackground( g2D );
		for( Edge e : graph_.allEdges_const() ) {
			drawEdge( g2D, e );
		}

		PreliminaryEdge edge_being_drawn = graph_.ghostEdge();
		if( edge_being_drawn != null ) {
			drawGhostEdge( g2D, edge_being_drawn );
		}

		drawNodes( g2D );
	}

	public void drawBackground( Graphics2D g2D ) {
		g2D.setColor( background_color_ );
		g2D.fillRect( 0, 0, getWidth(), getHeight() );
		if( view_grid_ ) {
			g2D.setColor( grid_color_ );
			final int grid_size = Options.getGridSize();
			for( int x = -1; x < getWidth(); x += 2 * grid_size ) {
				g2D.drawLine( x, 0, x, getHeight() );
			}
			for( int y = -1; y < getHeight(); y += 2 * grid_size ) {
				g2D.drawLine( 0, y, getWidth(), y );
			}
		}
	}

	public void drawNodes( Graphics2D g2D ) {
		final int grid_size = Options.getGridSize();

		if( Options.getShowNodeTitles() ) {
			int new_font_size = (int) ( 2 * grid_size );
			g2D.setFont( new Font( "TimesRoman", Font.PLAIN, new_font_size ) );
		}

		final int selection_width = grid_size / 2;
		for( Node n : graph_.allNodes_const() ) {
			int x = n.x() * grid_size + ( grid_size / 2 );
			int y = n.y() * grid_size + ( grid_size / 2 );
			int diameter = grid_size * node_width_;

			if( n == graph_.selectedNode() ) {
				g2D.setColor( Color.black );
				int sx = x - selection_width;
				int sy = y - selection_width;
				int sdiameter = diameter + 2 * selection_width;
				g2D.fillOval( sx, sy, sdiameter, sdiameter );
			}

			g2D.setColor( n.color() );
			g2D.fillOval( x, y, diameter, diameter );
			setBoxForNode( n, new Box( x, y, diameter, diameter ) );

			if( Options.getShowNodeTitles() ) {
				g2D.setColor( Color.black );
				// g2D.font
				if( Options.getPutNodeTitlesToSide() ) {
					g2D.drawString( n.title(), x + diameter + selection_width, y + diameter / 2 );
				} else {
					g2D.drawString( n.title(), x, y - selection_width );
				}
			}
		}
	}

	public int getClosestPointForPoint( int point ) {
		final int grid_size = Options.getGridSize();
		final int offset = ( node_width_ - ( node_width_ / 2 ) ) * grid_size;
		final double new_point = ( point - offset ) / ( (double) grid_size );
		return (int) Math.rint( new_point );
	}

	public int getClosestGridPointForPoint( int point ) {
		final int grid_size = Options.getGridSize();
		int diff_from_grid_size = point % grid_size;
		if( diff_from_grid_size > grid_size / 2 ) {
			point = point + grid_size - diff_from_grid_size;
		} else {
			point -= diff_from_grid_size;
		}

		final int offset = ( node_width_ - ( node_width_ / 2 ) ) * grid_size;
		final double new_point = ( point - offset ) / ( (double) grid_size );
		int new_point_int = (int) Math.rint( new_point );
		return new_point_int;
	}

	public void drawEdge( Graphics2D g2D, Edge e ) {
		final Node n_from = e.sourceNode();
		final Node n_to = e.destinationNode();

		if( e == graph_.selectedEdge() ) {
			g2D.setStroke( new BasicStroke( 4 ) );
		} else {
			g2D.setStroke( new BasicStroke( 2 ) );
		}
		g2D.setColor( edge_color_ );

		// Draw main line
		final int grid_size = Options.getGridSize();
		final int offset = ( node_width_ - ( node_width_ / 2 ) ) * grid_size;
		final int source_x = n_from.x() * grid_size + offset;
		final int source_y = n_from.y() * grid_size + offset;
		final int dest_x = n_to.x() * grid_size + offset;
		final int dest_y = n_to.y() * grid_size + offset;
		g2D.drawLine( source_x, source_y, dest_x, dest_y );

		// Draw Arrow Heads
		// B is halfway point between two nodes
		final double Bx = grid_size * ( n_to.x() + n_from.x() ) / 2 + offset;
		final double By = grid_size * ( n_to.y() + n_from.y() ) / 2 + offset;
		final double arrow_length = grid_size * arrow_length_coeff_;
		Line2D line1 = new Line2D.Double( Bx, By, Bx + arrow_length,
				By - arrow_length );
		Line2D line2 = new Line2D.Double( Bx, By, Bx + arrow_length,
				By + arrow_length );

		final double theta = Math.atan2( source_y - By, source_x - Bx );
		AffineTransform at = AffineTransform.getRotateInstance( theta,
				line1.getX1(), line1.getY1() );

		g2D.draw( at.createTransformedShape( line1 ) );
		g2D.draw( at.createTransformedShape( line2 ) );
		box_for_edge_.put( e, new Box( (int) ( Bx - grid_size ),
				(int) ( By - grid_size ), grid_size * 2, grid_size * 2 ) );
	}

	public void drawGhostEdge( Graphics2D g2D, PreliminaryEdge ghost_edge ) {
		g2D.setStroke( new BasicStroke( 3 ) );
		g2D.setColor( ghost_edge_color_ );

		// Draw main line
		final int grid_size = Options.getGridSize();
		final Node n_from = ghost_edge.source_node;
		final int offset = ( node_width_ - ( node_width_ / 2 ) ) * grid_size;
		final int source_x = n_from.x() * grid_size + offset;
		final int source_y = n_from.y() * grid_size + offset;
		g2D.drawLine( source_x, source_y, ghost_edge.x, ghost_edge.y );
	}

	public HashMap< Node, utility.Box > boxForNode() {
		return box_for_node_;
	}

	public Map< Node, utility.Box > boxForNode_const() {
		return Collections.unmodifiableMap( box_for_node_ );
	}

	public void setBoxForNode( Node n, Box b ) {
		box_for_node_.put( n, b );
	}

	public HashMap< Edge, utility.Box > boxForEdge() {
		return box_for_edge_;
	}

	public Map< Edge, utility.Box > boxForEdge_const() {
		return Collections.unmodifiableMap( box_for_edge_ );
	}

	public void setBoxForEdge( Edge n, Box b ) {
		box_for_edge_.put( n, b );
	}

}
