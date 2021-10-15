package application;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.Collection;
	import java.util.List;
	import java.sql.Timestamp;

	// data access object class for Rental table
	public class RentalDAO {
		private static Connection connection;

		public RentalDAO(){
			connection = DbConnection.getConnection();
		}
		
		// method that returns ArrayList of all Rentals 
		public Collection<Rental> selectAllRentals() {
			List<Rental> rentalList = new ArrayList<Rental>(); 
			String sqlStatement = new String("SELECT rental.RentalID, customer.Name, vendor.Name, item.Name,"
					+ " item.Serial, rental.DateTime, item.Stocked FROM rental JOIN customer ON rental.CustomerID = customer.CustomerID"
					+ " JOIN item ON rental.ItemID = item.ItemID JOIN vendor ON item.VendorID = vendor.VendorID;"); 
			PreparedStatement prepSqlStatement = null;
			ResultSet rs = null;
			try{
				prepSqlStatement = connection.prepareStatement(sqlStatement);
				rs = prepSqlStatement.executeQuery();
				while (rs.next()){
					int tempRentalID = rs.getInt(1);
					String tempCustomerName = rs.getString(2);
					String tempVendorName = rs.getString(3);
					String tempItemName = rs.getString(4);
					String tempItemSerial = rs.getString(5);
					Timestamp tempRentalDate = rs.getTimestamp(6);
					Boolean tempReturned = rs.getBoolean(7);
					rentalList.add(new Rental(tempRentalID, tempCustomerName, tempVendorName, tempItemName, 
							tempItemSerial, tempRentalDate, tempReturned));
				}
				return rentalList;  
			}
			catch (SQLException ex){
				ex.printStackTrace();
			}
			return null; 
		}
	}
