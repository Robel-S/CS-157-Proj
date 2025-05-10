# FilmBox
FilmBox is a movie rental system that lets customers sign in, rent movies for a period of time, and register when they return them. The purpose of this project is to give us experience in making database systems for real businesses. This project is relevant because it forces us to learn how to make the basic operations for Database systems (CRUD) work in a real project.

In the main folder you have the java and resources folder. In the resources folder is our FXML and SQL files. In the java folder under the cs157proj package is where all our classes and code is. Startup is how you run the application the fxml controllers control the presentation layer, the data objects are our application layer, and model is our database layer.

### Instructions for setting up and running the project
Make sure the your project SDK is using OpenJDK 21.0.7 and that you add your JDBC SQLite driver (version mentioned in Dependencies and required software) to wherever your IDE stores them. In order to load the application you need to run the start up class, start up uses the initialize method in DataHandler to set up the tables and populate them using the create_schema and initialize_data sql files and then sets the scene to the welcome page.
### Dependencies and required software
JDK 21.0.7, Java FX SDK 21.0.7, SQLite, Xerial JDBC SQLite driver ver 3.45.1.0, javafx.controls, javafx.fxml, java.sql, sqlite browser like DB Browser if your IDE does not have a built in one

### Any additional configuration steps needed to connect to the database
