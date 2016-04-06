import java.util.ArrayList;

public class Image {
	boolean[][] data;
	String category;
	ArrayList<Integer> featureOutputs;

	public Image(boolean[][] data, String category){
		this.data = data;
		this.category = category;
		featureOutputs = new ArrayList<Integer>();
	}

	public void evaluateFeature(Feature f){
		featureOutputs.add(f.value(this));
	}

	public boolean[][] getData(){
		return data;
	}

	public ArrayList<Integer> getFeatureOutputs(){
		return featureOutputs;
	}

	public boolean isYes(){
		return category.equals("Yes");
	}
}
