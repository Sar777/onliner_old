package by.onliner.newsonlinerby.Listeners;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import by.onliner.newsonlinerby.App;
import by.onliner.newsonlinerby.FullScreenImageActivity;

/**
 * Created by orion on 1.11.16.
 */

public class FullScreenImageListener implements View.OnClickListener {
    public static String INTENT_FULL_SCREEN_URL = "URL";

    private Activity mActivity;
    private ArrayList<String> mImageUrls;

    public FullScreenImageListener(Activity activity, ArrayList<String> imageUrls) {
        this.mActivity = activity;
        this.mImageUrls = new ArrayList<>(imageUrls);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(App.getContext(), FullScreenImageActivity.class);
        intent.putStringArrayListExtra(INTENT_FULL_SCREEN_URL, mImageUrls);
        mActivity.startActivity(intent);
    }
}
