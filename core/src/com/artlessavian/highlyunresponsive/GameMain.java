package com.artlessavian.highlyunresponsive;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMain extends Game
{
	SpriteBatch batch;
	//	AssetManager assets;
	BitmapFont font;
	private boolean started;

	@Override
	public void create()
	{
		batch = new SpriteBatch();
//		assets = new AssetManager();
		font = new BitmapFont();

		this.screen = new MenuScreen(this);
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0.3f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}
}
