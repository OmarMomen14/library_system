package librarymanagmentsystem.ui.addmember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import librarymanagmentsystem.database.DatabaseHandler;


public class AddMemberController implements Initializable {
    
    DatabaseHandler handler;
    
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton savebtn;
    @FXML
    private JFXButton cancelbtn;
    @FXML
    private AnchorPane rootPane;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       handler = DatabaseHandler.getInstance();
       
    }    

    @FXML
    private void addMember(ActionEvent event) {
        
        String mName = name.getText();
        String mId = id.getText();
        String mPhone = phone.getText();
        String mEmail = email.getText();
        
        Boolean flag = mName.isEmpty() || mId.isEmpty() || mPhone.isEmpty() || mEmail.isEmpty(); 
        if (flag) {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill in all fields");
            alert.showAndWait();
            return;
        } 
        
        String ac = "INSERT INTO MEMBER VALUES ("
                +"'"+mId+"',"
                +"'"+mName+"',"
                +"'"+mPhone+"',"
                +"'"+mEmail+"'"
                +")";
        
        System.out.println(ac);
        
        if (handler.execAction(ac)) {
        
            Alert alert = new Alert (Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Member Added Successfully");
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
    
}
