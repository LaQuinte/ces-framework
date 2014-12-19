package fr.seyara.ces.managers;

import java.util.HashMap;

import fr.seyara.ces.Entity;
import fr.seyara.ces.Manager;

/**
 * This manager allow you to store an Entity under a tag name to get it fast. 
 * Each TAG point only onto one Entity.
 * 
 * @example 	Tag "PLAYER", Tag "PET" etc...
 * @author 		Spoke
 *
 */
public class TagManager extends Manager {
	private HashMap<String, Entity> entitiesByTag;
	private HashMap<Entity, String> tagsByEntity;
	
	public TagManager(){
		entitiesByTag = new HashMap<String, Entity>();
		tagsByEntity = new HashMap<Entity, String>();
	}
	
	/**
	 * Tag an entity
	 * @param e
	 * @param t
	 */
	public void tag(Entity e, String t){
		entitiesByTag.put(t, e);
		tagsByEntity.put(e, t);
	}
	
	/**
	 * Remove by tag
	 * @param e
	 */
	public void remove(String t){
		Entity e = entitiesByTag.remove(t);
		if(e != null)
			tagsByEntity.remove(e);
	}
	
	/**
	 * Remove by Entity
	 * @param e
	 */
	public void remove(Entity e){
		String tag = tagsByEntity.remove(e);
		if(tag != null)
			entitiesByTag.remove(tag);
	}
	
	/**
	 * Obtain an Entity by Tag name
	 * @param t
	 * @return 
	 */
	public Entity getEntity(String t){
		return entitiesByTag.get(t);
	}
	
	/**
	 * Obtain a Tag by Entity instance
	 * @param t
	 * @return 
	 */
	public String getTag(Entity e){
		return tagsByEntity.get(e);
	}
	
	
	
	@Override
	protected void init() {}
	
}
