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

package by.onliner.news.Fragments.tabs;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;

import by.onliner.news.adapters.NewsListAdapter;
import by.onliner.news.listeners.OnLoadMoreListener;
import by.onliner.news.listeners.OnNewsListResponse;
import by.onliner.news.managers.NewsMgr;
import by.onliner.news.R;
import by.onliner.news.structures.news.News;

/**
 * The type Tab base.
 */
public class TabBase extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    protected String mProjectId = "default";

    // Intents
    public static String INTENT_URL_TAG = "URL";
    public static String INTENT_PROJECT_TAG = "PROJECT";
    public static String INTENT_TITLE_TAG = "TITLE";

    private NewsListAdapter mNewsListAdapter;

    // Views
    private Button btnLoadContent;
    private ViewGroup mLinerRepeatGroup;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ProgressBar mProgressBarStatus;

    public TabBase() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myFragmentView = inflater.inflate(R.layout.fragment_news_preview, container, false);

        // Views
        btnLoadContent = (Button) myFragmentView.findViewById(R.id.bt_loadContent);
        btnLoadContent.setOnClickListener(this);

        mLinerRepeatGroup = (ViewGroup) myFragmentView.findViewById(R.id.l_group_repeat);

        mProgressBarStatus = (ProgressBar) myFragmentView.findViewById(R.id.pb_news_list_loading);
        mProgressBarStatus.setVisibility(View.VISIBLE);

        mSwipeRefreshLayout = (SwipeRefreshLayout) myFragmentView.findViewById(R.id.swipe_news_preview);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) myFragmentView.findViewById(R.id.lv_news_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager horizontalLayoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        else
            horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(horizontalLayoutManager);

        mNewsListAdapter = new NewsListAdapter(getContext(), mProjectId, mRecyclerView);
        mNewsListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mNewsListAdapter.getResource().add(null);
                mNewsListAdapter.notifyItemInserted(mNewsListAdapter.getResource().size() - 1);
                loadingNews(true, false);
            }
        });

        mRecyclerView.setAdapter(mNewsListAdapter);
        mRecyclerView.setVisibility(View.INVISIBLE);

        loadingNews(false, false);
        return myFragmentView;
    }

    /**
     * Получнеие полного списка новостей
     */
    public void loadingNews(final boolean pull, final boolean refresh) {
        NewsMgr.getInstance().getLoadingNewsList(mProjectId, pull, refresh, new OnNewsListResponse() {
            @Override
            public void onResult(ArrayList<News> news) {
                mProgressBarStatus.setVisibility(View.GONE);

                if (news != null) {
                    mRecyclerView.setVisibility(View.VISIBLE);

                    if (mSwipeRefreshLayout.isRefreshing())
                        mSwipeRefreshLayout.setRefreshing(false);

                    // Запрос новостей
                    if (pull) {
                        mNewsListAdapter.notifyItemRemoved(mNewsListAdapter.getResource().size() - news.size()); // Очистка триггерного null
                        mNewsListAdapter.notifyItemRangeInserted(mNewsListAdapter.getResource().size() - news.size(), news.size()); // Сообщение о воставки элементов
                        mNewsListAdapter.setLoaded();
                    } else
                        mNewsListAdapter.notifyDataSetChanged();

                } else {
                    mLinerRepeatGroup.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);

                    if (mSwipeRefreshLayout.isRefreshing())
                        mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_loadContent:
                mLinerRepeatGroup.setVisibility(View.GONE);
                mProgressBarStatus.setVisibility(View.VISIBLE);
                loadingNews(false, true);
                break;
            default:
                throw new UnsupportedOperationException("Unknown view id (" + v.getId() + ")");
        }
    }

    @Override
    public void onRefresh() {
        loadingNews(false, true);
    }
}
