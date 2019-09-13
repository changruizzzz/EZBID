package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Auction;
import model.Bid;
import model.Customer;
import model.Item;

public class AuctionDao {
	
	public List<Auction> getAllAuctions() {
		
		List<Auction> auctions = new ArrayList<Auction>();
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Auction" class object and added to the "auctions" ArrayList
		 * Query to get data about all the auctions should be implemented
		 */
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "select * from Auction";
			ResultSet rs = st.executeQuery(query); 
			while(rs.next()){
				Auction auction = new Auction();
				auction.setAuctionID(rs.getInt("AuctionID"));
				auction.setBidIncrement(rs.getInt("BidIncrement"));
				auction.setMinimumBid(rs.getInt("MinimuBid"));
				auction.setCopiesSold(rs.getInt("Copies_Sold"));
				auction.setItemID(rs.getInt("ItemID"));
				auction.setCurrentBid(Math.round(rs.getFloat("CurrentBid")));
				auction.setCurrentHighBid(Math.round(rs.getFloat("CurrentHigh")));
				auction.setReserve(Math.round(rs.getFloat("Reserve")));
				auctions.add(auction);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return auctions;

	}

	public List<Auction> getAuctions(String customerID) {
		
		List<Auction> auctions = new ArrayList<Auction>();
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Auction" class object and added to the "auctions" ArrayList
		 * Query to get data about all the auctions in which a customer participated should be implemented
		 * customerID is the customer's primary key, given as method parameter
		 */
		
		/*Sample data begins*/
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "SELECT DISTINCT A.AuctionID, A.BidIncrement, A.Copies_Sold, A.CurrentBid, A.CurrentHigh, A.ItemID, A.MinimuBid, A.Reserve\r\n" + 
					"FROM Bid B, Post P, Auction A\r\n" + 
					"WHERE B.AuctionID = P.AuctionID and B.AuctionID = A.AuctionID and (B.CustomerID = \"" + customerID + "\" or P.CustomerID = \"" + customerID + "\")";
			ResultSet rs = st.executeQuery(query); 
			while(rs.next()){
				Auction auction = new Auction();
				auction.setAuctionID(rs.getInt("AuctionID"));
				auction.setBidIncrement(rs.getInt("BidIncrement"));
				auction.setMinimumBid(rs.getInt("MinimuBid"));
				auction.setCopiesSold(rs.getInt("Copies_Sold"));
				auction.setItemID(rs.getInt("ItemID"));
				auction.setCurrentBid(Math.round(rs.getFloat("CurrentBid")));
				auction.setCurrentHighBid(Math.round(rs.getFloat("CurrentHigh")));
				auction.setReserve(Math.round(rs.getFloat("Reserve")));
				auctions.add(auction);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return auctions;


	}

	public List<Auction> getOpenAuctions(String employeeEmail) {
		List<Auction> auctions = new ArrayList<Auction>();
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Auction" class object and added to the "auctions" ArrayList
		 * Query to get data about all the open auctions monitored by a customer representative should be implemented
		 * employeeEmail is the email ID of the customer representative, which is given as method parameter
		 */
		
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "SELECT DISTINCT A.AuctionID, A.BidIncrement, A.Copies_Sold, A.CurrentBid, A.CurrentHigh, A.ItemID, A.MinimuBid, A.Reserve\r\n" + 
					"FROM Bid B, Post P, Auction A, Person PP\r\n" + 
					"WHERE P.ExpireDate < 'today' and B.AuctionID = A.AuctionID";
			ResultSet rs = st.executeQuery(query);
			while(rs.next()){
				Auction auction = new Auction();
				auction.setAuctionID(rs.getInt("AuctionID"));
				auction.setBidIncrement(rs.getInt("BidIncrement"));
				auction.setMinimumBid(rs.getInt("MinimuBid"));
				auction.setCopiesSold(rs.getInt("Copies_Sold"));
				auction.setItemID(rs.getInt("ItemID"));
				auction.setCurrentBid(Math.round(rs.getFloat("CurrentBid")));
				auction.setCurrentHighBid(Math.round(rs.getFloat("CurrentHigh")));
				auction.setReserve(Math.round(rs.getFloat("Reserve")));
				auctions.add(auction);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return auctions;
		
		
	}

	public String recordSale(String auctionID) {
		/*
		 * The students code to update data in the database will be written here
		 * Query to record a sale, indicated by the auction ID, should be implemented
		 * auctionID is the Auction's ID, given as method parameter
		 * The method should return a "success" string if the update is successful, else return "failure"
		 */

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "SELECT CurrentHigh From Auction WHERE AuctionID = ";
		} catch(Exception e) {
			System.out.println(e);
			return "failure";
		}
		return "success";
	}

	public List getAuctionData(String auctionID, String itemID) {
		
		List output = new ArrayList();
		Item item = new Item();
		Bid bid = new Bid();
		Auction auction = new Auction();
		Customer customer = new Customer();
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The item details are required to be encapsulated as a "Item" class object
		 * The bid details are required to be encapsulated as a "Bid" class object
		 * The auction details are required to be encapsulated as a "Auction" class object
		 * The customer details are required to be encapsulated as a "Customer" class object
		 * Query to get data about auction indicated by auctionID and itemID should be implemented
		 * auctionID is the Auction's ID, given as method parameter
		 * itemID is the Item's ID, given as method parameter
		 * The customer details must include details about the current winner of the auction
		 * The bid details must include details about the current highest bid
		 * The item details must include details about the item, indicated by itemID
		 * The auction details must include details about the item, indicated by auctionID
		 * All the objects must be added in the "output" list and returned
		 */
		
		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			int itemId = Integer.parseInt(itemID);
			int auctionId = Integer.parseInt(auctionID);
			System.out.println(itemID);
			System.out.println(auctionID);

			String query = "SELECT *\r\n" + 
					"FROM Bid B, Auction A, Item I, Customer C, Person P\r\n" + 
					"WHERE A.AuctionID = B.AuctionID AND A.ItemID = I.ItemID AND C.CustomerID = B.CustomerID AND C.CustomerID = P.SSN AND A.AuctionID = " + auctionId +  
					" ORDER BY BidPrice DESC";
			ResultSet rs = st.executeQuery(query);
			rs.next();
			item.setItemID(rs.getInt("ItemID"));
			item.setDescription(rs.getString("Description"));
			item.setType(rs.getString("Type"));
			item.setName(rs.getString("Name"));
			
			bid.setCustomerID(rs.getString("CustomerID"));
			bid.setBidPrice(rs.getFloat("BidPrice"));
			
			customer.setCustomerID(rs.getString("CustomerID"));
			customer.setFirstName(rs.getString("FirstName"));
			customer.setLastName(rs.getString("LastName"));
			
			auction.setMinimumBid(rs.getFloat("MinimuBid"));
			auction.setBidIncrement(rs.getFloat("BidIncrement"));
			auction.setCurrentBid(Math.round(rs.getFloat("CurrentBid")));
			auction.setCurrentHighBid(Math.round(rs.getFloat("CurrentHigh")));
			auction.setAuctionID(Integer.parseInt(auctionID));
		} catch(Exception e) {
			System.out.println(e);
		}
		
		output.add(item);
		output.add(bid);
		output.add(auction);
		output.add(customer);
		
		return output;

	}

	
}
