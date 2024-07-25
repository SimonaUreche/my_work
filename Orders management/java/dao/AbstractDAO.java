package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;

import connection.ConnectionFactory;

/**
 * Clasa pentru operațiile generice de acces la date (DAO).
 *
 * @param <T> Tipul de obiect cu care lucrează clasa DAO.
 */
public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
	private final Class<T> type;

	/**
	 * Constructor care obține tipul de obiect cu care lucrează clasa DAO.
	 */
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Creează o interogare SELECT pentru un anumit câmp.
	 *
	 * @param field Numele câmpului după care se face interogarea.
	 * @return Șirul de caractere care reprezintă interogarea SELECT.
	 */
	//generic select query
	private String createSelectQuery(String field) { //numele campului dupa care se face interogarea
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());//returneaza simplu, numele clasei
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}

	/**
	 * Găsește un obiect în baza de date după ID.
	 *
	 * @param id ID-ul obiectului căutat.
	 * @return Obiectul găsit sau null dacă nu există.
	 */
	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	/**
	 * Găsește obiecte în baza de date după un anumit câmp.
	 *
	 * @param name Valoarea câmpului după care se face căutarea.
	 * @return Lista de obiecte găsite sau o listă goală dacă nu există.
	 */
	public List<T> find(String name) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<T> resultList = new ArrayList<>();
		String query = createSelectQuery("name");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			resultSet = statement.executeQuery();

			return createObjects(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:find " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return resultList;
	}

	/**
	 * Creează obiecte pe baza rezultatelor unei interogări SELECT.
	 *
	 * @param resultSet Rezultatele interogării SELECT.
	 * @return Lista de obiecte create.
	 */
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T) ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = resultSet.getObject(fieldName);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException | IntrospectionException e) {
			LOGGER.log(Level.SEVERE, "Eroare în crearea obiectelor din ResultSet", e);
		}
		return list;
	}
	/**
	 * Returnează toate obiectele de tip T din baza de date.
	 *
	 * @return O listă care conține toate obiectele de tip T găsite în baza de date.
	 */
	public List<T> findAll() {
		Connection connection = null; //conexiune cu baza de date
		PreparedStatement statement = null; //interogare SQL
		ResultSet resultSet = null; //sticare rez
		List<T> results = new ArrayList<>();
		String query = "SELECT * FROM " + type.getSimpleName(); //toate coloanele din tipul asociat lui T
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();

			results.addAll(createObjects(resultSet));
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return results;
	}

	/**
	 * Inserează un nou obiect de tip T în baza de date.
	 *
	 * @param t Obiectul de tip T care urmează să fie inserat în baza de date.
	 * @return Obiectul de tip T inserat cu ID-ul său generat setat.
	 */
	public T insert(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = generateInsertQuery();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			setStatementValues(statement, t);
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				int generatedId = resultSet.getInt(1);
				setGeneratedId(t, generatedId);
			}
			return t;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	/**
	 * Generează interogarea de INSERT pentru inserarea unui nou obiect în baza de date.
	 *
	 * @return Interogarea de INSERT sub formă de șir de caractere.
	 */
	public String generateInsertQuery() {
		StringBuilder queryBuilder = new StringBuilder("INSERT INTO ").append(type.getSimpleName()).append(" (");
		Field[] fields = type.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) { //iteram prin campuri pentru a face lista de coloane
			queryBuilder.append(fields[i].getName());
			if (i < fields.length - 1) {
				queryBuilder.append(", ");
			}
		}
		queryBuilder.append(") VALUES (");
		for (int i = 0; i < fields.length; i++) { //lista de valori a interogarii
			queryBuilder.append("?");
			if (i < fields.length - 1) {
				queryBuilder.append(", ");
			}
		}
		queryBuilder.append(")");
		return queryBuilder.toString();
	}

	/**
	 * Setează valorile declarației pregătite pentru inserarea unui obiect în baza de date.
	 *
	 * @param statement Declarația pregătită pentru a seta valorile.
	 * @param t         Obiectul de tip T din care se obțin valorile.
	 * @throws SQLException Dacă apare o excepție SQL.
	 */
	public void setStatementValues(PreparedStatement statement, T t) throws SQLException {
		Field[] fields = type.getDeclaredFields();//lista de campuri
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);//asiguram accesul la camp
			Object value;
			try {
				value = field.get(t);
				statement.setObject(i + 1, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Setează ID-ul generat al obiectului după inserarea în baza de date.
	 *
	 * @param t           Obiectul de tip T pentru care se setează ID-ul generat.
	 * @param generatedId ID-ul generat de setat.
	 */
	public void setGeneratedId(T t, int generatedId) {
		try {
			Field idField = type.getDeclaredField("id");
			idField.setAccessible(true);
			idField.set(t, generatedId);//setam valoarea id ului
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Actualizează un obiect de tip T în baza de date.
	 *
	 * @param t Obiectul de tip T care urmează să fie actualizat.
	 * @return Obiectul de tip T actualizat.
	 */
	public T update(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		StringBuilder queryBuilder = new StringBuilder("UPDATE ")
				.append(type.getSimpleName())
				.append(" SET ");//pt ca urmeaza sa actualizam valorile campurilor

		Field[] fields = type.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (i > 0) {
				queryBuilder.append(", ");
			}
			queryBuilder.append(fields[i].getName()).append("=?");//reflexie pt a accesa campurile obiectului t
		}
		queryBuilder.append(" WHERE id=?");
		String query = queryBuilder.toString();

		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);

			int parameterIndex = 1;
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(t);
				statement.setObject(parameterIndex++, value);
			}

			statement.setObject(parameterIndex, getIdValue(t));//specificam instructiunea de actualizat

			statement.executeUpdate();
			return t;
		} catch (SQLException | IllegalAccessException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	/**
	 * Obține valoarea ID-ului obiectului de tip T.
	 *
	 * @param t Obiectul de tip T din care se extrage ID-ul.
	 * @return Valoarea ID-ului obiectului de tip T.
	 */
	public Object getIdValue(T t) { //extragge valoarea id ului din t
		try {
			Field idField = t.getClass().getDeclaredField("id");
			idField.setAccessible(true);
			return idField.get(t);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Șterge un obiect din baza de date în funcție de un anumit câmp și valoarea acestuia.
	 *
	 * @param field Câmpul după care se face ștergerea.
	 * @param id    Valoarea câmpului după care se face ștergerea.
	 */
	public void delete(String field, int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "DELETE FROM " + type.getSimpleName() + " WHERE " + field + " = ?";
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}


}
