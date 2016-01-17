ces-framework
=============

Component-Entity-System Framework

The CES framework is mostly inspired by the Artemis Project coded in Java.

Basic usage
=============
```java
		World world = new World();
		world.init();
		
		Entity e = world.createEntity(); // create an entity
		e.addComponent(new ExampleComponent("This is my name")); // attach component to it
		e.addToWorld(); // add it to the world, the entity will be process by systems if needed
```
