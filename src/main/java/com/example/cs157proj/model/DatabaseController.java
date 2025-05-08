package com.example.cs157proj.model;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class DatabaseController {

    private Connection connection;
    private ConnectDB connectDB;
    public DatabaseController() {
        connectDB = new ConnectDB();
        connection = connectDB.getConnection();
        try {
            initialzeDatabase("SQL Files/create_schema.sql");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            initialzeDatabase("SQL Files/initialize_data.sql");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialzeDatabase(String path) throws IOException {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new FileNotFoundException("SQL file not found in resources.");
        }
        String sql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        String[] qeuries = sql.split(";");
        try{
            Statement statement = connection.createStatement();
            for(String query : qeuries){
                query = query.trim();
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



