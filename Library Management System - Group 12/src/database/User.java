package database;
import java.sql.*;

public class User {
	static DataParser data;
	static String username;
	static int id;

	public User(DataParser d) {
		data = d;
	}
	
	public boolean test(String user) {
		
		String q = String.format("SELECT * FROM books_and_others WHERE name = '" + user + "'"); 
		
		ResultSet r = data.performQuery(q);
		try {
			System.out.println("retrieved book " + r.next());
			return r.next();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			return true; //Returns true so we do not try and add user
		}
	}
	
	/**
	 * 
	 * @param user username to search for
	 * @return if user exists. Default to true if error
	 */
	public boolean userExists(String user) {
		String q = String.format("SELECT * FROM users_and_passwords WHERE username = '" + user + "'"); 
		ResultSet r = data.performQuery(q);
		try {
			return r.next();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			return true; //Returns true so we do not try and add user
		}
	}
	
	/**
	 * 
	 * @param user username to get hash for
	 * @return byte[] of hash
	 */
	public byte[] getHash(String user) {
		String q = String.format("SELECT * FROM users_and_passwords WHERE username = '" + user + "'");
		ResultSet r = data.performQuery(q);
		try {
			r.next();
			return (byte[])r.getObject("hash");
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			return new byte[0];
		}
	}
	
	/**
	 * 
	 * @param user username to get salt for
	 * @return byte[] of salt
	 */
	public byte[] getSalt(String user) {
		String q = String.format("SELECT * FROM users_and_passwords WHERE username = '" + user + "'");
		ResultSet r = data.performQuery(q);
		try {
			r.next();
			return (byte[])r.getObject("salt");
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			return new byte[0];
		}
	}
	
	public int getAccountType(String user) {
		String q = String.format("SELECT * FROM users_and_passwords WHERE username = '" + user + "'");
		ResultSet r = data.performQuery(q);
		try {
			 r.next();
			 if(r.getObject("accountType") != null) {
				 return	(int) r.getObject("accountType");
			 }
			 return 0;
			 
		}
		catch(SQLException ex) {
			return -1;
		}
	}
	
	/**
	 * 
	 * @param user username to set hash for
	 * @param h hash to set
	 */
	public void setHash(String user, byte[] h) {
		String q = String.format("UPDATE users_and_passwords hash = " + h +  "WHERE username = '" + user + "'");
		data.performUpdate(q);
	}
	
	/**
	 * 
	 * @param user username to set salt for
	 * @param s salt to set
	 */
	public void setSalt(String user, byte[] s) {
		String q = String.format("UPDATE users_and_passwords salt = " + s +  "WHERE username = '" + user + "'");
		data.performUpdate(q);
	}
	
	/**
	 * 
	 * @param user username to add
	 * @param s salt for user
	 * @param h hash for user
	 */
	public void createUser(String user, byte[] s, byte[] h) {
		if(!userExists(user)) {
			data.addUser(user, s, h);
		}
	}
	public static DataParser getData(){
		return data;
	}
	public static String getUserName() {
		return username;
	}
	public static void setUserName(String usernm) {
		username = usernm;
	}
}
