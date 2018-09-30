package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import controllers.GraphController;
import graph.*;
import utility.Box;

public class GraphView extends JPanel {

	/**
	 * Generated By Eclipse
	 */
	private static final long serialVersionUID = -208575025484602711L;

	private final Graph graph_;

	private int grid_size_ = 10;
	private int node_width_ = 3;
	private boolean view_grid_ = true;
	private final HashMap< Node, Box > box_for_node_;

	// COLORS:
	private final Color background_color_ = new Color( 220, 220, 220 );
	private final Color grid_color_ = new Color( 180, 180, 180 );
	private final Color edge_color_ = Color.BLACK;

	// Edge Geometry:
	private final double arrow_length_coeff_ = 1;

	public GraphView( Graph g ) {
		graph_ = g;
		box_for_node_ = new HashMap< Node, Box >();
		// this.requestFocus();
	}

	public void paint( Graphics g ) {
		Graphics2D g2D = (Graphics2D) g;
		drawBackground( g2D );
		drawEdge( g2D, graph_.getNode( 0 ), graph_.getNode( 1 ) );
		drawEdge( g2D, graph_.getNode( 1 ), graph_.getNode( 2 ) );
		drawNodes( g2D );
	}

	public void drawBackground( Graphics2D g2D ) {
		g2D.setColor( background_color_ );
		g2D.fillRect( 0, 0, getWidth(), getHeight() );
		if( view_grid_ ) {
			g2D.setColor( grid_color_ );
			for( int x = -1; x < getWidth(); x += 2 * grid_size_ ) {
				g2D.drawLine( x, 0, x, getHeight() );
			}
			for( int y = -1; y < getHeight(); y += 2 * grid_size_ ) {
				g2D.drawLine( 0, y, getWidth(), y );
			}
		}
	}

	public void drawNodes( Graphics2D g2D ) {
		for( Node n : graph_.allNodes_const() ) {
			int x = n.x() * grid_size_ + ( grid_size_ / 2 );
			int y = n.y() * grid_size_ + ( grid_size_ / 2 );
			int diameter = grid_size_ * node_width_;

			if( n == graph_.selectedNode() ) {
				g2D.setColor( Color.black );
				int selection_width = grid_size_ / 2;
				int sx = x - selection_width;
				int sy = y - selection_width;
				int sdiameter = diameter + 2 * selection_width;
				g2D.fillOval( sx, sy, sdiameter, sdiameter );
			}

			g2D.setColor( n.color() );
			g2D.fillOval( x, y, diameter, diameter );
			setBoxForNode( n, new Box( x, y, diameter, diameter ) );
		}
	}

	public int getClosestPointForPoint( int point ) {
		final int offset = ( node_width_ - ( node_width_ / 2 ) ) * grid_size_;
		final double new_point = (point - offset) / ((double) grid_size_);
		return (int) Math.rint(new_point);
	}
	
	
	
	public void drawEdge( Graphics2D g2D, Node n_from, Node n_to ) {
		g2D.setStroke( new BasicStroke( 3 ) );
		g2D.setColor( edge_color_ );

		// Draw main line
		final int offset = ( node_width_ - ( node_width_ / 2 ) ) * grid_size_;
		final int source_x = n_from.x() * grid_size_ + offset;
		final int source_y = n_from.y() * grid_size_ + offset;
		final int dest_x = n_to.x() * grid_size_ + offset;
		final int dest_y = n_to.y() * grid_size_ + offset;
		g2D.drawLine( source_x, source_y, dest_x, dest_y );

		// Draw Arrow Heads
		// B is halfway point between two nodes
		final double Bx = grid_size_ * ( n_to.x() + n_from.x() ) / 2 + offset;
		final double By = grid_size_ * ( n_to.y() + n_from.y() ) / 2 + offset;
		final double arrow_length = grid_size_ * arrow_length_coeff_;
		Line2D line1 = new Line2D.Double( Bx, By, Bx + arrow_length, By - arrow_length );
		Line2D line2 = new Line2D.Double( Bx, By, Bx + arrow_length, By + arrow_length );

		final double theta = Math.atan2( source_y - By, source_x - Bx );
		AffineTransform at = AffineTransform.getRotateInstance( theta, line1.getX1(), line1.getY1() );

		g2D.draw( at.createTransformedShape( line1 ) );
		g2D.draw( at.createTransformedShape( line2 ) );
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

}
