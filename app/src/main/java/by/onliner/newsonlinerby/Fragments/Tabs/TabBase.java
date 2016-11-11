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

package by.onliner.newsonlinerby.Fragments.Tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eschao.android.widget.elasticlistview.ElasticListView;
import com.eschao.android.widget.elasticlistview.LoadFooter;
import com.eschao.android.widget.elasticlistview.LoadFooter.DefaultLoadStateListener;
import com.eschao.android.widget.elasticlistview.OnLoadListener;

import by.onliner.newsonlinerby.Activity.ViewNewsActivity;
import by.onliner.newsonlinerby.Adapters.NewsListAdapter;
import by.onliner.newsonlinerby.Listeners.NewsListResponse;
import by.onliner.newsonlinerby.Managers.NewsMgr;
import by.onliner.newsonlinerby.R;
import by.onliner.newsonlinerby.Structures.News.News;

/**
 * The type Tab base.
 */
public class TabBase extends Fragment implements View.OnClickListener {
    protected String mProjectId = "default";

    // Intents
    public static String INTENT_URL_TAG = "URL";
    public static String INTENT_FRAGMENT = "ACTIVITY";

    private NewsListAdapter newsListAdapter;

    private View myFragmentView;

    // Views
    private Button btnLoadContent;
    private ViewGroup mLinerRepeatGroup;

    private ProgressBar progressBarStatus;
    private ElasticListView lvMain;

    public TabBase() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.t_news_preview, container, false);
        newsListAdapter = new NewsListAdapter(getContext(), mProjectId);

        // Views
        btnLoadContent = (Button)myFragmentView.findViewById(R.id.bt_loadContent);
        btnLoadContent.setOnClickListener(this);

        mLinerRepeatGroup = (ViewGroup)myFragmentView.findViewById(R.id.l_group_repeat);

        progressBarStatus = (ProgressBar)myFragmentView.findViewById(R.id.pb_news_list_loading);

        lvMain = (ElasticListView)myFragmentView.findViewById(R.id.lv_news_list);
        lvMain.setHorizontalFadingEdgeEnabled(true);
        lvMain.setClickable(true);
        lvMain.setAdapter(newsListAdapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                News item = (News)lvMain.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), ViewNewsActivity.class);
                intent.putExtra(INTENT_URL_TAG, item.getAttributes().getUrl());
                intent.putExtra(INTENT_FRAGMENT, myFragmentView.getId());
                startActivity(intent);
            }
        });

        DefaultLoadStateListener listener = new LoadFooter.DefaultLoadStateListener() {
            @Override
            public void onPullingUp(View view) {
                View progress = view.findViewById(R.id.pb_footer_loading);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onLoading(View view) {
                View progress = view.findViewById(R.id.pb_footer_loading);
                progress.setVisibility(View.VISIBLE);
                TextView text = (TextView)view.findViewById(R.id.tv_footer_text);
                text.setText(R.string.footerLoading);
            }
        };

        lvMain.enableUpdateHeader(false);
        lvMain.enableLoadFooter(true);
        lvMain.getLoadFooter().setOnLoadStateListener(listener).setContentView(R.layout.layout_news_footer_load, true);
        lvMain.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                loadingNews(true);
            }
        });

        loadingNews(false);
        return myFragmentView;
    }

    /**
     * Получнеие полного списка новостей
     */
    public void loadingNews(final boolean pull) {
        if (!pull) {
            lvMain.setVisibility(View.INVISIBLE);
            progressBarStatus.setVisibility(View.VISIBLE);
            mLinerRepeatGroup.setVisibility(View.GONE);
        }

        NewsMgr.getInstance().getLoadingNewsList(mProjectId, pull, new NewsListResponse() {
            @Override
            public void onResult(boolean success) {
                if (success) {
                    lvMain.setVisibility(View.VISIBLE);
                    // Обновление списка
                    newsListAdapter.notifyDataSetChanged();
                } else {
                    if (!pull) {
                        mLinerRepeatGroup.setVisibility(View.VISIBLE);
                        progressBarStatus.setVisibility(View.GONE);
                    }
                }

                progressBarStatus.setVisibility(View.GONE);
                lvMain.notifyLoaded();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_loadContent:
                loadingNews(false);
                break;
            default:
                throw new UnsupportedOperationException("Unknown view id (" + v.getId() + ")");
        }
    }
}
