package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

public class ScriptSystem extends IntervalIteratingSystem
{
	private final float interval;

	public ScriptSystem(float interval)
	{
		super(Family.all(ScriptComponent.class).get(), interval);
		this.interval = interval;
	}

	@Override
	public void processEntity(Entity e)
	{
		ScriptComponent sc = e.getComponent(ScriptComponent.class);
		sc.performWrap(e);
	}
}
