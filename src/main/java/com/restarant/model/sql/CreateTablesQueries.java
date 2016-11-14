package com.restarant.model.sql;

/**
 * Created by fancy on 06.11.16.
 */
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

}
