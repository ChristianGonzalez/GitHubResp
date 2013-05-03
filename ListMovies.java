import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;



public class ListMovies {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		
		ResultSet movies = getMovies();
		try{
			while(movies.next()){
				Movie m = getMovie(movies);
				String msg = Integer.toString(m.year);
				msg += ": "+m.title;
				msg += " ("+cf.format(m.price) +")";
				System.out.println(msg);
			}
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		
	}
	
	
	private static ResultSet getMovies(){
		Connection con = getConnection();
		try{
			Statement s = con.createStatement();
			String select = "Select title, year, price from movie order by year"; //This is a sql command
			ResultSet rows;
			rows = s.executeQuery(select);
			return rows;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		
		return null;
		
	}
	
	
	private static Connection getConnection(){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");		    //change these to your url, pw, and user
			String url = "jdbc:mysql://localhost:3306/Movies";
			String user = "root";
			String pw = "INSERT PASSWORD HERE";
			con = DriverManager.getConnection(url, user, pw);
		}
		catch(ClassNotFoundException e){
			System.out.println(e.getMessage()+" Class not found");
			System.exit(0);
		}
		catch(SQLException e){
			System.out.println(e.getMessage()+" SQL exception");
			System.exit(0);
		}
		
		return con;
	}
	
	private static Movie getMovie(ResultSet movies){
		
		try{
			String title = movies.getString("Title"); //names of the mysql sections
			int year = movies.getInt("Year");
			double price = movies.getDouble("price");
			return new Movie(title, year, price);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
			
		}
		return null;
	}
	
	private static class Movie{
		public String title;
		public int year;
		public double price;
		public Movie(String title, int year, double price){
			this.title= title;
			this.year= year;
			this.price= price;
		}
	}
	
	

}
