import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//TODO: Add Javadoc comments for this class and all its methods. (Task 3)
/**
 * A PlayerHuman class consisting of two message strings and BufferedReader class to get input
 * of row and col from the player and a othello game class and a char showing which token(X or O)
 * the player will place. The messages are used to check if the correctness of the player's 
 * input and store the valid row and col in Move object
 * 
 */
public class PlayerHuman {
	
	private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8";
	private static final String IO_ERROR_MESSAGE = "I/O Error";
	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	private Othello othello;
	private char player;

	/**
	 * Constructs a playerhuman with the given othello game and 
	 * the token of the play will place in this game(X or O) 
	 */
	public PlayerHuman(Othello othello, char player) {
		
		this.othello = othello;
		this.player = player;
	}

	/**
	 * Return a Move Object with the row and col that the player wants to place his token
	 * @return A Move object which stores the row and col for place the token
	 */
	public Move getMove() {
		
		int row = getMove("row: ");
		int col = getMove("col: ");
		return new Move(row, col);
	}
	
	/**
	 * Show the player  either the number he puts will be row index and col index
	 * and return the number the player put
	 * @param message "row: " The number that the player put is the row index for placing the token
	 * 				  "col: " The number that the player put is the col index for placing the token
	 * @return The row index or col index that the player put in the console
	 */
	private int getMove(String message) {
		
		int move, lower = 0, upper = 7;
		while (true) {
			try {
				System.out.print(message);
				String line = PlayerHuman.stdin.readLine();
				move = Integer.parseInt(line);
				if (lower <= move && move <= upper) {
					return move;
				} else {
					System.out.println(INVALID_INPUT_MESSAGE);
				}
			} catch (IOException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
				break;
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
			}
		}
		return -1;
	}
}
