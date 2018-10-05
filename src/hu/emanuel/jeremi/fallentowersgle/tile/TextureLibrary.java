package hu.emanuel.jeremi.fallentowersgle.tile;

import static hu.emanuel.jeremi.fallentowersgle.common.Tile64.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class TextureLibrary {
	/////////////////////////////// VARIABLES, CONSTS... /////////////////////////////////////
	public static final boolean DEBUG = true;
	public static final int startTextureId = START_ID;
	public int id = 0;
	
	public class Sheet {
		public BufferedImage s;
		public int w, h, l;
		
		public Sheet (BufferedImage s, int w, int h) {
			this.s = s;
			this.w = w;
			this.h = h;
			this.l = w * h;
		}
	}	
	
	public Sheet sprites;
	public Sheet items;
	public Sheet[] texturePacks;
	public Sheet[] tiles;
	
	public int length;
	//////////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////// CONSTRUCTORS /////////////////////////////////////////////
	public TextureLibrary(String spritesheet_filename, String itemsheet_filename, String...texturepacks) {
		sprites = loadSpriteSheet(spritesheet_filename);
		items = loadItemSheet(itemsheet_filename);
		texturePacks = loadTexturePacks(texturepacks);
		
		tiles = new Sheet[2 + texturePacks.length];
				
		tiles[0] = items;
		tiles[1] = sprites;
		for(int i = 0; i < texturePacks.length; i++) {
			tiles[i+2] = texturePacks[i];
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////

	/////////////////////////////// SHEET PROCESSING /////////////////////////////////////////
	private final Sheet loadItemSheet(String filename) {
		Sheet sheet = null;
		
		try {
			// getting texture atlas
			URL url = getClass().getResource("/res/"+filename);
			sheet = new Sheet(ImageIO.read(url).getSubimage(0, 2 * SIZE, ImageIO.read(url).getWidth(), SIZE),
					ImageIO.read(url).getWidth() >> SIZE_LOG, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sheet;
	}
	
	private final Sheet loadSpriteSheet(String filename) {
		Sheet sheet = null;
		
		try {
			// getting texture atlas
			URL url = getClass().getResource("/res/"+filename);
			sheet = new Sheet(ImageIO.read(url).getSubimage(0, 0, SIZE, ImageIO.read(url).getHeight()), 
					1, ImageIO.read(url).getHeight() >> SIZE_LOG);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sheet;
	}
	
	private final Sheet[] loadTexturePacks(String...filenames) {
		Sheet[] sheets = new Sheet[filenames.length];
		
		try {
			for (int i = 0; i < filenames.length; i++) {
				// getting texture atlas
				URL url = getClass().getResource("/res/"+filenames[i]);
				sheets[i] = new Sheet(ImageIO.read(url), ImageIO.read(url).getWidth() >> SIZE_LOG, ImageIO.read(url).getHeight() >> SIZE_LOG);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sheets;
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////// IMAGE ////////////////////////////////////////////////////
//	public BufferedImage getTexture(int pack, int id) {
//		int w = this.texturePacks[pack].s.getWidth() >> SIZE_LOG;
//		int h = this.texturePacks[pack].s.getHeight() >> SIZE_LOG;
//		
//		int col = ( id % w ) << SIZE_LOG;
//		int row = (int) Math.floor(id / h) << SIZE_LOG;
//		
//		length = col * row;
//		
//		//System.out.println("id: " + id + "\ncol: " + col + "\nrow: " + row);
//		
//		return this.texturePacks[pack].s.getSubimage(col, row, SIZE, SIZE);
//	}
	
	public BufferedImage getTexture(int pack, int id) {
		//System.out.println("Pack: " + pack + "\nId: " + id);
		
		int w = this.tiles[pack].s.getWidth() >> SIZE_LOG;
		
		int col = ( id % w ) << SIZE_LOG;
		int row = (int) Math.floor(id / w) << SIZE_LOG;
		
		length = col * row;
		
		//System.out.println("\nw: " + w + "\nh: " + h + "\nid: " + id + "\ncol: " + col + "\nrow: " + row);
		
		return this.tiles[pack].s.getSubimage(col, row, SIZE, SIZE);
	}
	
	public BufferedImage getItem(int id) {
		int w = this.items.s.getWidth() >> SIZE_LOG;
		int h = this.items.s.getHeight() >> SIZE_LOG;
		
		int col = ( w % (id+1) ) << SIZE_LOG;
		int row = (int) Math.floor(id / h) << SIZE_LOG;
		
		return this.items.s.getSubimage(col, row, SIZE, SIZE);
	}
	
	public BufferedImage getSprite(int id) {
		int w = this.sprites.s.getWidth() >> SIZE_LOG;
		int h = this.sprites.s.getHeight() >> SIZE_LOG;
		
		int col = ( w % (id+1) ) << SIZE_LOG;
		int row = (int) Math.floor(id / h) << SIZE_LOG;
		
		return this.sprites.s.getSubimage(col, row, SIZE, SIZE);
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	
	/////////////////////////////// MISC. ////////////////////////////////////////////////////
	// TODO: Need here anything?
	//////////////////////////////////////////////////////////////////////////////////////////	
}
