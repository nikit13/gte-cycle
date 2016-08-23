package ru.spb.iec.instructor.gte;

import java.net.URL;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class OutputFieldsGroup extends GridPane {

	public static final URL COMPONENT_RESOURCE = OutputFieldsGroup.class.getResource("OutputFieldsGroup.fxml");

	@FXML
	TextField loText;

	@FXML
	TextField qOneText;

	@FXML
	TextField qTwoText;
	
	@FXML
	TextField  nuText;

	public OutputFieldsGroup() {
		DynamicComponentUtil.loadComponentResource(COMPONENT_RESOURCE, this, this);
	}

	public StringProperty loTextProperty() {
		return loText.textProperty();
	}

	public void setLoText(String text) {
		loTextProperty().set(text);
	}

	public String getLoText() {
		return loTextProperty().get();
	}

	public StringProperty qOneTextProperty() {
		return qOneText.textProperty();
	}

	public void setQOneText(String text) {
		qOneTextProperty().set(text);
	}

	public String getQOneText() {
		return qOneTextProperty().get();
	}

	public StringProperty qTwoTextProperty() {
		return qTwoText.textProperty();
	}

	public void setQTwoText(String text) {
		qTwoTextProperty().set(text);
	}

	public String getQTwoText() {
		return qTwoTextProperty().get();
	}
}
