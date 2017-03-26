package com.artlessavian.highlyunresponsive;


import com.artlessavian.highlyunresponsive.ecsstuff.HurtboxComponent;
import com.artlessavian.highlyunresponsive.ecsstuff.PhysicsComponent;
import com.artlessavian.highlyunresponsive.ecsstuff.ShootyComponent;
import com.artlessavian.highlyunresponsive.ecsstuff.SpriteComponent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;

public class EntityFactory
{
	private static Entity makeBulletTracking(boolean isFriendly, Vector2 source, Vector2 target)
	{
		Entity e = new Entity();

		PhysicsComponent pc = new PhysicsComponent();
		pc.pos.set(source);
		pc.vel.set(player.getComponent(PhysicsComponent.class).pos);
		pc.vel.sub(pc.pos);
		pc.vel.setLength(150);
		pc.radius = 10;
		pc.isFriendly = isFriendly;
		e.add(pc);
		SpriteComponent sc = new SpriteComponent();
		sc.sprite = new Sprite(new Texture("circle.png"));
		sc.sprite.setSize(20, 20);
		e.add(sc);

		return e;
	}

	public static Entity makeBullet(boolean isFriendly, Vector2 source)
	{
		Entity e = new Entity();

		PhysicsComponent pc = new PhysicsComponent();
		pc.pos.set(source);
		pc.vel.y = 700;
		pc.radius = 10;
		pc.isFriendly = isFriendly;
		e.add(pc);
		SpriteComponent sc = new SpriteComponent();
		sc.sprite = new Sprite(new Texture("circle.png"));
		sc.sprite.setSize(20, 20);
		e.add(sc);

		return e;
	}

	public static Entity makeBullet(boolean isFriendly)
	{
		Entity e = new Entity();

		PhysicsComponent pc = new PhysicsComponent();
		pc.pos.y = 720;
		pc.vel.y = -50;
		pc.radius = 10;
		pc.isFriendly = isFriendly;
		e.add(pc);
		SpriteComponent sc = new SpriteComponent();
		sc.sprite = new Sprite(new Texture("circle.png"));
		sc.sprite.setSize(20, 20);
		e.add(sc);

		return e;
	}

	public static Entity makeEnemy()
	{
		Entity e = new Entity();

		final PhysicsComponent pc = new PhysicsComponent();
		pc.radius = 50;
		pc.pos.y = 500;
		pc.isFriendly = false;
		e.add(pc);
		SpriteComponent sc = new SpriteComponent();
		sc.sprite = new Sprite(new Texture("enemy.png"));
		sc.sprite.setSize(100, 100);
//		sc.sprite.setColor(Color.GREEN);
		e.add(sc);
		e.add(new HurtboxComponent(false));

		ShootyComponent shootC = new ShootyComponent();
		shootC.pattern = new ShootyComponent.BulletPattern()
		{
			@Override
			public void createBullets(HashSet<Entity> toAdd)
			{
				if (Gdx.graphics.getFrameId() % 7 == 0)
				{
					toAdd.add(EntityFactory.makeBulletTracking(false, pc.pos, player.getComponent(PhysicsComponent.class).pos));
				}
			}
		};
		e.add(shootC);

		return e;
	}

	public static Entity player;

	public static Entity makePlayer()
	{
		Entity e = new Entity();

		final PhysicsComponent pc = new PhysicsComponent();
		pc.playerStrength = 400;
		pc.radius = 10;
		pc.isPlayer = true;
		pc.isFriendly = true;
		e.add(pc);
		SpriteComponent sc = new SpriteComponent();
		sc.sprite = new Sprite(new Texture("player.png"));
		sc.sprite.setSize(100, 100);
		e.add(sc);
		e.add(new HurtboxComponent(true));

		ShootyComponent shootC = new ShootyComponent();
		shootC.isPlayer = true;
		shootC.pattern = new ShootyComponent.BulletPattern()
		{
			@Override
			public void createBullets(HashSet<Entity> toAdd)
			{
				if (Gdx.graphics.getFrameId() % 4 == 0)
				{
					toAdd.add(EntityFactory.makeBullet(true, pc.pos));
				}
			}
		};
		e.add(shootC);


		player = e;
		return e;
	}
}
