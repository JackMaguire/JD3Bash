package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import exceptions.LoadFailureException;
import global_data.GlobalViewData;
import graph.Graph;

public class SaveLoadView extends JPanel
		implements ActionListener, PropertyChangeListener {

	/**
	 * Generated By Eclipse
	 */
	private static final long serialVersionUID = -9088179087599621175L;

	private final Graph graph_;

	private final JFileChooser file_chooser_ = new JFileChooser();
	private final JTextField save_path_field_ = new JTextField();
	private final JTextField save_filename_field_ = new JTextField(
			"pipeline.dat" );
	private final JButton save_button_ = new JButton( "Save" );

	public SaveLoadView( Graph g ) {
		graph_ = g;

		save_button_.addActionListener( this );

		file_chooser_.setApproveButtonText( "Load" );
		file_chooser_.addActionListener( this );

		file_chooser_.addPropertyChangeListener( this );
		save_path_field_.setText( file_chooser_.getCurrentDirectory().getName() );
		save_path_field_.setEditable( false );

		JPanel bottom_panel = new JPanel( new GridLayout( 1, 3 ) );
		bottom_panel.add( save_path_field_ );
		bottom_panel.add( save_filename_field_ );
		bottom_panel.add( save_button_ );

		setLayout( new BorderLayout() );
		add( file_chooser_, BorderLayout.CENTER );
		add( bottom_panel, BorderLayout.SOUTH );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {

		if( e.getSource() == save_button_ ) {
			final String filename = save_path_field_.getText()
					+ save_filename_field_.getText();
			final File f = new File( filename );

			// Prevent overwriting existing files
			if( f.exists() ) {
				final Object[] options = { "Yes, overwrite this file",
						"No, don't save" };
				int n = JOptionPane.showOptionDialog( new JFrame(),
						filename + " already exists. Overwrite this file?",
						"Overwrite?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[ 1 ] );

				if( n == 1 )
					return;
			}

			BufferedWriter out = null;
			try {
				out = new BufferedWriter( new FileWriter( f ) );
				graph_.saveSelfNodesAndEdges( out );
			}
			catch( IOException e1 ) {
				utility.PopupMessages.send(
						"Problem writing to file " + filename + "!\n" + e1.getMessage() );
			}
			finally {
				try {
					out.close();
				}
				catch( IOException e1 ) {
					utility.PopupMessages.send(
							"Problem closing file " + filename + "!\n" + e1.getMessage() );
				}
			}
			file_chooser_.rescanCurrentDirectory();
			return;
		} // save

		if( e.getActionCommand()
				.equals( javax.swing.JFileChooser.APPROVE_SELECTION ) ) {
			final String file_to_load_from = file_chooser_.getSelectedFile()
					.getPath();
			BufferedReader in = null;
			try {
				in = new BufferedReader( new FileReader( file_to_load_from ) );
				graph_.loadSelfNodesAndEdges( in );
				utility.PopupMessages.send( "Load Successful!" );
			}
			catch( FileNotFoundException e1 ) {
				utility.PopupMessages
						.send( "File " + file_to_load_from + " not found!" );
			}
			catch( IOException e1 ) {
				utility.PopupMessages.send( "Problem loading file " + file_to_load_from
						+ "!\n" + e1.getMessage() );
			}
			catch( LoadFailureException e1 ) {
				utility.PopupMessages.send( "Problem loading importing graph from "
						+ file_to_load_from + "!\n" + e1.getMessage() );
			}
			finally {
				if( in != null ) {
					try {
						in.close();
					}
					catch( IOException e1 ) {
						e1.printStackTrace();
					}
				}
			} // finally
		} // load
		GlobalViewData.top_panel.repaint();
	}

	@Override
	public void propertyChange( PropertyChangeEvent evt ) {
		save_path_field_
				.setText( file_chooser_.getCurrentDirectory().getPath() + "/" );
	}

}
