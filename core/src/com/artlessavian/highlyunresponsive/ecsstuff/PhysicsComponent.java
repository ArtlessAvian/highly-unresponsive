package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PhysicsComponent implements Component
{
	public Vector2 pos;
	public Vector2 vel;
	public Vector2 playerVel;
	public int playerStrength = 0;

	public PhysicsComponent()
	{
		pos = new Vector2();
		vel = new Vector2();
		playerVel = new Vector2();
	}
}
