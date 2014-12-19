package fr.seyara.ces;

import fr.seyara.ces.tools.CustomList;

/**
 * Handle all components in list with index (bind Entity to Component)
 * @author Spoke
 *
 */
public class ComponentManager extends Manager {
	private CustomList<CustomList<Component>> components;
	private CustomList<Entity> entitiesToRemove;
	
	
	public ComponentManager(){
		components = new CustomList<CustomList<Component>>(30);
		entitiesToRemove = new CustomList<Entity>();
	}
	
	@Override
	protected void init(){ }
	
	/** 
	 * Add a component to an Entity (bind)
	 * @param e
	 * @param c
	 * @param type
	 */
	public void addComponent(Entity e, Component c, ComponentType type){
		CustomList<Component> comp = components.get(type.getIndex());
		
		if(comp == null){ // we create the sub-list components at the type Index
			comp = new CustomList<Component>();
			components.set(comp, type.getIndex());
		}
		comp.set(c, e.getId());
	}
	
	/**
	 * Remove a component from a Entity.
	 * @note  An Entity can only contain ONE component of each sort
	 * @param e
	 * @param type
	 */
	public void removeComponent(Entity e, ComponentType type){
		CustomList<Component> comp = components.get(type.getIndex());
		if(comp == null)
			return;
		
		components.set(null, e.getId());
	}
	
	
	/**
	 * Get a Component from an Entity
	 * @param entity
	 * @param type
	 * @return
	 */
	public Component getComponent(Entity e, ComponentType type) {
		CustomList<Component> comp = components.get(type.getIndex());
		if(comp == null)
			return null;
		return comp.get(e.getId());
	}
	
	/**
	 * Get all Components of a type
	 * @param entity
	 * @param type
	 * @return
	 */
	public CustomList<Component> getComponentsByType(ComponentType type) {
		CustomList<Component> comp = components.get(type.getIndex());
		if(comp == null){
			comp = new CustomList<Component>();
			components.set(comp, type.getIndex());
		}
		return comp;
	}
	
	
	/**
	 * Get all Components from an Entity
	 * Slow !
	 * @param entity
	 * @param type
	 * @return
	 */
	public CustomList<Component> getAllComponents(Entity e) {
		CustomList<Component> list = new CustomList<Component>();
		for(int i = 0; i < components.size(); i++){
			CustomList<Component> comp = components.get(i);
			if(comp != null && comp.get(e.getId()) != null)
				list.add(comp.get(e.getId()));
		}
		return list;
	}
	
	
	/**
	 * Remove all entity's components
	 * @param e
	 */
	public void removeAllComponents(Entity e){
		for(int i = 0; i < components.size(); i++){
			CustomList<Component> comp = components.get(i);
			if(comp != null && comp.get(e.getId()) != null)
				comp.set(null, e.getId());
		}
	}
	
	
	
	/** CONTAINS **/
	
	//slowest
	public boolean contains(Entity e, Component c){
		return contains(e.getId(), ComponentType.getType(c.getClass()));
	}
	
	public boolean contains(Entity e, ComponentType type){
		return contains(e.getId(), type);
	}
	
	// fastest
	public boolean contains(int elementId, ComponentType type){
		CustomList<Component> comp = components.get(type.getIndex());
		
		return (comp != null && comp.get(elementId) != null);
	}
	
	
	/** EVENTS **/
	@Override
	public void removed(Entity e){
		// We don't remove all components of the Entity here, cause it's not fast. We store the Entity and we'll call clean only one time by process !
		entitiesToRemove.add(e);
	}
	
	
	// si on delete
	public void clean(){
		if(entitiesToRemove.size() == 0)
			return;
		
		for(int i = 0; i < entitiesToRemove.size(); i++){
			removeAllComponents(entitiesToRemove.get(i));
		}
		entitiesToRemove.removeAll();
	}
	
}
