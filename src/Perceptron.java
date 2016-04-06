import java.util.ArrayList;
import java.util.Random;

public class Perceptron {
	ArrayList<Feature> features;
	ArrayList<Double> weights;

	public Perceptron(){
		Random generator = new Random();
		features = new ArrayList<Feature>();
		weights = new ArrayList<Double>();
		features.add(new DummyFeature(0));
		for (int i=0; i < 50; i++){
			features.add(new Feature(i));
			weights.add(generator.nextDouble());
		}
	}
}
