package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Component;

public class HurtboxComponent implements Component
{
	int maxHealth = 1;
	int health = 1;
	boolean isBoss;
	boolean isFriendly;

	public HurtboxComponent(boolean isFriendly)
	{
		this.isFriendly = isFriendly;
	}
}
