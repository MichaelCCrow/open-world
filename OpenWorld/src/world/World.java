package world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


public class World {
	
	List<Cell> cells;
	List<Cell> filledCells;
	List<Cell> borderCells;
	Mapper m;
	
	int res = 10;
	int offset = res+5;
	int dispx = Display.getWidth() / (res*2); 
	int dispy = Display.getHeight() / (res*2);
	
	String currentMap;
	
	public World() {
		System.out.println((dispx) + " " + (dispy));
		System.out.println(Display.getWidth() + " " + Display.getHeight());
		currentMap = "levelone";
		cells = new ArrayList<Cell>();
		filledCells = new ArrayList<Cell>();
		borderCells = new ArrayList<Cell>();
		m = new Mapper();
		m.readFromFile();
		buildGrid();
		// createMapBorder();
		setCellsFromFile();
	}
	
	public void setMap(String map) {
		this.currentMap = map;
	}
	
	
	public static class Cell implements Comparable<Cell> 
	{
		private int row, col;
		private World world;		
		private int res, offset, boundx, boundy;
		private boolean isFilled;
		private boolean clicked;
		private boolean filledCell;
		
		public Cell(int row, int col, World w) 
		{
			this.row = row;
			this.col = col;
			this.world = w;
			
			this.res = w.res;
			this.offset = w.offset;
			
			this.boundx = (row*(res*2))+offset;
			this.boundy = (col*(res*2))+offset;
			
			this.isFilled=false;
			this.clicked=false;
			this.filledCell=false;
			
			defineBounds(boundx, boundy);
		}
		
		private int lx, rx, by, ty;
		public void defineBounds(int x, int y) {
			this.lx = x-res; 
			this.by = y-res;
			this.rx = x+res;
			this.ty = y+res;
		}
		public int getRow() { return row; }
		public int getCol() { return col; }
		
		public int getLX() { return lx; }
		public int getRX() { return rx; }
		public int getBY() { return by; }
		public int getTY() { return ty; }
		
		public boolean isWithinBounds(int x, int y) {
			if (x > lx && x < rx && 
				y < ty && y > by) {
				System.out.println("ROW: " + row + " COL: " + col);
				return true;
			} else return false;
		}
		
		public void sc(float r, float g, float b) {
			GL11.glColor3f(r,g,b);
		}
		public void setColor(String color) {
			switch (color) {
				case "black": sc(0.0f, 0.0f, 0.0f); break;
				case "red": sc(1.0f, 0.0f, 0.0f); break;
				case "green": sc(0.0f, 1.0f, 0.0f); break;
				case "blue": sc(0.0f, 0.0f, 1.0f); break;
				case "white": sc(1.0f, 1.0f, 1.0f); break;
			}
		}
		
		public void draw() {
			// GL11.glColor3f(0.0f,0.0f,0.0f);
			if (clicked || filledCell) {
				setColor("blue");
				drawSquare(GL11.GL_QUADS);
				setColor("black");
			} else drawSquare(GL11.GL_LINE_LOOP);
			// if (clicked || filledCell) fill();
		}
		
		public void toggle(boolean b) { isFilled = b ? false : true; }
		
		public void fill() {
			// GL11.glColor3f(0.0f,0.2f,1.0f);
			setColor("blue");
			drawSquare(GL11.GL_QUADS);
			setColor("black");
		}
		public void drawMapBorder() {
			// GL11.glColor3f(0.0f, 0.0f, 0.0f);
			drawSquare(GL11.GL_QUADS);
		}
		public void drawSquare(int shape) {
			int x = boundx;
			int y = boundy;
			int g = res;
			GL11.glBegin(shape);
				GL11.glVertex2f(x-g, y-g);
				GL11.glVertex2f(x+g, y-g);
				GL11.glVertex2f(x+g, y+g);
				GL11.glVertex2f(x-g, y+g);
			GL11.glEnd();
		}
		
		public int compareTo(Cell other) {
			if (row < other.row) return -1;
        	if (row > other.row) return 1;
        	if (col < other.col) return -1;
        	if (col > other.col) return 1;
        	return 0;
		}
	}
	
	
	public void buildGrid() 
	{
		for (int i=0; i<dispx-1; i++) {
			for (int j=0; j<dispy-1; j++) {
				cells.add(new Cell(i, j, this));
			}
		}
	}
	public void setCellsFromFile() {
		int ln=0;
		Cell c;
		for (String l : m.getLines()) {
			for (int i=-1; (i = l.indexOf(".", i + 1)) != -1;) {
				borderCells.add(new Cell(i, ln, this));
			}
			for (int i=-1; (i = l.indexOf("/", i + 1)) != -1;) {
				c = new Cell(i, ln, this);
				c.filledCell=true;			
				filledCells.add(c);
			}
			ln++;
		}
	}
	/*public void createMapBorder() {
		int ln=0;
		for (String l : m.getLines()) {
			for (int i=-1; (i = l.indexOf(".", i + 1)) != -1;) {
				borderCells.add(new Cell(i, ln, this));
			}
			ln++;
		}
	}*/
	
	public void sc(float r, float g, float b) {
		GL11.glColor3f(r,g,b);
	}
	public void setColor(String color) {
		switch (color) {
			case "black": sc(0.0f, 0.0f, 0.0f); break;
			case "red": sc(1.0f, 0.0f, 0.0f); break;
			case "green": sc(0.0f, 1.0f, 0.0f); break;
			case "blue": sc(0.0f, 0.0f, 1.0f); break;
			case "white": sc(1.0f, 1.0f, 1.0f); break;
		}
	}
	
	public void clickCell(int x, int y) {
		setColor("blue");
		for (Cell cell : cells) {
			
			if (cell.isWithinBounds(x, y)) {
				if (!cell.clicked) cell.clicked=true;
				else cell.clicked=false;
				
				System.out.println("Mouse x: " + x + " Mouse y: " + y);
				System.out.println(cell.lx + " " + cell.rx + " " + cell.by + " " + cell.ty);
				System.out.println("x-:"+ (x-res) + " x+:" + (x+res) + " y-:" + (y-res) + " y+:" + (y+res));
			}
		}
	}
	
	
	public void checkMouse() {
		while (Mouse.next()) { 
			if (Mouse.getEventButtonState()) { 
				if (Mouse.isButtonDown(0)) {
					clickCell(Mouse.getX(), Display.getHeight()-Mouse.getY());
				}
			} else {}
		}
		// if (Mouse.isButtonDown(0)) checkCell(Mouse.getX(), Mouse.getY());
	}
	
	public void draw() 
	{
		setColor("black");
		for (Cell cell : cells) cell.draw();
		for (Cell cell : borderCells) cell.drawMapBorder();
		setColor("blue");
		for (Cell cell : filledCells) cell.draw();
		
    }
	
	public void update(float delta) throws IOException 
	{	
		checkMouse();
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) System.exit(0);
		
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
					setMap("levelone"); draw();
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
					setMap("leveltwo"); draw();
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
					setMap("levelthree");
				}
			} else {}
		}
	}
}
