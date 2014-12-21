package fr.seyara.ces.managers;

import java.util.HashMap;
import java.util.Set;

import fr.seyara.ces.Entity;
import fr.seyara.ces.Manager;
import fr.seyara.ces.tools.CustomList;


/**
 * Re-group entities under a group name. An Entity can belong to multiples groups tags.
 * @author Spoke
 *
 */
public class GroupManager extends Manager {
	private HashMap<String, CustomList<Entity>> entitiesByGroup;
	private HashMap<Entity, CustomList<String>> groupsByEntity;
	
	private int defaultEntitiesByGroup = 8; 
	private int defaultGroupsByEntity = 2;
	
	public GroupManager(){
		entitiesByGroup = new HashMap<String, CustomList<Entity>>();
		groupsByEntity = new HashMap<Entity, CustomList<String>>();
	}

	@Override
	protected void init() { }
	
	/**
	 * Add an empty group if it doesn't exist.
	 * @param g Name of the group
	 */
	public CustomList<Entity> addGroup(String g){
		CustomList<Entity> e = entitiesByGroup.get(g);
		if(e == null)
			e = entitiesByGroup.put(g, new CustomList<Entity>(defaultEntitiesByGroup));
		return e;
	}
	
	/**
	 * Add entities to a group name. If the group doesn't exist it will be created.
	 * @param g	Name of the group
	 * @param entities Entities
	 */
	public void addEntities(String g, CustomList<Entity> entities){
		addGroup(g).addAll(entities);
		
		for(int i = 0; i < entities.size(); i++)
			addGroupNameToExistentEntity(g, entities.get(i));
	}
	
	/**
	 * Add an Entity to a group name. If the group name does't exist it will be created.
	 * @param g Name of the group
	 * @param e 
	 */
	public void addEntity(String g, Entity e){
		addGroup(g).add(e);
		addGroupNameToExistentEntity(g, e);
	}
	
	/**
	 * This private function add a group name to an Entity. 
	 * The Entity MUST exists (added to entitiesByGroup) : it's the reason of its private status.
	 * @param g
	 * @param e
	 */
	private void addGroupNameToExistentEntity(String g, Entity e){
		CustomList<String> str = groupsByEntity.get(e);
		if(str == null){
			str = new CustomList<String>(defaultGroupsByEntity);
			groupsByEntity.put(e, str);
		}
		str.add(g);
	}
	
	/**
	 * Remove an Entity from all groups.
	 * This method is SLOW and must be avoid as possible !
	 * @param e
	 */
	public void removeEntity(Entity e){
		CustomList<String> groups = groupsByEntity.get(e);
		for(int i = 0; i < groups.size(); i++){
			CustomList<Entity> list = entitiesByGroup.get(groups.get(i));
			list.remove(e);
		}
		
		groupsByEntity.put(e, null);
	}
	
	/**
	 * Remove an Entity from a distinct group
	 * @param g Name of the group
	 * @param e
	 */
	public void removeEntityFromGroup(String g, Entity e){
		CustomList<Entity> entities = entitiesByGroup.get(g);
		CustomList<String> groups = groupsByEntity.get(e);
		
		if(entities != null)
			entities.remove(e);
		if(groups != null)
			groups.remove(g);
	}
	
	/**
	 * Remove a group and this Entities.
	 * This method is SLOW and must be avoid as possible !
	 * @param g Name of the group
	 */
	public void removeGroup(String g){
		CustomList<Entity> list = entitiesByGroup.get(g);
		for(int i = 0; i < list.size(); i++){
			CustomList<String> groups = groupsByEntity.get(list.get(i));
			groups.remove(g);
		}
		
		entitiesByGroup.put(g, null);
	}
	
	
	@Override
	public void removed(Entity e){
		removeEntity(e);
	}
	
	/**
	 * Return a CustomList which contains all entities. This method never return NULL but an empty list.
	 * @param g Name of the group
	 * @return
	 */
	public CustomList<Entity> getEntities(String g){
		CustomList<Entity> e = entitiesByGroup.get(g);
		if(e == null){
			e = new CustomList<Entity>(defaultEntitiesByGroup);
			entitiesByGroup.put(g, e);
		}
		return e;
	}
	
	/**
	 * Return the list of groups that an Entity belong to
	 * @param e
	 * @return
	 */
	public CustomList<String> getGroups(Entity e){
		return groupsByEntity.get(e);
	}
	
	/**
	 * Check if an Entity belong to a group name
	 * @param g Name of the group
	 * @param e
	 * @return
	 */
	public boolean isInGroup(String g, Entity e){
		CustomList<String> groups = groupsByEntity.get(e);
		for(int i = 0; i < groups.size(); i++){
			if(groups.get(i).equals(g))
				return true;
		}
		return false;
	}
	
	/**
	 * Check if an Entity belong to any group
	 * @param e
	 * @return
	 */
	public boolean hasAGroup(Entity e){
		return getGroups(e) != null;
	}
	
	/**
	 * Return a Set of all groups names
	 * @return
	 */
	public Set getAllGroups(){
		return entitiesByGroup.keySet();
	}
}