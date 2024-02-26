package isen.project.util;

import java.time.LocalDate;

import isen.project.model.Person;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class PersonValueFactoryBirthDate 
	implements Callback<TableColumn.CellDataFeatures<Person, LocalDate>, ObservableValue<LocalDate>> {

	@Override
	public ObservableValue<LocalDate> call(CellDataFeatures<Person, LocalDate> cellData) {
		return new SimpleObjectProperty<>(cellData.getValue().getBirthDate());
	}
}
