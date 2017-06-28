class Zilch extends Player {
	private int score = 0;
	private  int highScore = 0;
	
	Zilch() {
		
	}
	Zilch(String name, int score) {
		setName(name);
		this.score = score;
	}
	
	public int roll() {
		return (int)(Math.random()*6)+1;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addToScore(int add) {
		if (add > highScore) {
			highScore = add;
		}
		score += add;
	}
	
	public int getHighScore() {
		return highScore;
	}
	
	public void resetScore() {
		score = 0;
	}
}