package com.artlessavian.highlyunresponsive;

import com.artlessavian.highlyunresponsive.ecsstuff.PhysicsComponent;
import com.artlessavian.highlyunresponsive.ecsstuff.SpriteComponent;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashSet;

public class QuadTree
{
	Rectangle bounds;

	public static class QuadTreeRoot extends QuadTree
	{
		HashSet<Entity> all;

		public QuadTreeRoot(float x, float y, float width, float height)
		{
			super(null, x, y, width, height);
			all = new HashSet<Entity>();
		}

		public void addEntity(Entity entity)
		{
			all.add(entity);
			super.addEntity(entity);
		}

		public boolean contains(Entity entity)
		{
			return all.contains(entity);
		}
	}

	QuadTree parent;
	QuadTree[] children;
	HashSet<Entity> inBounds;
	Rectangle r;

	private QuadTree(QuadTree parent, float x, float y, float width, float height)
	{
		this.parent = parent;
		bounds = new Rectangle(x, y, width, height);
		inBounds = new HashSet<Entity>();
		r = new Rectangle();
	}

	private void createChildren()
	{
		// 3 4
		// 1 2
		children = new QuadTree[4];
		children[0] = new QuadTree(this, bounds.x, bounds.y, bounds.width / 2f, bounds.height / 2f);
		children[1] = new QuadTree(this, bounds.x + bounds.width / 2f, bounds.y, bounds.width / 2f, bounds.height / 2f);
		children[2] = new QuadTree(this, bounds.x, bounds.y + bounds.height / 2f, bounds.width / 2f, bounds.height / 2f);
		children[3] = new QuadTree(this, bounds.x + bounds.width / 2f, bounds.y + bounds.height / 2f, bounds.width / 2f, bounds.height / 2f);
	}

	private void addInChild(Entity entity)
	{
		PhysicsComponent pc = entity.getComponent(PhysicsComponent.class);
		r.setCenter(pc.pos);
		r.setSize(pc.radius * 2, pc.radius * 2);
		for (QuadTree q : children)
		{
			if (q.bounds.contains(r))
			{
				q.addEntity(entity);
				return;
			}
		}
		pc.quadtreePos = this;
		inBounds.add(entity);
	}

	private void addEntity(Entity entity)
	{
		if (children == null)
		{
			if (inBounds.size() < 2)
			{
				PhysicsComponent pc = entity.getComponent(PhysicsComponent.class);
				pc.quadtreePos = this;
				inBounds.add(entity);
			}
			else
			{
				createChildren();
				addInChild(entity);
				Object[] es = inBounds.toArray();
				inBounds.clear();
				for (Object e : es)
				{
					addInChild((Entity)e);
				}
			}
		}
		else
		{
			addInChild(entity);
		}
	}

	public void recheckEntity(Entity entity)
	{
		PhysicsComponent pc = entity.getComponent(PhysicsComponent.class);
		r.setCenter(pc.pos);
		r.setSize(pc.radius * 2, pc.radius * 2);
		if (!bounds.contains(r) && parent != null)
		{
			if (inBounds.contains(entity)) {inBounds.remove(entity);}
			pc.quadtreePos = null;
			parent.recheckEntity(entity);
		}
		else
		{
			addEntity(entity);
		}
	}

	public void getCollisions(Entity entity, HashSet<Entity> collisions)
	{
		PhysicsComponent pc = entity.getComponent(PhysicsComponent.class);
		for (Entity e : inBounds)
		{
			PhysicsComponent pcOther = e.getComponent(PhysicsComponent.class);

			if (e == entity) {continue;}
			if (pc.isFriendly == pcOther.isFriendly) {continue;}

			if (pc.pos.dst2(pcOther.pos) < (pc.radius + pcOther.radius) * (pc.radius + pcOther.radius))
			{
				collisions.add(e);
				pc.hasCollided = true;
				pcOther.hasCollided = true;
				e.getComponent(SpriteComponent.class).sprite.setColor(Color.RED);
			}
		}

		if (parent != null)
		{
			parent.getCollisions(entity, collisions);
		}
	}

//	Texture tex = new Texture("square.png");
//	public Color c = new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1);
//
//	public void draw(BitmapFont font, SpriteBatch batch)
//	{
//		batch.setColor(c);
//		batch.draw(tex, bounds.x, bounds.y, bounds.width, bounds.height);
//		font.draw(batch, inBounds.size() + "", bounds.x + 30, bounds.y + 30);
//		if (children != null)
//		{
//			for (QuadTree q : children)
//			{
//				q.draw(font, batch);
//			}
//		}
//	}
}
