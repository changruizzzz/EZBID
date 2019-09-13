package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Employee;
import model.Customer;

import java.util.stream.IntStream;

public class CustomerDao {
	/*
	 * This class handles all the database operations related to the customer table
	 */
	
	/**
	 * @param String searchKeyword
	 * @return ArrayList<Customer> object
	 */
	public List<Customer> getCustomers(String searchKeyword) {
		/*
		 * This method fetches one or more customers based on the searchKeyword and returns it as an ArrayList
		 */
		
		List<Customer> customers = new ArrayList<Customer>();

		/*
		 * The students code to fetch data from the database based on searchKeyword will be written here
		 * Each record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "select * from Customer, Person where CustomerID = SSN";
			if(searchKeyword != null) {
				query += " AND (FirstName like \'%" + searchKeyword + "%\' OR LastName like \'%" + searchKeyword + "%\')";
			}
			ResultSet rs = st.executeQuery(query); // 
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(rs.getString("CustomerID"));
				customer.setAddress(rs.getString("Address"));
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setEmail(rs.getString("Email"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setCreditCard(rs.getString("CreditCardNum"));
				customer.setRating(rs.getInt("Rating"));
				customers.add(customer);
			} 
		}catch(Exception e) {
				System.out.println(e);
			
		}
		return customers;

	}


	public Customer getHighestRevenueCustomer() {
		/*
		 * This method fetches the customer who generated the highest total revenue and returns it
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
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
			String q2 = "CREATE OR REPLACE VIEW CustomerRevenue (CustomerID, Revenue)\r\n" + 
					"AS\r\n" + 
					"SELECT S.SellerID, SUM(S.SoldPrice)\r\n" + 
					"FROM Sold S\r\n" + 
					"GROUP BY  S.SellerID;";
			String q3 = "SELECT CR.CustomerID, P.LastName, P.FirstName, P.Email\r\n" + 
					"FROM CustomerRevenue CR, Person P\r\n" + 
					"WHERE CR.Revenue >= ALL(Select CR1.Revenue From CustomerRevenue CR1) AND CR.CustomerID = P.SSN";
			st.execute(q1);
			st.execute(q2);
			ResultSet rs = st.executeQuery(q3);
			if(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(rs.getString("CustomerID"));
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setEmail(rs.getString("Email"));
				return customer;
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	
		return null;
		
	}

	public List<Customer> getCustomerMailingList() {

		/*
		 * This method fetches the all customer mailing details and returns it
		 * The students code to fetch data from the database will be written here
		 * Each customer record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		
		List<Customer> customers = new ArrayList<Customer>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "select * from Customer, Person where CustomerID = SSN";
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(rs.getString("CustomerID"));
				customer.setAddress(rs.getString("Address"));
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setEmail(rs.getString("Email"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setCreditCard(rs.getString("CreditCardNum"));
				customer.setRating(rs.getInt("Rating"));
				customers.add(customer);
			} 
		}catch(Exception e) {
				System.out.println(e);
			
		}
		return customers;
	}

	public Customer getCustomer(String customerID) {

		/*
		 * This method fetches the customer details and returns it
		 * customerID, which is the Customer's ID who's details have to be fetched, is given as method parameter
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "select * from Customer, Person where CustomerID = SSN AND SSN = \"" + customerID + "\"";
			ResultSet rs = st.executeQuery(query); 
			if(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(rs.getString("CustomerID"));
				customer.setAddress(rs.getString("Address"));
				System.out.println(customer.getAddress());
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setEmail(rs.getString("Email"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setCreditCard(rs.getString("CreditCardNum"));
				customer.setRating(rs.getInt("Rating"));
				return customer;
			} 
		}catch(Exception e) {
				System.out.println(e);
		}
		return null;
	}
	
	public String deleteCustomer(String customerID) {

		/*
		 * This method deletes a customer returns "success" string on success, else returns "failure"
		 * The students code to delete the data from the database will be written here
		 * customerID, which is the Customer's ID who's details have to be deleted, is given as method parameter
		 */
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String i1 = "select * from Person where SSN=\"" + customerID +"\"";
			ResultSet rs = st.executeQuery(i1);
			if(rs.next()) {
				String email = rs.getString("Email");
				String i2 = "Delete from Login where Email=\"" + email + "\"";
				String i3 = "Delete from Customer where CustomerID=\"" + customerID + "\"";
				String i4 = "Delete from Person where SSN=\"" + customerID + "\"";
				st.executeUpdate(i2);
				st.executeUpdate(i3);
				st.executeUpdate(i4);
			}
			
		} catch(Exception e) {
			return "failure";
		}
		return "success";
		
	}


	public String getCustomerID(String username) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "select * from Customer where Email = \"" + username + "\"";
			ResultSet rs = st.executeQuery(query); 
			if(rs.next()) {
				String id = rs.getString("CustomerID");
				return id;
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}


	public List<Customer> getSellers() {
		
		/*
		 * This method fetches the all seller details and returns it
		 * The students code to fetch data from the database will be written here
		 * The seller (which is a customer) record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		List<Customer> customers = new ArrayList<Customer>();

		/*
		 * The students code to fetch data from the database based on searchKeyword will be written here
		 * Each record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "select distinct C.CustomerID, P.Address, P.LastName, P.FirstName, P.City, P.State, P.Email, P.ZipCode, P.Telephone, C.CreditCardNum, C.Rating from Customer C, Person P, Post PS where C.CustomerID = P.SSN AND C.CustomerID = PS.CustomerID" ;
			ResultSet rs = st.executeQuery(query); // 
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerID(rs.getString("CustomerID"));
				customer.setAddress(rs.getString("Address"));
				customer.setLastName(rs.getString("LastName"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setCity(rs.getString("City"));
				customer.setState(rs.getString("State"));
				customer.setEmail(rs.getString("Email"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setCreditCard(rs.getString("CreditCardNum"));
				customer.setRating(rs.getInt("Rating"));
				customers.add(customer);
			} 
		}catch(Exception e) {
				System.out.println(e);
			
		}
		return customers;


	}


	public String addCustomer(Customer customer) {

		/*
		 * All the values of the add customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the customer details and return "success" or "failure" based on result of the database insertion.
		 */
		
		String id = customer.getCustomerID();
		String address = customer.getAddress();
		String lastName = customer.getLastName();
		String firstName = customer.getFirstName();
		String city = customer.getCity();
		String state = customer.getState();
		String email = customer.getEmail();
		int zip = customer.getZipCode();
		int rating = customer.getRating();
		String tel = customer.getTelephone();
		String cc = customer.getCreditCard();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String i1 = "insert into Person values(\"" + id + "\",\"" + lastName + "\",\"" + firstName + "\",\"" + address + "\"," + zip + ",\"" + tel + 
					"\",\"" + email + "\",\"" + city + "\",\"" + state + "\")";
			st.executeUpdate(i1);
			
			st.executeUpdate("insert into Customer values(" + rating + ",\"" + cc + "\",\"" + id + "\")");
		} catch(Exception e) {
			System.out.println(e);
		}
		return "success";
		

	}

	public String editCustomer(Customer customer) {
		/*
		 * All the values of the edit customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		String id = customer.getCustomerID();
		String address = customer.getAddress();
		String lastName = customer.getLastName();
		String firstName = customer.getFirstName();
		String city = customer.getCity();
		String state = customer.getState();
		String email = customer.getEmail();
		int zip = customer.getZipCode();
		String tel = customer.getTelephone();
		String cc = customer.getCreditCard();
		int rating = customer.getRating();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String i1 = "update Person set LastName=\"" + lastName + "\",FirstName=\"" + firstName + "\",Address=\"" + address + "\",ZipCode=" + zip + ",Telephone=\"" + tel + 
					"\",Email=\"" + email + "\",City=\"" + city + "\",State=\"" + state + "\" where SSN=\"" + id + "\"";
			st.executeUpdate(i1);
			st.executeUpdate("update Customer set CreditCardNum=\"" + cc + "\",Rating=\"" + rating + "\" where CustomerID=\"" + id + "\"" );
		} catch(Exception e) {
			System.out.println(e);
			return "failure";
		}
		return "success";


	}

}
