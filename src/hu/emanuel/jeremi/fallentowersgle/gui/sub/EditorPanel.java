package hu.emanuel.jeremi.fallentowersgle.gui.sub;

import hu.emanuel.jeremi.fallentowersgle.tile.MapData.*;
import hu.emanuel.jeremi.fallentowersgle.tile.TileType;

import static hu.emanuel.jeremi.fallentowersgle.common.Tile64.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import hu.emanuel.jeremi.fallentowersgle.common.Packs;
import hu.emanuel.jeremi.fallentowersgle.gui.EditorWindow;

import java.awt.Color;
import java.awt.Dimension;

public class EditorPanel extends JPanel implements MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1359214857451903978L;

	EditorWindow gui;
	
	public int w = 1000, h = 1000;
	
	private int gridSize;
	public int width, height;
	private final Color GRID_COLOR = Color.BLUE;
	private final Color BG_COLOR = Color.BLACK;
	
	/*
	 * Modes:
	 * 0000 0001 -> default
	 * 0000 0010 -> player pose	-- deactivated by clicking and placing a position
	 * 0000 0100 -> floor
	 */
	private byte MODE_FLAG = 0b0000_0001;
	
	public ArrayList<CellData> walls;
	public ArrayList<DoorData> doors;
	public ArrayList<SpriteData> sprites;
	public ArrayList<EnemyData> enemies;
	public ArrayList<ItemData> items;
	public ArrayList<MessageData> messages;
	
	// MouseDragged
	private int lastGridX, lastGridY;
	
	public EditorPanel(EditorWindow gui, int mapwidth, int mapheight) {
		this.gui = gui;
		this.setBackground(Color.black);
		this.gridSize = SIZE;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.w = mapwidth;
		this.h = mapheight;
		
		lastGridX = -1;
		lastGridY = -1;
		
		walls = new ArrayList<>();
		doors = new ArrayList<>();
		sprites = new ArrayList<>();
		enemies = new ArrayList<>();
		items = new ArrayList<>();
		messages = new ArrayList<>();
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("S"), "PlayerPose"
		);
		this.getActionMap().put("PlayerPose", new AbstractAction("PlayerPose") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5961636558938002855L;

			@Override
			public void actionPerformed(ActionEvent e) {
				togglePlayerPoseMode();
			}
		});
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("F"), "Floor"
		);
		this.getActionMap().put("Floor", new AbstractAction("Floor") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7009839466547179083L;

			@Override
			public void actionPerformed(ActionEvent e) {
				toggleFloorMode();
			}
		});
	}
	
	private void togglePlayerPoseMode() {
		if ( (MODE_FLAG & 0b0000_0010) == 0b0000_0000 ) {
			MODE_FLAG = 0b0000_0010;
			System.out.println("<<< PLAYER POSE MODE >>>");
		} else {
			MODE_FLAG >>= 1;
			System.out.println("<<< PLAYER POSE MODE DEACTIVATED >>>");
		}
	}
	
	private void toggleFloorMode() {
		if ( (MODE_FLAG & 0b0000_0100) == 0b0000_0000 ) {
			MODE_FLAG = 0b0000_0100;
			System.out.println("<<< FLOOR MODE >>>");
		} else {
			MODE_FLAG >>= 2;
			System.out.println("<<< FLOOR DEACTIVATED >>>");
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGrid(g);
		drawTiles(g);
	}
	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void setMinimumSize(Dimension dim) {
		super.setMinimumSize(dim);
		this.width = dim.width;
		this.height = dim.height;
	}
	
	@Override
	public void setPreferredSize(Dimension dim) {
		super.setPreferredSize(dim);
		this.width = dim.width;
		this.height = dim.height;
	}
	
	public void setDefaultBackgroundColor() {
		setBackground(BG_COLOR);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}

	// Drawing functions:
	public void drawGrid(Graphics g) {
		g.setColor(GRID_COLOR);
		for(int x = 0; x <= w; x += gridSize) {
			g.drawLine(x, 0, x, h);
		}
		for(int y = 0; y <= h; y += gridSize) {
			g.drawLine(0, y, w, y);
		}
	}
	
	public void drawTiles(Graphics g) {
		for(Data t : walls) {
			//System.out.println("Drawing tile at x: "+ (t.x << SIZE_LOG) + ", y: " + (t.y << SIZE_LOG) + ", tile: " + this.gui.chosenTile );
			if(t.x < width && t.y < height)
				g.drawImage(gui.getTextureLibrary().getTexture(gui.chosenTexturePack, t.texture()), t.x << SIZE_LOG, t.y << SIZE_LOG, null);
		}
		for(Data t : doors) {
			//System.out.println("Drawing tile at x: "+ (t.x << SIZE_LOG) + ", y: " + (t.y << SIZE_LOG) + ", tile: " + this.gui.chosenTile );
			if(t.x < width && t.y < height)
				g.drawImage(gui.getTextureLibrary().getTexture(gui.chosenTexturePack, t.texture()), t.x << SIZE_LOG, t.y << SIZE_LOG, null);
		}
		for(Data t : sprites) {
			//System.out.println("Drawing tile at x: "+ (t.x << SIZE_LOG) + ", y: " + (t.y << SIZE_LOG) + ", tile: " + this.gui.chosenTile );
			if(t.x < width && t.y < height)
				g.drawImage(gui.getTextureLibrary().getTexture(gui.chosenTexturePack, t.texture()), t.x << SIZE_LOG, t.y << SIZE_LOG, null);
		}
		for(Data t : enemies) {
			//System.out.println("Drawing tile at x: "+ (t.x << SIZE_LOG) + ", y: " + (t.y << SIZE_LOG) + ", tile: " + this.gui.chosenTile );
			if(t.x < width && t.y < height)
				g.drawImage(gui.getTextureLibrary().getTexture(Packs.enemy.ordinal(), t.texture()), t.x << SIZE_LOG, t.y << SIZE_LOG, null);
		}
		for(Data t : items) {
			//System.out.println("Drawing tile at x: "+ (t.x << SIZE_LOG) + ", y: " + (t.y << SIZE_LOG) + ", tile: " + this.gui.chosenTile );
			if(t.x < width && t.y < height)
				g.drawImage(gui.getTextureLibrary().getTexture(Packs.items.ordinal(), t.texture()), t.x << SIZE_LOG, t.y << SIZE_LOG, null);
		}
		for(Data t : messages) {
			//System.out.println("Drawing tile at x: "+ (t.x << SIZE_LOG) + ", y: " + (t.y << SIZE_LOG) + ", tile: " + this.gui.chosenTile );
			if(t.x < width && t.y < height) {
				g.setColor(new Color(0, 0, 255, 100));
				g.fillRect(t.x << SIZE_LOG, t.y << SIZE_LOG, SIZE, SIZE);
				g.setColor(Color.WHITE);
				g.drawString("msg", t.x << SIZE_LOG, t.y << SIZE_LOG);
			}			
		}
	}
	
	private void placeTile(TileType t) {
		//System.out.println("Texture: " + gui.chosenTile + "\nPack: " + gui.choosenPack);
		switch(t) {
		case wall:
			// x y fw height inside virtual storey //
			if (MODE_FLAG == 0b0000_0100) {
				walls.add(
					new CellData(
							lastGridX,
							lastGridY,
							gui.chosenTile,
							gui.ta.inside
				));
			} else {
				walls.add(
					new CellData(
							lastGridX,
							lastGridY,
							gui.chosenTile,
							gui.ta.inside
				));
			}			
			return;
		case door:
			// x y closed opened id value
			doors.add(
				new DoorData(
						lastGridX,
						lastGridY,
						gui.chosenDoorClosed,
						gui.chosenDoorOpened,
						gui.ta.value
			));
			return;
		case item:
			// x y tile id value type //
			items.add(
				new ItemData(
						lastGridX,
						lastGridY,
						gui.ta.id(),
						gui.ta.value,
						gui.chosenItemType.ordinal()
			));
			return;
		case sprite:
			// x y tile id //
			sprites.add(
				new SpriteData(
						lastGridX,
						lastGridY,
						gui.chosenTile,
						gui.ta.id()
			));
			return;
		case enemy:
			// x y tile id type //
			enemies.add(
				new EnemyData(
						lastGridX, 
						lastGridY,
						gui.ta.id(),
						gui.chosenEnemyType.ordinal()
			));
			return;
		case message:
			// x y time id sendercode stringCode //
			messages.add(
				new MessageData(
						lastGridX,
						lastGridY,
						gui.ta.time,						
						gui.ta.id(),
						gui.ta.sendercose,
						gui.ta.msgcode
			));
			return;
		default:
			break;
		}
	}
	
	private void removeTile(TileType ty) {
		Data temp = null;
		switch(ty) {
		case wall:
			for(Data t : walls) {
				if (lastGridX == t.x && lastGridY == t.y) {
					temp = t;
				}
			}
			if (temp != null) {
				walls.remove(temp);
			}
		case door:
			for(Data t : doors) {
				if (lastGridX == t.x && lastGridY == t.y) {
					temp = t;
				}
			}
			if (temp != null) {
				walls.remove(temp);
			}
			return;
		case item:
			for(Data t : items) {
				if (lastGridX == t.x && lastGridY == t.y) {
					temp = t;
				}
			}
			if (temp != null) {
				walls.remove(temp);
			}
			return;
		case sprite:
			for(Data t : sprites) {
				if (lastGridX == t.x && lastGridY == t.y) {
					temp = t;
				}
			}
			if (temp != null) {
				walls.remove(temp);
			}
			return;
		case enemy:
			for(Data t : enemies) {
				if (lastGridX == t.x && lastGridY == t.y) {
					temp = t;
				}
			}
			if (temp != null) {
				walls.remove(temp);
			}
			return;
		case message:
			for(Data t : messages) {
				if (lastGridX == t.x && lastGridY == t.y) {
					temp = t;
				}
			}
			if (temp != null) {
				walls.remove(temp);
			}
			return;
		default:
			break;
		}
	}
	
	public void setMapSize(int w, int h) {
		this.w = w << SIZE_LOG;
		this.h = h << SIZE_LOG;
		width = w;
		height = h;
		repaint();
	}
	
	/////////////////////////////// MOUSE EVENT HANDLING /////////////////////////////////////
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = (int) Math.floor(e.getX() / SIZE);
		int y = (int) Math.floor(e.getY() / SIZE);
		
		if ( MODE_FLAG == 0b0000_0010 ) {
			togglePlayerPoseMode();
			gui.setPlayerPose(x,y);
		}		
		else if (e.getButton() == MouseEvent.BUTTON1 && ( x != lastGridX || y != lastGridY ) ) {
			lastGridX = x;
			lastGridY = y;
				
			placeTile(this.gui.chosenType);
		} 
		else if (e.getButton() == MouseEvent.BUTTON3 && ( x != lastGridX || y != lastGridY ) ) {
			lastGridX = x;
			lastGridY = y;
			
			removeTile(this.gui.chosenType);
		}
		
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = (int) Math.floor(e.getX() / SIZE);
		int y = (int) Math.floor(e.getY() / SIZE);
		
		if (SwingUtilities.isLeftMouseButton(e) && ( x != lastGridX || y != lastGridY ) ) {
			lastGridX = x;
			lastGridY = y;
			
			placeTile(this.gui.chosenType);
		} 
		else if (SwingUtilities.isRightMouseButton(e) && ( x != lastGridX || y != lastGridY ) ) {
			lastGridX = x;
			lastGridY = y;
			
			removeTile(this.gui.chosenType);
		}
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	//////////////////////////////////////////////////////////////////////////////////////////
}
