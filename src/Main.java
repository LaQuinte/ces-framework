import components.ExampleComponent;

import fr.seyara.ces.Entity;
import fr.seyara.ces.World;


public class Main {
	private static World world;
	
	public static void main(String[] args) {
		world = new World();
		world.init();
		
		Entity e = world.createEntity();
		e.addComponent(new ExampleComponent("This is my name"));
		e.addToWorld();
	}

}
