package fr.seyara.ces;

import java.util.HashMap;

/**
 * Permet de stocker une liste de tous les types de composants utilisés dans l'application
 * Lie un Composant à un index (unique donc).
 * 
 * @author Spoke
 * 
 */
public class ComponentType {
	//static
	private static HashMap<Class<? extends Component>, ComponentType> types = new HashMap<Class<? extends Component>, ComponentType>();
	private static int CURRENT = 0;
	
	
	public static ComponentType getType(Class<? extends Component> clazz){
		ComponentType ct = types.get(clazz);
		
		if(ct == null) { // on ajoute à la liste
			ct = new ComponentType(clazz);
			types.put(clazz, ct);
		}
		return ct;
	}
	
	//instance
	private final int index; // Index du type (unique)
	private final Class<? extends Component> type; // Classe originelle du type
	
	private ComponentType(Class<? extends Component> p_type){
		index = CURRENT++;
		type = p_type;
	}
	
	public int getIndex(){
		return index;
	}
	
	public Class<? extends Component> getType(){
		return type;
	}
}
