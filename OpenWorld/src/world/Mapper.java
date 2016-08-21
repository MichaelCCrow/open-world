package world;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class Mapper {
	
	List<String> lines = new ArrayList<String>();
	Properties prop;
	
	private static int dim=0;
	private void setDim(int dim) { Mapper.dim = dim; }
	public static int getDim() { return dim; }
	
	public void readFromFile() {
		
		Scanner in = null;
		prop = new Properties();
		InputStream input = null;
		int c=0;
		
		try {
			
			input = new FileInputStream("res/maps.properties");
			prop.load(input);
			
			List<String> maps = loadMaps(new File("res/maps"), prop);
			
			for (String m : maps) {			
				in = new Scanner(new File(m));
				//in = new Scanner( new File("res/map.txt"));
			}
			while (in.hasNextLine()) {
				String line = in.nextLine();
				c++;
				lines.add(line);
			} System.out.println(c); setDim(c);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in.close();
			if (input!=null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (lines.isEmpty()) System.exit(0);
	}
	
	public static List<String> loadMaps(File folder, Properties prop) {
		File[] files = folder.listFiles();
		List<String> mapfiles = new ArrayList<String>(); 
		for (File f : files) {
			Set<Object> ob = prop.keySet();
			Iterator it = ob.iterator();
			while (it.hasNext()) {
//				System.out.println(it.next().toString());
				String mapfile = prop.getProperty(it.next().toString());
				mapfiles.add(mapfile);
				System.out.println(mapfile);
			}
		}
		return mapfiles;
	}
	
	List<Integer> slashes;
	List<Integer> spaces;
	
	public Mapper() {
		// setSlashes(new ArrayList<Integer>());
		// setSpaces(new ArrayList<Integer>());
		slashes = new ArrayList<Integer>();
		spaces = new ArrayList<Integer>();
	}
	
	public void setLines(List<String> lines) { this.lines = lines; }
	public List<String> getLines() { 
		/*for (Object o : prop.keySet()) {
			if (o.toString() == bleh) 
		}*/
		return lines; 
	}
	
	// public void setSlashes(ArrayList<Integer> slashes) { this.slashes = slashes; }
	// public void setSpaces(ArrayList<Integer> spaces) { this.spaces = spaces; }
	public ArrayList<Integer> getSlashes() { return (ArrayList<Integer>) slashes; }
	public ArrayList<Integer> getSpaces() { return (ArrayList<Integer>) spaces; }
	
	

}
