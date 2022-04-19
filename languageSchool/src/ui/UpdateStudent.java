package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.*;

public class UpdateStudent {
	public Label lid, lname, lphone, lidErr, lnameErr, lphoneErr;
	public TextField tid, tname, tphone;
	public Button btnAdd, btnClear;
	public VBox idVb, nameVb, phoneVb;
	public GridPane mainPane;
	
	public void createNode(Student s)
	{
		lid = new Label("ID: ");
		lname = new Label("Name: ");
		lphone = new Label("Phone: ");
		lidErr = new Label("");
		lnameErr = new Label("");
		lphoneErr = new Label("");
		tid = new TextField(s.getStudentID());
		tid.setEditable(false);
		tname = new TextField(s.getStudentName());
		tname.setOnKeyReleased(e->{
			String name = tname.getText();
			if(Checker.isValidName(name)) {
				lnameErr.setText("");
			}
			else
				lnameErr.setText("Wrong Name Format. e.g. Aye Aye");
		});
		tphone = new TextField(s.getStudentPhone());
		tphone.setOnKeyReleased(e->{
			String phone = tphone.getText();
			if(Checker.isValidPhone(phone)) {
				lphoneErr.setText("");
			}
			else
				lphoneErr.setText("Wrong Phone Format. e.g. 09...");
		});
		btnAdd = new Button("Update");
		btnAdd.setOnAction(e->{
			updateStudent();
		});
		btnClear = new Button("Clear");
		btnClear.setOnAction(e->{
			clear();
		});
	}
	
	public void updateStudent()
	{
		String id = tid.getText();
		String name = tname.getText();
		String phone = tphone.getText();
		Alert alt = new Alert(AlertType.INFORMATION);
		if(lidErr.getText() == "" && lnameErr.getText() == "" && lphoneErr.getText() == "") {
			if(DBHandler.updateStudent(id, name, phone)) {
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
		tphone.setText("");
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
		mainPane.add(lphone, 0, 2);
		phoneVb = new VBox(tphone,lphoneErr);
		mainPane.add(phoneVb, 1, 2);
		mainPane.add(btnAdd, 0, 3);
		mainPane.add(btnClear, 1, 3);
		mainPane.setHgap(20);
		mainPane.setVgap(20);
		mainPane.setAlignment(Pos.TOP_CENTER);
		mainPane.setPadding(new Insets(20));
	}

	public void setStyle()
	{
		lidErr.getStyleClass().add("err");
		lnameErr.getStyleClass().add("err");
		lphoneErr.getStyleClass().add("err");
		mainPane.getStyleClass().add("centerPane");
	}
	
	public GridPane getUpdateStudent(Student s)
	{
		createNode(s);
		defineLayout();
		setStyle();
		
		return mainPane;
	}
}
