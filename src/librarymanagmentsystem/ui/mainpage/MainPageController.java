
package librarymanagmentsystem.ui.mainpage;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
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
    Boolean isReadyForSubmit = false;
    
    @FXML
    private JFXTextField bookIDTF;
    @FXML
    private ListView<String> issueList;
   
    
    
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

    @FXML
    private void loadIssueBook(ActionEvent event) {
        
        String memberID = memberIdInput.getText();
        String bookID = bookIdInput.getText();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText("");
        alert.setContentText("Are you sure to issue "+bookNameTF.getText()+"\n for the member "+memberNameTF.getText());
        
        Optional <ButtonType> response = alert.showAndWait();
        
        if (response.get() == ButtonType.OK){
            String ac1 = "INSERT INTO ISSUE(memberID, bookID) VALUES ( "
                    +"'"+ memberID + "',"
                    +"'"+ bookID + "')";
            String ac2 = "UPDATE BOOK SET isavail = false WHERE id = '"+bookID+"'";
            System.out.println(ac1);
            System.out.println(ac2);
            
            if (handler.execAction(ac1) && handler.execAction(ac2)) {
            
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Success!");
                alert2.setHeaderText("");
                alert2.setContentText("Book Issue Complete!");
                alert2.showAndWait();
                
            } else {
            
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Failed!");
                alert2.setHeaderText("");
                alert2.setContentText("Book Issue Failed!");
                alert2.showAndWait();
            }
            
        } else {
        
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Cancelled!");
            alert2.setHeaderText("");
            alert2.setContentText("Book Issue Cancelled!");
            alert2.showAndWait();
        
        }
        
        
    }

    @FXML
    private void loadBookInfo2(ActionEvent event) {
        
        isReadyForSubmit = false;
        ObservableList <String> issueData = FXCollections.observableArrayList();
        
        
        String bookID = bookIDTF.getText();
        String qu = "SELECT * FROM ISSUE WHERE bookID = '"+bookID+"'";
        
        ResultSet rs = handler.execQuery(qu);
        
        try {
            while (rs.next()) {
                
                String bID = bookID;
                String bMemberID = rs.getString("memberID");
                Timestamp bIssueTime = rs.getTimestamp("issueTime");
                int renewCount = rs.getInt("renewCount");
                
                issueData.add("Issue Date and Time: "+bIssueTime.toGMTString());
                issueData.add("Renew Count: "+renewCount);
                
                issueData.add("Book Information");
                
                qu = "SELECT * FROM BOOK WHERE id = '"+bID+"'";
                ResultSet rs2 = handler.execQuery(qu);
                
                while (rs2.next()) {
                
                    issueData.add("         Title: "+rs2.getString("title"));
                    issueData.add("         ID: "+rs2.getString("id"));
                    issueData.add("         Author: "+rs2.getString("author"));
                    issueData.add("         Publisher: "+rs2.getString("publisher"));
                    issueData.add("         Available: "+rs2.getBoolean("isavail"));
                    
                } 
                
                qu = "SELECT * FROM MEMBER WHERE id = '"+bMemberID+"'";
                ResultSet rs3 = handler.execQuery(qu);
                
                issueData.add("Member Information");
                
                while (rs3.next()) {
                
                    issueData.add("         Name: "+rs3.getString("name"));
                    issueData.add("         ID: "+rs3.getString("id"));
                    issueData.add("         Phone: "+rs3.getString("phone"));
                    issueData.add("         Email: "+rs3.getString("email"));
                    
                } 
                
                isReadyForSubmit = true;
                
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        issueList.getItems().setAll(issueData);
        
    }

    @FXML
    private void loadSubmitOp(ActionEvent event) {
        String bookID = bookIDTF.getText();
        
        if (!isReadyForSubmit) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Failed!");
            alert2.setHeaderText("");
            alert2.setContentText("Please select a book to submit!");
            alert2.showAndWait();
            
            return;
        }
        
        
        String ac1 = "DELETE FROM ISSUE WHERE bookID = '"+bookID+"'";
        String ac2 = "UPDATE BOOK SET isavail = true WHERE ID = '"+bookID+"'";
        
        if (handler.execAction(ac1) && handler.execAction(ac2)) {
        
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Success!");
            alert2.setHeaderText("");
            alert2.setContentText("Book Has been Submitted!");
            alert2.showAndWait();
        } else {
        
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("Failed!");
            alert3.setHeaderText("");
            alert3.setContentText("Submission Has been failed!");
            alert3.showAndWait();
        
        }
    }
    
}
