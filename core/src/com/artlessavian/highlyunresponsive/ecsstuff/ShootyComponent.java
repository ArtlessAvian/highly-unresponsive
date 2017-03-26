package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.HashSet;


public class ShootyComponent implements Component
{
	public interface BulletPattern
	{
		void createBullets(HashSet<Entity> toAdd, Entity entity);
	}

	public boolean isPlayer;
	public boolean isActive;
	public BulletPattern pattern;
}
