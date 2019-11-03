package librarymanagmentsystem.ui.addbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import librarymanagmentsystem.database.DatabaseHandler;



public class FXMLDocumentController implements Initializable {
    
    private Label label;
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
    }

    @FXML
    private void cancel(ActionEvent event) {
    }
    
}
