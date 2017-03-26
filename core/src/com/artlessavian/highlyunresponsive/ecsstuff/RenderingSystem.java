package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderingSystem extends EntitySystem
{
	private float interval;
	private float rollover;

	private final SpriteBatch batch;
	ImmutableArray<Entity> entities;
	private boolean doInterpolation = true;
	Sprite ui;

	public RenderingSystem(Engine engine, SpriteBatch batch, float interval)
	{
		this.batch = batch;
		entities = engine.getEntitiesFor(Family.all(SpriteComponent.class).get());
		this.interval = interval;

		ui = new Sprite(new Texture("Stuff.png"));
		ui.setCenter(0, 360);
	}

	@Override
	public void update(float deltaTime)
	{
		rollover = (rollover + deltaTime) % interval;

		for (Entity e : entities)
		{
			SpriteComponent sc = e.getComponent(SpriteComponent.class);
			PhysicsComponent pc = e.getComponent(PhysicsComponent.class);

			if (doInterpolation && pc != null)
			{
				sc.sprite.setCenterX(pc.pos.x + pc.vel.x * rollover);
				sc.sprite.setCenterY(pc.pos.y + pc.vel.y * rollover);
			}

			//sc.sprite.setColor(pc.quadtreePos.c);
			sc.sprite.draw(batch);
		}

//		System.out.println("rendering");

		ui.draw(batch);
	}
}
