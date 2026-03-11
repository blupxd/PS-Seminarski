package dbb;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class DBBroker {

    private static DBBroker instance;
    private Connection connection;

    private DBBroker() {
    }

    public static DBBroker getInstance() {
        if (instance == null) {
            instance = new DBBroker();
        }
        return instance;
    }

    public void connect() throws Exception {
        Properties props = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("server.properties");
        if (is == null) throw new Exception("server.properties nije pronađen!");
        props.load(is);
        connect(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.pass"));
    }

    public void connect(String url) throws Exception {
        Properties props = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("server.properties");
        String user = "root", pass = "";
        if (is != null) {
            props.load(is);
            user = props.getProperty("db.user", "root");
            pass = props.getProperty("db.pass", "");
        }
        connect(url, user, pass);
    }

    public void connect(String url, String user, String pass) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, pass);
        System.out.println("[DBBroker] Konekcija uspostavljena: " + url);
    }

    public void disconnect() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("[DBBroker] Konekcija zatvorena.");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet executeQuery(String sql) throws Exception {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public int executeUpdate(String sql) throws Exception {
        Statement stmt = connection.createStatement();
        return stmt.executeUpdate(sql);
    }

    public PreparedStatement prepareStatement(String sql) throws Exception {
        return connection.prepareStatement(sql);
    }

    public PreparedStatement prepareStatementWithKeys(String sql) throws Exception {
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    public void beginTransaction() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.setAutoCommit(false);
        }
    }

    public void commit() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    public void rollback() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
            connection.setAutoCommit(true);
        }
    }
}
