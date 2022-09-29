package jogadores;

public class Jogador_Caverna_Do_Dragao extends Jogador {

	public Jogador_Caverna_Do_Dragao(String nome) {
		super(nome);
	}

	static Boolean there_is_movement(int flap[][]) {
		for (int i = 0; i < flap.length; i++)
			for (int j = 0; j < flap.length; j++)
				if (flap[i][j] == -1) {
					return true;
				}
		return false;
	}

	static int check_probabilities(int flap[][], int symbol) {
		int win = 0;
		int miss = 0;

		for (int line = 0; line < flap.length; line++) {
			for (int column = 0; column < flap.length; column++) {
				if (flap[line][column] == symbol) {
					win++;
				} else if (flap[line][column] == -1 * (symbol - 1)) {
					miss++;
				}
			}

			if (win == flap.length) {
				return +10;
			} else if (miss == flap.length) {
				return -10;
			}
			win = 0;
			miss = 0;
		}

		win = 0;
		miss = 0;
		for (int line = 0; line < flap.length; line++) {
			for (int column = 0; column < flap.length; column++) {
				if (flap[column][line] == symbol) {
					win++;
				} else if (flap[column][line] == -1 * (symbol - 1)) {
					miss++;
				}
			}

			if (win == flap.length) {
				return +10;
			} else if (miss == flap.length) {
				return -10;
			}
			win = 0;
			miss = 0;
		}

		win = 0;
		miss = 0;
		for (int i = 0; i < flap.length; i++) {
			if (flap[i][i] == symbol) {
				win++;
			} else if (flap[i][i] == -1 * (symbol - 1)) {
				miss++;
			}
		}
		if (win == flap.length) {
			return +10;
		} else if (miss == flap.length) {
			return -10;
		}

		win = 0;
		miss = 0;
		int utmost_indice = flap.length - 1;
		for (int i = 0; i <= utmost_indice; i++) {
			if (flap[i][utmost_indice - i] == symbol) {
				win++;
			} else if (flap[i][utmost_indice - i] == -1 * (symbol - 1)) {
				miss++;
			}
		}
		if (win == flap.length) {
			return +10;
		} else if (miss == flap.length) {
			return -10;
		}

		return 0;
	}

	@Override
	public int[] jogar(int[][] board_flap) {
		int[] motion = new int[2];
		motion[0] = -1;
		motion[1] = -1;
		int best_value = -1000;

		for (int x = 0; x < board_flap.length; x++) {
			for (int y = 0; y < board_flap.length; y++) {
				if (board_flap[x][y] == -1) {

					board_flap[x][y] = this.getSimbolo();

					int amount_movement = minimax_method(board_flap, 0, false, this.getSimbolo());

					board_flap[x][y] = -1;

					if (amount_movement > best_value) {
						motion[0] = x;
						motion[1] = y;
						best_value = amount_movement;
					}
				}
			}
		}

		return motion;
	}

	static int minimax_method(int flap[][], int depth, Boolean is_utmost, int symbol) {

		if (flap.length > 10) {
			if (depth > 0) {
				return 0;
			}
		}
		if (flap.length > 6) {
			if (depth > 2) {
				return 0;
			}
		}
		if (flap.length > 5) {
			if (depth > 3) {
				return 0;
			}
		}
		if (flap.length > 4) {
			if (depth > 4) {
				return 0;
			}
		}
		if (flap.length > 3) {
			if (depth > 5) {
				return 0;
			}
		}

		int amount = check_probabilities(flap, symbol);

		if (amount == 10) {
			return amount;
		}
		if (amount == -10) {
			return amount;
		}
		if (!there_is_movement(flap)) {
			return 0;
		}

		if (is_utmost) {
			int best_value = -1000;

			for (int i = 0; i < flap.length; i++) {
				for (int j = 0; j < flap.length; j++) {

					if (flap[i][j] == -1) {

						flap[i][j] = symbol;

						best_value = Math.max(best_value, minimax_method(flap, depth + 1, !is_utmost, symbol));

						flap[i][j] = -1;

					}
				}
			}
			return best_value;
		} else {
			int best_value = 1000;

			for (int i = 0; i < flap.length; i++) {
				for (int j = 0; j < flap.length; j++) {

					if (flap[i][j] == -1) {

						flap[i][j] = -1 * (symbol - 1);

						best_value = Math.min(best_value, minimax_method(flap, depth + 1, !is_utmost, symbol)); // montando
																												// a

						flap[i][j] = -1;

					}
				}
			}

			return best_value;
		}
	}
}
