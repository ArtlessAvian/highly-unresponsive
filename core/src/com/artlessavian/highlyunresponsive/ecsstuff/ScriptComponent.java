package com.artlessavian.highlyunresponsive.ecsstuff;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;

public class ScriptComponent implements Component
{
	public interface Script
	{
		void perform(Entity e, int scriptTime);
	}

	public static class ScriptFollowPath implements Script
	{
		float[] xs;
		float[] ys;

		public ScriptFollowPath(float[] xs, float[] ys)
		{
			this.xs = xs;
			this.ys = ys;
		}

		public void perform(Entity e, int scriptTime)
		{
			PhysicsComponent pc = e.getComponent(PhysicsComponent.class);

			if (scriptTime/10 < xs.length - 1)
			{
				pc.pos.x = (xs[scriptTime / 10] * (10 - (scriptTime%10)) + xs[scriptTime / 10 + 1] * (scriptTime%10)) / 10f;
			}
			else
			{
				pc.pos.x = xs[xs.length-1];
			}

			if (scriptTime/10 < ys.length - 1)
			{
				pc.pos.y = (ys[scriptTime / 10] * (10 - (scriptTime%10)) + ys[scriptTime / 10 + 1] * (scriptTime%10)) / 10f;
			}
			else
			{
				pc.pos.y = ys[ys.length-1];
			}

//			System.out.println(pc.pos);
		}
	}

	int scriptTime = 0;

	public void performWrap(Entity e)
	{
		scriptTime++;
		script.perform(e, scriptTime);
	}

	public Script script;
}
