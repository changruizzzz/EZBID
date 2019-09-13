package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Employee;

public class EmployeeDao {
	/*
	 * This class handles all the database operations related to the employee table
	 */
	
	public String addEmployee(Employee employee) {

		/*
		 * All the values of the add employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the employee details and return "success" or "failure" based on result of the database insertion.
		 */
		String id = employee.getEmployeeID();
		String address = employee.getAddress();
		String lastName = employee.getLastName();
		String firstName = employee.getFirstName();
		String city = employee.getCity();
		String state = employee.getState();
		String email = employee.getEmail();
		int zip = employee.getZipCode();
		String tel = employee.getTelephone();
		String startDate = employee.getStartDate();
		double rate = employee.getHourlyRate();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String i1 = "insert into Person values(\"" + id + "\",\"" + lastName + "\",\"" + firstName + "\",\"" + address + "\"," + zip + ",\"" + tel + 
					"\",\"" + email + "\",\"" + city + "\",\"" + state + "\")";
			st.executeUpdate(i1);
			st.executeUpdate("insert into Employee values(\"" + startDate + "\"," + rate + ",\"" + id + "\",\"" + email + "\")" );
		} catch(Exception e) {
			return "failure";
		}
		return "success";

	}

	public String editEmployee(Employee employee) {
		/*
		 * All the values of the edit employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */

		String id = employee.getEmployeeID();
		String address = employee.getAddress();
		String lastName = employee.getLastName();
		String firstName = employee.getFirstName();
		String city = employee.getCity();
		String state = employee.getState();
		String email = employee.getEmail();
		int zip = employee.getZipCode();
		String tel = employee.getTelephone();
		String startDate = employee.getStartDate();
		double rate = employee.getHourlyRate();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String i1 = "update Person set LastName=\"" + lastName + "\",FirstName=\"" + firstName + "\",Address=\"" + address + "\",ZipCode=" + zip + ",Telephone=\"" + tel + 
					"\",Email=\"" + email + "\",City=\"" + city + "\",State=\"" + state + "\" where SSN=\"" + id + "\"";
			st.executeUpdate(i1);
			st.executeUpdate("update Employee set StartDate=\"" + startDate + "\",HourlyRate=\"" + rate + "\",Email=\"" + email + "\" where EmployeeID=\"" + id + "\"" );
		} catch(Exception e) {
			return "failure";
		}
		return "success";

	}

	public String deleteEmployee(String employeeID) {
		/*
		 * employeeID, which is the Employee's ID which has to be deleted, is given as method parameter
		 * The sample code returns "success" by default.
		 * You need to handle the database deletion and return "success" or "failure" based on result of the database deletion.
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String i1 = "select * from Person where SSN=\"" + employeeID +"\"";
			ResultSet rs = st.executeQuery(i1);
			if(rs.next()) {
				String email = rs.getString("Email");
				String i2 = "Delete from Login where Email=\"" + email + "\"";
				String i3 = "Delete from Employee where EmployeeID=\"" + employeeID + "\"";
				String i4 = "Delete from Person where SSN=\"" + employeeID + "\"";
				st.executeUpdate(i2);
				st.executeUpdate(i3);
				st.executeUpdate(i4);
			}
			
		} catch(Exception e) {
			return "failure";
		}
		return "success";

	}

	
	public List<Employee> getEmployees() {

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to return details about all the employees must be implemented
		 * Each record is required to be encapsulated as a "Employee" class object and added to the "employees" List
		 */
		List<Employee> employees = new ArrayList<Employee>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "select * from Employee, Person where EmployeeID = SSN";
			ResultSet rs = st.executeQuery(query); 
			while(rs.next()) {
				Employee employee = new Employee();
				employee.setEmployeeID(rs.getString("EmployeeID"));
				employee.setAddress(rs.getString("Address"));
				employee.setLastName(rs.getString("LastName"));
				employee.setFirstName(rs.getString("FirstName"));
				employee.setCity(rs.getString("City"));
				employee.setState(rs.getString("State"));
				employee.setEmail(rs.getString("Email"));
				employee.setZipCode(rs.getInt("ZipCode"));
				employee.setTelephone(rs.getString("Telephone"));
				employee.setHourlyRate(rs.getFloat("HourlyRate"));
				employee.setStartDate(rs.getString("StartDate"));
				employees.add(employee);
			} 
		}catch(Exception e) {
				System.out.println(e);
		}
		return employees;
			
	}

	public Employee getEmployee(String employeeID) {

		/*
		 * The students code to fetch data from the database based on "employeeID" will be written here
		 * employeeID, which is the Employee's ID who's details have to be fetched, is given as method parameter
		 * The record is required to be encapsulated as a "Employee" class object
		 */


		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "select * from Employee, Person where EmployeeID = SSN AND SSN = \"" + employeeID + "\"";
			ResultSet rs = st.executeQuery(query); 
			if(rs.next()) {
				Employee employee = new Employee();
				employee.setEmployeeID(rs.getString("EmployeeID"));
				employee.setAddress(rs.getString("Address"));
				employee.setLastName(rs.getString("LastName"));
				employee.setFirstName(rs.getString("FirstName"));
				employee.setCity(rs.getString("City"));
				employee.setState(rs.getString("State"));
				employee.setEmail(rs.getString("Email"));
				employee.setZipCode(rs.getInt("ZipCode"));
				employee.setTelephone(rs.getString("Telephone"));
				employee.setHourlyRate(rs.getFloat("HourlyRate"));
				employee.setStartDate(rs.getString("StartDate"));
				return employee;
			} 
		}catch(Exception e) {
				System.out.println(e);
		}
		return null;
	}
	
	public Employee getHighestRevenueEmployee() {
		
		/*
		 * The students code to fetch employee data who generated the highest revenue will be written here
		 * The record is required to be encapsulated as a "Employee" class object
		 */
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String q1 = "CREATE OR REPLACE VIEW Sold (CustomerID, SellerID, AuctionID, SoldPrice)\r\n" + 
					"AS\r\n" + 
					"SELECT  distinct B1.CustomerID, P.CustomerID, P.AuctionID, B1.BidPrice AS SoldPrice\r\n" + 
					"FROM    Bid B1, Post P, Auction A\r\n" + 
					"WHERE  B1.BidTime <= P.ExpireDate  AND B1.AuctionID = P.AuctionID \r\n" + 
					"AND B1.BidPrice >= ALL ((SELECT B2.BidPrice FROM Bid B2 WHERE B1.AuctionID = B2.AuctionID))\r\n" + 
					"AND B1.BidPrice >= A.Reserve;";
			String q2 = "CREATE OR REPLACE VIEW CusRepRevenue(EmployeeID, Revenue)\r\n" + 
					"AS\r\n" + 
					"SELECT A.Monitor, SUM(S.SoldPrice)\r\n" + 
					"FROM Auction A, Sold S\r\n" + 
					"WHERE A.AuctionID = S.AuctionID\r\n" + 
					"group by A.Monitor";
			String q3 = "SELECT CRR.EmployeeID, P.LastName, P.FirstName, P.Email\r\n" + 
					"FROM CusRepRevenue CRR, Person P\r\n" + 
					"WHERE CRR.Revenue >= ALL(Select CRR1.Revenue From CusRepRevenue CRR1) AND CRR.EmployeeID = P.SSN;";
			st.execute(q1);
			st.execute(q2);
			ResultSet rs = st.executeQuery(q3);
			if(rs.next()) {
				Employee employee = new Employee();
				employee.setEmail(rs.getString("Email"));
				employee.setFirstName(rs.getString("FirstName"));
				employee.setLastName(rs.getString("LastName"));
				employee.setEmployeeID(rs.getString("EmployeeID"));
				return employee;
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	
		return null;

	}

	public String getEmployeeID(String username) {
		/*
		 * The students code to fetch data from the database based on "username" will be written here
		 * username, which is the Employee's email address who's Employee ID has to be fetched, is given as method parameter
		 * The Employee ID is required to be returned as a String
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "select * from Employee where Email = \"" + username + "\"";
			ResultSet rs = st.executeQuery(query); 
			if(rs.next()) {
				String id = rs.getString("EmployeeID");
				return id;
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
