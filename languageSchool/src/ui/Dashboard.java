package ui;

import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dashboard extends Application {
	
private Label lheader, lenroll, lcourse,lteacher,lstudent;
	
	private Stage st;
	private FlowPane topPane;
	private VBox leftPane;
	private BorderPane mainPane;
	
	public void createTopPane()
	{
		lheader = new Label("Language School");
		lheader.setId("lheader");
		topPane = new FlowPane(lheader);
		topPane.setAlignment(Pos.CENTER_LEFT);
		topPane.setPadding(new Insets(20));
		topPane.setId("topPane");
	}
	
	public void createLeftPane()
	{
		lenroll = new Label("Enroll");
		lenroll.setPrefWidth(200);
		lenroll.setOnMouseClicked(e->{
			mainPane.setCenter(null);
			mainPane.setCenter(new EnrollPane().getEnrollPane());
		});
		lenroll.getStyleClass().add("option");
		lcourse = new Label("Course");
		lcourse.setPrefWidth(200);
		lcourse.getStyleClass().add("option");
		lcourse.setOnMouseClicked(e->{
			mainPane.setCenter(null);
			mainPane.setCenter(new CoursePane().getCoursePane());
		});
		lteacher = new Label("Teacher");
		lteacher.setPrefWidth(200);
		lteacher.getStyleClass().add("option");
		lteacher.setOnMouseClicked(e->{
			mainPane.setCenter(null);
			mainPane.setCenter(new TeacherPane().getTeacherPane());
		});
		lstudent = new Label("Student");
		lstudent.setPrefWidth(200);
		lstudent.getStyleClass().add("option");
		lstudent.setOnMouseClicked(e->{
			mainPane.setCenter(null);
			mainPane.setCenter(new StudentPane().getStudentPane());
		});
		leftPane = new VBox(20,lenroll,lcourse,lteacher,lstudent);
		leftPane.setPadding(new Insets(20));
	}
	
	public void createMainPane()
	{
		mainPane = new BorderPane();
		mainPane.setTop(topPane);
		mainPane.setMargin(topPane, new Insets(0,0,20,0));
		mainPane.setLeft(leftPane);
		
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		st = new Stage();
		createTopPane();
		createLeftPane();
		createMainPane();
		
		Scene sc = new Scene(mainPane,900,700);
		URL url = this.getClass().getResource("schoolStyle.css");
		sc.getStylesheets().add(url.toExternalForm());
		st.setScene(sc);
		st.setTitle("Language School");
		st.show();
		
	}


	public static void main(String[] args) {
		Application.launch(args);
	}

}
