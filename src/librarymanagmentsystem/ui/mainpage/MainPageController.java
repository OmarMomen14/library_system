
package librarymanagmentsystem.ui.mainpage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainPageController implements Initializable {
    @FXML
    private StackPane rootPane;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
    
}
