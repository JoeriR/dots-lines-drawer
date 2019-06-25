import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DotCanvas extends Canvas {
	public List<Dot> dotList = new ArrayList<Dot>();
	public Font numberFont = new Font("Courier New", 5);
	GraphicsContext gc = this.getGraphicsContext2D();
	
	public DotCanvas(double width, double height)
	{
		super(width, height);
		
		clearCanvas();
		
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("Canvas geklikt op (" + event.getX() + "," + event.getY() + ")");
				
				double clickX = event.getX();
				double clickY = event.getY();
				
				dotList.add(new Dot(clickX, clickY));
				
				gc.fillRect(clickX, clickY, Dot.dotSize, Dot.dotSize);
				gc.fillText(""+(dotList.size()), clickX+3, clickY+3);
			}
		});
	}
	
	public void simulateClick(double x, double y) {
		System.out.println("Canvas simulated click op (" + x + "," + y + ")");
		
		dotList.add(new Dot(x, y));
		
		gc.fillRect(x, y, Dot.dotSize, Dot.dotSize);
		gc.fillText(""+(dotList.size()), x+3, y+3);
	}
	
	public void clearCanvas() {
		dotList.clear();
		
		gc.setFill(Settings.backgroundColor);
		gc.fillRect(0, 0, getWidth(), getHeight());
		gc.strokeRect(0, 0, getWidth(), getHeight());
		gc.setFill(Settings.lineColor);
	}
	
	public void clearDrawnLines() {
		
		gc.setFill(Settings.backgroundColor);
		gc.fillRect(0, 0, getWidth(), getHeight());
		gc.strokeRect(0, 0, getWidth(), getHeight());
		gc.setFill(Settings.lineColor);
		
		for (Dot dot : dotList) {
			gc.fillRect(dot.getX(), dot.getY(), Dot.dotSize, Dot.dotSize);
			gc.fillText(""+(dotList.indexOf(dot) + 1), dot.getX()+3, dot.getY()+3);
		}
		
	}
}
