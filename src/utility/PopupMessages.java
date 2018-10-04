package utility;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PopupMessages {

	public static void send( String s ) {
		JOptionPane.showMessageDialog( new JFrame(), s );
	}

}
