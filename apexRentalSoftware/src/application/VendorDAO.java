package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// data access object class for Vendor table
public class VendorDAO {
	private static Connection connection;

	public VendorDAO() {
		connection = DbConnection.getConnection();
	}

	// method that returns ArrayList of all Vendors
	public Collection<Vendor> selectAllVendors() {
		List<Vendor> vendorList = new ArrayList<Vendor>();
		String sqlStatement = new String("SELECT * FROM vendor");
		PreparedStatement prepSqlStatement = null;
		ResultSet rs = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()) {
				int tempVendorID = rs.getInt(1);
				String tempName = rs.getString(2);
				String tempAddress = rs.getString(3);
				String tempCity = rs.getString(4);
				String tempState = rs.getString(5);
				String tempWebsite = rs.getString(6);
				double tempPhone = rs.getDouble(7);

				vendorList.add(
						new Vendor(tempVendorID, tempName, tempAddress, tempCity, tempState, tempWebsite, tempPhone));
			}
			return vendorList;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	// method to insert a Vendor
	public boolean insertVendor(Vendor vendor) {
		boolean result = false;
		String sqlStatement = new String("INSERT INTO vendor VALUES (NULL, ?, ?, ?, ?, ?, ?)");
		PreparedStatement prepSqlStatement = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);		
			prepSqlStatement.setString(1, vendor.getName());
			prepSqlStatement.setString(2, vendor.getAddress());
			prepSqlStatement.setString(3, vendor.getCity());
			prepSqlStatement.setString(4, vendor.getState());
			prepSqlStatement.setString(5, vendor.getWebsite());
			prepSqlStatement.setDouble(6, vendor.getPhone());
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


	// Method to delete a Vendor
	public boolean deleteVendor(int vendorID) {
		boolean result = false;
		String sqlStatement = new String("DELETE FROM vendor WHERE vendorID = ?");
		PreparedStatement prepSqlStatement = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, String.valueOf(vendorID));
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
