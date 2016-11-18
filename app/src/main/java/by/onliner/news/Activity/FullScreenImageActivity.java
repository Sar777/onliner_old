package by.onliner.news.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import by.onliner.news.Adapters.ImageSliderAdapter;
import by.onliner.news.Listeners.FullScreenImageListener;
import by.onliner.news.R;
import by.onliner.news.Structures.User.User;

/**
 * Показ полноразмерного изображения
 */
public class FullScreenImageActivity extends AppCompatActivity {
    private ImageSliderAdapter mImageSliderAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_full_screen_image);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ArrayList<String> urls = getIntent().getExtras().getStringArrayList(FullScreenImageListener.INTENT_FULL_SCREEN_URL);
        mImageSliderAdapter = new ImageSliderAdapter(this, urls);

        ((TextView)findViewById(R.id.tv_fullscreen_counter)).setText(String.format("1 из %d", mImageSliderAdapter.getCount()));

        mViewPager = (ViewPager) findViewById(R.id.full_screen_image_pager);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mImageSliderAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageScrollStateChanged(int state) { }

            @Override
            public void onPageSelected(int position) {
                ((TextView)findViewById(R.id.tv_fullscreen_counter)).setText(String.format("%d из %d", position + 1, mImageSliderAdapter.getCount()));
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

                User user = User.createUser("", "");
                Log.e("ORION", user.getEmail());
            }
        }).start();
    }
}
