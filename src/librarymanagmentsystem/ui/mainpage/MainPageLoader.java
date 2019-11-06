
package librarymanagmentsystem.ui.mainpage;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import librarymanagmentsystem.database.DatabaseHandler;


public class MainPageLoader extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        DatabaseHandler.getInstance();
    }

   
    public static void main(String[] args) {
        launch(args);
    }
    
}
