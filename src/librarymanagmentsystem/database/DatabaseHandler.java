package librarymanagmentsystem.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;



public final class DatabaseHandler {

    private static DatabaseHandler handler;
    private static final String DB_URL = "jdbc:derby:database; create=true";
    private static Connection conn = null;
    private static Statement stat = null;
    
    public DatabaseHandler () {
        
        createConnection();
        setupBookTable();
    }
    
    void createConnection () {
    
        try {
        
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void setupBookTable () {
    
        String TABLE_NAME = "BOOK";
        
        try {
            stat = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            
            if(tables.next()) {
                System.out.println("Table "+ TABLE_NAME + " already exists, Ready for go...");
            } else {
                stat.execute("CREATE TABLE " + TABLE_NAME + "("
                        +"      id varchar (200) primary key,\n"
                        +"      title varchar (200),\n"
                        +"      author varchar (200),\n"
                        +"      publisher varchar (100),\n"
                        +"      isavail boolean default true"
                        +" )"
                );
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " ........ setupDatabase");
        } finally {
        
        }
    
    }
    
    
    
    
}
