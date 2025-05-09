package com.example.cs157proj.model;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class DatabaseController {

    private Connection connection;
    private ConnectDB connectDB;

    //connects the database and runs the scripts in the create_schema and initalize_data files
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

    //method to read SQL file from path given and execute the updates in them
    public void initialzeDatabase(String path) throws IOException {

        //stores stream to read the file specified in the path variable
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new FileNotFoundException("SQL file not found in resources.");
        }
        //stores the sql script in the file into a string
        String sql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        //splits that string into an array based on the semi-colon since thats the indicator that and statement is finished
        String[] update = sql.split(";");
        //executes each update in the array of strings
        try{
            Statement statement = connection.createStatement();
            for(String query : update){
                query = query.trim();
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



