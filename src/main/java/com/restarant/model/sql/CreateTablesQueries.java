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

    static final public String createGroupsTable = "CREATE TABLE IF NOT EXISTS groups\n" +
            "(\n" +
            "  id INT NOT NULL PRIMARY KEY,\n" +
            "  name VARCHAR(35),\n" +
            "  creator VARCHAR(35)\n" +
            ");";
    static final public String createOrdersOfGroupTable = "CREATE TABLE IF NOT EXISTS ordersOfGroup\n"+
            "(\n"+
            "  groupId INT,\n"+
            "  orderId INT,\n"+
            "  CONSTRAINT FOREIGN KEY (groupId) REFERENCES groups(id) ON UPDATE CASCADE ON DELETE CASCADE,\n"+
            "  CONSTRAINT FOREIGN KEY (orderId) REFERENCES userOrders(orderId) ON UPDATE CASCADE ON DELETE CASCADE\n"+
            ");";

    static final public String createUsersOfGroupTable = "CREATE TABLE IF NOT EXISTS usersOfGroup\n" +
            "(\n" +
            "  id INT,\n" +
            "  name VARCHAR(35),\n" +
            "  CONSTRAINT FOREIGN KEY (id) REFERENCES groups(id) ON UPDATE CASCADE ON DELETE CASCADE \n" +
            ");";

    static final public String createRequestsOfUsersToGroup = "CREATE TABLE IF NOT EXISTS groupRequests\n" +
            "(\n" +
            "  groupId INT,\n" +
            "  currentUser VARCHAR(35),\n" +
            "  CONSTRAINT FOREIGN KEY (groupId) REFERENCES groups(id) ON UPDATE CASCADE ON DELETE CASCADE\n" +
            ");";
}
