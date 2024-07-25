package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa pentru gestionarea conexiunii la baza de date.
 *
 * @Author: Laboratorul de Cercetare în Sisteme Distribuite,
 *          Universitatea Tehnică din Cluj-Napoca, România, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source: http://theopentutorials.com/tutorials/java/jdbc/jdbc-mysql-create-database-example/
 */
public class ConnectionFactory {

	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/schooldb";
	private static final String USER = "root";
	private static final String PASS = "vydqyc-radsoz";

	private static ConnectionFactory singleInstance = new ConnectionFactory();

	/**
	 * Constructor privat pentru a preveni instanțierea externă.
	 */
	private ConnectionFactory() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Driverul MySQL nu a fost găsit", e);
		}
	}

	/**
	 * Creează și returnează o conexiune la baza de date.
	 *
	 * @return Conexiunea la baza de date.
	 */
	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DBURL, USER, PASS);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "A apărut o eroare în timpul conectării la baza de date", e);
		}
		return connection;
	}

	/**
	 * Returnează o instanță a clasei ConnectionFactory.
	 *
	 * @return Instanța clasei ConnectionFactory.
	 */
	public static Connection getConnection() {
		return singleInstance.createConnection();
	}

	/**
	 * Închide conexiunea la baza de date.
	 *
	 * @param connection Conexiunea care trebuie închisă.
	 */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "A apărut o eroare în timpul închiderii conexiunii", e);
			}
		}
	}

	/**
	 * Închide obiectul Statement.
	 *
	 * @param statement Obiectul Statement care trebuie închis.
	 */
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "A apărut o eroare în timpul închiderii Statement-ului", e);
			}
		}
	}

	/**
	 * Închide obiectul ResultSet.
	 *
	 * @param resultSet Obiectul ResultSet care trebuie închis.
	 */
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "A apărut o eroare în timpul închiderii ResultSet-ului", e);
			}
		}
	}
}
