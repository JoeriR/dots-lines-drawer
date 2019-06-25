import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SideMenu extends VBox {
	private DotCanvas canvasLink;
	
	private DrawingAnimation drawingAnimation = new DrawingAnimation();
	
	// all GUI elements contained by this menu in order
	private Button startAnimationButton;
	private CheckBox toggleCheckBox;
	private Button clearLinesButton;
	private Button clearCanvasButton;
	private HBox bufferFrameBox;
	private ColorPicker lineColorPicker;
	private ColorPicker backgroundColorPicker;
	private NumberField randomizerNumberField;
	private Button randomizerButton;
	private HBox randomizerBox;
	
	public SideMenu(DotCanvas mainCanvas) {
		canvasLink = mainCanvas;
		
		startAnimationButton = new Button("Start Animation");
		startAnimationButton.setOnAction(event -> {
				System.out.println("DrawingAnimation has started");
				drawingAnimation.stop();
				canvasLink.clearDrawnLines();
				drawingAnimation.start(canvasLink);
		});
		
		
		toggleCheckBox = new CheckBox("Connect Last w First");
		toggleCheckBox.setTextFill(Color.WHITE);
		toggleCheckBox.setSelected(Settings.connectLastDotWithFirstDot);
		
		toggleCheckBox.setOnAction(event -> {
				Settings.connectLastDotWithFirstDot = toggleCheckBox.isSelected();
		});
		
		
		clearLinesButton = new Button("Clear lines");
		clearLinesButton.setOnAction(event -> {
				if (!drawingAnimation.isAnimationRunning)
					canvasLink.clearDrawnLines();
		});
		
		
		clearCanvasButton = new Button("Clear Canvas");
		clearCanvasButton.setOnAction(event -> {
				if (!drawingAnimation.isAnimationRunning)
					canvasLink.clearCanvas();
		});
		
		
		bufferFrameBox = new HBox();
		bufferFrameBox.setSpacing(5);
		
		Label bufferFrameLabel = new Label("Frames/Line");
		bufferFrameLabel.setTextFill(Color.WHITE);
		
		TextField bufferFrameTextField = new TextField("" + Settings.bufferFrames);
		
		bufferFrameTextField.setMinWidth(40);
		bufferFrameTextField.setMaxWidth(40);
		
		bufferFrameBox.getChildren().addAll(bufferFrameLabel, bufferFrameTextField);
		bufferFrameBox.setAlignment(Pos.CENTER_LEFT);

		// force the field to be numeric only and limit the field to 3 numbers
	    bufferFrameTextField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                bufferFrameTextField.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	            if (newValue.length() > 3) {
	            	bufferFrameTextField.setText(oldValue);
	            }
	            
	            if (bufferFrameTextField.getText().length() > 0) {
	            	Settings.bufferFrames = Integer.parseInt(bufferFrameTextField.getText()); // changes the bufferFrames Setting to the value of bufferFrameTextField
	            }
	        }
	    });
		
		
		lineColorPicker = new ColorPicker(Settings.lineColor);
		lineColorPicker.setOnAction(event -> Settings.lineColor = lineColorPicker.getValue());
		
		
		backgroundColorPicker = new ColorPicker(Settings.backgroundColor);
		backgroundColorPicker.setOnAction(event -> Settings.backgroundColor = backgroundColorPicker.getValue());
		
		
		randomizerNumberField = new NumberField("50");
		randomizerNumberField.setMinWidth(80);
		randomizerNumberField.setMaxWidth(80);
		
		randomizerButton = new Button("Random");
		randomizerButton.setOnAction(event -> {
			int amount = Integer.parseInt(randomizerNumberField.getText());
			
			double x;
			double y;
			Random random = new Random();
			
			for (int i = 0; i < amount; ++i) {
				x = (double) random.nextInt( (int) mainCanvas.getWidth());
				y = (double) random.nextInt( (int) mainCanvas.getHeight());
				
				mainCanvas.simulateClick(x, y);
			}
		});
		
		randomizerBox = new HBox();
		randomizerBox.getChildren().addAll(randomizerNumberField, randomizerButton);
		randomizerBox.setSpacing(10);
		
		this.setOnKeyPressed(event -> {
			String code = event.getCode().toString();
			
			if (code.equals("ENTER"))
				startAnimationButton.fire();
		});
		
		// prepare the SideMenu itself
		this.getChildren().addAll(startAnimationButton, toggleCheckBox, clearLinesButton, clearCanvasButton, bufferFrameBox, lineColorPicker, backgroundColorPicker, randomizerBox);
		this.setStyle("-fx-border-color: black; -fx-border-width: 3px; -fx-background-color: #444;");
		this.setSpacing(10);
	}
	
	
}
