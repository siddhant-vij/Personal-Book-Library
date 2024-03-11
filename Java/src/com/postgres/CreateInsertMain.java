package com.postgres;

import java.sql.Connection;

public class CreateInsertMain {
  public static void main(String[] args) {
    // Read books from CSV file
    CSVReader reader = new CSVReader();
    reader.readBooksFromCSV("Problem/input_data.csv");

    String dbName = "bookslibrary";
    String user = "postgres";
    String password = "admin";

    // Create database - psql
    // CREATE DATABASE bookslibrary;

    // Connect to database
    Connection con = CrudOperations.connectToDB(dbName, user, password);

    // Create books table
    String tableName = "books";
    CrudOperations.createTable(con, tableName);

    // Insert books into table
    for (int i = 0; i < reader.getBooks().size(); i++) {
      Book book = reader.getBooks().get(i);
      CrudOperations.insertBook(con, tableName, book, i + 1);
    }

    // Drop books table
    // ------------------------
    // While dropping table, comment out the table
    // creation, data reading & insertion above...
    // ------------------------
    // CrudOperations.dropTable(con, tableName);

    // Drop database - psql
    // \c postgres;
    // DROP DATABASE bookslibrary;

    // Close database connection
    CrudOperations.closeConnection(con);
  }
}
