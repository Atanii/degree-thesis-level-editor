package hu.emanuel.jeremi.fallentowersgle.tile;

public class MapData {
	
	public static int tex_pack = 0;
	
	static TextureLibrary tl;
	
	static public class Data {		
		public int x, y;
		
		public Data(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int texture() {
			return 0;
		}
	}
	
	static public class CellData extends Data {
		public int inside, ceiling, floor, wall;
		public int fw;
		
		// x y fw height inside //
		public CellData(int x, int y, int fw, int inside
		) {
			super(x, y);
			this.inside = inside; 
			this.floor = fw;
			this.wall = fw;
			this.fw = fw;
		}
		
		@Override
		public int texture() {
			return fw;
		}
	}
	
	static public class DoorData extends Data {
		public int tex, opened, closed, code;
		
		// x y closed opened id value //
		public DoorData(int x, int y, int closed, int opened, int code
		) {
			super(x, y);
			this.opened = opened;
			this.tex = this.closed = closed;
			this.code = code;
		}
		
		@Override
		public int texture() {
			return closed;
		}
	}
	
	static public class SpriteData extends Data {
		public int tile, id;
		
		// x y tile id //
		public SpriteData(int x, int y, int tile, int id) {
			super(x, y);
			this.tile = tile;
			this.id = id;
		}
		
		@Override
		public int texture() {
			return tile;
		}
	}
	
	static public class EnemyData extends Data {
		public int id, tile, type;
		
		// x y tile id type //
		public EnemyData(int x, int y, int id, int type
		) {
			super(x, y);
			this.id = id;
			this.type = type;
			this.tile = tile;
		}
		
		@Override
		public int texture() {
			return type;
		}
	}
	
	static public class ItemData extends Data {
		public int type, id, tile, value;
		
		// x y tile id value type //
		public ItemData(int x, int y,
						int id, int value, int type				
		) {
			super(x, y);
			this.id = id;
			this.value = value;
			this.type = type;
			this.tile = tile;
		}
		
		@Override
		public int texture() {
			return type;
		}
	}
	
	static public class MessageData extends Data {
		public int time, id, senderCode, stringCode;
		
		// x y time id sendercode stringCode //
		public MessageData(int x, int y, int time, int id, 
				int senderCode, int stringCode
		) {
			super(x, y);
			this.id = id;
			this.time = time;
			this.senderCode = senderCode;
			this.stringCode = stringCode;
		}
	}
	
	public MapData(TextureLibrary tl) {
		this.tl = tl;
	}
}
