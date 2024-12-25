package FileManagement;

import NotePackge.Note;
import NotePackge.Secured_Note;

import java.util.LinkedList;

public class test {
    public static void main(String[] args) {
//        FileManager.updateAllUsersData(new LinkedList<User>());
        LinkedList<User> data = FileManager.UploadAllData();
        User user = data.get(1);
        System.out.println(user.UserName());
        System.out.println(((Secured_Note)user.getFolder().getAllNotes().get(0)).getPassword());
    }
}
