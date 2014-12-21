package fr.seyara.ces;

import fr.seyara.ces.tools.CustomList;

/**
 * Process method on Entities
 * 
 * @example  	La gravité sur les Entity possèdant un composant Gravity doivent être traités par un GravitySystem extends EntitySystem.
 * @author  	Spoke
 *
 */
abstract public class EntitySystem implements IListener {
	private CustomList<Entity> entities;
	private boolean active = true;
	
	// TODO: Améliorer le stockage des types pour les performances
	private CustomList<ComponentType> allTypes; // all ComponentType needed to use the System (the lastType IS NOT INCLUDE INSIDE, you must check it too)
	private CustomList<ComponentType> excludeTypes; // all ComponentTypes which don't permit the use of the system (if one = exclude from system)
	private ComponentType lastType; // last component add to the system check method
	
	private World world;
	
	public EntitySystem(){
		allTypes = new CustomList<ComponentType>(3);
		excludeTypes = new CustomList<ComponentType>(2);
		
		entities = new CustomList<Entity>();
		
		setTypesConfiguration();
	}
	
	public abstract void init();
	protected abstract void setTypesConfiguration(); // permet de définir la liste des Components à valider/refuser etc..

	
	public final void process(){
		if(!active){
			ifInactive();
			return;
		}
		
		before();
		start(entities);
		after();
	}
	
	// if inactive
	protected void ifInactive(){
	}
	
	// before start
	protected void before(){
	}
	
	// Loop for process Entities
	protected void start(CustomList<Entity> items){
	}
	
	// after start
	protected void after(){
	}
	
	protected final void check(Entity e){
		if(e == null || lastType == null) 
			return;
		
		ComponentManager cm = world.getComponentManager();
		
		if(allTypes.size() > 0){ // case : many include types
			for(int i = 0; i < allTypes.size(); i++){
				if(!cm.contains(e, allTypes.get(i))){
					entities.remove(e);
					return;
				}
			}
		}
		
		if(!cm.contains(e, lastType)) 
			entities.remove(e);
		
		if(excludeTypes.size() > 0){
			for(int i = 0; i < excludeTypes.size(); i++){
				if(cm.contains(e, excludeTypes.get(i))){
					entities.remove(e);
					return;
				}
			}
		}
		
		//Everything is okay
		entities.add(e);
	}
	
	
	@Override
	public void added(Entity e) {
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
	
	
	// setters & getters
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


	public World getWorld() {
		return world;
	}


	public void setWorld(World world) {
		this.world = world;
	}	
}
