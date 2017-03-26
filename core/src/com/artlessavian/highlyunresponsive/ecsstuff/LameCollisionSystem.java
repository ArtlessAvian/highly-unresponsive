package com.artlessavian.highlyunresponsive.ecsstuff;

import com.artlessavian.highlyunresponsive.GameMain;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import java.util.HashSet;

//import com.artlessavian.highlyunresponsive.QuadTree;

public class LameCollisionSystem extends IntervalSystem
{
	private final ImmutableArray<Entity> entities;
	private final ImmutableArray<Entity> withHurtboxes;

	private GameMain gameMain;
	HashSet<Entity> collisions;
	HashSet<Entity> toRemove;

	public LameCollisionSystem(Engine engine, float interval, HashSet<Entity> toRemove, GameMain gameMain)
	{
		super(interval);
		this.gameMain = gameMain;
		entities = engine.getEntitiesFor(Family.all(PhysicsComponent.class).get());
		withHurtboxes = engine.getEntitiesFor(Family.all(HurtboxComponent.class).get());
		collisions = new HashSet<Entity>();
		this.toRemove = toRemove;
	}

	@Override
	protected void updateInterval()
	{
		for (Entity mainEntity : withHurtboxes)
		{
			PhysicsComponent pc = mainEntity.getComponent(PhysicsComponent.class);
			collisions.clear();
			for (Entity entity : entities)
			{
				PhysicsComponent pcOther = entity.getComponent(PhysicsComponent.class);

				if (mainEntity == entity) {continue;}
				if (pc.isFriendly == pcOther.isFriendly) {continue;}

				if (pc.pos.dst2(pcOther.pos) < (pc.radius + pcOther.radius) * (pc.radius + pcOther.radius))
				{
					collisions.add(entity);
				}
			}
			HurtboxComponent hc = mainEntity.getComponent(HurtboxComponent.class);
			for (Entity collider : collisions)
			{
				hc.health -= collider.getComponent(PhysicsComponent.class).damage;
				toRemove.add(collider);
			}
			if (hc.health <= 0)
			{
				toRemove.add(mainEntity);
				break;
			}
		}

		//quadtree.draw(gameMain.font, gameMain.batch);
	}
}
