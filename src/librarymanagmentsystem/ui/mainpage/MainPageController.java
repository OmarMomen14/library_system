
package librarymanagmentsystem.ui.mainpage;

import com.jfoenix.effects.JFXDepthManager;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import librarymanagmentsystem.database.DatabaseHandler;


public class MainPageController implements Initializable {
    @FXML
    private StackPane rootPane;
    @FXML
    private HBox bookinfo;
    @FXML
    private HBox memberinfo;
    @FXML
    private TextField bookIdInput;
    @FXML
    private Text bookNameTF;
    @FXML
    private Text authorTF;
    @FXML
    private Text statusTF;
    @FXML
    private TextField memberIdInput;
    @FXML
    private Text memberNameTF;
    @FXML
    private Text contactTF;

    
    DatabaseHandler handler;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        JFXDepthManager.setDepth(bookinfo, 1);
        JFXDepthManager.setDepth(memberinfo, 1);
        
        handler = DatabaseHandler.getInstance();
        
    }    

    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("/librarymanagmentsystem/ui/addmember/addMember.fxml","Add New Member");
         
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
    
        loadWindow("/librarymanagmentsystem/ui/addbook/addBookUI.fxml","Add New Book");
        
    }

    @FXML
    private void loadViewMembers(ActionEvent event) {
        
        loadWindow("/librarymanagmentsystem/ui/listmembers/listMembers.fxml","Members List");
    }

    @FXML
    private void loadViewBooks(ActionEvent event) {
        
        loadWindow("/librarymanagmentsystem/ui/listbooks/listBooks.fxml","Books List");
    }
    
    void loadWindow (String loc, String title) {
    
    
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        
        } catch (IOException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }

    @FXML
    private void loadBookInfo(ActionEvent event) {
        
        String id = bookIdInput.getText();
        String qu = "SELECT * FROM BOOK WHERE id='"+id+"'";
        
        ResultSet rs = handler.execQuery(qu);
        Boolean flag = false;
        
        try {
            while (rs.next()) {
                
                String bName = rs.getString("title");
                String bAuthor = rs.getString("author");
                Boolean bStatus = rs.getBoolean("isavail");
                
                bookNameTF.setText(bName);
                authorTF.setText(bAuthor);
                statusTF.setText((bStatus)?"Avaliable":"Not Available");
                
                flag = true;
            }
            
            if (!flag) {
                bookNameTF.setText("Book is not added yet!");
                authorTF.setText("");
                statusTF.setText("");
            }
                
            
        } catch (SQLException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void loadMemberInfo(ActionEvent event) {
        
        
        String id = memberIdInput.getText();
        String qu = "SELECT * FROM MEMBER WHERE id='"+id+"'";
        
        ResultSet rs = handler.execQuery(qu);
        Boolean flag = false;
        
        try {
            while (rs.next()) {
                
                String mName = rs.getString("name");
                String mContact = rs.getString("email");
                String mContact2 = rs.getString("phone");

                
                memberNameTF.setText(mName);
                contactTF.setText(mContact+"\n"+mContact2);
                              
                flag = true;
            }
            
            if (!flag) {
                memberNameTF.setText("Member is not added yet");
                contactTF.setText("");
            }
                
            
        } catch (SQLException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
