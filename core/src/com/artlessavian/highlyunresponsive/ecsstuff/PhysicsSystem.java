package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.Rectangle;

public class PhysicsSystem extends IntervalIteratingSystem
{
	float interval;
	private Rectangle gameBounds;

	public PhysicsSystem(float interval, Rectangle gameBounds)
	{
		super(Family.all(PhysicsComponent.class).get(), interval);
		this.interval = interval;

		this.gameBounds = gameBounds;
	}

	@Override
	protected void processEntity(Entity entity)
	{
		PhysicsComponent pc = entity.getComponent(PhysicsComponent.class);

		pc.vel.scl(interval);
		pc.playerVel.scl(interval);
		pc.pos.add(pc.vel);
		pc.pos.add(pc.playerVel);
		pc.vel.scl(1 / interval);
		pc.playerVel.scl(1 / interval);

//		System.out.println(pc.y + " " + pc.x);

		SpriteComponent sc = entity.getComponent(SpriteComponent.class);

		if (pc.isPlayer)
		{
			if (pc.pos.x - pc.radius < gameBounds.x) {pc.pos.x = gameBounds.x + pc.radius;}
			if (pc.pos.x + pc.radius > gameBounds.x + gameBounds.width)
			{
				pc.pos.x = gameBounds.x + gameBounds.width - pc.radius;
			}
			if (pc.pos.y - pc.radius < gameBounds.y) {pc.pos.y = gameBounds.y + pc.radius;}
			if (pc.pos.y + pc.radius > gameBounds.y + gameBounds.height)
			{
				pc.pos.y = gameBounds.y + gameBounds.height - pc.radius;
			}
		}

		if (sc != null)
		{
			sc.sprite.setCenter(pc.pos.x, pc.pos.y);
		}
//		System.out.println("hi");
	}
}
