package utility;

public class Box {
	public int x_begin;
	public int y_begin;
	public int x_end;
	public int y_end;

	public Box( int x, int y, int width, int height ) {
		x_begin = x;
		y_begin = y;
		x_end = x + width;
		y_end = y + height;
	}

	public boolean pointIsInBox( int x, int y ) {
		return ( x >= x_begin ) && ( x < x_end ) && ( y >= y_begin )
				&& ( y < y_end );
	}
}
