package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.lwjgl.opengl.Display;

import world.Mapper;

public class Builder {
	
	public static void main(String[] args) {
		
		PrintWriter writer = null;
		
		try {
			// writer = new PrintWriter("res/gridData.txt", "UTF-8");
			// writeGridData(writer);
			writer = new PrintWriter("res/maps/map.txt", "UTF-8");
			writeDimensions(writer);
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} finally {
			writer.close();
		}
		
		/*if (writer != null) {
			String pair="";
			for(int i=0; i<10; i++) {
				for (int j=0; j<10; j++) {
					pair = i + "," + j + "|";
					if (i==9 && j==9) pair = pair.replace("|", "");
					writer.print(pair);
				}
				writer.println();
			}
			writer.close();
		}*/
		
	} // end main
	
	
	public static void writeDimensions(PrintWriter writer) {
		if (writer != null) {
			int dispx = 1024 / (10*2);
			int dispy = 768 / (10*2);
//			int dispx = (1024 / 25)*2;
//			int dispy = (768 / 25)*2;
			
			for(int i=0; i<dispy-1; i++) {
				if (i==0 || i==dispy-2) {	
					for (int j=0; j<dispx-1; j++) {
						writer.print(".");
					}
				} else {
					for (int j=0; j<dispx-1; j++) {
						if (j==0 || j==dispx-2) writer.print(".");
						else writer.print(" ");
					}
				}
				if (i!=dispy-2) writer.println();
			}
			writer.close();
		}
	}
	
	
	public static void writeGridData(PrintWriter writer) {
		if (writer != null) {
			String pair="";
			for(int i=0; i<10; i++) {
				for (int j=0; j<10; j++) {
					pair = i + "," + j + "|";
					if (i==9 && j==9) pair = pair.replace("|", "");
					writer.print(pair);
				}
				writer.println();
			}
			writer.close();
		}
	}
	
	
public static void readFromFile() {
		
		// File f = new File("res/gridData.txt"); 
		/*FileReader file = null;
		try {
			file = new FileReader("res/gridData.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
		
		
		Scanner in = null;
		String raw = null;
		try {
			in = new Scanner( new File("res/gridData.txt") );
			raw = in.useDelimiter("\\A").next();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
		
		if (raw==null) System.exit(0);
		
			
		String[] pieces = raw.split("[|]");
		// int[] xy = new int[2];
		int len = pieces.length;
		int l = (int) Math.sqrt(len);
		// int[][] data = new int[l][l];
		
		int x, y;
		String t;
		int index=0;
		
		/*for (int i=0; i<l; i++) {
			for (int j=0; j<l; j++) {
				// for (int k=0; k<2;k++) Integer.parseInt(pieces[index].split("{,]")[k]);
				x = Integer.parseInt(pieces[index].split("[,]")[0]);
				y = Integer.parseInt(pieces[index].split("[,]")[1]);
				index++;
			}
		}*/
		
		/*for (String s : pieces) {
			x = Integer.parseInt(s.split("[,]")[0]);
			y = Integer.parseInt(s.split("[,]")[1]);
		}*/
		
	}
	
	
	
}
