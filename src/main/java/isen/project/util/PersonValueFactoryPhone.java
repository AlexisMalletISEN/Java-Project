package isen.project.util;

import isen.project.model.Person;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class PersonValueFactoryPhone
		implements Callback<TableColumn.CellDataFeatures<Person, String>, ObservableValue<String>> {

	@Override
	public ObservableValue<String> call(CellDataFeatures<Person, String> cellData) {
		return new SimpleStringProperty(cellData.getValue().getPhoneNumber());
	}
}
