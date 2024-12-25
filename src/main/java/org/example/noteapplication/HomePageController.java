package org.example.noteapplication;

import FileManagement.FileManager;
import FileManagement.User;
import NotePackge.Encryption;
import NotePackge.Note;
import NotePackge.Secured_Note;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    Label search_Msg = new Label();
    @FXML
    private Label emptyLabel = new Label();
    @FXML
    private Label usernameLabel = new Label();
    @FXML
    private TextField SearchBar_txt = new TextField();
    @FXML
    private ListView<Note> noteListView = new ListView<Note>();
    @FXML
    PasswordField displayPassword_txt = new PasswordField();
    @FXML
    Button enterBtn = new Button();
    private ObservableList<Note> noteList;
    private User currentUser;
    private LinkedList<Note> notes;
    public void do_Logout(@NotNull ActionEvent e) throws IOException {
        Node node = (Node)e.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene scene = new Scene(root,1200,700);
        stage.setTitle("Nationary");
        stage.setScene(scene);
        stage.show();
        return;
    }
    public void do_Exit(ActionEvent e) throws IOException {
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayPassword_txt.setVisible(false);
        enterBtn.setVisible(false);
        currentUser = FileManager.get_CurrentUser();
        noteList = FXCollections.observableArrayList(currentUser.getFolder().getAllNotes());
        noteListView.getItems().addAll(noteList);
   }
    public void goToCreatePage(ActionEvent e) throws IOException {
        Node node = (Node)e.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("CreationPage.fxml"));
        Scene scene = new Scene(root,1200,700);
        stage.setTitle("Nationary");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
        return;
    }
    public void deleteNote(ActionEvent e) throws IOException {
        if(noteListView.getSelectionModel().getSelectedItem() == null)return;
        int index = noteListView.getSelectionModel().getSelectedIndex();
        noteList.remove(index);
        noteListView.getItems().remove(index);
        currentUser.getFolder().remove(index);
        FileManager.setCurrentUser(currentUser);
    }
    public void Go_Display(ActionEvent e) throws IOException {
        if(noteListView.getSelectionModel().getSelectedItem() == null) return;
        Secured_Note currentNote = (Secured_Note) noteListView.getSelectionModel().getSelectedItem();
        if(currentNote.getPassword().isEmpty() || currentNote.getPassword().equals(Encryption.hash(""))){
            displayPassword_txt.setVisible(false);
            enterBtn.setVisible(false);
            Do_Display(e,currentNote);
            return;
        }
        displayPassword_txt.setVisible(true);
        enterBtn.setVisible(true);
        return;
    }
    public void Do_Display(ActionEvent e , Secured_Note currentNote) throws IOException {
        FileManager.setCurrentNote(currentNote);
        Node node = (Node)e.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("DisplayPage.fxml"));
        Scene scene = new Scene(root,1200,700);
        stage.setTitle("Nationary");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(true);
        return;
    }
    public void Do_Search(ActionEvent e) throws IOException {
        if(SearchBar_txt.getText().equals("") || SearchBar_txt.getText().isEmpty())return;
        int idx = 0;
        while(idx < noteList.size()){
            Secured_Note note = (Secured_Note) noteList.get(idx);
            if(note.getName().equals(SearchBar_txt.getText())){
                noteListView.getSelectionModel().select(idx);
                Go_Display(e);
                return;
            }
            idx++;
        }
        search_Msg.setText("Can't find this note ,Please try again");
    }
    public void check_Note_Password (ActionEvent e) throws IOException {
        if(noteListView.getSelectionModel().getSelectedItem() == null) return;
        Secured_Note currentNote = (Secured_Note) noteListView.getSelectionModel().getSelectedItem();
        if(! currentNote.getPassword().equals(displayPassword_txt.getText())){
            displayPassword_txt.setVisible(false);
            Do_Display(e,currentNote);
        }
    }

}