package librarymanagmentsystem.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



public final class DatabaseHandler {

    private static DatabaseHandler handler = null;
    private static final String DB_URL = "jdbc:derby:database; create=true";
    private static Connection conn = null;
    private static Statement stat = null;
    
    private DatabaseHandler () {
        
        createConnection();
        setupBookTable();
        setupMemberTable();
        setupIssueTable();
    }
    
    public static DatabaseHandler getInstance () {
        if (handler == null)
            handler = new DatabaseHandler();
        return handler;   
    }
    
    void createConnection () {
    
        try {
        
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet execQuery (String query) {
    
        ResultSet result;
        
        try {
            stat = conn.createStatement();
            result = stat.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Error at execQuery:DatabaseHandler"+ex.getLocalizedMessage());
            return null;   
        }    
        return result;
    }
    
    public boolean execAction (String ac) {
    
        try {
            stat = conn.createStatement();
            stat.execute(ac);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR"+ex.getMessage(), "Error Ocurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execAction:DatabaseHandler" + ex.getLocalizedMessage());
            return false;
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
        }
    
    }
    
    private void setupMemberTable() {

        
        String TABLE_NAME = "MEMBER";
        
        try {
            stat = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            
            if(tables.next()) {
                System.out.println("Table "+ TABLE_NAME + " already exists, Ready for go...");
            } else {
                stat.execute("CREATE TABLE " + TABLE_NAME + "("
                        +"      id varchar (200) primary key,\n"
                        +"      name varchar (200),\n"
                        +"      phone varchar (20),\n"
                        +"      email varchar (200) )"
                );
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " ........ setupDatabase");
        }
        
    
    }
    
    private void setupIssueTable() {

        
        String TABLE_NAME = "ISSUE";
        
        try {
            stat = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            
            if(tables.next()) {
                System.out.println("Table "+ TABLE_NAME + " already exists, Ready for go...");
            } else {
                stat.execute("CREATE TABLE " + TABLE_NAME + "("
                        +"      bookID varchar (200) primary key,\n"
                        +"      memberID varchar (200),\n"
                        +"      issueTime timestamp default CURRENT_TIMESTAMP,\n"
                        +"      renewCount integer default 0, \n"
                        +"      FOREIGN KEY (bookID) REFERENCES BOOK(id), \n"
                        +"      FOREIGN KEY (memberID) REFERENCES MEMBER(id) )"
                );
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " ........ setupDatabase");
        }
        
    
    }
    
    
    
    
}
