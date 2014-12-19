package fr.seyara.ces;

/**
 * Listener for changements in the CES. Performed for EACH Entity modification in world
 * @author Spoke
 *
 */
interface IListener {
	public void added(Entity e);
	public void removed(Entity e);
	public void changed(Entity e);
	public void enabled(Entity e);
	public void disabled(Entity e);
}