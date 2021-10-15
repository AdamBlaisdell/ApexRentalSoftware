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

		public VendorDAO(){
			connection = DbConnection.getConnection();
		}
		
		// method that returns ArrayList of all Vendors 
		public Collection<Vendor> selectAllVendors() {
			List<Vendor> vendorList = new ArrayList<Vendor>(); 
			String sqlStatement = new String("SELECT * FROM vendor"); 
			PreparedStatement prepSqlStatement = null;
			ResultSet rs = null;
			try{
				prepSqlStatement = connection.prepareStatement(sqlStatement);
				rs = prepSqlStatement.executeQuery();
				while (rs.next()){
					int tempVendorID = rs.getInt(1);
					
					String tempName = rs.getString(2);
					String tempAddress = rs.getString(3);
					String tempCity = rs.getString(4);
					String tempState = rs.getString(5);
					String tempWebsite = rs.getString(6);
					int tempPhone = rs.getInt(7);

					vendorList.add(new Vendor(tempVendorID, tempName, tempAddress, tempCity, 
							tempState, tempWebsite, tempPhone));
				}
				return vendorList;  
			}
			catch (SQLException ex){
				ex.printStackTrace();
			}
			return null; 
		}
	}
