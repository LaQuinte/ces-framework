package fr.seyara.ces;

/**
 * Base class representing a Manager. Be careful when you extend this, don't forget the removed() Override if you deal with entities.
 * @author Spoke
 *
 */
public abstract class Manager implements IListener {
	protected World world;
	
	protected abstract void init();
	
	public World getWorld(){
		return world;
	}
	
	public void setWorld(World w){
		world = w;
	}
	
	
	@Override
	public void added(Entity e) {
		System.out.println("Entity added");
	}

	@Override
	public void removed(Entity e) {
		
	}

	@Override
	public void changed(Entity e) {
		
	}

	@Override
	public void enabled(Entity e) {
		
	}

	@Override
	public void disabled(Entity e) {
		
	}
	
}
