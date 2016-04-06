import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Perceptron {
	ArrayList<Feature> features;
	ArrayList<Double> weights;
	ArrayList<Image> images;
	public static final double TRAINING_VALUE = 0.25;
	public static final int EPOCHS = 1000;

	public Perceptron(String fileName){
		Random generator = new Random();
		features = new ArrayList<Feature>();
		weights = new ArrayList<Double>();
		images = new ArrayList<Image>();
		loadImages(fileName);

		features.add(new DummyFeature(0)); //ADD Dummy feature
		weights.add(1.0);

		for (int i=0; i < 50; i++){ //Init random features with different seeds.
			features.add(new Feature(i*12345));
			weights.add(generator.nextDouble());
		}


		evaluateFeatures();
		trainPerceptron();

	}

	public void trainPerceptron(){
		//Train Perceptron
		int count = 0;
		boolean converged = false;
	 while (!converged && count < EPOCHS){
		int numberCorrect = 0;
		boolean changed = false;
		for(Image image: images){
			ArrayList<Integer> featureOutputs = image.getFeatureOutputs();
			double perceptronOutput = 0;
			for (int i = 0; i < featureOutputs.size(); i++){
				perceptronOutput += featureOutputs.get(i) * weights.get(i);
			}
			if ((perceptronOutput > 0 && image.isYes()) || (perceptronOutput < 0 && !image.isYes())){
				numberCorrect++;
			} else if (perceptronOutput < 0 && image.isYes()) {
				for (int i = 0; i < featureOutputs.size(); i++){
					if (featureOutputs.get(i) == 1){
						double weight = weights.get(i);
						weight += TRAINING_VALUE;
						weights.set(i, weight);
					}
				}
				changed = true;
			} else if (perceptronOutput > 0 && !image.isYes()) {
				for (int i = 0; i < featureOutputs.size(); i++){
					if (featureOutputs.get(i) == 1){
						double weight = weights.get(i);
						weight -= TRAINING_VALUE;
						weights.set(i, weight);
					}
				}
				changed = true;
			}
		//	System.out.printf("Class %b : Perceptron %f\n", image.isYes(), perceptronOutput);
		}
		if (!changed) converged = true;
		System.out.printf("Epoch %d: %d/%d correct\n", count, numberCorrect, images.size());
		if (!converged) count++;
	 }
	 	System.out.println("");
		System.out.printf("Feature 0: DUMMY\n");
		for (int i =1; i < features.size(); i++) {
			System.out.printf("Feature %d:\n", i);
			features.get(i).print();
			System.out.println("");
		}

		System.out.printf("Weight %d (dummy): %f\n", 0, weights.get(0));
		for (int i = 1; i < weights.size(); i++) {
			System.out.printf("Weight %d: %f\n", i, weights.get(i));
		}

	}

	public void loadImages(String data) {
		try {
			java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");
			Scanner f = new Scanner(new File(System.getProperty("user.dir") + "/" + data));
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

	public void evaluateFeatures(){
		for (Feature f: features){
			for (Image img: images){
				img.evaluateFeature(f);
			}
		}

	}

	public static void main(String[] args) {
		new Perceptron(args[0]);
	}

}
