package com.artlessavian.highlyunresponsive;

import com.artlessavian.highlyunresponsive.ecsstuff.*;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen
{
	private Engine engine;
	private GameMain gameMain;
	private OrthographicCamera cam;
	Rectangle gameBounds;
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

		engine.addSystem(new PlayerSystem(1 / 60f, gameBounds));
		engine.addSystem(new PhysicsSystem(1 / 60f, gameBounds));
		engine.addSystem(new CollisionSystem(engine, 1 / 60f, gameBounds));
		engine.addSystem(new RenderingSystem(engine, gameMain.batch, 1 / 60f));

		player = new Entity();
		PhysicsComponent pc = new PhysicsComponent();
		pc.playerStrength = 250;
		pc.radius = 10;
		pc.isPlayer = true;
		player.add(pc);
		SpriteComponent sc = new SpriteComponent();
		sc.sprite = new Sprite(new Texture("circle.png"));
		sc.sprite.setSize(20, 20);
		sc.sprite.setColor(Color.GREEN);
		player.add(sc);
		player.add(new HurtboxComponent(true));
		engine.addEntity(player);
	}

	@Override
	public void show()
	{

	}

	@Override
	public void render(float delta)
	{
		if (Gdx.graphics.getFrameId() % 5 == 0)
		{
			Entity test = new Entity();
			PhysicsComponent pc = new PhysicsComponent();

			float a = (float)(Math.sin(Gdx.graphics.getFrameId() / 4f) * 200);

//			pc.pos.y = 700;
//			pc.pos.x = a;
//			pc.vel.set(0, -20);
//			pc.vel.setAngle((float)(Math.sin(Gdx.graphics.getFrameId()/7f) * 45 + 270));
			pc.pos.y = 700;
			pc.pos.x = a;
			pc.vel.set(player.getComponent(PhysicsComponent.class).pos);
			pc.vel.sub(pc.pos);
			pc.vel.setLength(150);

			pc.radius = 10;
			test.add(pc);
			SpriteComponent sc = new SpriteComponent();
			sc.sprite = new Sprite(new Texture("circle.png"));
			sc.sprite.setSize(20, 20);
			test.add(sc);
			engine.addEntity(test);
		}


		gameMain.batch.setProjectionMatrix(cam.combined);

		gameMain.batch.begin();
		engine.update(delta);


//		for (int i = 0; i < 1000; i += 50)
//		{
//			gameMain.font.draw(gameMain.batch, i + "", 0, i);
//		}
//		for (int i = 0; i < 1000; i += 50)
//		{
//			gameMain.font.draw(gameMain.batch, i + "", i, 0);
//		}

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
