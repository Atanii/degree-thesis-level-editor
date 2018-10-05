package hu.emanuel.jeremi.fallentowersgle.tile;

public final class Tile64 {
	
	private Tile64() {}
	
	public static final int SIZE = 64;
	public static final int SIZE_LOG = 6;
	public static final int PLAYERHEIGHT = SIZE>>1;
	
	
	/*
	 * 0 - 9 -- special
	 * 
	 */
	
	public static final int INSIDE = 0;
	public static final int OUTSIDE = 8;
	
	public static final int DOOR_CLOSED = 5;
	public static final int DOOR_OPENED = 7;

	public static final int ICONSIZE = SIZE>>1;
	
	public static final int START_TEXTURE_ID = 10;
	public static final int START_ID = 0;
	
}
