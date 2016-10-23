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

package by.onliner.newsonlinerby.Tabs;

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
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import by.onliner.newsonlinerby.Adapters.NewsListAdapter;
import by.onliner.newsonlinerby.Listeners.NewsListResponse;
import by.onliner.newsonlinerby.Managers.NewsMgr;
import by.onliner.newsonlinerby.R;
import by.onliner.newsonlinerby.Structures.HeaderNews;
import by.onliner.newsonlinerby.ViewNewsActivity;

public class TabBase extends Fragment implements View.OnClickListener {
    protected String Url = "https://onliner.by";

    public static String INTENT_URL_TAG = "URL";
    public static String INTENT_FRAGMENT = "ACTIVITY";

    protected TabStatus status;

    private ArrayList<HeaderNews> newsData = new ArrayList<>();
    private NewsListAdapter newsListAdapter;

    private View myFragmentView;

    private Button btnLoadContent;
    private ProgressBar progressBarStatus;
    private ElasticListView lvMain;

    public TabBase() {
        status = TabStatus.None;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.t_news_preview, container, false);
        newsListAdapter = new NewsListAdapter(getContext(), newsData);

        // Views
        btnLoadContent = (Button)myFragmentView.findViewById(R.id.btnLoadContent);
        btnLoadContent.setOnClickListener(this);

        progressBarStatus = (ProgressBar)myFragmentView.findViewById(R.id.progressBarLoading);

        lvMain = (ElasticListView)myFragmentView.findViewById(R.id.lvMain);
        lvMain.setHorizontalFadingEdgeEnabled(true);
        lvMain.setClickable(true);
        lvMain.setAdapter(newsListAdapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                HeaderNews o = (HeaderNews)lvMain.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), ViewNewsActivity.class);
                intent.putExtra(INTENT_URL_TAG, Url + o.getUrl());
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
        lvMain.getLoadFooter().setOnLoadStateListener(listener).setContentView(R.layout.l_news_footer_load, true);
        lvMain.setOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoad() {
                status = TabStatus.Pull;
                LoadingContent();
            }
        });

        status = TabStatus.Load;

        LoadContentIfNeed();
        return myFragmentView;
    }

    public void LoadingContent() {
        RequestParams requestParams = new RequestParams();

        switch (status) {
            case Load: {
                newsData.clear();
                progressBarStatus.setVisibility(View.VISIBLE);
                break;
            }
            case Pull: {
                if (newsData.isEmpty())
                    throw new IllegalArgumentException("Empty news data container for pull news");

                requestParams.put("fromDate", newsData.get(newsData.size() - 1).getPostDateUnix());
                break;
            }
            default:
                break;
        }

        NewsMgr.getInstance().getAsyncNewsList(Url, requestParams, new NewsListResponse<ArrayList<HeaderNews>>() {
            @Override
            public void OnSuccess(ArrayList<HeaderNews> response) {
                View view = getView();
                if (view == null)
                    return;

                for (HeaderNews header : response){
                    if (!newsData.contains(header))
                        newsData.add(header);
                }

                if (status == TabStatus.Pull)
                    lvMain.notifyLoaded();
                else
                    view.findViewById(R.id.progressBarLoading).setVisibility(View.GONE);

                newsListAdapter.notifyDataSetChanged();
                status = TabStatus.None;
            }

            @Override
            public void onFailure() {
                if (status == TabStatus.Pull)
                    lvMain.notifyLoaded();
                else {
                    btnLoadContent.setVisibility(View.VISIBLE);
                    progressBarStatus.setVisibility(View.GONE);
                }

                status = TabStatus.Fail;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadContent:
                btnLoadContent.setVisibility(View.GONE);
                LoadingContent();
                break;
            default:
                throw new UnsupportedOperationException("Unknown view id (" + v.getId() + ")");
        }
    }

    private void LoadContentIfNeed() {
        switch (status) {
            case Pull:
            case Load:
                LoadingContent();
                break;
            case Fail:
                btnLoadContent.setVisibility(View.VISIBLE);
                break;
            default:
                throw new UnsupportedOperationException("Unknown loading tab status(" + status + ")");
        }
    }
}
