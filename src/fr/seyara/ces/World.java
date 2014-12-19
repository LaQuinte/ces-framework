package fr.seyara.ces;

import java.util.HashMap;

import fr.seyara.ces.tools.CustomList;

public class World {
	/** Private interfaces **/
	private interface Notifier {
		void notify(Entity e, IListener handler);
	}
	
	// vars
	private ComponentManager cm;
	private EntityManager em;
	
	private CustomList<Manager> managers; // to iterate throught
	private HashMap<Class<? extends Manager>, Manager> managersMap; // to retrieve fastly a reference to a manager with its ClassName
	
	private CustomList<EntitySystem> systems; // to iterate throught
	private HashMap<Class<? extends EntitySystem>, EntitySystem> systemsMap; // to retrieve fastly a reference to a manager with its ClassName
	
	private float delta; // represent the time passed since last update
	
	// vars for events listeners
	private CustomList<Entity> added;
	private CustomList<Entity> removed;
	private CustomList<Entity> changed;
	private CustomList<Entity> enabled;
	private CustomList<Entity> disabled;
	
	/**
	 * Constructor
	 */
	public World(){
		added = new CustomList<Entity>();
		removed = new CustomList<Entity>(30);
		changed = new CustomList<Entity>(20);
		enabled = new CustomList<Entity>(10);
		disabled = new CustomList<Entity>(10);
		
		managers = new CustomList<Manager>(10); // we can predicate that
		managersMap = new HashMap<Class<? extends Manager>, Manager>();
		
		systems = new CustomList<EntitySystem>(10); // we can predicate that
		systemsMap = new HashMap<Class<? extends EntitySystem>, EntitySystem>();
		
		cm = new ComponentManager();
		addManager(cm);
		
		em = new EntityManager();
		addManager(em);
	}
	
	/**
	 * Initialize all managers & systems
	 * Must be call to provide all World.methods
	 */
	public void init() {
        for (int i = 0; i < managers.size(); i++)
        	managers.get(i).init();
        
        for (int i = 0; i < systems.size(); i++) {
            //ComponentMapperInitHelper.config(systems.get(i), this);
            systems.get(i).init();
        }
	}
	
	
	/**
	 * Method to run all process() and to notify everyone
	 * @param delta
	 */
	public void run(float delta){
		this.delta = delta; // see if we keep this here
		
		// On gère les notifs sur les IListener
		dispatcher(added, new Notifier() {
			@Override
			public void notify(Entity e, IListener handler) {
				handler.added(e);
			}
		});
		added.removeAll();
		
		dispatcher(removed, new Notifier() {
			@Override
			public void notify(Entity e, IListener handler) {
				handler.removed(e);
			}
		});
		removed.removeAll();
		
		dispatcher(changed, new Notifier() {
			@Override
			public void notify(Entity e, IListener handler) {
				handler.changed(e);
			}
		});
		changed.removeAll();
		
		dispatcher(enabled, new Notifier() {
			@Override
			public void notify(Entity e, IListener handler) {
				handler.enabled(e);
			}
		});
		enabled.removeAll();
		
		dispatcher(disabled, new Notifier() {
			@Override
			public void notify(Entity e, IListener handler) {
				handler.disabled(e);
			}
		});
		disabled.removeAll();
		
		// Certains managers ont besoin d'un appel particulier
		cm.clean();
		
		
		// Processing all systems in order
		for(int i = 0; i < systems.size(); i++){
			systems.get(i).process();
		}
	}
	
	
	/** ENTITIES **/
	public Entity createEntity(){
		return em.createEntity();
	}
	
	protected void addEntity(Entity e){
		added.add(e);
	}
	
	protected void changeEntity(Entity e){
		changed.add(e);
	}
	
	protected void removeEntity(Entity e){
		removed.add(e);
	}
	
	protected void disableEntity(Entity e){
		disabled.add(e);
	}
	
	protected void enableEntity(Entity e){
		enabled.add(e);
	}
	

	/* MANAGERS */
	
	
	public ComponentManager getComponentManager(){
		return cm;
	}
	
	public EntityManager getEntityManager(){
		return em;
	}
	
	/**
	 * Add a Manager to the world.
	 * @return
	 */
	public <T extends Manager> T addManager(T m){
		managers.add(m);
		managersMap.put(m.getClass(), m);
		m.setWorld(this);
		return m;
	}
	
	public void removeManager(Manager m){
		managers.remove(m);
		managersMap.remove(m);
	}
	
	// Fast access
	public <T extends Manager> T getManager(Class<T> mType){
		return mType.cast(managersMap.get(mType));
	}
	
	public void notifyManagers(Entity e, Notifier notifier){
		for(int i = 0; i < managers.size(); i++)
			notifier.notify(e, managers.get(i));
	}
	
	/* SYSTEMS */
	
	/**
	 * Add a System to the world.
	 * The order you added systems is really important : it's the order of process()
	 * @param s
	 * @return
	 */
	public <T extends EntitySystem> T addSystem(T s){
		systems.add(s);
		systemsMap.put(s.getClass(), s);
		s.setWorld(this);
		return s;
	}
	
	public void removeSystem(EntitySystem s){
		systems.remove(s);
		managersMap.remove(s);
	}
	
	// Fast access
	public <T extends EntitySystem> T getSystem(Class<T> mType){
		return mType.cast(systemsMap.get(mType));
	}
	
	public void notifySystems(Entity e, Notifier notifier){
		for(int i = 0; i < systems.size(); i++)
			notifier.notify(e, systems.get(i));
	}
	
	
	private void dispatcher(CustomList<Entity> elems, Notifier notifier){
		if(elems.size() == 0)
			return;
		
		for(int i = 0; i < elems.size(); i++){
			Entity e = elems.get(i);
			notifySystems(e, notifier);
			notifyManagers(e, notifier);
		}
	}
	
	
	/** DELTA **/
	public void setDelta(float d){
		delta = d;
	}
	
	public float getDelta(){
		return delta;
	}
}