import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DBDemo2 extends Frame implements ActionListener  {

	Statement s = null;
	ResultSet rs = null;

	Connection con;

	public DBDemo2()
	{
		// Build GUI
		// Add EventListeners
	}

	// i think the following is to be commented out
	//	public void actionPerformed(actionEvent e)
	//	{
	//		if (e.getSource()=b)
	//			// do something (BACK)
	//		else
	//			// do something else (PREVIOUS)
	//	}

	// Connect to MySQL and do some stuff.
	public Connection connectToDatabase()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2_create_db", "root", "");
			//loadEmployees();
		} catch (Exception e) {
			//alertMessage("Error connecting to Database", "Warning!");
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * Connect to MySQL and do some stuff.
	 */
	public void run() throws SQLException {

		// Connect to MySQL
		Connection conn = null;
		conn = connectToDatabase();

		try
		{
		conn = connectToDatabase();
		System.out.println("Connected to database");
		}
		catch(Exception e)
		{
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}

		Statement s = conn.createStatement();
		
		s.executeQuery("SELECT id, firstname, lastname, email " +	"FROM web_members3");

		ResultSet rs = s.getResultSet ();

		int count = listOutDatabaseContents(rs);

		rs.close ();
		s.close ();
		System.out.println (count + " rows were retrieved");
	}

	private static int listOutDatabaseContents(ResultSet rs) throws SQLException
	{
		int count = 0;

		while (rs.next())
		{
			String idVal = rs.getString("id");

			if (idVal.length() == 0)
			{
				break;
			}

			String firstnameVal = rs.getString("firstname");
			String lastnameVal = rs.getString("lastname");
			String emailVal = rs.getString("email");
			System.out.println("id = " + idVal + ", firstname = " + firstnameVal + ", lastname = " + lastnameVal + ", email =" + emailVal);
			++count;
		}

		return count;
	}

	/**
	 * Connect to the DB and do some stuff
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {

		DBDemo2 app = new DBDemo2();
		app.run();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}