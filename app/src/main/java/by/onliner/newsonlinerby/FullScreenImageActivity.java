package by.onliner.newsonlinerby;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import by.onliner.newsonlinerby.Listeners.FullScreenImageListener;
import eu.fiskur.simpleviewpager.ImageURLLoader;
import eu.fiskur.simpleviewpager.SimpleViewPager;

public class FullScreenImageActivity extends AppCompatActivity {

    private SimpleViewPager mSimpleViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        mSimpleViewPager = (SimpleViewPager)findViewById(R.id.full_screen_image_pager);

        ArrayList<String> urls = getIntent().getExtras().getStringArrayList(FullScreenImageListener.INTENT_FULL_SCREEN_URL);

        mSimpleViewPager.setImageUrls(urls.toArray(new String[urls.size()]), new ImageURLLoader() {
            @Override
            public void loadImage(ImageView view, String url) {
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                view.setAdjustViewBounds(true);

                Picasso.with(App.getContext()).
                        load(url).
                        error(R.drawable.ic_broken_image).
                        memoryPolicy(MemoryPolicy.NO_CACHE).
                        into(view);
            }
        });

        mSimpleViewPager.showIndicator(Color.WHITE, Color.BLACK);
    }
}
