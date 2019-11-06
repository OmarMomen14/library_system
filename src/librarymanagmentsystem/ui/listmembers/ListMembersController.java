
package librarymanagmentsystem.ui.listmembers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import librarymanagmentsystem.database.DatabaseHandler;


public class ListMembersController implements Initializable {
    
    ObservableList <Member> list = FXCollections.observableArrayList();
    
    
    @FXML
    private TableView<Member> listmemberstableview;
    @FXML
    private TableColumn<Member, String> idcol;
    @FXML
    private TableColumn<Member, String> namecol;
    @FXML
    private TableColumn<Member, String> phonecol;
    @FXML
    private TableColumn<Member, String> emailcol;
    

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initCol();
        loadData();
        
    }

    private void initCol() {

        
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        phonecol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));
        
    }

    private void loadData() {

        
        DatabaseHandler handler = DatabaseHandler.getInstance();
        
        
        String qu = "SELECT * FROM MEMBER";
        ResultSet rs = handler.execQuery(qu);
        
        try {
            while(rs.next()) {
                
                String id = rs.getString("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                
                list.add(new Member (name, id, phone, email));
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(ListMembersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        listmemberstableview.getItems().setAll(list);
        
    }
    
    
    
    public static class Member {
    
        private final SimpleStringProperty name;
        private final SimpleStringProperty id;
        private final SimpleStringProperty phone;
        private final SimpleStringProperty email;

        Member (String name, String id, String phone, String email) {
            
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.phone = new SimpleStringProperty(phone);
            this.email = new SimpleStringProperty(email);
      
        }

        public String getName() {
            return name.get();
        }

        public String getId() {
            return id.get();
        }

        public String getPhone() {
            return phone.get();
        }

        public String getEmail() {
            return email.get();
        }

        
        
        
    
    }
    
    
}
