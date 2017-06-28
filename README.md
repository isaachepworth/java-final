# java-final
My Zilch game that I programmed is for me to discover what type of prgoramming I would like to do as a career. The game itself is for the times when someone wants to just play a quick game, by their selves or with friends. One of the challenges with programming this game was making sure that you stayed in bounds of the rules. I learned a lot while doing this project. I created this program using arrays, objects, photos, and methods. This is an example of one of the methods I used. This particular one resets the buttons (aka dice) back to the center pane. Don't be too greedy or you just might get Zilch!

public void resetButtons() {
		left.getChildren().clear();
		gridPaneCenter.getChildren().clear();
		int count = 0;
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 3; y++) {
				gridPaneCenter.add(dice[count], y, x);
				count++;
			}
			
		}
		for (int i = 0; i < 6; i++) {
			dice[i].setSelected(false);
			inLeft[i] = false;
			isSelected[i] = false;
		}
	}
