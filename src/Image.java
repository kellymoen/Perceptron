
public class Image {
	boolean[][] data;
	String category;

	public Image(boolean[][] data, String category){
		this.data = data;
		this.category = category;
	}

	public boolean[][] getData(){
		return data;
	}

}
