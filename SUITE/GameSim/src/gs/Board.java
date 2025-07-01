package gs;

import java.util.List;

public class Board {

	int   turn      = 0;
	int[] container = new int[20];
	
	public void init() {
		container[ 0] = 0;
		container[ 1] = 2;
		container[ 2] = 2;
		container[ 3] = 2;
		container[ 4] = 2;
		container[ 5] = 2;
		container[ 6] = 2;
		container[ 7] = 2;
		container[ 8] = 2;
		container[ 9] = 0;
		container[10] = 2;
		container[11] = 2;
		container[12] = 2;
		container[13] = 2;
		container[14] = 2;
		container[15] = 2;
		container[16] = 2;
		container[17] = 2;
	}
	
	public List<Integer> moves(List<Integer> list) {
		list.clear();
		if (turn == 0) {
			for (int i=1; i<9; i++) {
				if (container[i] != 0) list.add(i);
			}
		} else {
			for (int i=10; i<17; i++) {
				if (container[i] != 0) list.add(i);
			}
		}
		return list;
	}
	
	public void move(int move) {
		
		int n = container[move];
		container[move] = 0;
		for (int i=1; i<=n; i++) {
			int idx = (move+i) % 18;
			container[idx]++;
		}

		int lastidx = (move+n) % 18;
		
		if (turn == 0 && lastidx>=1 && lastidx <= 8 && container[lastidx] == 1 && /* gegenüberliegender container nicht leer */) {
			....
		}

		if (turn == 1 && lastidx>=10 && lastidx <= 17 && container[lastidx] == 1 && /* gegenüberliegender container nicht leer */) {
			....
		}

		if ((turn == 0) && (lastidx != 0)) {
			turn++;
		}
		if ((turn == 1) && (lastidx != 9)) {
			turn--;
		}
		
	}
	
}
