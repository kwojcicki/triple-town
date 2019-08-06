import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Application {


	private static char[] peices = {
			's',
			's',
			's',
			's',
			's',
			's',
			'b',
			'b',
			'b',
			'b',
			't',
			't',
			'h',
			'H'
	};

	private static Random random = new Random();

	public static char increasePiece(char c) {
		if(c == 's') return 'b';
		if(c == 'b') return 't';
		if(c == 't') return 'h';
		if(c == 'h') return 'H';
		if(c == 'H') return 'x';
		
		return 'x';
		
	}
	
	public static char getNext() {
		return peices[random.nextInt(peices.length)];
	}

	public static void printBoard(char[][] board) {
		for(char[] b: board) {
			System.out.println(Arrays.toString(b));
		}

	}

	public static void combine(char[][] board, char combining, int row, int col) {
		boolean[][] seen = new boolean[board.length][board[0].length];
		int count = combine(board, seen, combining, row, col);

		if(count >= 3) {
			seen = new boolean[board.length][board[0].length];
			change(board, seen, combining, row, col);
			board[row][col] = increasePiece(combining);
		}
	}

	public static void change(char[][] board, boolean[][] seen, char combining, int row, int col) {
		if(seen[row][col]) {
			return;
		}

		seen[row][col] = true;

		//System.out.println(row + " " + col + " " + board[row][col] + " " + combining);
		if(board[row][col] != combining) {
			return;
		}
		
		board[row][col] = '0';
		
		if(row > 0) change(board, seen, combining, row - 1, col);
		if(row < board.length - 1) change(board, seen, combining, row + 1, col);
		if(col > 0) change(board, seen, combining, row, col - 1);
		if(col < board[0].length - 1) change(board, seen, combining, row, col + 1);
	}
	
	public static int combine(char[][] board, boolean[][] seen, char combining, int row, int col) {
		if(seen[row][col]) {
			return 0;
		}

		int ret = board[row][col] == combining ? 1 : 0;
		seen[row][col] = true;
		if(row > 0) ret += combine(board, seen, combining, row - 1, col);
		if(row < board.length - 1) ret += combine(board, seen, combining, row + 1, col);
		if(col > 0) ret += combine(board, seen, combining, row, col - 1);
		if(col < board[0].length - 1) ret += combine(board, seen, combining, row, col + 1);

		return ret;
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		char[][] board = new char[6][6];
		int score = 0;

		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				board[i][j] = '0';
			}
		}

		board[0][0] = 'x';

		while(true) {
			System.out.println("---");
			System.out.println("Score: " + score);
			printBoard(board);
			char toPlace = getNext();
			boolean valid = false;

			int row = 0, col = 0;
			while(!valid) {
				System.out.println("Place: " + toPlace + " (row/col)");

				row = sc.nextInt();
				col = sc.nextInt();

				if(row < board.length && row >= 0 &&
						col < board[0].length && col >= 0 &&
						board[row][col] == '0') {
					valid = true;
				}
			}

			board[row][col] = toPlace;


			combine(board, toPlace, row, col);


			if(board[0][0] == 'a') {
				break;
			}

		}

		sc.close();

	}

}
