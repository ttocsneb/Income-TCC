package com.ttocsneb.inventory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;

public class Inventory extends ApplicationAdapter {

	/**
	 * The stage contains/renders all objects held within it.
	 */
	Stage stage;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationAdapter#create()
	 */
	@Override public void create() {
		//Create a stage with the dimensions of the window.
		stage = new Stage(new ScreenViewport());
		//Load the assets for the stage.
		VisUI.load();
		
		//Create a test label for the window.
		stage.addActor(new VisLabel("Hi!", Color.BLACK));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationAdapter#render()
	 */
	@Override public void render() {
		//Clear the screen with a white color
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//update, and draw the stage.
		stage.act();
		stage.draw();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationAdapter#resize(int, int)
	 */
	@Override public void resize(int width, int height) {
		//Resize the stage when the window resizes.
		stage.getViewport().update(width, width);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ApplicationAdapter#dispose()
	 */
	@Override public void dispose() {
		//unload the assets/stage when the program closes.
		VisUI.dispose();
		stage.dispose();
	}
}
