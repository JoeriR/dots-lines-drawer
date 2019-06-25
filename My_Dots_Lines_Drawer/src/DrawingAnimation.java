
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class DrawingAnimation extends AnimationTimer {
	
	protected boolean isAnimationRunning = false;
	
	private DotCanvas canvas = null;
	private List<Dot> dotList = new ArrayList<Dot>();
	
	private Color lineColor = Settings.lineColor;
	private Color backgroundColor = Settings.backgroundColor;
	
	private WritableImage frameBeforeDrawingSubDots = null;
	
	private GraphicsContext graphicsContext;
	
	private int currentIndex = 0;
	private int finalIndex = 0;
	
	private int frameBuffer = Settings.bufferFrames;
	private int currentFrame = 1;
	
	public void start(DotCanvas dotCanvas)
	{
		this.canvas = dotCanvas;
		this.dotList = dotCanvas.dotList;
		this.graphicsContext = dotCanvas.getGraphicsContext2D();
		
		currentIndex = 0;
		finalIndex = dotList.size() - 1;
		
		frameBuffer = Settings.bufferFrames;
		currentFrame = 1;
		
		// Set linecolor
		graphicsContext.setStroke(Settings.lineColor);
		
		dotCanvas.clearDrawnLines();
		
		// checks
		if (dotList.size() < 2)
			return;	// prevent the animation when there are less than 2 dots on the Canvas
		
		this.isAnimationRunning = true;
		
		super.start();
	}
	
	@Override
	public void handle(long currentTime) {
		if (Settings.bufferFrames > 1)
			drawSubLinesBetweenDots();
		else if (Settings.bufferFrames == 1)
			drawOneFullLinePerFrame();
		else
			drawLinesInstantly();
	}
	
	private void drawLinesInstantly() {
		Dot nextDot = null;
		
		// draw background first
		graphicsContext.setFill(Settings.backgroundColor);
		//graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		
		
		for (Dot currentDot : dotList) {
			
			if (currentIndex < finalIndex)	
				nextDot = dotList.get(currentIndex + 1);
			else if (Settings.connectLastDotWithFirstDot)
				nextDot = dotList.get(0); // Get the first node for the last line
			
			graphicsContext.strokeLine(currentDot.getX(), currentDot.getY(), nextDot.getX(), nextDot.getY());
			
			if (currentIndex >= finalIndex)
			{
				System.out.println("drawingAnimation has finished.");
				this.isAnimationRunning = false;
				this.stop();
			}
			
			++currentIndex;
		}
	}
	
	private void drawOneFullLinePerFrame() {
		Dot currentDot = dotList.get(currentIndex);
		Dot nextDot = currentDot;
		
		if (currentIndex < finalIndex)	
			nextDot = dotList.get(currentIndex + 1);
		else if (Settings.connectLastDotWithFirstDot)
			nextDot = dotList.get(0); // Get the first node for the last line
		
		graphicsContext.strokeLine(currentDot.getX(), currentDot.getY(), nextDot.getX(), nextDot.getY());
		
		if (currentIndex >= finalIndex)
		{
			System.out.println("drawingAnimation has finished.");
			this.isAnimationRunning = false;
			this.stop();
		}
		
		++currentIndex;
	}
	
	private void drawSubLinesBetweenDots() {
		//System.out.println("subframes lol");
		
		Dot currentDot = dotList.get(currentIndex);
		Dot nextDot = currentDot;
		
		if (frameBeforeDrawingSubDots == null)
			frameBeforeDrawingSubDots = canvas.snapshot(null, new WritableImage( (int) canvas.getWidth(), (int) canvas.getHeight())); // create a snapshot
		
		if (currentIndex < finalIndex)	
			nextDot = dotList.get(currentIndex + 1);
		else if (Settings.connectLastDotWithFirstDot)
			nextDot = dotList.get(0); // Get the first node for the last line
		
		if (currentFrame < frameBuffer) {
			double currentSubDotX = (nextDot.getX() - currentDot.getX()) / frameBuffer * currentFrame + currentDot.getX();
			double currentSubDotY = (nextDot.getY() - currentDot.getY()) / frameBuffer * currentFrame + currentDot.getY();
			
			graphicsContext.drawImage(frameBeforeDrawingSubDots, 0, 0);
			graphicsContext.strokeLine(currentDot.getX(), currentDot.getY(), currentSubDotX, currentSubDotY);
			++currentFrame;
		}
		else {
			graphicsContext.drawImage(frameBeforeDrawingSubDots, 0, 0);
			graphicsContext.strokeLine(currentDot.getX(), currentDot.getY(), nextDot.getX(), nextDot.getY());
			
			frameBeforeDrawingSubDots = null;
			
			if (currentIndex >= finalIndex)
			{
				System.out.println("drawingAnimation has finished.");
				
				this.isAnimationRunning = false;
				this.stop();
			}
			
			currentFrame = 1;
			++currentIndex;
		}
			
	}
	
	public void stop() {
		this.isAnimationRunning = false;
		frameBeforeDrawingSubDots = null;
		
		super.stop();
	}

}
