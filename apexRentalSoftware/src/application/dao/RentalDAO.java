package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import application.dao.objects.Rental;

import java.sql.Timestamp;

// data access object class for Rental table
public class RentalDAO {
	private static Connection connection;

	public RentalDAO() {
		connection = DbConnection.getConnection();
	}

	// method that returns ArrayList of all Rentals
	public Collection<Rental> selectAllRentals() {
		List<Rental> rentalList = new ArrayList<Rental>();
		String sqlStatement = new String("SELECT rental.RentalID, customer.Name, vendor.Name, item.Name,"
				+ " item.Serial, rental.DateTime, rental.Returned, customer.customerID, item.itemID FROM rental JOIN customer ON rental.CustomerID = customer.CustomerID"
				+ " JOIN item ON rental.ItemID = item.ItemID JOIN vendor ON item.VendorID = vendor.VendorID;");
		PreparedStatement prepSqlStatement = null;
		ResultSet rs = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()) {
				int tempRentalID = rs.getInt(1);
				String tempCustomerName = rs.getString(2);
				String tempVendorName = rs.getString(3);
				String tempItemName = rs.getString(4);
				String tempItemSerial = rs.getString(5);
				Timestamp tempRentalDate = rs.getTimestamp(6);
				Boolean tempReturned = rs.getBoolean(7);
				int tempCustomerID = rs.getInt(8);
				int tempItemID = rs.getInt(9);
				rentalList.add(new Rental(tempRentalID, tempCustomerName, tempVendorName, tempItemName, tempItemSerial,
						tempRentalDate, tempReturned, tempCustomerID, tempItemID));
			}
			return rentalList;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// method to insert a Rental
	public boolean insertRental(Rental rental) {
		boolean result = false;
		String sqlStatement = new String("INSERT INTO rental VALUES (NULL, ?, ?, ?, 0)");

		PreparedStatement prepSqlStatement = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setInt(1, rental.getCustomerID());
			prepSqlStatement.setInt(2, rental.getItemID());
			prepSqlStatement.setTimestamp(3, rental.getDate());
			int rowCount = prepSqlStatement.executeUpdate();
			if (rowCount != 1) {
				result = false;
			} else {
				try {
					// update item stocked status
					String sqlStatement2 = new String("UPDATE item SET Stocked = 0 WHERE itemID = ?;");
					PreparedStatement prepSqlStatement2 = null;
					prepSqlStatement2 = connection.prepareStatement(sqlStatement2);
					prepSqlStatement2.setInt(1, rental.getItemID());
					prepSqlStatement2.executeUpdate();
					result = true;
				} catch (SQLException ex) {
					ex.printStackTrace();
					result = false;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	// Method to delete a Rental
	public boolean deleteRental(Rental rental) {
		boolean result = false;
		String sqlStatement = new String("DELETE FROM rental WHERE rentalID = ?");
		PreparedStatement prepSqlStatement = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, String.valueOf(rental.getRentalID()));
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

	// method to return a Rental
	public static boolean returnRental(Rental rental) {
		boolean result = false;
		String sqlStatement = new String("UPDATE Rental SET returned = 1, DateTime = DateTime WHERE rentalID = ?;");
		PreparedStatement prepSqlStatement = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, String.valueOf(rental.getRentalID()));
			int rowCount = prepSqlStatement.executeUpdate();
			if (rowCount != 1) {
				result = false;
			} else {
				try {
					// update item stocked status
					String sqlStatement2 = new String("UPDATE item SET Stocked = 1 WHERE itemID = ?;");
					PreparedStatement prepSqlStatement2 = null;
					prepSqlStatement2 = connection.prepareStatement(sqlStatement2);
					prepSqlStatement2.setInt(1, rental.getItemID());
					prepSqlStatement2.executeUpdate();
					result = true;
				} catch (SQLException ex) {
					ex.printStackTrace();
					result = false;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	// method to update a Rental
	public boolean updateRental(Rental rental) {
		boolean result = false;
		String sqlStatement = new String(
				"UPDATE rental SET CustomerID = ?, ItemID = ?, Returned = 0 WHERE rental.RentalID = ?");
		PreparedStatement prepSqlStatement = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, String.valueOf(rental.getCustomerID()));
			prepSqlStatement.setString(2, String.valueOf(rental.getItemID()));
			prepSqlStatement.setString(3, String.valueOf(rental.getRentalID()));
			int rowCount = prepSqlStatement.executeUpdate();
			if (rowCount != 1) {
				result = false;
			} else {
				try {
					// update item stocked status
					String sqlStatement2 = new String("UPDATE item SET Stocked = 0 WHERE itemID = ?;");
					PreparedStatement prepSqlStatement2 = null;
					prepSqlStatement2 = connection.prepareStatement(sqlStatement2);
					prepSqlStatement2.setInt(1, rental.getItemID());
					prepSqlStatement2.executeUpdate();
					result = true;
				} catch (SQLException ex) {
					ex.printStackTrace();
					result = false;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

}
