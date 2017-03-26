package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class PlayerSystem extends IntervalIteratingSystem
{
	private final float interval;

	private Rectangle r;
	private Rectangle gameBounds;

	public PlayerSystem(float interval, Rectangle gameBounds)
	{
		super(Family.all(PhysicsComponent.class).get(), interval);
		this.interval = interval;
		this.r = new Rectangle();
		this.gameBounds = gameBounds;
	}

	@Override
	public void processEntity(Entity e)
	{
		PhysicsComponent pc = e.getComponent(PhysicsComponent.class);
		ShootyComponent sc = e.getComponent(ShootyComponent.class);

		if (pc.playerStrength != 0)
		{
			pc.playerVel.set(0, 0);

			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {pc.playerVel.y = pc.playerStrength;}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {pc.playerVel.y = -pc.playerStrength;}
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {pc.playerVel.x = -pc.playerStrength;}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {pc.playerVel.x = pc.playerStrength;}

			if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {pc.playerVel.scl(1 / 2f);}
		}
	}
}
