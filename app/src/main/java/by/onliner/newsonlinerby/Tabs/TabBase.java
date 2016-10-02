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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import by.onliner.newsonlinerby.Adapters.NewsListAdapter;
import by.onliner.newsonlinerby.Parser.Parsers.PreviewParser;
import by.onliner.newsonlinerby.R;
import by.onliner.newsonlinerby.Structures.Preview.PreviewData;
import cz.msebera.android.httpclient.Header;

public class TabBase extends Fragment implements View.OnClickListener {
    protected String Url = "https://onliner.by";

    protected TabStatus status;

    private ArrayList<PreviewData> news = new ArrayList<PreviewData>();
    private NewsListAdapter newsListAdapter;

    private View myFragmentView;

    private Button btnLoadContent;
    private ProgressBar progressBarStatus;

    private AsyncHttpClient client = new AsyncHttpClient();

    public TabBase() {
        status = TabStatus.None;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.t_news_preview, container, false);
        newsListAdapter = new NewsListAdapter(getContext(), news);

        // Views
        btnLoadContent = (Button)myFragmentView.findViewById(R.id.btnLoadContent);
        btnLoadContent.setOnClickListener(this);

        progressBarStatus = (ProgressBar)myFragmentView.findViewById(R.id.progressBarLoading);

        ListView lvMain = (ListView)myFragmentView.findViewById(R.id.lvMain);
        lvMain.setAdapter(newsListAdapter);

        LoadContentIfNeed();
        return myFragmentView;
    }

    public void LoadingContent() {
        RequestParams requestParams = new RequestParams();
        client.get(Url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progressBarStatus.setVisibility(View.VISIBLE);
                status = TabStatus.Loading;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                news.clear();
                Document doc = Jsoup.parse(new String(responseBody));
                Elements elements = doc.getElementsByClass("news-tidings__item_condensed");
                for (Element element : elements) {
                    PreviewData data = new PreviewParser().parse(element);
                    if (!data.isValid())
                        continue;

                    news.add(data);
                }

                getView().findViewById(R.id.progressBarLoading).setVisibility(View.GONE);
                status = TabStatus.Loaded;
                newsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                btnLoadContent.setVisibility(View.VISIBLE);
                progressBarStatus.setVisibility(View.GONE);
                status = TabStatus.Fail;
            }
        });
    }

    private void LoadContentIfNeed() {

        switch (status) {
            case None:
                LoadingContent();
                break;
            case Fail: {
                btnLoadContent.setVisibility(View.VISIBLE);
                break;
            }
            case Loaded:
            case Loading:
                break;
            default:
                throw new UnsupportedOperationException("Unknown loading tab status(" + status + ")");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLoadContent: {
                btnLoadContent.setVisibility(View.GONE);
                LoadingContent();
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown view id");
        }
    }

    public TabStatus getStatus() {
        return status;
    }
}
