package kalah;


import board.Board;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import playingProcess.PlayingProcess;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure. Remove this comment (or rather, replace it
 * with something more appropriate)
 */

public class Kalah {
	public static void main(String[] args) {
		new Kalah().play(new MockIO(), false, true);
	}
	/**
	 * Play a game of Kalah.
	 * @param io There to get input from and direct output to
	 * @param vertical If true, then play the game between two humans but orient the board vertically.
	 * If it is false <b>and</b> bmf is false then orient the board horizontally.
	 * @param bmf If vertical if false <b>and</b> this is true then play the game where the second player
	 * (P2) is the "best first move" robot.
	 */
	public void play(IO io, boolean vertical, boolean bmf) {
		PlayingProcess playingProcess = new PlayingProcess(io,vertical);
		Board board = new Board(io,playingProcess.getPlayers(), vertical);
		board.printBoard();

		if (bmf)
			executeBot(io,board,playingProcess);
		if (!bmf)
			executeCommend(io,board,playingProcess);

		endGame(io,board,playingProcess);
	}

	int playerInOperation = 1;// first turn: player 1

	public void executeCommend(IO io, Board board,PlayingProcess playingProcess){
		while (playingProcess.canMove(playerInOperation)) {
			if (actualPlayerPlay(io, board,playingProcess)) break;
		}
	}

	private boolean actualPlayerPlay(IO io, Board board,PlayingProcess playingProcess) {
		String prompt = "Player P" + playerInOperation +
				"'s turn - Specify house number or 'q' to quit: ";
		int commend = io.readInteger(prompt,1,6,-1,"q");
		if (commend == -1){
			io.println("Game over");
			board.printBoard();
			return true;
		}else{
			playingProcess.move(playerInOperation,commend);
			if (playingProcess.notAnotherMove())
				playerInOperation = playingProcess.oppositePlayer(playerInOperation);
		}
		return false;
	}

	public void endGame(IO io,Board board,PlayingProcess playingProcess){
		if(!playingProcess.canMove(playerInOperation)){
			io.println("Game over");
			board.printBoard();
			board.printScores();
		}

	}

	public void executeBot(IO io,Board board,PlayingProcess playingProcess){

		while (playingProcess.canMove(playerInOperation)) {
			if (playerInOperation == 1) {
				if (actualPlayerPlay(io, board,playingProcess)) break;
			}else if (playerInOperation == 2){
				playingProcess.botMove();
				if (playingProcess.notAnotherMove())
					playerInOperation = playingProcess.oppositePlayer(playerInOperation);
			}
		}

	}
}
