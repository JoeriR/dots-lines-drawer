import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Dots_Lines_Main_GUI extends Application {

	// private variables voor de GUI enzo
	private DotCanvas canvas = new DotCanvas(700, 700);
	private GridPane gridpane = new GridPane();

	@Override
	public void start(Stage stage) throws Exception {
		
		// JavaFX GUI spul regelen
		SideMenu sidemenu = new SideMenu(canvas);
		gridpane.add(sidemenu, 0, 0);
		gridpane.add(canvas, 1, 0);
		
		Scene scene = new Scene(gridpane);
		
		stage.setScene(scene);
		stage.sizeToScene();
		
		stage.setTitle("Make your own Dot Drawing - By Joeri");
		stage.show();
		
		stage.setMinWidth(stage.getWidth());
		stage.setMinHeight(stage.getHeight());
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
