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

	public ItemDAO(){
		connection = DbConnection.getConnection();
	}
	
	// method that returns ArrayList of all Items 
	public Collection<Item> selectAllItems() {
		List<Item> itemList = new ArrayList<Item>(); 
		String sqlStatement = new String("SELECT * FROM item INNER JOIN vendor ON item.VendorID = vendor.VendorID;"); 
		PreparedStatement prepSqlStatement = null;
		ResultSet rs = null;
		try{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				int tempItemID = rs.getInt(1);
				String tempVendorName = rs.getString(8);
				String tempName = rs.getString(3);
				String tempSerial = rs.getString(4);
				Boolean tempStocked = rs.getBoolean(5);
				double tempCost = rs.getDouble(6);

				itemList.add(new Item(tempItemID, tempVendorName, tempName, 
						tempSerial, tempStocked, tempCost));
			}
			return itemList;  
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return null; 
	}
}