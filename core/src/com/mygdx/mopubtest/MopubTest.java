package com.mygdx.mopubtest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MopubTest extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    public static ListenerManager listenerManager;

    public MopubTest() {
        listenerManager = new ListenerManager();
    }

    public void addListener(Listener l) {
        listenerManager.add(l);
    }

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        Gdx.app.log("Core", "Hello");
        System.out.println("Println Hello");
        Gdx.app.debug("Core Debug", "Debug");
//
	}

    private int y = 0;

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isTouched()) {
            Gdx.app.log("Mopub", "Load Ad");

            listenerManager.call(ListenerManager.ListenerType.SHOWAD);
        }

        y++;
		batch.begin();
		batch.draw(img, 0, y);
		batch.end();
	}
}
