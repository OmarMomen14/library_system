package librarymanagmentsystem.ui.addbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import librarymanagmentsystem.database.DatabaseHandler;



public class FXMLDocumentController implements Initializable {
    
   
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        databaseHandler = new DatabaseHandler();
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
    }
    
}
