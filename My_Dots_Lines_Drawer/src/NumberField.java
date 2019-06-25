
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumberField extends TextField {

	public NumberField(String text) {
		super(text);

		this.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

	}
	
	public void setWidth(int i) {
		super.setWidth(i);
	}
	
	public void setMinWidth(int i) {
		super.setMinWidth(i);
	}
	
	public void setMaxWidth(int i) {
		super.setMaxWidth(i);
	}
}
