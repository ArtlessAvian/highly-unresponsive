package com.artlessavian.highlyunresponsive.ecsstuff;

//import com.artlessavian.highlyunresponsive.QuadTree;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PhysicsComponent implements Component
{
	public Vector2 pos;
	public float radius = 0;
	public Vector2 vel;
	public boolean isPlayer;
	public Vector2 playerVel;
	public int playerStrength = 0;
	//	public QuadTree quadtreePos;
	public boolean hasCollided;
	public boolean isFriendly;
	public int damage;
	public int despawn = 0;

	public PhysicsComponent()
	{
		pos = new Vector2();
		vel = new Vector2();
		playerVel = new Vector2();
	}
}
