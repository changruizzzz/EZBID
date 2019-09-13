package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Employee;
import model.Item;
import model.Post;

public class PostDao {

	
	public List<Item> getSalesReport(Post post) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Item" class object and added to the "items" List
		 * Query to get sales report for a particular month must be implemented
		 * post, which has details about the month and year for which the sales report is to be generated, is given as method parameter
		 * The month and year are in the format "month-year", e.g. "10-2018" and stored in the expireDate attribute of post object
		 * The month and year can be accessed by getter method, i.e., post.getExpireDate()
		 */
		System.out.println(post.getPostDate());
		List<Item> items = new ArrayList<Item>();
		try {
			System.out.println("HELLLO");
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
			st.execute(q1);
			String q2 = "SELECT * \r\n" + 
					"From Sold S, Auction A, Item I, Post P\r\n" + 
					"WHERE S.AuctionID = A.AuctionID AND A.ItemID=I.itemID And P.AuctionID = A.AuctionID AND P.ExpireDate like \"" + post.getExpireDate() + "\"";
			ResultSet rs = st.executeQuery(q2); 
			while(rs.next()) {
				Item item = new Item();
				item.setName(rs.getString("Name"));
				item.setSoldPrice(Math.round(rs.getFloat("SoldPrice")));
				items.add(item);
			} 
		}catch(Exception e) {
				System.out.println(e);
				return null;
			
		}
		return items;
		
	}
}
