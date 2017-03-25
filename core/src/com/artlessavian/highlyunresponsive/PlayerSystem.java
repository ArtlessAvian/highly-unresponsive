package com.artlessavian.highlyunresponsive;

import com.artlessavian.highlyunresponsive.ecsstuff.PhysicsComponent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static sun.audio.AudioPlayer.player;

public class PlayerSystem extends IntervalIteratingSystem
{
	private final float interval;

	public PlayerSystem(float interval)
	{
		super(Family.all(PhysicsComponent.class).get(), interval);
		this.interval = interval;
	}

	@Override
	public void processEntity(Entity e)
	{
		PhysicsComponent pc = e.getComponent(PhysicsComponent.class);

		if (pc.playerStrength != 0)
		{
			pc.playerVel.set(0,0);

			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {pc.playerVel.y = pc.playerStrength;}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {pc.playerVel.y = -pc.playerStrength;}
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {pc.playerVel.x = -pc.playerStrength;}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {pc.playerVel.x = pc.playerStrength;}

			if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {pc.playerVel.scl(1 / 2f);}
		}
	}
}
