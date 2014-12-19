package fr.seyara.ces;

import fr.seyara.ces.tools.CustomList;

/**
 * Load the full bag of components and put it in memory for a fast access to this Component from entities
 * Useful when you have to pull of the same component from many entities
 * @author Spoke
 *
 * @param <M>
 */
public class ComponentMapper<T extends Component> {
	private CustomList<Component> components;
	private Class<T> clazz;
	private ComponentType type;
	
	// easy to use than the default constructor (which needs to be parametized)
	public static <T extends Component> ComponentMapper<T> getFor(Class<T> type, World world) {
	    return new ComponentMapper<T>(type, world);
	}
	
	private ComponentMapper(Class<T> clazz, World w){
		this.clazz = clazz;
		type = ComponentType.getType(this.clazz);
		components = w.getComponentManager().getComponentsByType(type);
	}
	
	@SuppressWarnings("unchecked")
	public T get(Entity e){
		return (T) components.get(e.getId());
	}
	
	
	@SuppressWarnings("unchecked")
	public T getWithCheck(Entity e){
		if(components.isIn(e.getId()))
			return (T) components.get(e.getId());
		return null;
	}
	
	public boolean has(Entity e){
		return getWithCheck(e) != null;
	}
}
