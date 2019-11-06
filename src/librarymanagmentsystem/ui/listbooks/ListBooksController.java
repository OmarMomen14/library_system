
package librarymanagmentsystem.ui.listbooks;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import librarymanagmentsystem.database.DatabaseHandler;
import librarymanagmentsystem.ui.addbook.AddBookController;


public class ListBooksController implements Initializable {
    
    ObservableList<Book> list = FXCollections.observableArrayList();
    
    
    @FXML
    private TableView<Book> listbookstableview;
    @FXML
    private TableColumn<Book, String> idcol;
    @FXML
    private TableColumn<Book, String> titlecol;
    @FXML
    private TableColumn<Book, String> authorcol;
    @FXML
    private TableColumn<Book, String> publishercol;
    @FXML
    private TableColumn<Book, Boolean> availablecol;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        
        initCol();
        loadData();
    } 

    private void initCol() {

        titlecol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorcol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publishercol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availablecol.setCellValueFactory(new PropertyValueFactory<>("available"));
        
    
    }

    private void loadData() {

        DatabaseHandler handler = DatabaseHandler.getInstance();
        
        
        String qu = "SELECT * FROM BOOK";
        ResultSet rs = handler.execQuery(qu);
        
        try {
            while(rs.next()) {
                
                String id = rs.getString("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                Boolean av = rs.getBoolean("isavail");
                
                list.add(new Book (title, id, author, publisher, av));
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        listbookstableview.getItems().setAll(list);
        
    
    }
    
    public static class Book {
    
        private final SimpleStringProperty title;
        private final SimpleStringProperty id;
        private final SimpleStringProperty author;
        private final SimpleStringProperty publisher;
        private final SimpleBooleanProperty available;
        
        Book (String title, String id, String author, String publisher, Boolean available) {
            
            this.id = new SimpleStringProperty(id);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(publisher);
            this.available = new SimpleBooleanProperty(available);
           
        }

        public String getTitle() {
            return title.get();
        }

        public String getId() {
            return id.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public Boolean getAvailable() {
            return available.get();
        }
        
        
    
    }
    
}
