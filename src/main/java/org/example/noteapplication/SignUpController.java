package org.example.noteapplication;

import FileManagement.FileManager;
import FileManagement.User;
import My_Lib.valdation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private TextField username_txt;
    @FXML
    private PasswordField password_txt;
    @FXML
    private PasswordField confirmpassword_txt;
    @FXML
    private Button SignUp_Btn;
    @FXML
    private Button Login_Btn;
    @FXML
    private Label Sign_Msg;
    HashMap<String,String> map ;
    public void try_Sign(ActionEvent e) throws IOException {
        User user = new User(username_txt.getText(),password_txt.getText());
        if(map.get(user.UserName()) == null && valdation.checkPassword(password_txt.getText(), confirmpassword_txt.getText())){
            FileManager.addUserInSystem(user);
            FileManager.setCurrentUser(user);
            // go to UserHomePage
            Node node = (Node)e.getSource();
            Stage stage = (Stage)node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
            Scene scene = new Scene(root,1200,700);
            stage.setTitle("Nationary");
            stage.setScene(scene);
            stage.show();
            stage.setResizable(true);
            return;
        }
        Sign_Msg.setText("User Name is taken or password does not match");
    }
    public void goToLoginPage(ActionEvent e) throws IOException {
        Node node = (Node)e.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene scene = new Scene(root,1200,700);
        stage.setTitle("Nationary");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LinkedList<User> data = FileManager.UploadAllData();
        map = new HashMap<String,String>();
        int idx = 0;
        while(idx < data.size()) {
            map.put(data.get(idx).UserName(),data.get(idx).Password());// coping data to a map for better Time complexity
            idx++;
        }
    }
}