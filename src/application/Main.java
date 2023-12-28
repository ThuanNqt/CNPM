package application;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/Login.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("NHOM 17");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
