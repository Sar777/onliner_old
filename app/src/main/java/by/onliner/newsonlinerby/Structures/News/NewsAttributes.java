package by.onliner.newsonlinerby.Structures.News;

/**
 * Created by Mi Air on 21.10.2016.
 */

public class NewsAttributes {
    private Integer mId;
    private String mProject;
    private String mUrl;

    public NewsAttributes() {
        this.mId = 0;
        this.mProject = "";
        this.mUrl = "";
    }

    public NewsAttributes(Integer id, String project, String url) {
        this.mId = id;
        this.mProject = project;
        this.mUrl = url;
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

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }
}
