package by.onliner.newsonlinerby.Structures.News;

/**
 * Created by Mi Air on 21.10.2016.
 */

public class NewsAttributes {
    private Integer mId;
    private String mProject;

    public NewsAttributes() {
        this.mId = 0;
    }

    public NewsAttributes(Integer id, String project) {
        this.mId = id;
        this.mProject = project;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getProject() {
        return mProject;
    }

    public void setProject(String project) {
        this.mProject = project;
    }
}
