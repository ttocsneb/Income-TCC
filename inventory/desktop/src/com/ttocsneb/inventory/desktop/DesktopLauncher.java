package com.ttocsneb.inventory.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ttocsneb.inventory.Inventory;

public class DesktopLauncher {
	/**
	 * @param arg
	 */
	public static void main(String[] arg) {
		//Create a configuration for the window.
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//Set the title of the window.
		config.title = "Inventory - Level 3";
		
		//Create the window.
		new LwjglApplication(new Inventory(), config);
	}
}
