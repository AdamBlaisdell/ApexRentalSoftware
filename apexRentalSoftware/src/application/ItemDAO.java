package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// data access object class for Item table
public class ItemDAO {
	private static Connection connection;

	public ItemDAO() {
		connection = DbConnection.getConnection();
	}

	// method that returns ArrayList of all Items
	public Collection<Item> selectAllItems() {
		List<Item> itemList = new ArrayList<Item>();
		String sqlStatement = new String("SELECT * FROM item INNER JOIN vendor ON item.VendorID = vendor.VendorID;");
		PreparedStatement prepSqlStatement = null;
		ResultSet rs = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()) {
				int tempItemID = rs.getInt(1);
				int tempVendorID = rs.getInt(2);
				String tempVendorName = rs.getString(8);
				String tempName = rs.getString(3);
				String tempSerial = rs.getString(4);
				Boolean tempStocked = rs.getBoolean(5);
				double tempCost = rs.getDouble(6);

				itemList.add(new Item(tempItemID, tempVendorID, tempVendorName, tempName, tempSerial, tempStocked, tempCost));
			}
			return itemList;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	// method to insert an Item
	public boolean insertItem(Item item) {
		boolean result = false;
		String sqlStatement = new String("INSERT INTO item VALUES (NULL, ?, ?, ?, 1, ?)");
		PreparedStatement prepSqlStatement = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);	
			prepSqlStatement.setInt(1, item.getVendorID());
			prepSqlStatement.setString(2, item.getName());
			prepSqlStatement.setString(3, item.getSerial());
			prepSqlStatement.setDouble(4, item.getCost());
			int rowCount = prepSqlStatement.executeUpdate();
			if (rowCount != 1) {
				result = false;
			} else {
				result = true;
			}		
		} catch (SQLException ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}
	
	// Method to delete an Item
	public boolean deleteItem(int itemID) {
		boolean result = false;
		String sqlStatement = new String("DELETE FROM item WHERE itemID = ?");
		PreparedStatement prepSqlStatement = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, String.valueOf(itemID));
			int rowCount = prepSqlStatement.executeUpdate();
			if (rowCount != 1) {
				result = false;
			} else {
				result = true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}
}