package isen.project.util;

import isen.project.model.Person;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class PersonValueFactoryId 
	implements Callback<TableColumn.CellDataFeatures<Person, Integer>, ObservableValue<Integer>> {

	@Override
	public ObservableValue<Integer> call(CellDataFeatures<Person, Integer> cellData) {
			return new SimpleIntegerProperty(cellData.getValue().getIdperson()).asObject();
	}
}
