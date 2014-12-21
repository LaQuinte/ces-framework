package fr.seyara.ces.managers;

import java.util.HashMap;

import fr.seyara.ces.Entity;
import fr.seyara.ces.Manager;
import fr.seyara.ces.tools.CustomList;


/**
 * Handle player's entities. It's a kind of TagManager but for player.
 * @author Spoke
 *
 */
public class PlayerManager extends Manager {
	private HashMap<String, CustomList<Entity>> entitiesByPlayer;
	private HashMap<Entity, String> playerByEntity;
	
	public PlayerManager(){
		entitiesByPlayer = new HashMap<String, CustomList<Entity>>();
		playerByEntity = new HashMap<Entity, String>();
	}
	
	/**
	 * Return Entities which belong to a player p
	 * @param p
	 * @return
	 */
	public CustomList<Entity> getPlayerEntities(String p){
		CustomList<Entity> list = entitiesByPlayer.get(p);
		if(list == null){
			list = new CustomList<Entity>();
			entitiesByPlayer.put(p, list);
		}
		return list;
	}
	
	/**
	 * Link an Entity to a player
	 * @param p
	 * @param e
	 */
	public void setToPlayer(String p, Entity e){
		CustomList<Entity> list = entitiesByPlayer.get(p);
		if(list == null){
			list = new CustomList<Entity>();
			entitiesByPlayer.put(p, list);
		}
		list.add(e);
		playerByEntity.put(e, p);
	}
	
	/**
	 * Remove an entity from a player. Fast version.
	 * @param p
	 * @param e
	 */
	public void removeFromPlayer(String p, Entity e){
		CustomList<Entity> el = entitiesByPlayer.get(p);
		
		if(el == null || el.size() == 0)
			return;
		
		el.remove(e);
	}
	
	
	/**
	 * Remove an entity form a player. If you do know the player's name, use the overload method (faster)
	 * @param e
	 */
	public void remove(Entity e){
		String p = playerByEntity.get(e);
		if(p == null) 
			return; 
		removeFromPlayer(p, e);
	}
	
	public String getPlayer(Entity e){
		return playerByEntity.get(e);
	}
	
	/**
	 * If remove from the world we unlink it here.
	 * If you want to remove a entity please use the remove method instead. This one is an call when the Entity is remove from the world.
	 */
	@Override
	public void removed(Entity e){
		remove(e);
	}

	@Override
	protected void init() {
	}
}
