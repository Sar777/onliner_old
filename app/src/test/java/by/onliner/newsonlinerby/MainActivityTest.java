package by.onliner.newsonlinerby;

import android.support.v4.view.ViewPager;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by orion on 3.11.16.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, packageName="by.onliner.newsonlinerby")
public class MainActivityTest {

    private MainActivity mActivity;

    @Before
    public void setUp() throws Exception
    {
        mActivity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }

    @Test
    public void shouldNotBeNull() throws Exception  {
        assertNotNull(mActivity);
    }

    @Test
    public void pagerAdapterNotBeNull() throws Exception {
        assertNotNull(mActivity.findViewById(R.id.pager_news_list));
    }

    @Test
    public void pagerTabsCount() throws Exception {
        ViewPager pager = (ViewPager) mActivity.findViewById(R.id.pager_news_list);
        Assert.assertEquals(pager.getAdapter().getCount(), 4);
    }
}
