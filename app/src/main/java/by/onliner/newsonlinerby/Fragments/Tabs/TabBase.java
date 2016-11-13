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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;

import by.onliner.newsonlinerby.Adapters.NewsListAdapter;
import by.onliner.newsonlinerby.Listeners.NewsListResponse;
import by.onliner.newsonlinerby.Listeners.OnLoadMoreListener;
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

    private NewsListAdapter mNewsListAdapter;

    private View myFragmentView;

    // Views
    private Button btnLoadContent;
    private ViewGroup mLinerRepeatGroup;
    private RecyclerView mRecyclerView;

    private ProgressBar progressBarStatus;

    public TabBase() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.t_news_preview, container, false);

        // Views
        btnLoadContent = (Button)myFragmentView.findViewById(R.id.bt_loadContent);
        btnLoadContent.setOnClickListener(this);

        mLinerRepeatGroup = (ViewGroup)myFragmentView.findViewById(R.id.l_group_repeat);

        progressBarStatus = (ProgressBar)myFragmentView.findViewById(R.id.pb_news_list_loading);

        mRecyclerView = (RecyclerView) myFragmentView.findViewById(R.id.lv_news_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(horizontalLayoutManagaer);

        mNewsListAdapter = new NewsListAdapter(getContext(), mProjectId, mRecyclerView);

        mNewsListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mNewsListAdapter.getResource().add(null);
                mNewsListAdapter.notifyItemInserted(mNewsListAdapter.getResource().size() - 1);
                loadingNews(true);
            }
        });

        mRecyclerView.setAdapter(mNewsListAdapter);

        loadingNews(false);
        return myFragmentView;
    }

    /**
     * Получнеие полного списка новостей
     */
    public void loadingNews(final boolean pull) {
        if (!pull) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            progressBarStatus.setVisibility(View.VISIBLE);
            mLinerRepeatGroup.setVisibility(View.GONE);
        }

        NewsMgr.getInstance().getLoadingNewsList(mProjectId, pull, new NewsListResponse() {
            @Override
            public void onResult(boolean success, ArrayList<News> news) {
                if (success) {
                    mRecyclerView.setVisibility(View.VISIBLE);

                    // Скрываем прогрессбар
                    if (pull) {
                        mNewsListAdapter.getResource().remove(mNewsListAdapter.getResource().size() - 1);
                        mNewsListAdapter.notifyItemRemoved(mNewsListAdapter.getResource().size());
                    }

                    mNewsListAdapter.getResource().addAll(news);

                    // Обновление списка
                    mNewsListAdapter.notifyItemRangeInserted(mNewsListAdapter.getResource().size(), news.size());
                    mNewsListAdapter.setLoaded();
                } else {
                    if (!pull) {
                        mLinerRepeatGroup.setVisibility(View.VISIBLE);
                        progressBarStatus.setVisibility(View.GONE);
                    }
                }

                progressBarStatus.setVisibility(View.GONE);
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
