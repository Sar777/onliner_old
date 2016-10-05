package by.onliner.newsonlinerby.Structures;

/**
 * Created by Mi Air on 06.10.2016.
 */

public class HeaderAttributes {
    private Boolean upd;
    private Boolean camera;
    private Integer photos;

    public HeaderAttributes() {
        this.upd = false;
        this.camera = false;
        this.photos = 0;
    }

    public HeaderAttributes(Boolean upd, Boolean camera, Integer photos) {
        this.upd = upd;
        this.camera = camera;
        this.photos = photos;
    }

    public HeaderAttributes(HeaderAttributes attr) {
        this.upd = attr.getUpd();
        this.camera = attr.getCamera();
        this.photos = attr.getPhotos();
    }

    public Boolean getUpd() {
        return upd;
    }

    public void setUpd(Boolean upd) {
        this.upd = upd;
    }

    public Boolean getCamera() {
        return camera;
    }

    public void setCamera(Boolean camera) {
        this.camera = camera;
    }

    public Integer getPhotos() {
        return photos;
    }

    public void setPhotos(Integer photos) {
        this.photos = photos;
    }
}
