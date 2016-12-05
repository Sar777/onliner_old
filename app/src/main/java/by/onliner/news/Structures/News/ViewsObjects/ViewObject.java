package by.onliner.news.Structures.News.ViewsObjects;

import android.os.Parcel;
import android.os.Parcelable;

import by.onliner.news.Enums.ViewNewsType;

/**
 * Created by orion on 15.11.16.
 */

public abstract class ViewObject implements Parcelable {
    private ViewNewsType mType;

    public ViewObject(ViewNewsType type) {
        this.mType = type;
    }

    public ViewNewsType getType() {
        return mType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public abstract boolean isValid();

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mType.ordinal());
    }

    public static final Creator<ViewObject> CREATOR = new Creator<ViewObject>() {
        @Override
        public ViewObject createFromParcel(Parcel source) {

            return ViewObject.getConcreteClass(source);
        }

        @Override
        public ViewObject[] newArray(int size) {
            return new ViewObject[size];
        }
    };

    private static ViewObject getConcreteClass(Parcel source) {
        switch (ViewNewsType.values()[source.readInt()]) {
            case TYPE_VIEW_PREVIEW:
                return new PreviewViewObject(source);
            case TYPE_VIEW_TITLE:
                return new TitleViewObject(source);
            case TYPE_VIEW_SPAN:
                return new SpanViewObject(source);
            case TYPE_VIEW_QUOTE:
                return new QuoteViewObject(source);
            case TYPE_VIEW_UL:
                return new ULViewObject(source);
            case TYPE_VIEW_HR:
                return new HRViewObject(source);
            case TYPE_VIEW_H2:
                return new H2ViewObject(source);
            case TYPE_VIEW_SPEECH:
                return new SpeechViewObject(source);
            case TYPE_VIEW_VOTE:
                return new VoteViewObject(source);
            case TYPE_VIEW_IMAGE:
                return new ImageViewObject(source);
            case TYPE_VIEW_IMAGE_SLIDER:
                return new ImageSliderViewObject(source);
            case TYPE_VIEW_YOUTUBE_PLAYER:
                return new YoutubeViewObject(source);
            default:
                return null;
        }
    }
}
