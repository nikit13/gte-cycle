package ru.spb.iec.instructor.gte;

import java.net.URL;

import javafx.beans.NamedArg;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class FieldLabelPair extends GridPane {

	public static final URL COMPONENT_RESOURCE = FieldLabelPair.class.getResource("FieldLabelPair.fxml");

	@FXML
	Label label;

	@FXML
	TextField field;

	public FieldLabelPair() {
		DynamicComponentUtil.loadComponentResource(COMPONENT_RESOURCE, this, this);
	}

	public FieldLabelPair(@NamedArg("labelText") String labelText) {
		this();
		label.textProperty().set(labelText);
	}

	public FieldLabelPair(@NamedArg("labelText") String labelText, @NamedArg("fieldText") String fieldText) {
		this(labelText);
		field.textProperty().set(fieldText);
	}
	
	public BooleanProperty editableFieldProperty() {
		return field.editableProperty();
	}
	
	public void setEditableField(boolean editable) {
		editableFieldProperty().set(editable);
	}
	
	public boolean isEditableField() {
		return editableFieldProperty().get();
	}

	public StringProperty labelTextProperty() {
		return label.textProperty();
	}

	public StringProperty fieldTextProperty() {
		return field.textProperty();
	}

	public void setFieldText(String text) {
		fieldTextProperty().set(text);
	}

	public String getFieldText() {
		return fieldTextProperty().get();
	}

	public void setLabelText(String text) {
		labelTextProperty().set(text);
	}

	public String getLabelText() {
		return labelTextProperty().get();
	}
}
