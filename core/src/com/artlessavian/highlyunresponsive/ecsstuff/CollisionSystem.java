package com.artlessavian.highlyunresponsive.ecsstuff;

import com.artlessavian.highlyunresponsive.GameMain;
import com.artlessavian.highlyunresponsive.QuadTree;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashSet;

public class CollisionSystem extends IntervalSystem
{
	private final ImmutableArray<Entity> entities;
	private final ImmutableArray<Entity> withHurtboxes;

	private final QuadTree.QuadTreeRoot quadtree;
	private GameMain gameMain;
	private Rectangle gameBounds;
	HashSet<Entity> collisions;

	public CollisionSystem(Engine engine, float interval, Rectangle gameBounds)
	{
		super(interval);
		this.gameBounds = gameBounds;
//		this.gameMain = gameMain;
		entities = engine.getEntitiesFor(Family.all(PhysicsComponent.class).get());
		withHurtboxes = engine.getEntitiesFor(Family.all(HurtboxComponent.class).get());
		quadtree = new QuadTree.QuadTreeRoot(gameBounds.x, gameBounds.y, gameBounds.width, gameBounds.height);
		collisions = new HashSet<Entity>();
	}

	@Override
	protected void updateInterval()
	{
		for (Entity e : entities)
		{
			if (!quadtree.contains(e))
			{
				quadtree.addEntity(e);
			}
			else
			{
				PhysicsComponent pc = e.getComponent(PhysicsComponent.class);
				pc.quadtreePos.recheckEntity(e);
			}
		}

		for (Entity e : withHurtboxes)
		{
			PhysicsComponent pc = e.getComponent(PhysicsComponent.class);
			collisions.clear();
			pc.quadtreePos.getCollisions(e, collisions);
			if (collisions.size() != 0)
			{ System.out.println(collisions.size()); }
		}

		//quadtree.draw(gameMain.font, gameMain.batch);
	}
}
