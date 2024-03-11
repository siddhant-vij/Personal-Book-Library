package com.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.io.FileNotFoundException;

public class CrudOperations {
  public static Connection connectToDB(String dbName, String user, String password) {
    Connection conn = null;
    String url = "jdbc:postgresql://localhost:5432/";

    try {
      conn = DriverManager.getConnection(url + dbName, user, password);
      return conn;
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
      return null;
    }
  }

  public static void closeConnection(Connection con) {
    try {
      con.close();
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void createTable(Connection con, String tableName) {
    Statement stmt = null;

    try {
      stmt = con.createStatement();
      String sql = "CREATE TABLE " + tableName
          + " (book_id SERIAL NOT NULL PRIMARY KEY, isbn VARCHAR(20) NOT NULL, title VARCHAR(50) NOT NULL, author VARCHAR(25) NOT NULL, publication_year INT NOT NULL)";
      stmt.executeUpdate(sql);
      System.out.println("Table " + tableName + " created successfully");
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void dropTable(Connection con, String tableName) {
    Statement stmt = null;
    try {
      stmt = con.createStatement();
      String sql = "DROP TABLE " + tableName;
      stmt.executeUpdate(sql);
      System.out.println("Table " + tableName + " dropped successfully");
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void insertBook(Connection con, String tableName, Book book, int bookId) {
    Statement stmt = null;
    try {
      stmt = con.createStatement();
      String sql = "INSERT INTO " + tableName
          + " (isbn, title, author, publication_year) VALUES ('" + book.getIsbn() + "', '"
          + book.getTitle() + "', '" + book.getAuthor() + "', " + book.getPublicationYear() + ")";
      stmt.executeUpdate(sql);
      System.out.println("Book " + bookId + " inserted successfully");
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static ResultSet executeQuery(Connection con, String query) {
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.createStatement();
      rs = stmt.executeQuery(query);
      return rs;
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
      return null;
    }
  }

  public static int executeUpdateQuery(Connection con, String query) {
    try (Statement stmt = con.createStatement()) {
      int count = stmt.executeUpdate(query);
      return count;
    } catch (SQLException e) {
      System.out.println("Error: " + e.getMessage());
      return -1;
    }
  }

  // This version handles ResultSet from SELECT queries
  public static void printQueryResultToFile(String queryStr, ResultSet rs, String filePath) {
    try (PrintWriter pw = new PrintWriter(new FileOutputStream(filePath, true))) {
      pw.println(queryStr + ":");
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();

      // Print each row of the ResultSet
      while (rs.next()) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= columnCount; i++) {
          int columnType = rsmd.getColumnType(i);
          switch (columnType) {
            case java.sql.Types.INTEGER:
            case java.sql.Types.BIGINT:
            case java.sql.Types.SMALLINT:
              sb.append(rs.getInt(i));
              break;
            case java.sql.Types.FLOAT:
            case java.sql.Types.DOUBLE:
            case java.sql.Types.DECIMAL:
              sb.append(rs.getDouble(i));
              break;
            default:
              sb.append(rs.getString(i));
              break;
          }
          if (i < columnCount) {
            sb.append("|");
          }
        }
        pw.println(sb.toString());
      }
      pw.println("\n\n\n\n");
    } catch (SQLException e) {
      System.out.println("SQL Error: " + e.getMessage());
    } catch (FileNotFoundException e) {
      System.out.println("File Not Found Error: " + e.getMessage());
    }
  }

  // This version handles the number of rows affected by UPDATE or DELETE queries
  public static void printQueryResultToFile(String queryStr, int rowsAffected, String filePath) {
    try (PrintWriter pw = new PrintWriter(new FileOutputStream(filePath, true))) {
      pw.println(queryStr + ":");
      pw.println(rowsAffected + " rows affected.");
      pw.println("\n\n\n\n");
    } catch (FileNotFoundException e) {
      System.out.println("File Not Found Error: " + e.getMessage());
    }
  }
}
