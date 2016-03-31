package com.ttocsneb.inventory.window;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisScrollPane;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;
import com.ttocsneb.inventory.Inventory;
import com.ttocsneb.inventory.save.Order;
import com.ttocsneb.inventory.save.OrderItem;

public class Orders {

	public VisWindow window;

	private VisTextButton newPurchase;

	public Orders(final Stage stage) {
		window = new VisWindow("Orders");
		window.setWidth(400);
		stage.addActor(window);
		
		//Create the scrollable Surface.
		VisTable table = new VisTable();
		VisScrollPane scroll = new VisScrollPane(table);

		//Create the new-purchase button
		newPurchase = new VisTextButton("New Purchase");
		newPurchase.addListener(new ChangeListener() {

			@Override public void changed(ChangeEvent event, Actor actor) {
				//TODO add New Purchase.
			}
		});
		window.add(newPurchase).row();

		//Add the titles to the order grid
		table.add(new VisLabel("Shipped")).padRight(10);
		table.add(new VisLabel("No.")).padRight(10);
		table.add(new VisLabel("Name")).padRight(10);
		table.add(new VisLabel("Cost")).padRight(10);
		table.add(new VisLabel("Address")).row();
		
		
		//Add each order to the order grid.
		float total;
		for(final Order o : Inventory.save.orders) {
			//Create the Shipped check box
			VisCheckBox shipped = new VisCheckBox("");
			shipped.addListener(new ChangeListener() {
				
				@Override public void changed(ChangeEvent event, Actor actor) {
					//When the check box has been pressed, update the system.
					o.shipped = ((VisCheckBox) actor).isChecked();
					Inventory.save.save();
				}
			});
			
			//Add the Shipped checkbox to the order grid
			table.add(shipped).padRight(10);
			//Add the ID
			table.add(new VisLabel(o.id + "")).padRight(10);
			//Add the Name
			table.add(new VisLabel(o.name)).padRight(10);
			//Calculate the total Cost
			total = 0;
			for(OrderItem oi : o.orders) {
				total += oi.price;
			}
			//Add the Cost
			table.add(new VisLabel("$" + total)).padRight(10);
			//Add the Address
			table.add(new VisLabel(o.address)).row();
			
		}

		window.add(scroll);
		
		
		

	}

}
