package com.artlessavian.highlyunresponsive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

public class MenuScreen implements Screen
{
	private final GameMain gameMain;

	public MenuScreen(GameMain gameMain)
	{
		this.gameMain = gameMain;
	}

	@Override
	public void show()
	{

	}

	@Override
	public void render(float delta)
	{
		if (Gdx.input.isKeyPressed(Input.Keys.Z))
		{
			System.out.println("hi");
			gameMain.setScreen(new GameScreen(gameMain));
		}

		gameMain.batch.begin();
		gameMain.font.draw(gameMain.batch, "im stoopid", 64, 64);
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
