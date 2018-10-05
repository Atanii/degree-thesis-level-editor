package hu.emanuel.jeremi.fallentowersgle.saveload;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import hu.emanuel.jeremi.fallentowersgle.gui.EditorWindow;
import hu.emanuel.jeremi.fallentowersgle.tile.MapData.*;

/**
 * 
 * This class loads the levels from the .tow files.
 * 
 * @author Jeremi
 *
 */
public final class TowHandler {

	EditorWindow gui;
	
	public TowHandler(EditorWindow gui) {
		this.gui = gui;
	}
	
	//private String PATH = "C:\\test.tow";
	
	private class BlankMapException extends Exception {}
	
	public class LevelData {
		public int playerX, playerY;
		public int mapW, mapH;
		public int pack;
		public ArrayList<CellData> walls = new ArrayList<>();
		public ArrayList<DoorData> doors = new ArrayList<>();
		public ArrayList<SpriteData> sprites = new ArrayList<>();
		public ArrayList<EnemyData> enemies = new ArrayList<>();
		public ArrayList<ItemData> items = new ArrayList<>();
		public ArrayList<MessageData> messages = new ArrayList<>();
	}
	
	public void SaveLevel(final String PATH) {
		
		try(
			FileWriter fw = new FileWriter(PATH);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
		) {
			System.out.println("<<< SAVING: " + PATH + " >>>");
			
			ArrayList<CellData> walls = gui.getGridPane().walls;
			ArrayList<DoorData> doors = gui.getGridPane().doors;
			ArrayList<SpriteData> sprites = gui.getGridPane().sprites;
			ArrayList<EnemyData> enemies = gui.getGridPane().enemies;
			ArrayList<ItemData> items = gui.getGridPane().items;
			ArrayList<MessageData> messages = gui.getGridPane().messages;
			
			pw.println("MAP_VERSION");
			
			// MAP VERSION
			pw.print(Integer.toString(3) + ';');
			
			pw.print('\n');
			pw.println("PLAYER_DATA");
			
			// PLAYER DATA
			pw.print(Integer.toString(gui.playerX) + ',' + Integer.toString(gui.playerY) + ';');
			
			pw.print('\n');
			pw.println("USED_TEXTURE_PACK");
			
			// USED TEXTURE PACK
			pw.print(Integer.toString(gui.chosenTexturePack) + ';');
			
			pw.print('\n');
			pw.println("MAP_SIZE");
			
			// MAP SIZE (in gridcount)
			pw.print(Integer.toString(gui.mapW) + ',' + Integer.toString(gui.mapH) + ';');
			
			pw.print('\n');
			pw.println("CEILING");
			
			// CEILING
			pw.print(Integer.toString(gui.chosenCeiling) + ';');
			
			pw.print('\n');
			pw.println("MAP_CELLS");
			
			// MAP CELLS
			// x y fw height inside virtual storey //
			for(CellData d : walls) {
				pw.print(
					Integer.toString(d.x) + ',' + 
					Integer.toString(d.y) + ',' +
					Integer.toString(d.fw) + ',' +
					Integer.toString(d.inside) + ','
				);
			}
			
			pw.print('\n');
			pw.println("DOORS");
			
			// DOORS
			// x y closed opened id value //
			pw.print(Integer.toString(doors.size()) + ';');
			System.out.println("Doors: " + doors.size());
			if (doors.size() > 0) {
				pw.print('\n');
				
				pw.print(Integer.toString(gui.chosenDoorClosed) + ',');
				pw.print(Integer.toString(gui.chosenDoorOpened) + ';');
				
				for(DoorData d : doors) {
					pw.print(
						Integer.toString(d.x) + ',' + 
						Integer.toString(d.y) + ',' +
						Integer.toString(d.id) + ',' +
						Integer.toString(d.value) + ';'
					);
				}
			}
			
			pw.print('\n');
			pw.println("SPRITES");
			
			// SPRITES
			// x y tile id //
			pw.print(Integer.toString(sprites.size()) + ';');
			pw.print('\n');
			for(SpriteData d : sprites) {
				pw.print(
					Integer.toString(d.x) + ',' + 
					Integer.toString(d.y) + ',' +
					Integer.toString(d.texture()) + ',' +
					Integer.toString(d.id) + ';'
				);
			}
			
			pw.print('\n');
			pw.println("ENEMIES");
			
			// ENEMIES
			// x y id type //
			pw.print(Integer.toString(enemies.size()) + ';');
			pw.print('\n');
			for(EnemyData d : enemies) {
				pw.print(
					Integer.toString(d.x) + ',' + 
					Integer.toString(d.y) + ',' +
					Integer.toString(d.id) + ',' +
					Integer.toString(d.type) + ';'
				);
			}
			
			pw.print('\n');
			pw.println("ITEMS");
			
			// ITEMS
			// x y _tile_ id value type //
			pw.print(Integer.toString(items.size()) + ';');
			pw.print('\n');
			for(ItemData d : items) {
				pw.print(
					Integer.toString(d.x) + ',' + 
					Integer.toString(d.y) + ',' +
					Integer.toString(d.id) + ',' +
					Integer.toString(d.value) + ',' +
					Integer.toString(d.type) + ';'
				);
			}
			
			pw.print('\n');
			pw.println("MESSAGES");
			
			// MESSAGES
			// x y time id sendercode stringCode //
			pw.print(Integer.toString(messages.size()) + ';');
			pw.print('\n');
			for(MessageData d : messages) {
				pw.print(
					Integer.toString(d.x) + ',' + 
					Integer.toString(d.y) + ',' +
					Integer.toString(d.time) + ',' +
					Integer.toString(d.id) + ',' +
					Integer.toString(d.senderCode) + ',' +
					Integer.toString(d.stringCode) + ';'
				);
			}
			
			System.out.println("<<< SAVED: " + PATH + " >>>");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public LevelData LoadLevel(final String PATH) {
		
		LevelData d = new LevelData();
		
		try(
			Scanner sc = new Scanner(Paths.get(PATH));
		) {
			System.out.println("<<< LOADING: " + PATH + " >>>");
			
			int current = 0;
			StringTokenizer tokenizer = null;
			sc.useDelimiter(";");
			
			sc.nextLine();
			
			// MAP VERSION
			tokenizer = new StringTokenizer(sc.next(), ",");
			System.out.println("<<< Map version: v" + Integer.parseInt(tokenizer.nextToken()) + " >>>");
			
			sc.nextLine();
			sc.nextLine();
						
			// PLAYER DATA
			tokenizer = new StringTokenizer(sc.next(), ",");
			d.playerX = Integer.parseInt(tokenizer.nextToken());
			d.playerY = Integer.parseInt(tokenizer.nextToken());
			System.out.println("<<< Player X: " + d.playerX + " | " + "Y: " + d.playerY + " >>>");
			
			sc.nextLine();
			sc.nextLine();
			
			// USED TEXTURE PACK
			tokenizer = new StringTokenizer(sc.next(), ",");
			d.pack = Integer.parseInt(tokenizer.nextToken());
			System.out.println("<<< Texture pack: " + d.pack + " >>>");
			
			sc.nextLine();
			sc.nextLine();
			
			// MAP SIZE (in gridcount)
			tokenizer = new StringTokenizer(sc.next(), ",");
			d.mapW = Integer.parseInt(tokenizer.nextToken());
			d.mapH = Integer.parseInt(tokenizer.nextToken());
			System.out.println("<<< Width: " + d.mapW + " | " + "Height: " + d.mapH + " >>>");
			
			if (d.mapW * d.mapH == 0) {
				throw new BlankMapException();
			}
			
			sc.nextLine();
			sc.nextLine();
			
			// CEILING
			tokenizer = new StringTokenizer(sc.next(), ",");
			gui.chosenCeiling = Integer.parseInt(tokenizer.nextToken());			
			System.out.println("<<< Ceiling: " + gui.chosenCeiling + " >>>");
			
			sc.nextLine();
			sc.nextLine();
			
			// MAP CELLS
			// x y inside ceiling floor wall height virtual storey //
			System.out.print("<<<");
			for(int i = 0; i < d.mapH * d.mapW; i++) {
				tokenizer = new StringTokenizer(sc.next(), ",");
				
				d.walls.add( new CellData(
					Integer.parseInt(tokenizer.nextToken()),
					Integer.parseInt(tokenizer.nextToken()),
					Integer.parseInt(tokenizer.nextToken()),
					Integer.parseInt(tokenizer.nextToken())
				));
				
				System.out.print(" " + i);
			}
			System.out.println(" >>>");
			
			sc.nextLine();
			sc.nextLine();
			
			// DOORS
			// x y closed opened id value //
			tokenizer = new StringTokenizer(sc.next(), ",");
			current = Integer.parseInt(tokenizer.nextToken());
			System.out.println("<<< Doors: " + current + " >>>");
			
			if (current > 0) {
				sc.nextLine();
				
				gui.chosenDoorClosed = Integer.parseInt(tokenizer.nextToken());
				gui.chosenDoorOpened = Integer.parseInt(tokenizer.nextToken());
				
				for(int i = 0; i < current; i++) {
					tokenizer = new StringTokenizer(sc.next(), ",");
					
					d.doors.add( new DoorData(
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken()),
						gui.chosenDoorClosed,
						gui.chosenDoorOpened,
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken())
					));
				}
			}
			
			sc.nextLine();
			sc.nextLine();
			
			// SPRITES
			// number; texture-id, x, y, id //
			tokenizer = new StringTokenizer(sc.next(), ",");
			current = Integer.parseInt(tokenizer.nextToken());
			if (current > 0) {
				sc.nextLine();
				for(int i = 0; i < current; i++) {
					tokenizer = new StringTokenizer(sc.next(), ",");
					
					d.sprites.add( new SpriteData(
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken())
					));
				}
			}
			
			sc.nextLine();
			sc.nextLine();
			
			// ENEMIES
			// x y tile id type //
			tokenizer = new StringTokenizer(sc.next(), ",");
			current = Integer.parseInt(tokenizer.nextToken());
			if (current > 0) {
				sc.nextLine();
				for(int i = 0; i < current; i++) {
					tokenizer = new StringTokenizer(sc.next(), ",");
					
					d.enemies.add( new EnemyData(
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken())
					));
				}
			}
			
			sc.nextLine();
			sc.nextLine();
			
			// ITEMS
			// x y tile id value type //
			tokenizer = new StringTokenizer(sc.next(), ",");
			current = Integer.parseInt(tokenizer.nextToken());
			if (current > 0) {
				sc.nextLine();
				for(int i = 0; i < current; i++) {
					tokenizer = new StringTokenizer(sc.next(), ",");
					
					d.items.add( new ItemData(
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken())
					));
				}
			}
			
			sc.nextLine();
			sc.nextLine();
			
			// MESSAGES
			// x y time id sendercode stringCode //
			tokenizer = new StringTokenizer(sc.next(), ",");
			current = Integer.parseInt(tokenizer.nextToken());
			if (current > 0) {
				sc.nextLine();
				for(int i = 0; i < current; i++) {
					tokenizer = new StringTokenizer(sc.next(), ",");
					
					d.messages.add( new MessageData(
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken()),
						Integer.parseInt(tokenizer.nextToken())
					));
				}
			}
			
			System.out.println("<<< LOADED: " + PATH + " >>>");
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BlankMapException e) {
			JOptionPane.showMessageDialog(null, "Invalid map file! There are no map cells to load.");
			return null;
		}
		
		return d;
		
	}

}
