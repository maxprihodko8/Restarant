package com.restarant.model.sql;

final public class CreateTablesQueries {
    static final public String createUsersTable = "CREATE TABLE IF NOT EXISTS user (" +
    "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
    "name VARCHAR(50), " +
    "password VARCHAR(25), " +
    "gender BOOLEAN );";

    static final public String createbuyingTable = "";

    static final public String createDishTable = "CREATE TABLE IF NOT EXISTS dish " +
            "( id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
            " name VARCHAR(50), " +
            "price INT, " +
            "type VARCHAR(10));";

    static final public String createUserOrdersTable = "CREATE TABLE IF NOT EXISTS userOrders\n" +
            "(\n" +
            "  name VARCHAR(40) ,\n" +
            "  orderId INT PRIMARY KEY\n" +
            ");";

    static final public String createOrdersTable = "CREATE TABLE IF NOT EXISTS orders\n" +
            "(\n" +
            "  orderId INT,\n" +
            "  dishName VARCHAR(30),\n" +
            "  count INT,\n" +
            "  CONSTRAINT FOREIGN KEY (orderId) REFERENCES userOrders(orderId) ON UPDATE CASCADE On DELETE CASCADE\n" +
            ");";

}
