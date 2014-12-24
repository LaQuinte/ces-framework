package fr.seyara.ces.managers;

import java.util.HashMap;

import fr.seyara.ces.Manager;
import fr.seyara.ces.tools.CustomList;

/**
 * Handle team between players (tag for players). Use in parallele with PlayerManager, it doesn't use Entities, only tag names.
 * @author Spoke
 *
 */
public class TeamManager extends Manager {
	private HashMap<String, String> teamByPlayer;
	private HashMap<String, CustomList<String>> playersByTeam;
	
	public TeamManager(){
		teamByPlayer = new HashMap<String, String>();
		playersByTeam = new HashMap<String, CustomList<String>>();
	}
	
	/**
	 * Add a player to a team
	 * @param p
	 * @param t
	 */
	public void addToTeam(String p, String t){
		teamByPlayer.put(p, t);
		CustomList<String> l = playersByTeam.get(t);
		if(l == null){
			l = new CustomList<String>(4);
			playersByTeam.put(t, l);
		}
		l.add(p);
	}
	
	/**
	 * Remove fastly a player from his team.
	 * @param p
	 * @param t
	 */
	public void removeFromTeam(String p, String t){
		CustomList<String> l = playersByTeam.get(t);
		l.remove(p);
	}
	
	/**
	 * Remove a player from his team
	 * If you know the team name, use the removeFromTeam instead (faster).
	 * @param p
	 */
	public void removePlayer(String p){
		String t = teamByPlayer.remove(p);
		if(t == null)
			return;
		removeFromTeam(p, t);
	}
	
	/**
	 * Return the list of the players of the team passed in parameter
	 * @param t
	 * @return
	 */
	public CustomList<String> getPlayers(String t){
		CustomList<String> l = playersByTeam.get(t);
		if(l == null){
			l = new CustomList<String>(4);
			playersByTeam.put(t, l);
		}
		return l;
	}
	
	
	@Override
	protected void init() {
	}
	
}