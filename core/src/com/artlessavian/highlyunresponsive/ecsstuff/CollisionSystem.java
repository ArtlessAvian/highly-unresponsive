//package com.artlessavian.highlyunresponsive.ecsstuff;
//
//import com.artlessavian.highlyunresponsive.GameMain;
////import com.artlessavian.highlyunresponsive.QuadTree;
//import com.badlogic.ashley.core.Engine;
//import com.badlogic.ashley.core.Entity;
//import com.badlogic.ashley.core.Family;
//import com.badlogic.ashley.systems.IntervalSystem;
//import com.badlogic.ashley.utils.ImmutableArray;
//import com.badlogic.gdx.math.Rectangle;
//
//import java.util.HashSet;
//
//public class CollisionSystem extends IntervalSystem
//{
//	private final ImmutableArray<Entity> entities;
//	private final ImmutableArray<Entity> withHurtboxes;
//
////	private final QuadTree.QuadTreeRoot quadtree;
//	private GameMain gameMain;
//	private Rectangle gameBounds;
//	HashSet<Entity> collisions;
//	HashSet<Entity> toRemove;
//
//	public CollisionSystem(Engine engine, float interval, Rectangle gameBounds, HashSet<Entity> toRemove, QuadTree.QuadTreeRoot quadtree, GameMain gameMain)
//	{
//		super(interval);
//		this.gameBounds = gameBounds;
//		this.gameMain = gameMain;
//		entities = engine.getEntitiesFor(Family.all(PhysicsComponent.class).get());
//		withHurtboxes = engine.getEntitiesFor(Family.all(HurtboxComponent.class).get());
//		this.quadtree = quadtree;
//		collisions = new HashSet<Entity>();
//		this.toRemove = toRemove;
//	}
//
//	@Override
//	protected void updateInterval()
//	{
//		for (Entity e : entities)
//		{
//			if (!quadtree.contains(e))
//			{
//				quadtree.addEntity(e);
//			}
//			else
//			{
//				PhysicsComponent pc = e.getComponent(PhysicsComponent.class);
//				pc.quadtreePos.recheckEntity(e);
//			}
//
//			PhysicsComponent pc = e.getComponent(PhysicsComponent.class);
//			if (!pc.quadtreePos.inBounds.contains(e))
//			{
//				System.out.println("what");
//			}
//		}
//
//		for (Entity e : withHurtboxes)
//		{
//			PhysicsComponent pc = e.getComponent(PhysicsComponent.class);
//			collisions.clear();
//			pc.quadtreePos.getCollisions(e, collisions);
//			HurtboxComponent hc = e.getComponent(HurtboxComponent.class);
//			if (!collisions.isEmpty()) System.out.println(collisions.size());
//			for (Entity collider : collisions)
//			{
//				hc.health -= collider.getComponent(PhysicsComponent.class).damage;
//				toRemove.add(collider);
//			}
//			if (hc.health <= 0)
//			{
//				toRemove.add(e);
//				break;
//			}
//		}
//
//		quadtree.draw(gameMain.font, gameMain.batch);
//	}
//}
