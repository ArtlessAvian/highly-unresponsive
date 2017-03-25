package com.artlessavian.highlyunresponsive;

import com.artlessavian.highlyunresponsive.ecsstuff.PhysicsComponent;
import com.artlessavian.highlyunresponsive.ecsstuff.PhysicsSystem;
import com.artlessavian.highlyunresponsive.ecsstuff.RenderingSystem;
import com.artlessavian.highlyunresponsive.ecsstuff.SpriteComponent;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameScreen implements Screen
{
	private Engine engine;
	private GameMain gameMain;
	private OrthographicCamera cam;

	public GameScreen(GameMain gameMain)
	{
		this.gameMain = gameMain;
		this.engine = new PooledEngine();
		this.cam = new OrthographicCamera();
		cam.viewportHeight = 720;
		cam.viewportWidth = cam.viewportHeight * 16f / 9f;
		cam.translate(0, cam.viewportHeight/2f);
		cam.update();

		engine.addSystem(new PlayerSystem(1/60f));
		engine.addSystem(new PhysicsSystem(1/60f));
		engine.addSystem(new RenderingSystem(engine, gameMain.batch, 1/60f));

		Entity test = new Entity();
		PhysicsComponent pc = new PhysicsComponent();
		pc.playerStrength = 250;
		test.add(pc);
		SpriteComponent sc = new SpriteComponent();
		sc.sprite = new Sprite(new Texture("circle.png"));
		sc.sprite.setSize(20,20);
		sc.sprite.setColor(Color.GREEN);
		test.add(sc);
		engine.addEntity(test);
	}

	@Override
	public void show()
	{

	}

	@Override
	public void render(float delta)
	{
		if (Gdx.graphics.getFrameId() % 1 == 0)
		{
			Entity test = new Entity();
			PhysicsComponent pc = new PhysicsComponent();

			float a = (float)(Math.sin(Gdx.graphics.getFrameId()/4f) * 400);

			pc.pos.y = 700;
			pc.pos.x = a;
			pc.vel.set(200, 0);
			pc.vel.setAngle((float)(Math.sin(Gdx.graphics.getFrameId()/7f) * 45 + 270));
			test.add(pc);
			SpriteComponent sc = new SpriteComponent();
			sc.sprite = new Sprite(new Texture("circle.png"));
			sc.sprite.setSize(20,20);
			test.add(sc);
			engine.addEntity(test);
		}


		gameMain.batch.setProjectionMatrix(cam.combined);

		gameMain.batch.begin();
		engine.update(delta);


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
