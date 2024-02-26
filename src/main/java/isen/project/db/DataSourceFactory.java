package isen.project.db;

import javax.sql.DataSource;

import org.sqlite.SQLiteDataSource;

public class DataSourceFactory {

	private static SQLiteDataSource dataSource;

	private DataSourceFactory() {
		throw new IllegalStateException("This is a static class that should not be instantiated");
	}

	/**
	 * @return a connection to the SQLite Database
	 * 
	 */
	public static DataSource getDataSource() {
		if (dataSource == null) {
			dataSource = new SQLiteDataSource();
			dataSource.setUrl("jdbc:sqlite:database.db");
		}
		return dataSource;
	}
}

