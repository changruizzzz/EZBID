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

public class BidDao {

	public List<Bid> getBidHistory(String auctionID) {
		
		List<Bid> bids = new ArrayList<Bid>();

		/*
		 * The students code to fetch data from the database
		 * Each record is required to be encapsulated as a "Bid" class object and added to the "bids" ArrayList
		 * auctionID, which is the Auction's ID, is given as method parameter
		 * Query to get the bid history of an auction, indicated by auctionID, must be implemented
		 */
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "SELECT *\r\n" + 
					"FROM Bid B, Auction A\r\n" + 
					"WHERE B.AuctionID = A.AuctionID AND A.AuctionID = " + Integer.parseInt(auctionID) +
					" ORDER BY B.BidTime DESC";
			ResultSet rs = st.executeQuery(query); 
			while(rs.next()){
				Bid bid = new Bid();
				bid.setAuctionID(rs.getInt("AuctionID"));
				bid.setCustomerID(rs.getString("CustomerID"));
				bid.setBidTime(rs.getString("BidTime"));
				bid.setBidPrice(rs.getFloat("BidPrice"));
				bids.add(bid);		
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return bids;
	}

	public List<Bid> getAuctionHistory(String customerID) {
		
		List<Bid> bids = new ArrayList<Bid>();

		/*
		 * The students code to fetch data from the database
		 * Each record is required to be encapsulated as a "Bid" class object and added to the "bids" ArrayList
		 * customerID, which is the Customer's ID, is given as method parameter
		 * Query to get the bid history of all the auctions in which a customer participated, indicated by customerID, must be implemented
		 */

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String query = "SELECT *\r\n" + 
					"FROM Bid B, Auction A\r\n" + 
					"WHERE B.AuctionID = A.AuctionID ORDER BY B.BidTime DESC";
			ResultSet rs = st.executeQuery(query); 
			while(rs.next()){
				Bid bid = new Bid();
				bid.setAuctionID(rs.getInt("AuctionID"));
				bid.setCustomerID(rs.getString("CustomerID"));
				bid.setBidTime(rs.getString("BidTime"));
				bid.setBidPrice(rs.getFloat("BidPrice"));
				bids.add(bid);		
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return bids;
	}

	public Bid submitBid(String auctionID, String itemID, Float currentBid, Float maxBid, String customerID) {
		
		Bid bid = new Bid();

		/*
		 * The students code to insert data in the database
		 * auctionID, which is the Auction's ID for which the bid is submitted, is given as method parameter
		 * itemID, which is the Item's ID for which the bid is submitted, is given as method parameter
		 * currentBid, which is the Customer's current bid, is given as method parameter
		 * maxBid, which is the Customer's maximum bid for the item, is given as method parameter
		 * customerID, which is the Customer's ID, is given as method parameter
		 * Query to submit a bid by a customer, indicated by customerID, must be implemented
		 * After inserting the bid data, return the bid details encapsulated in "bid" object
		 */
		try {
			System.out.println(customerID);
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "Xzy351501");
			Statement st = con.createStatement();
			String date = java.time.LocalDateTime.now().toString();
			String time = date.substring(11, 19);
			date = date.substring(0,10) + " " + time;
			String query = "INSERT INTO BID VALUES(\"" + customerID + "\"," + Integer.parseInt(auctionID) + ",\"" + date + "\","  + currentBid + ")" ;
			st.executeUpdate(query);
			query = "UPDATE Auction SET CurrentHigh=" + maxBid + ",currentBid=" + currentBid + " Where auctionID=" + Integer.parseInt(auctionID);
		} catch(Exception e) {
			System.out.println(e);
		}
		/*Sample data begins*/
		bid.setAuctionID(Integer.parseInt(auctionID));
		bid.setCustomerID(customerID);
		bid.setBidTime("2008-12-11");
		bid.setBidPrice(currentBid);
		/*Sample data ends*/
		
		return bid;
	}

	public List<Bid> getSalesListing(String searchKeyword) {
		
		List<Bid> bids = new ArrayList<Bid>();

		/*
		 * The students code to fetch data from the database
		 * Each record is required to be encapsulated as a "Bid" class object and added to the "bids" ArrayList
		 * searchKeyword, which is the search parameter, is given as method parameter
		 * Query to  produce a list of sales by item name or by customer name must be implemented
		 * The item name or the customer name can be searched with the provided searchKeyword
		 */

		/*Sample data begins*/
		for (int i = 0; i < 10; i++) {
			Bid bid = new Bid();
			bid.setAuctionID(123);
			bid.setCustomerID("123-12-1234");
			bid.setBidTime("2008-12-11");
			bid.setBidPrice(100);
			bids.add(bid);			
		}
		/*Sample data ends*/
		
		return bids;
	}

}
