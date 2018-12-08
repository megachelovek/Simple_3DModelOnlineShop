package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import  org.postgresql.Driver;

public abstract class DAO {
  private String driver = null;
  protected String url = null;
  private Properties properties = null;

  public DAO(String driver) {
    this.driver = driver;
  }

  private void registerDriverManager() {
    try {
      Class.forName(driver).newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public abstract void setURL(String host, String database, int port);

  public abstract Connection getConnection();

  public abstract void closeConnection();

  public void connect(String login, String password) {
    registerDriverManager();
    properties = new Properties();
    properties.setProperty("password", password);
    properties.setProperty("user", login);
    properties.setProperty("useUnicode", "true");
    properties.setProperty("characterEncoding", "utf8");
  }
  public ResultSet execSQL (final String sql) {
    ResultSet result = null;
    try {
      if (getConnection() != null) {
        Statement statement = getConnection().createStatement();
        result = statement.executeQuery(sql);
      }
    } catch (SQLException e) {
      System.err.println ("SQLException : code = " + String.valueOf(e.getErrorCode()) +
              " - " + e.getMessage());
      System.err.println ("\tSQL : " + sql);

    }
    return result;
  }

  public void sendMessage(String message){}
}