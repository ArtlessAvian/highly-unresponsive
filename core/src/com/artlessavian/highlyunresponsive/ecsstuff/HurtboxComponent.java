package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Component;

public class HurtboxComponent implements Component
{
	int health = 1;
	boolean isFriendly;

	HurtboxComponent(boolean isFriendly)
	{
		this.isFriendly = isFriendly;
	}
}
