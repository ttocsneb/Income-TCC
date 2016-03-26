package com.ttocsneb.inventory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.util.InputValidator;
import com.kotcrab.vis.ui.widget.NumberSelector;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisValidatableTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.ttocsneb.inventory.save.Item;
import com.ttocsneb.inventory.save.SaveData;

public class Inventory extends ApplicationAdapter {

	SaveData save = new SaveData();

	/**
	 * The stage contains/renders all objects held within it.
	 */
	Stage stage;

	VisWindow stock;

	/*
	 * (non-Javadoc)
	 * 
	 * Called when the {@link Application} is first created.
	 *
	 * @see com.badlogic.gdx.ApplicationAdapter#create()
	 */
	@Override public void create() {
		save.load();

		// Create a stage with the dimensions of the window.
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		// Load the assets for the stage.
		VisUI.load();

		initStockWindow();

		/*
		 * SaveData.Item i = save.new Item(); i.id = 42; i.name = "TestItem";
		 * i.price = 1.02f; i.stock = 64;
		 * 
		 * save.stock.put(i.id, i); save.save();
		 */
	}

	private void initStockWindow() {
		stock = new VisWindow("Stock");
		stage.addActor(stock);

		VisTable table = new VisTable();
		VisScrollPane scroll = new VisScrollPane(table);
		stock.add(scroll).expand().fill();

		// Create the Section Labels.
		table.add(new VisLabel("ID:Name")).padRight(10);
		table.add().padRight(10);
		table.add(new VisLabel("Stock")).colspan(2).padRight(10);
		table.add(new VisLabel("Price")).padRight(10);
		table.add(new VisLabel("Weight")).row();

		// Create all Stock Items with their information.
		for (Item i : save.stock.values()) {

			// Create the buttons
			VisTextButton restock = new VisTextButton("Restock");
			VisTextButton edit = new VisTextButton("Edit");

			// Create the labels
			final VisLabel name = new VisLabel(i.id + ":" + i.name);
			final VisLabel stock = new VisLabel(i.stock + "");
			final VisLabel price = new VisLabel("$" + i.price);
			final VisLabel weight = new VisLabel(i.weight + "lbs");

			// Add the items to the row.
			table.add(name).padRight(10);
			table.add(edit).padRight(10);
			table.add(restock).padRight(10);
			table.add(stock).padRight(10);
			table.add(price).padRight(10);
			table.add(weight).padRight(10).row();

			final Item info = i;

			// Create the Restock Button.
			restock.addListener(new ChangeListener() {

				@Override public void changed(ChangeEvent event, Actor actor) {
					final VisWindow restock = new VisWindow("Restock " + info.name);
					// Center the window in the screen.
					restock.setPosition(Gdx.graphics.getWidth() / 2 - restock.getWidth() / 2,
							Gdx.graphics.getHeight() / 2 - restock.getHeight() / 2);
					// Open the window.
					restock.fadeIn();
					// Add the window to the stage.
					stage.addActor(restock);

					final NumberSelector selecter = new NumberSelector("Stock", 1, 1, Integer.MAX_VALUE);

					VisTextButton ok = new VisTextButton("OK");
					VisTextButton cancel = new VisTextButton("Cancel");

					restock.add(selecter).colspan(2).padBottom(10).row();
					restock.add(cancel).padRight(10);
					restock.add(ok).padRight(10);

					// Listeners

					ok.addListener(new ChangeListener() {

						@Override public void changed(ChangeEvent event, Actor actor) {
							// update the stock/label
							info.stock += selecter.getValue();
							stock.setText(info.stock + "");
							// save the information.
							save.save();
							// Close the window.
							restock.fadeOut();
						}
					});
					cancel.addListener(new ChangeListener() {

						@Override public void changed(ChangeEvent event, Actor actor) {
							// Close the window.
							restock.fadeOut();
						}
					});

				}
			});

			edit.addListener(new ChangeListener() {

				@Override public void changed(ChangeEvent event, Actor actor) {
					final VisWindow edit = new VisWindow("Edit " + info.name);
					//Set the width/height and place the window in the center of the screen.
					edit.setWidth(200);
					edit.setHeight(175);
					edit.setPosition(Gdx.graphics.getWidth() / 2 - edit.getWidth() / 2,
							Gdx.graphics.getHeight() / 2 - edit.getHeight() / 2);
					//Open the window.
					edit.fadeIn();
					//Add the window to the screen.
					stage.addActor(edit);

					//Create the name field with a validator (must have text)
					final VisValidatableTextField iname = new VisValidatableTextField(new InputValidator() {

						@Override public boolean validateInput(String input) {
							return !input.equals("");
						}
					});
					//Set the empty text message (shows when there is no text in the field)
					iname.setMessageText("Name");
					//Set the text to the already existing setting.
					iname.setText(info.name);

					//Create the price field with a validator (Must be in the form of money ($ ok))
					final VisValidatableTextField iprice = new VisValidatableTextField(new InputValidator() {

						@Override public boolean validateInput(String input) {
							input = input.replace("$", "");

							try {
								Float.parseFloat(input);
							}
							catch (Exception e) {
								return false;
							}

							return true;
						}
					});
					iprice.setMessageText("Price");
					iprice.setText("$" + info.price);

					//Create the weight field with a validator (Must be a number (lbs,pound,pounds ok))
					final VisValidatableTextField iweight = new VisValidatableTextField(new InputValidator() {

						@Override public boolean validateInput(String input) {
							input = input.replace("lbs", "").replace("pounds", "").replace("pound", "");
							try {
								Float.parseFloat(input);
							}
							catch (Exception e) {
								return false;
							}
							return true;
						}
					});
					iweight.setMessageText("Weight");
					iweight.setText(info.weight + " lbs");

					//Creat the cancel/ok buttons
					VisTextButton cancel = new VisTextButton("Cancel");
					VisTextButton ok = new VisTextButton("OK");

					//add the inputs/buttons to the window.
					edit.add(iname).pad(10).colspan(2).row();
					edit.add(iprice).padBottom(10).colspan(2).row();
					edit.add(iweight).padBottom(10).colspan(2).row();
					edit.add(cancel).padRight(10);
					edit.add(ok);

					//Set the cancel Listener
					cancel.addListener(new ChangeListener() {

						@Override public void changed(ChangeEvent event, Actor actor) {
							edit.fadeOut();
						}
					});

					
					//Set the ok listener
					ok.addListener(new ChangeListener() {

						@Override public void changed(ChangeEvent event, Actor actor) {
							//Only allow all valid inputs before saving 
							if (iname.isInputValid() && iweight.isInputValid() && iprice.isInputValid()) {
								//set the values.
								info.name = iname.getText();
								info.price = Float.parseFloat(iprice.getText().replace("$", ""));
								info.weight = Float.parseFloat(
										iweight.getText().replace("lbs", "").replace("pounds", "").replace("pound", ""));
								
								//Set the labels.
								name.setText(info.id + ":" + info.name);
								price.setText("$" + info.price);
								weight.setText(info.weight + "lbs");
								
	
								save.save();
								//Close the window.
								edit.fadeOut();
								
							}
						}
					});

				}
			});

		}

		stock.setWidth(500);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * Called when the {@link Application} should render itself.
	 * 
	 * @see com.badlogic.gdx.ApplicationAdapter#render()
	 */
	@Override public void render() {
		// Clear the screen with a white color
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// update, and draw the stage.
		stage.act();
		stage.draw();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Called when the {@link Application} is resized. This can happen at any
	 * point during a non-paused state but will never happen before a call to
	 * {@link #create()}.
	 * 
	 * @param width the new width in pixels
	 * 
	 * @param height the new height in pixels
	 * 
	 * @see com.badlogic.gdx.ApplicationAdapter#resize(int, int)
	 */
	@Override public void resize(int width, int height) {
		// Resize the stage when the window resizes.
		stage.getViewport().update(width, width);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Called when the {@link Application} is destroyed. Preceded by a call to
	 * {@link #pause()}.
	 * 
	 * @see com.badlogic.gdx.ApplicationAdapter#dispose()
	 */
	@Override public void dispose() {
		// unload the assets/stage when the program closes.
		VisUI.dispose();
		stage.dispose();
	}
}
