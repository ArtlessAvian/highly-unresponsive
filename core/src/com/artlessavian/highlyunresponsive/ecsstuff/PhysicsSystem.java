package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;

public class PhysicsSystem extends IntervalIteratingSystem
{
	float interval;

	public PhysicsSystem(float interval)
	{
		super(Family.all(PhysicsComponent.class).get(), interval);
		this.interval = interval;

	}

	@Override
	protected void processEntity(Entity entity)
	{
		PhysicsComponent pc = entity.getComponent(PhysicsComponent.class);

		pc.vel.scl(interval);
		pc.playerVel.scl(interval);
		pc.pos.add(pc.vel);
		pc.pos.add(pc.playerVel);
		pc.vel.scl(1/interval);
		pc.playerVel.scl(1/interval);

//		System.out.println(pc.y + " " + pc.x);

		SpriteComponent sc = entity.getComponent(SpriteComponent.class);

		if (sc != null)
		{
			sc.sprite.setCenter(pc.pos.x, pc.pos.y);
		}
//		System.out.println("hi");
	}
}
