package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// data access object class for Customer table
public class CustomerDAO {
	private static Connection connection;

	public CustomerDAO(){
		connection = DbConnection.getConnection();
	}
	
	// method that returns ArrayList of all Customers 
	public Collection<Customer> selectAllCustomers() {
		List<Customer> customerList = new ArrayList<Customer>(); 
		String sqlStatement = new String("SELECT * FROM customer"); 
		PreparedStatement prepSqlStatement = null;
		ResultSet rs = null;
		try{
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()){
				int tempCustomerID = rs.getInt(1);
				String tempName = rs.getString(2);
				String tempAddress = rs.getString(3);
				String tempCity = rs.getString(4);
				String tempState = rs.getString(5);
				int tempPhone = rs.getInt(6);

				customerList.add(new Customer(tempCustomerID, tempName, tempAddress, 
						tempCity, tempState, tempPhone));
			}
			return customerList;  
		}
		catch (SQLException ex){
			ex.printStackTrace();
		}
		return null; 
	}
	
	// Method to delete a Customer 
	public boolean deleteCustomer(int customerID) {
		boolean result = false; 
		String sqlStatement = new String("DELETE FROM customer WHERE customerID = ?"); 
		PreparedStatement prepSqlStatement = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			prepSqlStatement.setString(1, String.valueOf(customerID));
			int rowCount = prepSqlStatement.executeUpdate();
			if (rowCount != 1){
				result = false; 
			} 
			else {
				result = true;
			}
		}
		catch (SQLException ex){
			ex.printStackTrace();
			result = false;
		}
		return result;
	}
}