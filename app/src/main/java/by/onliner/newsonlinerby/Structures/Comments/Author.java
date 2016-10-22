package by.onliner.newsonlinerby.Structures.Comments;

import java.io.Serializable;

/**
 * Created by Mi Air on 21.10.2016.
 */

public class Author implements Serializable {
    private Integer mId;
    private String mName;

    public Author() {
    }

    public Author(String name, Integer id) {
        this.mName = name;
        this.mId = id;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
