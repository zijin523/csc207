// TODO: Add Javadoc comments for this class and all its methods. (Task 3)
/***
 * An OthelloControllerHumanVSHuman class consisting of an Othello class and two PlayerHuman class
 * It reports what the board looks like before and after once one player places his token.
 * And whose turn is now and who is going for the next and which token he places and where he places 
 * and how many X and O tokens are on the board for each turn. Then who won the game finally. 
 */
public class OthelloControllerHumanVSHuman {

	protected Othello othello;
	PlayerHuman player1, player2;

	/**
	 * Constructs a new OthelloController with a new Othello game, ready to play
	 * with two users at the console.
	 */
	public OthelloControllerHumanVSHuman() {
		
		this.othello = new Othello();
		this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);
		this.player2 = new PlayerHuman(this.othello, OthelloBoard.P2);
	}

	/**
	 * If the game has end, reporting what the final board looks like and 
	 * the number of tokens for each player and 
	 * who win 
	 * If the game has not end, showing what the board looks like before the player places token and 
	 * number of tokens for each player and 
	 * who will place his token.
	 * Then report the player token(X or O) and where he places
	 */
	public void play() {
		
		while (!othello.isGameOver()) {
			this.report();

			Move move = null;
			char whosTurn = othello.getWhoseTurn();

			if (whosTurn == OthelloBoard.P1)
				move = player1.getMove();
			if (whosTurn == OthelloBoard.P2)
				move = player2.getMove();

			this.reportMove(whosTurn, move);
			othello.move(move.getRow(), move.getCol());
		}
		this.reportFinal();
	}

	/**
	 * reporting which players is placing token and where he places.
	 * @param whosTurn Which player's token, O or X
	 * @param move Which coordination on the othelloboard this player place the token
	 */
	private void reportMove(char whosTurn, Move move) {
		System.out.println(whosTurn + " makes move " + move + "\n");
	}

	/**
	 * After placing the token, showing the current board and 
	 * how many tokens has been placed for each player and 
	 * who will place for the next turn
	 */
	private void report() {
		
		String s = othello.getBoardString() + OthelloBoard.P1 + ":" 
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  " 
				+ othello.getWhoseTurn() + " moves next";
		System.out.println(s);
	}

	/**
	 * After placing the token, showing the current board and 
	 * how many tokens has been placed for each player and 
	 * who win the game with more tokens. 
	 */
	private void reportFinal() {
		
		String s = othello.getBoardString() + OthelloBoard.P1 + ":" 
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) 
				+ "  " + othello.getWinner() + " won\n";
		System.out.println(s);
	}
	
	/**
	 * Run main to play two Humans against each other at the console.
	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloControllerHumanVSHuman oc = new OthelloControllerHumanVSHuman();
		oc.play();
	}

}
