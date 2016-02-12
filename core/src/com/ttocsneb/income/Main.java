package com.ttocsneb.income;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.InputValidator;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.LinkLabel;
import com.kotcrab.vis.ui.widget.NumberSelector;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisValidableTextField;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.ttocsneb.income.save.Save;
import com.ttocsneb.income.save.Transaction;

public class Main extends ApplicationAdapter {
	private static final long WEEK = 604800000;// ms/week

	Stage stage;

	Save save;

	VisTable table;
	VisTable trans;

	VisLabel lTotal, lAvgExpense, lAvgIncome;
	double total, avgExpense, avgIncome;

	@Override
	public void create() {
		// load the previous transactions.
		save = Save.load();

		// Load the UI
		VisUI.load();

		// Create the sage to draw the ui.
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		// Create a link to the sourceCode
		LinkLabel l = new LinkLabel("SourceCode",
				"https://github.com/ttocsneb/Income-TCC");
		l.setPosition(0, 720, Align.topLeft);
		stage.addActor(l);

		// Create a table to contain all of the UI
		table = new VisTable(true);
		table.setFillParent(true);
		// Add the table to the stage.
		stage.addActor(table);

		initPrevTrans();

		initInfo();

	}

	/**
	 * Calculate the Totals and averages for the info window.
	 */
	private void calculate() {
		//Reset the values to 0
		total = 0;
		avgExpense = 0;
		avgIncome = 0;

		int c = 0;
		
		//Get the current time, used to find if a transaction as been made more than a week ago.
		long time = new Date().getTime();

		//Go through each transaction, add up the totals, and average the averages.
		for (Transaction t : save.transactions) {
			total += t.income - t.expense;

			//Check if the seleccted transaction has been made in less than a week.
			if (time - t.date < WEEK) {
				c++;
				Gdx.app.log("Within Week!", new Date(t.date).toString());
				avgExpense += t.expense;
				avgIncome += t.income;
				Gdx.app.log("Time from now",
						(Math.round((time - t.date) / 8640000000.0) / 100f)
								+ " days");
			}
		}
		//Get the averages.
		avgExpense /= c;
		avgIncome /= c;

		//Set the values for the info window.
		lTotal.setText("$" + total);
		lAvgExpense.setText("Avg: $" + Math.round(avgExpense * 10)
				/ 10f);
		lAvgIncome.setText("Avg: $" + Math.round(avgIncome * 10) / 10f);// TODO

	}

