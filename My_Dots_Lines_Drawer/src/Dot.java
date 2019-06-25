import javafx.scene.shape.Rectangle;

public class Dot extends Rectangle {
	public static double dotSize = 2;
	
	public Dot(double x, double y) {
		super.setX(x);
		super.setY(y);
		super.setWidth(dotSize);
		super.setHeight(dotSize);
	}
	
	
}
