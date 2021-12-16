
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class Main2 extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{



        Parent root = FXMLLoader.load(getClass().getResource("/Login/FXML/LoginScreen.fxml"));
        primaryStage.setTitle("Mijn Applicatie");
        primaryStage.setScene(new Scene(root, 550, 400));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> Platform.exit());

    }

    public static void main(String[] args) {
        launch(args);
    }


}
