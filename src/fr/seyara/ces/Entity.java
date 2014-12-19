package fr.seyara.ces;

import fr.seyara.ces.tools.CustomList;

/**
 * The representation of an Entity
 * The ComponentManager is passed to the entity to facilitate access to ComponentManager methods.
 * @author Spoke
 *
 */
public final class Entity {
	private int id;
	private World world;
	private ComponentManager cm;
	private EntityManager em;
	
	/**
	 * It's the World which handle the Entity creation.
	 * @param w
	 * @param id
	 */
	protected Entity(World w, int id){
		this.id = id;
		this.world = w;
		this.cm = world.getComponentManager();
		this.em = world.getEntityManager();
	}
	
	
	/** 
	 * Add a component to this entity
	 * @see The overload method is faster
	 * @param c
	 * @return
	 */
	public Entity addComponent(Component c){
		return addComponent(c, ComponentType.getType(c.getClass()));
	}
	
	/**
	 * Fastest method to add a component to this entity
	 * @param c
	 * @param type
	 * @return
	 */
	public Entity addComponent(Component c, ComponentType type){
		cm.addComponent(this, c, type);
		return this;
	}
	
	/** 
	 * Remove a component from this entity
	 * Slower than the overload because the type has to be detected. This method determine the Type and call the overload method.
	 * @param c
	 * @return
	 */
	public Entity removeComponent(Component c){
		return removeComponent(ComponentType.getType(c.getClass()));
	}
	
	/**
	 * Remove a component from this entity based on its Type.
	 * @param type
	 * @return
	 */
	public Entity removeComponent(ComponentType type){
		cm.removeComponent(this, type);
		return this;
	}
	
	/**
	 * Remove all components of this entity
	 */
	public void removeAllComponents(){
		cm.removeAllComponents(this);
	}
	
	/** 
	 * Return the component matching to this type of component.
	 * The return type can be NULL
	 * @param type
	 * @return
	 */
	public Component getComponent(ComponentType type){
		return cm.getComponent(this, type);
	}
	
	/**
	 * Return all component of this entity
	 * @return
	 */
	public CustomList<Component> getAllComponents(){
		return cm.getAllComponents(this);
	}
	
	/** 
	 * Add this entity to the world.
	 * This action add the entity to all listeners (Systems & Managers)
	 */
	public void addToWorld(){
		world.addEntity(this);
	}
	
	/**
	 * Remove this entity from the world. Unset the listeners.
	 */
	public void removeFromWorld(){
		world.removeEntity(this);
	}
	
	/**
	 * If components of the Entity have changed, you MUST call this method to notify the world.
	 */
	public void changedInWorld(){
		world.changeEntity(this);
	}
	
	/**
	 * Enable this entity in the world.
	 */
	public void enable(){
		world.enableEntity(this);
	}
	
	/**
	 * Disable this entity in the world
	 */
	public void disable(){
		world.disableEntity(this);
	}
	
	
	/** 
	 * Return the unique ID of this Entity
	 * @return
	 */
	public int getId(){
		return id;
	}
}