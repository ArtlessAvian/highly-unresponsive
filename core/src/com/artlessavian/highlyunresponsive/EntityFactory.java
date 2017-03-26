package com.artlessavian.highlyunresponsive;


import com.artlessavian.highlyunresponsive.ecsstuff.*;
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
		pc.damage = 1;
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
		pc.damage = 1;
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
		pc.pos.x = -250;
		pc.isFriendly = false;
		e.add(pc);
		SpriteComponent sc = new SpriteComponent();
		sc.sprite = new Sprite(new Texture("enemy.png"));
		sc.sprite.setSize(100, 100);
//		sc.sprite.setColor(Color.GREEN);
		e.add(sc);
		HurtboxComponent hc = new HurtboxComponent(false);
		hc.health = 70;
		e.add(hc);

		ShootyComponent shootC = new ShootyComponent();

		switch ((int)(Math.random() * 2))
		{
			case 0: {shootC.pattern = new ShootyComponent.BulletPattern()
			{
				@Override
				public void createBullets(HashSet<Entity> toAdd, Entity e)
				{
						if (Gdx.graphics.getFrameId() % 30 == 0)
						{
							toAdd.add(fuzzDir(addColor(makeBulletTracking(false, pc.pos, player.getComponent(PhysicsComponent.class).pos), Color.GOLDENROD)));
						}
						}
				}; break;}
			case 1: {shootC.pattern = new ShootyComponent.BulletPattern()
			{
				@Override
				public void createBullets(HashSet<Entity> toAdd, Entity e)
				{
					if (Gdx.graphics.getFrameId() % 120 == 0)
					{
						for (int i = 0; i < 36; i++)
						{
							Entity butts = EntityFactory.makeBullet(false, pc.pos);
							PhysicsComponent pc = butts.getComponent(PhysicsComponent.class);
							pc.vel.set(0, -60);
							pc.vel.setAngle(i * 10);
							toAdd.add(addColor(butts,Color.GREEN));
						}
					}
				}
			}; break;}
		}


		e.add(shootC);

		ScriptComponent scriptC = new ScriptComponent();
		float[] xs = {(float)Math.random() * 400 - 200};
		float[] ys = {900, 700, 600, 550};
		scriptC.script = new ScriptComponent.ScriptFollowPath(xs, ys);
		e.add(scriptC);

		return e;
	}

	public static Entity player;

	public static Entity makePlayer()
	{
		Entity e = new Entity();

		final PhysicsComponent pc = new PhysicsComponent();
		pc.playerStrength = 400;
		pc.radius = 2;
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
			public void createBullets(HashSet<Entity> toAdd, Entity e)
			{
//				if (Gdx.graphics.getFrameId() % 4 == 0)
//				{
//					toAdd.add(EntityFactory.makeBullet(true, pc.pos));
//				}
				if (Gdx.graphics.getFrameId() % 1 == 0)
				{
					toAdd.add(addColor(fuzzDir(makeBullet(true, pc.pos)),Color.BLUE));
				}
			}
		};
		e.add(shootC);


		player = e;
		return e;
	}

	private static Entity fuzzDir(Entity entity)
	{
		PhysicsComponent pc = entity.getComponent(PhysicsComponent.class);
		pc.vel.setAngle((float)(pc.vel.angle() + Math.random() * 20 - 10));
		return entity;
	}

	private static Entity addColor(Entity entity, Color c)
	{
		SpriteComponent sc = entity.getComponent(SpriteComponent.class);
		sc.sprite.setColor(c);
		return entity;
	}
}
