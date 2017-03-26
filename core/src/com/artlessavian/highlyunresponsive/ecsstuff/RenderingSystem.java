package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderingSystem extends EntitySystem
{
	private float interval;
	private float rollover;

	private final SpriteBatch batch;
	private final BitmapFont font;
	ImmutableArray<Entity> entities;
	private boolean doInterpolation = true;
	Sprite ui;
	Sprite circle;

	public RenderingSystem(Engine engine, SpriteBatch batch, BitmapFont font, float interval)
	{
		this.batch = batch;
		this.font = font;
		entities = engine.getEntitiesFor(Family.all(SpriteComponent.class).get());
		this.interval = interval;

		ui = new Sprite(new Texture("Stuff.png"));
		ui.setCenter(0, 360);

		circle = new Sprite(new Texture("circle.png"));
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

//			sc.sprite.setColor(pc.quadtreePos.c);
			sc.sprite.draw(batch);
			if (pc != null)
			{
				circle.setSize(pc.radius * 2, pc.radius * 2);
				circle.setCenter(sc.sprite.getX() + sc.sprite.getWidth() / 2f, sc.sprite.getY() + sc.sprite.getHeight() / 2f);
				circle.draw(batch, (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) ? 0.3f : 0);
			}

			HurtboxComponent hc = e.getComponent(HurtboxComponent.class);
			if (hc != null)
			{
				font.draw(batch, hc.health + "", pc.pos.x, pc.pos.y + 100);
			}
		}

//		System.out.println("rendering");

		ui.draw(batch);
	}
}
