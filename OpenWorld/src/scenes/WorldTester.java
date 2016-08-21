package scenes;

import java.io.IOException;

import org.lwjgl.opengl.GL11;


import world.World;

public class WorldTester extends Scene {

	World w;

	public WorldTester() {
		
		w = new World();
		
		GL11.glClearColor(1f, 1f, 1f, 1f);
		
	}
	/*public boolean drawFrame(float delta) throws IOException {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		w.setDrawEdges(true);
		p1.update(delta);
		p2.update(delta);
		w.update(delta);
//		g.draw();
		w.draw(); 
		return true;
	}*/
/*	public static void main(String[] args) {
		
		// World.readFromFile();
		
		World w = new World();
		w.buildGrid();
		
		
	}
*/

	@Override
	public boolean drawFrame(float delta) throws IOException {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		w.draw();
		w.update(delta);
		
		return true;
	}
}
