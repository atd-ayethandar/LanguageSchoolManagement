package ui;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.*;

public class UpdateCourse {
	ArrayList<Teacher> teachers;
	private Label lid, lname, lsdate, ledate, lteacher, lidErr, lnameErr;
	private TextField tid, tname;
	private DatePicker dsdate, dedate;
	private ComboBox<String> cbTeacher;
	private Button btnCreate, btnClear;
	
	private VBox idVb, nameVb;
	private GridPane mainPane;
	
	public void  createNode(Course c)
	{
		lid = new Label("Course ID:");
		lname = new Label("Course Name:");
		lsdate = new Label("Start date:");
		ledate = new Label("End date:");
		lteacher = new Label("Teacher:");
		lidErr = new Label("");
		lnameErr = new Label();
		tid = new TextField(c.getCourseID());
		tid.setEditable(false);
		tname = new TextField(c.getCourseName());
		tname.setOnKeyReleased(e->{
			String name = tname.getText();
			if(Checker.isValidName(name)) {
				lnameErr.setText("");
			}
			else
				lnameErr.setText("Wrong Name Format.");
		});
		dsdate = new DatePicker(c.getStartDate().toLocalDate());
		dedate = new DatePicker(c.getEndDate().toLocalDate());
		teachers = DBHandler.getAllTeacher();
		ArrayList<String> al = new ArrayList<String>();
		for(Teacher t: teachers) {
			al.add(t.getTeacherID());
		}
		cbTeacher = new ComboBox<String>(FXCollections.observableArrayList(al));
		for(int i=0; i<al.size(); i++) {
			if(cbTeacher.getItems().get(i).equals(c.getTeacher_id())){
				cbTeacher.getSelectionModel().select(i);
			}
		}
		
		btnCreate = new Button("Update");
		btnCreate.setOnAction(e->{
			updateCourse();
		});
		btnClear = new Button("Clear");
		btnClear.setOnAction(e->{
			clear();
		});
	}
	
	public void updateCourse()
	{
		String id = tid.getText();
		String name = tname.getText();
		Date sd = Date.valueOf(dsdate.getValue());
		Date ed = Date.valueOf(dedate.getValue());
		String tid = cbTeacher.getValue();
		Alert alt = new Alert(AlertType.INFORMATION);
		if(lnameErr.getText() == "" && tid != "") {
			if(DBHandler.updateCourse(id, name, sd, ed, tid)) {
				alt.setContentText("Successfully updated");
				alt.show();
				clear();
			}
			else {
				alt.setContentText("Can't update");
				alt.show();
			}
		}
		else {
			alt.setContentText("Can't update");
			alt.show();
		}
	}
	
	public void clear()
	{
		tname.setText("");
		tname.requestFocus();
		dsdate.setValue(LocalDate.now());
		dedate.setValue(LocalDate.now());
		cbTeacher.getSelectionModel().select(0);
	}
	
	public void defineLayout()
	{
		mainPane = new GridPane();
		mainPane.add(lid, 0, 0);
		idVb = new VBox(tid,lidErr);
		mainPane.add(idVb, 1, 0);
		mainPane.add(lname, 0, 1);
		nameVb = new VBox(tname,lnameErr);
		mainPane.add(nameVb, 1, 1);
		mainPane.add(lsdate, 0, 2);
		mainPane.add(dsdate, 1, 2);
		mainPane.add(ledate, 0, 3);
		mainPane.add(dedate, 1, 3);
		mainPane.add(lteacher, 0, 4);
		mainPane.add(cbTeacher, 1, 4);
		mainPane.add(btnCreate, 0, 5);
		mainPane.add(btnClear, 1, 5);
		mainPane.setHgap(20);
		mainPane.setVgap(20);
		mainPane.setAlignment(Pos.TOP_CENTER);
		mainPane.setPadding(new Insets(20));
	}
	
	public void setStyle()
	{
		lidErr.getStyleClass().add("err");
		lnameErr.getStyleClass().add("err");
		mainPane.getStyleClass().add("centerPane");
	}
	
	public GridPane getUpdateCourse(Course c)
	{
		createNode(c);
		defineLayout();
		setStyle();
		
		return mainPane;
		
	}
}
