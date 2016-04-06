import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Classifier {

	public Classifier() {
		load();
	}

	public void load() {
		ArrayList<Image> images = new ArrayList<Image>();
		try {
			java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");
			Scanner f = new Scanner(new File(System.getProperty("user.dir") + "/" + "image.data"));
			while (f.hasNext()) {
				boolean[][] newimage = null;
				if (!f.next().equals("P1"))
					System.out.println("Not a P1 PBM file");
				String category = f.next().substring(1);
				int rows = f.nextInt();
				int cols = f.nextInt();

				newimage = new boolean[rows][cols];
				for (int r = 0; r < rows; r++) {
					for (int c = 0; c < cols; c++) {
						newimage[r][c] = (f.findWithinHorizon(bit, 0).equals("1"));
					}
				}
				images.add(new Image(newimage, category));
			}
			f.close();
		} catch (IOException e) {
			System.out.println("Load from file failed");
		}
	}

	public static void main(String[] args) {
		new Classifier();
	}

}
