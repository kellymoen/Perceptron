import java.util.Random;

public class Feature {
	int[] row;
	int[] col;
	boolean[] sgn;

	public Feature(int seed) {
		Random generator = new Random(seed);
		row = new int[4];
		col = new int[4];
		sgn = new boolean[4];
		for (int i = 0; i < 4; i++) {
			row[i] = generator.nextInt(10);
			col[i] = generator.nextInt(10);
			sgn[i] = generator.nextBoolean();
		}
	}

	public int value(Image img) {
		boolean[][] image = img.getData();
		int sum = 0;
		for (int i = 0; i < 4; i++)
			if (image[row[i]][col[i]] == sgn[i])
				sum++;
		return (sum >= 3) ? 1 : 0;
	}

	public void print(){
		for (int i = 0; i < 4; i++){
			System.out.printf("Row: %d Col : %d Connection : %b\n", row[i], col[i], sgn[i]);
		}
	}

}
