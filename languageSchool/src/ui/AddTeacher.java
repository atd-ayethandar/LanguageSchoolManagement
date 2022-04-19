package ui;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import model.Checker;
import model.DBHandler;

public class AddTeacher {
	
	public Label lid, lname, lphone, lidErr, lnameErr, lphoneErr;
	public TextField tid, tname, tphone;
	public Button btnAdd, btnClear;
	public VBox idVb, nameVb, phoneVb;
	public GridPane mainPane;
	
	public void createNode()
	{
		lid = new Label("ID: ");
		lname = new Label("Name: ");
		lphone = new Label("Phone: ");
		lidErr = new Label();
		lnameErr = new Label();
		lphoneErr = new Label();
		tid = new TextField();
		tid.setOnKeyReleased(e->{
			String id = tid.getText();
			if(Checker.isValidTeacherID(id)) {
				lidErr.setText("");
			}
			else
				lidErr.setText("Wrong Id Format. e.g. T001");
		});
		tname = new TextField();
		tname.setOnKeyReleased(e->{
			String name = tname.getText();
			if(Checker.isValidName(name)) {
				lnameErr.setText("");
			}
			else
				lnameErr.setText("Wrong Name Format. e.g. Aye Aye");
		});
		tphone = new TextField();
		tphone.setOnKeyReleased(e->{
			String phone = tphone.getText();
			if(Checker.isValidPhone(phone)) {
				lphoneErr.setText("");
			}
			else
				lphoneErr.setText("Wrong Phone Format. e.g. 09...");
		});
		btnAdd = new Button("Add");
		btnAdd.setOnAction(e->{
			addTeacher();
		});
		btnClear = new Button("Clear");
		btnClear.setOnAction(e->{
			clear();
		});
	}
	
	public void addTeacher()
	{
		String id = tid.getText();
		String name = tname.getText();
		String phone = tphone.getText();
		Alert alt = new Alert(AlertType.INFORMATION);
		if(lidErr.getText() == "" && lnameErr.getText() == "" && lphoneErr.getText() == "") {
			if(DBHandler.insertTeacher(id, name, phone)) {
				alt.setContentText("Successfully added");
				alt.show();
				clear();
			}
			else {
				alt.setContentText("Can't add");
				alt.show();
			}
		}
		else {
			alt.setContentText("Can't add");
			alt.show();
		}
	}
	
	public void clear()
	{
		tid.setText("");
		tid.requestFocus();
		tname.setText("");
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
	
	public GridPane getAddTeacher()
	{
		createNode();
		defineLayout();
		setStyle();
		
		return mainPane;
	}

}
