//import javafx.animation.Animation;
import javafx.application.Application;
import javafx.stage.Stage;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.paint.Color;
//import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
//import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import java.util.Arrays;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.control.TextField;

public class JavaFinal extends Application {
	private Zilch[] players = new Zilch[4];
	private int maxPlayers = 4;
	private int currentPlayer = 0;
	private int playerScore = 0;
	private int addScore = 0;
	private int inLeftNow = 0;
	private int inLeftPrevious = 0;
	private int playerCounter = 0;
	private int[] diceNum = new int[6];
	private int goalScore;
	private ToggleButton[] dice = new ToggleButton[6];
	boolean[] isSelected = new boolean[6];
	boolean[] inLeft = new boolean[6];
	private Label[] score = new Label[4];
	private Label possibleScore;
	private Button roll;
	private Button bank;
	private Button rules;
	private Label info;
	private GridPane gridPaneCenter;
	private VBox left;
	private VBox right;
	private ImageView rulesPic;
	private String note;
	
	public BorderPane startScene() {
			
		BorderPane setupPane = new BorderPane();
		TextField numberOfPlayers = new TextField("Enter how many players (Max 4)");
		TextField playerNames = new TextField("Enter player 1 name");
		TextField highScore = new TextField("Enter the amount you want to play to");
		Button startGame = new Button("Start game");
		rules = new Button("Rules");
		
		Image image = new Image ("startScene.gif");
		setupPane.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		
		setupPane.setCenter(numberOfPlayers);
		setupPane.setBottom(rules);
		setupPane.setAlignment(rules, Pos.CENTER);
		
		for (int i = 0; i < maxPlayers; i++) {
			players[i] = new Zilch("player", 0);
		}
			
		numberOfPlayers.setOnAction(ex -> {
			if (((int)Double.parseDouble(numberOfPlayers.getText()) <= 4)) {
				maxPlayers = (int)Double.parseDouble(numberOfPlayers.getText());
				setupPane.getChildren().remove(numberOfPlayers);
				setupPane.setCenter(playerNames);
			} else {
				numberOfPlayers.setText("please enter a number less or equel to 4");
			}
		});
			
		
		playerNames.setOnAction(ex -> {
			if (playerCounter < maxPlayers) {
				players[playerCounter].setName(playerNames.getText());
				playerNames.setText("Enter player " + (playerCounter + 2) + " name");
				playerCounter++;
				if (playerCounter == maxPlayers) {
					setupPane.getChildren().remove(playerNames);
					setupPane.setCenter(highScore);
				}			
			}
		});
		
		highScore.setOnAction(ex -> {
			goalScore = (int)Double.parseDouble(highScore.getText());
			setupPane.getChildren().remove(highScore);
			setupPane.setCenter(startGame);
		});
			
		startGame.setOnAction(ex -> {
			Stage stage = (Stage) startGame.getScene().getWindow();
			stage.close();
			Scene scene = new Scene(getPane(), 600, 500);
			Stage startScene = new Stage();
			startScene.setTitle("Java Final");
			startScene.setScene(scene);
			startScene.setResizable(false);
			startScene.sizeToScene();
			startScene.show();
		});
			
		rules.setOnAction(ex -> {
			Pane pane = new Pane();
			BorderPane rulesPane = new BorderPane();
			rulesPic = new ImageView("ZilchRules.png");
			ScrollBar scroll = new ScrollBar();
			scroll.setOrientation(Orientation.VERTICAL);
			pane.getChildren().add(rulesPic);
			rulesPane.setCenter(pane);
			rulesPane.setRight(scroll);
				
			scroll.valueProperty().addListener(ov -> rulesPic.setY(-(scroll.getValue() * (1578) / scroll.getMax())));
			Scene scene = new Scene(rulesPane, 1024, 1578/3);
			Stage rulesStage = new Stage();
			rulesStage.setTitle("RULES");
			rulesStage.setScene(scene);
			rulesStage.show();
		});
		
		return setupPane;
	}

	
	protected BorderPane getPane(){
		BorderPane borderPane = new BorderPane();
		Image image = new Image ("backgroundZILCH.gif");
		borderPane.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		
		info = new Label(players[currentPlayer].getName() + " turn");
		borderPane.setTop(info);
		borderPane.setAlignment(info, Pos.CENTER);
		left = new VBox(5);
		left.setPadding(new Insets(2,2,2,2));
		left.setAlignment(Pos.CENTER);
		
		borderPane.setLeft(left);
		
		gridPaneCenter = new GridPane();
		for (int i = 0; i < 6; i++) {
			dice[i] = new ToggleButton();
		}
		rollDice();
		int count = 0;
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 3; y++) {
				gridPaneCenter.add(dice[count], y, x);
				count++;
			}
		}
		
		
		gridPaneCenter.setHgap(10);
		gridPaneCenter.setVgap(10);
		gridPaneCenter.setAlignment(Pos.CENTER);
		
		borderPane.setCenter(gridPaneCenter);
		
		right = new VBox(5);
		for (int i = 0; i < maxPlayers; i++) {
			score[i] = new Label(players[i].getName() + ": " + String.valueOf(players[i].getScore()));
		}
		
		possibleScore = new Label(String.valueOf(0));
		roll = new Button("roll");
		roll.setPrefHeight(20);
		roll.setPrefWidth(100);
		bank = new Button("Bank");
		bank.setPrefHeight(20);
		bank.setPrefWidth(100);
		rules.setPrefHeight(20);
		rules.setPrefWidth(100);
		Label goal = new Label("goal: " + String.valueOf(goalScore));
		right.getChildren().add(goal);
		for (int i = 0; i < maxPlayers; i++) {
			right.getChildren().add(score[i]);
		}
		right.getChildren().addAll(possibleScore, roll, bank, rules);
		right.setPadding(new Insets(5,5,5,5));
		right.setAlignment(Pos.CENTER);
		
		roll.setOnAction(ex -> {
			if (inLeftNow > inLeftPrevious) {
				inLeftPrevious = inLeftNow;
				addScore += checkScore(checkLeft());
				int count1 = 0;
				for (int i = 0; i < 6; i++) {
					if (!inLeft[i]) {
						count1 ++;
					}
					if(dice[i].isSelected()) {
						isSelected[i] = true;
					}
				}
				if (count1 == 0){
					resetButtons();
				}
				rollDice();
				int tempScore = checkScore(checkCenter());
				if (tempScore == 0) {
					playerScore = 0;
					addScore = 0;
					changePlayer();
					resetButtons();
					info.setText(players[currentPlayer].getName() + " turn");
					possibleScore.setText(String.valueOf(0));
					note = "you Zilched player " + players[currentPlayer].getName() + " turn";
					turnScene();
				}
			} else {
				info.setText("Need to take at least one dice " + players[currentPlayer].getName() + " turn");
			}
		});
		
		bank.setOnAction(ex -> {
			addScore += checkScore(checkLeft());
			if (addScore >= 300) {
				players[currentPlayer].addToScore(addScore);
				score[currentPlayer].setText(players[currentPlayer].getString());
				playerScore = 0;
				addScore = 0;
				if (players[currentPlayer].getScore() >= goalScore) {
					note = players[currentPlayer].getName() + " WINS";
					turnScene();
					Stage stage = (Stage) bank.getScene().getWindow();
					stage.close();
				}
				changePlayer();
				resetButtons();
				rollDice();
				int tempScore = checkScore(checkCenter());
				while (tempScore == 0) {
					tempScore = checkScore(checkCenter());
				}
				info.setText(players[currentPlayer].getName() + " turn");
				possibleScore.setText(String.valueOf(0));
				note = "you banked your points player " + players[currentPlayer].getName() + " turn";
				turnScene();
			} else {
				info.setText("must have at laest 300pt " + players[currentPlayer].getName() + " turn");
			}
						
		});
		
				
		dice[0].setOnAction(ex -> {
			if (dice[0].isSelected() && !inLeft[0]) {
				inLeftNow++;
				gridPaneCenter.getChildren().remove(dice[0]);
				left.getChildren().add(dice[0]);
				inLeft[0] = true;
				System.out.println(Arrays.toString(checkLeft()));
				playerScore = checkScore(checkLeft());
				possibleScore.setText(String.valueOf(playerScore + addScore));
			} else {
				dice[0].setSelected(true);
			}
		});
		
		dice[1].setOnAction(ex -> {
			if (dice[1].isSelected() && !inLeft[1]) {
				inLeftNow++;
				gridPaneCenter.getChildren().remove(dice[1]);
				left.getChildren().add(dice[1]);
				inLeft[1] = true;
				System.out.println(Arrays.toString(checkLeft()));
				playerScore = checkScore(checkLeft());
				possibleScore.setText(String.valueOf(playerScore + addScore));
			} else {
				dice[1].setSelected(true);
			}
		});
		
		dice[2].setOnAction(ex -> {
			if (dice[2].isSelected() && !inLeft[2]) {
				inLeftNow++;
				gridPaneCenter.getChildren().remove(dice[2]);
				left.getChildren().add(dice[2]);
				inLeft[2] = true;
				System.out.println(Arrays.toString(checkLeft()));
				playerScore = checkScore(checkLeft());
				possibleScore.setText(String.valueOf(playerScore + addScore));
			} else {
				dice[2].setSelected(true);
			}
		});
		
		dice[3].setOnAction(ex -> {
			if (dice[3].isSelected() && !inLeft[3]) {
				inLeftNow++;
				gridPaneCenter.getChildren().remove(dice[3]);
				left.getChildren().add(dice[3]);
				inLeft[3] = true;
				System.out.println(Arrays.toString(checkLeft()));
				playerScore = checkScore(checkLeft());
				possibleScore.setText(String.valueOf(playerScore + addScore));
			} else {
				dice[3].setSelected(true);
			}
		});
		
		dice[4].setOnAction(ex -> {
			if (dice[4].isSelected() && !inLeft[4]) {
				inLeftNow++;
				gridPaneCenter.getChildren().remove(dice[4]);
				left.getChildren().add(dice[4]);
				inLeft[4] = true;
				System.out.println(Arrays.toString(checkLeft()));
				playerScore = checkScore(checkLeft());
				possibleScore.setText(String.valueOf(playerScore + addScore));
			} else {
				dice[4].setSelected(true);
			}
		});
		
		dice[5].setOnAction(ex -> {
			if (dice[5].isSelected() && !inLeft[5]) {
				inLeftNow++;
				gridPaneCenter.getChildren().remove(dice[5]);
				left.getChildren().add(dice[5]);
				inLeft[5] = true;
				System.out.println(Arrays.toString(checkLeft()));
				playerScore = checkScore(checkLeft());
				possibleScore.setText(String.valueOf(playerScore + addScore));
			} else {
				dice[5].setSelected(true);
			}
		});

		borderPane.setRight(right);
				
		return borderPane;
	}
	
	public void rollDice() {
		for (int i = 0; i < 6; i++) {
			if (!dice[i].isSelected()) {
				diceNum[i] = players[currentPlayer].roll();
				if (diceNum[i] == 1) {
					dice[i].setGraphic(new ImageView("dice1.gif"));
				} else if (diceNum[i] == 2) {
					dice[i].setGraphic(new ImageView("dice2.gif"));
				} else if (diceNum[i] == 3) {
					dice[i].setGraphic(new ImageView("dice3.gif"));
				} else if (diceNum[i] == 4) {
					dice[i].setGraphic(new ImageView("dice4.gif"));
				} else if (diceNum[i] == 5) {
					dice[i].setGraphic(new ImageView("dice5.gif"));
				} else  {
					dice[i].setGraphic(new ImageView("dice6.gif"));
				} 
			}
		}
	}
	
	public int[] checkCenter() {
		int count = 0;
		for (int i = 0; i < 6; i++) {
			if (!dice[i].isSelected()) {
				count ++;
			}
		}
		int[] figScore = new int[count];
		count = 0;
		for (int i = 0; i < 6; i++) {
			if (!dice[i].isSelected() && !isSelected[i]) {
				figScore[count] = diceNum[i];
				count ++;
			}
		}
		return figScore;
	}
	
	public int[] checkLeft() {
		int count = 0;
		for (int i = 0; i < 6; i++) {
			if(dice[i].isSelected()) {
				count ++;
			}
		}
		int[] figScore = new int[count];
		count = 0;
		for (int i = 0; i < 6; i++) {
			if (dice[i].isSelected() && !isSelected[i]) {
				figScore[count] = diceNum[i];
				count ++;
			}
		}
		return figScore;
	}
	
	public int checkScore(int[] figScore) {
		int one, two, three, four, five, six;
		one = two = three = four = five = six = 0;
		Arrays.sort(figScore);
		for (int i = 0; i < figScore.length; i++) {
			if (figScore[i] == 1) {
				one++;
			} else if(figScore[i] == 2) {
				two++;
			} else if(figScore[i] == 3) {
				three++;
			} else if(figScore[i] == 4) {
				four++;
			} else if(figScore[i] == 5) {
				five++;
			} else if(figScore[i] == 6) {
				six++;
			}
		}
		int score = 0;
		if (one == 1 && two == 1 && three == 1 && four == 1 && five == 1 && six == 1) {
			score = 1500;
			one -= 1;
			two -= 1;
			three -= 1;
			four -= 1;
			five -= 1;
			six -= 1;
		}
		if ((figScore.length == 6) && ((figScore[0] == figScore[1]) && (figScore[1] != figScore[2]) && (figScore[2] == figScore[3]) && (figScore[3] != figScore[4]) && (figScore[4] == figScore[5])  && (!Arrays.asList(figScore).contains(0)))) {
			score = 1500;
			one -= 2;
			two -= 2;
			three -= 2;
			four -= 2;
			five -= 2;
			six -= 2;
		}
		if (one >= 3) {
			score += 1000;
			one -= 3;
			while (one > 0) {
				score *= 2;
				one--;
			}
		} 
		if (one < 3 && one > 0) {
			score += one*100;
		}
		if (two >= 3) {
			score += 200;
			two -= 3;
			while (two > 0) {
				score *= 2;
				two--;
			}
		} 
		if (three >= 3) {
			score += 300;
			three -= 3;
			while (three > 0) {
				score *= 2;
				three--;
			}
		}
		if (four >= 3) {
			score += 400;
			four -= 3;
			while (four > 0) {
				score *= 2;
				four--;
			}
		} 
		if (five >= 3) {
			score += 500;
			five -= 3;
			while (five > 0) {
				score *= 2;
				five--;
			}
		}
		if (five < 3 && five > 0) {
			score += five*50;
		} 
		if (six >= 3) {
			score += 600;
			six -= 3;
			while (six > 0) {
				score *= 2;
				six--;
			}			
		}
		return score;
	}
	
	public void changePlayer() {
		if((currentPlayer + 1) < maxPlayers) {
			currentPlayer ++;
		}else {
			currentPlayer = 0;
		}	
	}
	
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
	
	public void turnScene() {
				
		BorderPane turnPane = new BorderPane();
		Label lnote = new Label(note);
		Button startTurn = new Button("Start turn");
		
		Image image = new Image ("turnScene.gif");
		turnPane.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
			
		turnPane.setCenter(lnote);
		turnPane.setBottom(startTurn);
		turnPane.setAlignment(startTurn, Pos.CENTER);
				
		startTurn.setOnAction(ex -> {
			Stage stage = (Stage) startTurn.getScene().getWindow();
			stage.close();
		});
		
		Scene scene = new Scene(turnPane, 400, 333);
		Stage turnScene = new Stage();
		turnScene.setTitle("next player");
		turnScene.setScene(scene);
		turnScene.setResizable(false);
		turnScene.sizeToScene();
		turnScene.show();
	}
	
	@Override	
	public void start(Stage primaryStage) {
		Scene scene = new Scene(startScene(), 300, 250);
		primaryStage.setTitle("Setup");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}