package com.ttocsneb.income.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.ttocsneb.income.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//Set the color of the background on start up.
		config.initialBackgroundColor = Color.WHITE;
		//The screen will not be able to resize.
		config.resizable = false;
		//set the window to be portrait 540x720
		config.width = 540;
		config.height = 720;
		//Set the title of the window.
		config.title = "Income - Ben Jacobs";
		
		//Set the Icon for the program.
		config.addIcon("ico-big.png", FileType.Internal);
		config.addIcon("ico-med.png", FileType.Internal);
		config.addIcon("ico-sml.png", FileType.Internal);
		
		
		new LwjglApplication(new Main(), config);
	}
}
