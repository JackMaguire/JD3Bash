package apps;

import javax.swing.JFrame;

public class NodeEditor {

	public static void main( String[] args ) {
		JFrame F = new JFrame( "RosettaNodeEditor" );
		F.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		F.setExtendedState( JFrame.MAXIMIZED_BOTH );
		// F.setUndecorated( true );
		// F.add( new MainView() );
		F.setVisible( true );
	}

}