	/**
	 * Create the Info Window.
	 */
	private void initInfo() {
		//Create the actual window, with a size of 300x250
		VisWindow w = new VisWindow("Info");
		w.setBounds(540 - 300, 0, 300, 250);

		//Create the balance label.
		w.add(new VisLabel("Balance")).colspan(2).row();
		w.add(lTotal = new VisLabel("")).pad(10).colspan(2).row();

		//Craete the week info.
		w.add(new VisLabel("Week")).colspan(2).row();
		w.add(new VisLabel("Income"));
		w.add(new VisLabel("Expence")).row();
		w.add(lAvgExpense = new VisLabel("")).pad(10);
		w.add(lAvgIncome = new VisLabel("")).pad(10).row();
		calculate();

		//Create a new Transaction button.  When pressed a new window will be created.
		VisTextButton ntrans = new VisTextButton("New Transaction");
		ntrans.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//Create a new Window with a size of 350x250
				final VisWindow w = new VisWindow("New Transaction");
				w.setBounds(540/2-350/2, 720/2-250/2, 350, 250);

				// //////////DATE/////////////
				//Create the date option.
				
				//Hold the date in a table.
				VisTable t = new VisTable();

				t.add(new VisLabel("Date")).padBottom(15).colspan(3).row();
				
				//Get the current date.
				String[] d = new Date().toString().split(" ");

				//Create the month option.
				final VisSelectBox<Month> month = new VisSelectBox<Month>();
				month.setItems(Month.values());
				t.add(month).padRight(5);

				//Set the selected month to the current month.
				if (d[1].toLowerCase().equals("jan"))
					month.setSelected(Month.JAN);
				else if (d[1].toLowerCase().equals("feb"))
					month.setSelected(Month.FEB);
				else if (d[1].toLowerCase().equals("mar"))
					month.setSelected(Month.MAR);
				else if (d[1].toLowerCase().equals("apr"))
					month.setSelected(Month.APR);
				else if (d[1].toLowerCase().equals("may"))
					month.setSelected(Month.MAY);
				else if (d[1].toLowerCase().equals("jun"))
					month.setSelected(Month.JUN);
				else if (d[1].toLowerCase().equals("jul"))
					month.setSelected(Month.JUL);
				else if (d[1].toLowerCase().equals("aug"))
					month.setSelected(Month.AUG);
				else if (d[1].toLowerCase().equals("sep"))
					month.setSelected(Month.SEP);
				else if (d[1].toLowerCase().equals("oct"))
					month.setSelected(Month.OCT);
				else if (d[1].toLowerCase().equals("nov"))
					month.setSelected(Month.NOV);
				else if (d[1].toLowerCase().equals("dec"))
					month.setSelected(Month.DEC);

				//Get the day of the moth/year
				int da = 0;
				int yr = 0;
				try {
					da = Integer.parseInt(d[2]);
					yr = Integer.parseInt(d[5]);
				} catch (Exception e) {

				}

				//Create the day option, and set the selected day to today.
				final NumberSelector day = new NumberSelector("Day", da, 1, 31);
				t.add(day).padRight(5);

				//Create the year option, and set the selected year to this year.
				final NumberSelector year = new NumberSelector("Year", yr,
						1900, yr);
				t.add(year);
				
				//add the table to the window.
				w.add(t).colspan(2).row();

				// /////////TRANSACTION/////////////

				w.add(new VisLabel("Transaction")).pad(15).colspan(2).row();

				//Create the label for the income/expense options.
				w.add(new VisLabel("Income"));
				w.add(new VisLabel("Expense")).row();

				//Create the Income field.
				final VisValidableTextField in = new VisValidableTextField(
						new MoneyValidator());
				in.setText("$0.00");
				w.add(in).padRight(1);

				//Create the Expense Field.
				final VisValidableTextField out = new VisValidableTextField(
						new MoneyValidator());
				out.setText("$0.00");
				w.add(out).row();

				//Create the Done Button, when pressed, save the settings.
				VisTextButton done = new VisTextButton("Done");
				done.addListener(new ChangeListener() {

					@Override
					public void changed(ChangeEvent event, Actor actor) {
						Transaction t = new Transaction();

						//Convert the selected date to a timestamp.
						try {
							t.date = new SimpleDateFormat("yyyy-MM-dd",
									Locale.ENGLISH).parse(year.getValue() + "-"
									+ month.getSelected().getMonth() + "-"
									+ day.getValue()).getTime();

							Gdx.app.log("TEST", new Date(t.date).toString());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
						//Set the expense.
						try {
							t.expense = Long.parseLong(out.getText()
									.replaceAll("[$,]", ""));
						} catch (Exception e) {

						}

						//Set the income.
						try {
							t.income = Long.parseLong(in.getText().replaceAll(
									"[$,]", ""));
						} catch (Exception e) {
						}

						//Save the transaction.
						save.transactions.add(t);
						Save.save();
						addTrans(t);
						//Recalculate the total/average.
						calculate();
						//Close the window.
						w.fadeOut();
					}
				});
				w.add(done).padTop(5);

				//Create a cancel button.
				VisTextButton cancel = new VisTextButton("Cancel");
				cancel.addListener(new ChangeListener() {

					@Override
					public void changed(ChangeEvent event, Actor actor) {
						//Close the window without saving.
						w.fadeOut();
					}
				});
				cancel.setColor(Color.RED);
				w.add(cancel);

				//add the window to the stage.
				stage.addActor(w.fadeIn());

			}
		});
		w.add(ntrans).colspan(2);

		//Add the window to the stage.
		stage.addActor(w);
	}

	/**
	 * Validates money fields to only accept money.
	 * @author Ben
	 *
	 */
	private class MoneyValidator implements InputValidator {

		@Override
		public boolean validateInput(String input) {
			//return true if the string can be parsed.
			try {
				Float.parseFloat(input.replaceAll("[$,]", ""));
				return true;
			} catch (Exception e) {
				return false;
			}
		}

	}

	/**
	 * Add a transaction to the save object.
	 * @param t
	 */
	private void addTrans(Transaction t) {
		// Split the date into its separate components.
		String[] d = new Date(t.date).toString().split(" ");
		// Set the date to a simplified version of the original.
		trans.add(new VisLabel(d[1] + " " + d[2] + ", " + d[5])).padRight(20);
		trans.add(new VisLabel("+ $" + t.income)).padRight(20);
		trans.add(new VisLabel("- $" + t.expense)).row();
	}

	/**
	 * Init the window to view old transactions.
	 */
	private void initPrevTrans() {

		//Create a window 300x450
		VisWindow w = new VisWindow("Transactions");
		w.setBounds(540 - 300, 720 - 450, 300, 450);

		//Create a table to hold the transactions in.
		trans = new VisTable(true);

		//Add a title to the window.
		w.add(new VisLabel("Date")).padRight(40);
		w.add(new VisLabel("Income"));
		w.add(new VisLabel("Expense")).row();

		//Add all transactions from previous sessions.
		for (Transaction t : save.transactions) {
			addTrans(t);
		}

		//Make the table scroll.
		VisScrollPane scroll = new VisScrollPane(trans);
		scroll.setColor(Color.DARK_GRAY);
		scroll.setSmoothScrolling(true);
		w.add(scroll).colspan(3);
		stage.addActor(w);
	}

	@Override
	public void render() {
		// Clear the Screen.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Update the Ui for the stage.
		stage.act();

		// Draw the Stage.
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void dispose() {
		// Free the stage.
		stage.dispose();
		VisUI.dispose();
	}
}
