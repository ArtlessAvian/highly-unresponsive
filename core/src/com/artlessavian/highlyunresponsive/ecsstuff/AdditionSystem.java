package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.HashSet;

public class AdditionSystem extends IntervalSystem
{
	private final float interval;
	private HashSet<Entity> toAdd;
	ImmutableArray<Entity> entities;
	private Engine engine;

	public AdditionSystem(Engine engine, float interval, HashSet<Entity> toAdd)
	{
		super(interval);
		this.engine = engine;
		entities = engine.getEntitiesFor(Family.all(ShootyComponent.class).get());
		this.interval = interval;
		this.toAdd = toAdd;
	}

	@Override
	protected void updateInterval()
	{
		for (Entity e : entities)
		{
			ShootyComponent sc = e.getComponent(ShootyComponent.class);
			if (!sc.isPlayer || Gdx.input.isKeyPressed(Input.Keys.Z))
			{
				sc.pattern.createBullets(toAdd, e);
			}
		}

		for (Entity e : toAdd)
		{
			engine.addEntity(e);
		}
		toAdd.clear();
	}
}
