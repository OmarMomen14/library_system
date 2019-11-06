package librarymanagmentsystem.ui.addbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import librarymanagmentsystem.database.DatabaseHandler;



public class AddBookController implements Initializable {
    
   
    @FXML
    private JFXTextField titletf;
    @FXML
    private JFXTextField idtf;
    @FXML
    private JFXTextField authortf;
    @FXML
    private JFXTextField publishertf;
    @FXML
    private JFXButton savebtn;
    @FXML
    private JFXButton cancelbtn;
    
    DatabaseHandler databaseHandler;
    @FXML
    private AnchorPane rootPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        databaseHandler = DatabaseHandler.getInstance();
        checkData();
        
    }    

    @FXML
    private void saveBook(ActionEvent event) {
        
        String bookId = idtf.getText();
        String bookTitle = titletf.getText();
        String bookAuthor = authortf.getText();
        String bookPublisher = publishertf.getText();
        
        if (bookId.isEmpty() || bookTitle.isEmpty() || bookAuthor.isEmpty() || bookPublisher.isEmpty()) {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill in all fields");
            alert.showAndWait();
            return;
        }
        
        String ac = "INSERT INTO BOOK VALUES ("+
                        "'"+bookId+"', "+
                        "'"+bookTitle+"', "+
                        "'"+bookAuthor+"', "+
                        "'"+bookPublisher+"', "+
                        "true)";
        System.out.println(ac);
        
        if (databaseHandler.execAction(ac)) {
            
            Alert alert = new Alert (Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Book Added Successfully");
            alert.showAndWait();
            
        } else {
        
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Operation Failed");
            alert.showAndWait();
        }
        
        
    }

    @FXML
    private void cancel(ActionEvent event) {
        
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void checkData() {

        String qu = "SELECT title FROM BOOK";
        ResultSet rs = databaseHandler.execQuery(qu);
        
        try {
            while(rs.next()) {
                
                String currTitle = rs.getString("title");
                System.out.println(currTitle);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
