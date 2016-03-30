package test.testDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testdb {

	public static void main(String[] args) {
		// Informations d'acces a la bdd
		String url = "jdbc:mysql://localhost/testdb";
		String login = "root";
		String mdp = "root";
		Connection cn = null;
		Statement st = null;
		
		try {
			// Chargement du driver
			Class.forName("com.mysql.jdbc.Driver");
			
			// Recuperation de la connexion
			cn = DriverManager.getConnection(url,login,mdp);
			
			// Creation d'un statement
			st = cn.createStatement();
	//		String sql = "INSERT INTO names (first, last) VALUES ('Margaux', 'Gouysse')";
			String sql = "SELECT * FROM names";

	/*		DatabaseMetaData md = cn.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			while (rs.next()) {
			  System.out.println(rs.getString(3));
			}
	*/		
			// Execution de la requete
			ResultSet res = st.executeQuery(sql);

			while (res.next()) {
				System.out.println(res.getString("first") + " " + res.getString("last"));
			}
			
	//		st.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// Liberer les ressources de la memoire
				cn.close();
	//			st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
 
	}

}
