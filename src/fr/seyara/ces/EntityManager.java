package fr.seyara.ces;

import fr.seyara.ces.tools.CustomList;

public class EntityManager extends Manager {
	CustomList<Entity> entities;
	private PoolInteger pi; 
	
	private class PoolInteger {
		private CustomList<Integer> ids; // contains available id (used and free now)
		private int counter = 0; //
		
		public PoolInteger(){
			ids = new CustomList<Integer>(50);
		}
		
		public Integer getId(){
			if(ids.size() > 0)
				return ids.removeLast();
			return counter++;
		}
		
		public void releaseId(int id){
			ids.add(id);
		}
	}
	
	public EntityManager(){
		pi = new PoolInteger();
		entities = new CustomList<Entity>(150);
	}
	
	@Override
	protected void init() {
		
	}
	
	@Override
	public void added(Entity e){
		entities.set(e, pi.getId());
	}
	
	@Override
	public void removed(Entity e){
		entities.set(null, e.getId());
		pi.releaseId(e.getId());
	}

	
	// return only a new entity object
	public Entity createEntity(){
		return new Entity(world, pi.getId());
	}
	
	public Entity get(int id){
		return entities.get(id);
	}
}
