/*
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package by.onliner.news.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import by.onliner.news.Adapters.ViewPagerAdapter;
import by.onliner.news.App;
import by.onliner.news.Fragments.Tabs.TabBase;
import by.onliner.news.R;
import by.onliner.news.Structures.User.User;
import by.onliner.news.Transforms.CircleTransform;

/**
 * Главное окно
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {

    // Views
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private Button mButtonAuth;
    private Button mButtonLogout;
    private ImageView mImageViewNavBarAvatar;
    private TextView mTextViewNavBarUsername;

    private ViewPager mPager;
    private ViewPagerAdapter mAdapter;

    private TabLayout mTabs;
    private CharSequence mTitles[];
    private final static int Numboftabs = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        mDrawerLayout.addDrawerListener(this);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        View headerNavigationView = mNavigationView.getHeaderView(0);

        mButtonAuth = (Button) headerNavigationView.findViewById(R.id.bt_auth_account);
        mButtonAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                Intent intent = new Intent(App.getContext(), AuthActivity.class);
                startActivity(intent);
            }
        });

        mButtonLogout = (Button) headerNavigationView.findViewById(R.id.bt_logout_account);
        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                App.logoutUser();
                updateNavBarLoginInfo();
            }
        });

        mImageViewNavBarAvatar = (ImageView) headerNavigationView.findViewById(R.id.img_nav_header_avatar);
        mTextViewNavBarUsername = (TextView) headerNavigationView.findViewById(R.id.tv_nav_bar_username);

        updateNavBarLoginInfo();

        mTitles = new CharSequence[] { getString(R.string.tabAuto),  getString(R.string.tabPeoples),  getString(R.string.tabRealt),  getString(R.string.tabTechnologies) };

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTitles, Numboftabs);

        mPager = (ViewPager) findViewById(R.id.pager_news_list);
        mPager.setOffscreenPageLimit(1);
        mPager.setAdapter(mAdapter);

        mTabs = (TabLayout) findViewById(R.id.tabs_news_list);
        mTabs.setupWithViewPager(mPager);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0)
            super.onBackPressed();
        else
            getFragmentManager().popBackStack();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                int index = mPager.getCurrentItem();
                ViewPagerAdapter adapter = ((ViewPagerAdapter)mPager.getAdapter());
                TabBase fragment = adapter.getFragment(index);
                fragment.loadingNews(false, true);
                break;
            default:
                break;
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        updateNavBarLoginInfo();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    public void updateNavBarLoginInfo() {
        User user = App.getLoggedUser();
        if (user == null) {
            mImageViewNavBarAvatar.setImageResource(R.drawable.ic_profile_user);

            mTextViewNavBarUsername.setVisibility(View.GONE);
            mTextViewNavBarUsername.setText("");

            mButtonAuth.setVisibility(View.VISIBLE);
            mButtonLogout.setVisibility(View.GONE);
        }
        else {
            Picasso.with(this).
                    load(App.getLoggedUser().getAvatar().getUrlLarge()).
                    transform(new CircleTransform()).
                    error(R.drawable.ic_broken_image).
                    into(mImageViewNavBarAvatar);

            mTextViewNavBarUsername.setVisibility(View.VISIBLE);
            mTextViewNavBarUsername.setText(App.getLoggedUser().getUsername());

            mButtonAuth.setVisibility(View.GONE);
            mButtonLogout.setVisibility(View.VISIBLE);
        }
    }
}
