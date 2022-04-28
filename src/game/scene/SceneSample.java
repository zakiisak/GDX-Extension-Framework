package game.scene;

import game.gui.ActionListener;
import game.gui.Button;
import game.gui.Component;
import game.gui.RadioButton;
import game.gui.RadioButtonGroup;

public class SceneSample extends Scene {

	@Override
	public void create() {
		Button button = new Button(40, 40, "Sample Button 1");
		addEntity(button);
		
		final RadioButtonGroup group = new RadioButtonGroup(40, 250);
		Button button2 = new Button(100, 90, "Add new choice");
		button2.setActionListener(new ActionListener() {
			int counter = 3;
			@Override
			public void invoke(Component component) {
				counter++;
				group.addRadioButton(new RadioButton("Choice " + counter));
			}
		});
		button2.setSize(200, 100);
		
		addEntity(button2);
		
		group.addRadioButton(new RadioButton("Choice 1"));
		group.addRadioButton(new RadioButton("Choice 2"));
		
		addEntity(group);
	}

	@Override
	public void destroy() {
		
	}

}
