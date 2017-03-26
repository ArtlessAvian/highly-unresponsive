package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import java.util.HashSet;

//import com.artlessavian.highlyunresponsive.QuadTree;

public class RemovalsSystem extends IntervalSystem
{
	private final float interval;
	private HashSet<Entity> toRemove;
	//	private QuadTree.QuadTreeRoot quadtree;
	ImmutableArray<Entity> entities;
	private Engine engine;

	public RemovalsSystem(Engine engine, float interval, HashSet<Entity> toRemove)
	{
		super(interval);
		this.engine = engine;
		this.interval = interval;
		this.toRemove = toRemove;
//		this.quadtree = quadtree;
	}

	@Override
	protected void updateInterval()
	{
		for (Entity e : toRemove)
		{
//			e.getComponent(SpriteComponent.class).sprite.setColor(Color.GREEN);
//			PhysicsComponent pc = e.getComponent(PhysicsComponent.class);
//			pc.quadtreePos.recheckEntity(e);
//			pc.quadtreePos.inBounds.remove(e);
//			pc.quadtreePos.removeEntity(e);
//			quadtree.all.remove(e);
			engine.removeEntity(e);
		}
		toRemove.clear();
	}
}
