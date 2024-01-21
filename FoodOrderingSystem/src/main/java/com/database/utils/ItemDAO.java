package com.database.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.food.ordering.items.RequiredItems;
public class ItemDAO {
	private static final String INSERT_ITEMS_SQL = "INSERT INTO items VALUES(?, ?, ?)";
	private static final String SELECT_ITEMS_BY_ID = "select id,item_name,price_rupee from items where id =?";
	private static final String SELECT_ALL_ITEMS = "select * from items";
	private static final String DELETE_ITEMS_SQL = "delete from items where id = ?";
	private static final String UPDATE_ITEMS_SQL = "update items set item_name = ?,price_rupee= ? where id = ?";

	public void insertItem(RequiredItems item) throws SQLException {
		// try-with-resource statement will auto close the connection.
		try (Connection connection = DbConnection.init(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ITEMS_SQL)) {
			preparedStatement.setInt(1, item.getId());
			preparedStatement.setString(2, item.getName());
			preparedStatement.setDouble(3, item.getPrice());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public RequiredItems selectItem(int id) {
		RequiredItems item = null;
		// Step 1: Establishing a Connection
		try (Connection connection = DbConnection.init();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEMS_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement); 
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name = rs.getString("item_name");
				int rupee = rs.getInt("price_rupee");
				item = new RequiredItems(id, name,rupee);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return item;
	}

	public List < RequiredItems > selectAllItems() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List < RequiredItems> items = new ArrayList < > ();
		// Step 1: Establishing a Connection
		try (Connection connection = DbConnection.init();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ITEMS);) {
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("item_name");
				int rupee = rs.getInt("price_rupee");
				items.add(new RequiredItems(id, name, rupee));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return items;
	}

	public boolean deleteItem(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = DbConnection.init(); PreparedStatement statement = connection.prepareStatement(DELETE_ITEMS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	public boolean updateItem(RequiredItems item) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = DbConnection.init(); PreparedStatement statement = connection.prepareStatement(UPDATE_ITEMS_SQL);) {

			statement.setString(1,item.getName());
			statement.setDouble(2,item.getPrice());
			statement.setInt(3, item.getId());
			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e: ex) {
			if (e instanceof SQLException) {
				e.printStackTrace();
				System.out.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}


}


