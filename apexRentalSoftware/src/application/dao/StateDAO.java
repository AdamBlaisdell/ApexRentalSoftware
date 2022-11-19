package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import application.dao.objects.State;

// data access object class for Rental table
public class StateDAO {
	private static Connection connection;

	public StateDAO() {
		connection = DbConnection.getConnection();
	}

	// method that returns ArrayList of all States
	public Collection<State> selectAllStates() {
		List<State> stateList = new ArrayList<State>();
		String sqlStatement = new String("SELECT * FROM state;");
		PreparedStatement prepSqlStatement = null;
		ResultSet rs = null;
		try {
			prepSqlStatement = connection.prepareStatement(sqlStatement);
			rs = prepSqlStatement.executeQuery();
			while (rs.next()) {
				int tempStateID = rs.getInt(1);
				String tempStateCode = rs.getString(2);
				String tempStateName = rs.getString(3);
				stateList.add(new State(tempStateID, tempStateCode, tempStateName));
			}
			return stateList;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
