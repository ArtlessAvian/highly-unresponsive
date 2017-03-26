package com.artlessavian.highlyunresponsive;

import com.artlessavian.highlyunresponsive.ecsstuff.*;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashSet;

public class GameScreen implements Screen
{

	private Engine engine;
	private GameMain gameMain;
	private OrthographicCamera cam;

	RenderingSystem renderingSys;
	HashSet<Entity> toAdd;
	HashSet<Entity> toRemove;
	Rectangle gameBounds;
	//	QuadTree.QuadTreeRoot quadtree;
	Entity player;

	public GameScreen(GameMain gameMain)
	{
		this.gameMain = gameMain;
		this.engine = new PooledEngine();
		this.cam = new OrthographicCamera();
		cam.viewportHeight = 720;
		cam.viewportWidth = cam.viewportHeight * 16f / 9f;
		cam.translate(0, cam.viewportHeight / 2f);
		//cam.zoom = 1.1f;
		cam.update();

		gameBounds = new Rectangle(-720 / 2, 0, 720, 720);

		toAdd = new HashSet<Entity>();
		toRemove = new HashSet<Entity>();
//		quadtree = new QuadTree.QuadTreeRoot(gameBounds.x, gameBounds.y, gameBounds.width, gameBounds.height);

		engine.addSystem(new RemovalsSystem(engine, 1 / 60f, toRemove));
		engine.addSystem(new AdditionSystem(engine, 1 / 60f, toAdd));
		engine.addSystem(new PlayerSystem(1 / 60f, gameBounds));
		engine.addSystem(new ScriptSystem(1 / 60f));
		engine.addSystem(new PhysicsSystem(1 / 60f, gameBounds, toRemove));
//		engine.addSystem(new CollisionSystem(engine, 1 / 60f, gameBounds, toRemove, quadtree, gameMain));
		engine.addSystem(new LameCollisionSystem(engine, 1 / 60f, toRemove, gameMain));
		renderingSys = new RenderingSystem(engine, gameMain.batch, gameMain.font, 1 / 60f);
		renderingSys.setProcessing(false);
		engine.addSystem(renderingSys);

		player = EntityFactory.makePlayer();
		engine.addEntity(player);

		engine.addEntity(EntityFactory.makeEnemy());
	}

	@Override
	public void show()
	{

	}

	@Override
	public void render(float delta)
	{
		if (Gdx.input.isKeyJustPressed(Input.Keys.S))
		{
			engine.addEntity(EntityFactory.makeEnemy());
		}

		gameMain.batch.setProjectionMatrix(cam.combined);
		gameMain.batch.begin();
		engine.update(Math.min(1 / 60f, delta));
		renderingSys.update(delta);

		gameMain.font.draw(gameMain.batch, engine.getEntities().size() + "", -1280 / 2f, 400);
		for (int i = 0; i < 1000; i += 50)
		{
			gameMain.font.draw(gameMain.batch, i + "", 0, i);
		}
		for (int i = 0; i < 1000; i += 50)
		{
			gameMain.font.draw(gameMain.batch, i + "", i, 0);
		}
		gameMain.batch.end();
	}

	@Override
	public void resize(int width, int height)
	{

	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{

	}

	@Override
	public void hide()
	{

	}

	@Override
	public void dispose()
	{

	}
}
