package ui;

import java.net.URL;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import model.*;

public class EnrollPane {
	ArrayList<Course> courses;
	
	private Label lsid, lcid, lsidErr;
	private TextField tsid;
	private ComboBox<String> cbcourses;
	private Button btnEnroll, btnClear;
	
	private VBox sidVb;
	private GridPane enrollPane;
	
	public void createNode()
	{
		lsid = new Label("Student ID:");
		lcid = new Label("Course ID:");
		lsidErr = new Label();
		lsidErr.getStyleClass().add("err");
		tsid = new TextField();
		tsid.setOnKeyReleased(e->{
			String name = tsid.getText();
			if(Checker.isValidStudent(name)) {
				lsidErr.setText("");
			}
			else {
				lsidErr.setText("Wrong Id format!!e.g. S001");
			}
		});
		courses = DBHandler.getAllCourse();
		ArrayList<String> al = new ArrayList<String>();
		for(Course c: courses) {
			al.add(c.getCourseID());
		}
		cbcourses = new ComboBox<String>(FXCollections.observableArrayList(al));
		
		btnEnroll = new Button("Enroll");
		btnEnroll.setOnAction(e->{
			enroll();
		});
		btnClear = new Button("Clear");
		btnClear.setOnAction(e->{
			clear();
		});
	}
	
	public void enroll()
	{
		String sid = tsid.getText();
		String cid = cbcourses.getValue();
		Alert alt = new Alert(AlertType.INFORMATION);
		if(lsidErr.getText() != "") {
			alt.setContentText("Can't enroll");
			alt.show();
		}
		else {
			if(DBHandler.enroll(sid, cid)) {
				alt.setContentText("Successfully enrolled");
				alt.show();
				clear();
			}
			else {
				alt.setContentText("Can't enroll");
				alt.show();
			}
		}
	}
	
	public void clear() {
		tsid.setText("");
		tsid.requestFocus();
		cbcourses.getSelectionModel().select(null);
	}
	
	public void defineLayout()
	{
		enrollPane = new GridPane();
		enrollPane.add(lsid, 0, 0);
		sidVb = new VBox(tsid,lsidErr);
		enrollPane.add(sidVb, 1, 0);
		enrollPane.add(lcid, 0, 1);
		enrollPane.add(cbcourses, 1, 1);
		enrollPane.add(btnEnroll, 0, 2);
		enrollPane.add(btnClear, 1, 2);
		enrollPane.setHgap(20);
		enrollPane.setVgap(20);
		enrollPane.setAlignment(Pos.TOP_CENTER);
		enrollPane.setPadding(new Insets(20));
		enrollPane.getStyleClass().add("centerPane");
	}
	
	public GridPane getEnrollPane()
	{
		createNode();
		defineLayout();
	//	URL url = this.getClass().getResource("schoolStyle.css");
	//	enrollPane.getStylesheets().add(url.toExternalForm());
		
		return enrollPane;
	}
	
}
