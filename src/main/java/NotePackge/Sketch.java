package NotePackge;

import java.io.Serializable;

public class Sketch implements Serializable {

    private String sketchPath;
    public Sketch (String path) {
        this.sketchPath =  path;
    }
    public String getPath() {
        return sketchPath;
    }
}