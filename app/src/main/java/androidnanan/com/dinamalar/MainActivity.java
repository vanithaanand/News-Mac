package androidnanan.com.dinamalar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String FRONT_PAGE_URI =
            "http://feeds.feedburner.com/dinamalar/Front_page_news?format=xml";
    private static final String POLITICS_PAGE_URI =
            "http://rss.dinamalar.com/?cat=ara1";
    private static final String GENERAL_PAGE_URI =
            "http://rss.dinamalar.com/?cat=pot1";
    private static final String INCIDENTS_PAGE_URI =
            "http://rss.dinamalar.com/?cat=sam1";
    private static final String COURT_NEWS_PAGE_URI =
            "http://rss.dinamalar.com/?cat=kut1";
    private static final String TAMIL_NADU_PAGE_URI =
            "http://rss.dinamalar.com/tamilnadunews.asp";
    private static final String DELHI_NEWS_PAGE_URI =
            "http://rss.dinamalar.com/Delhinews.asp";
    private static final String INTERNATIONAL_NEWS_PAGE_URI =
            "http://rss.dinamalar.com/?cat=INL1";
    private static final String BUSINESS_NEWS_PAGE_URI =
            "http://rss.dinamalar.com/?cat=business1";
    private static final String CINEMA_NEWS_PAGE_URI =
            "http://cinema.dinamalar.com/rss.php";
    private static final String SPORTS_NEWS_PAGE_URI =
            "http://cinema.dinamalar.com/rss.php";

    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int DFP_CONTENT_AD_INDEX = 6;
    private static final String DFP_NATIVE_AD_UNIT_ID = "/6499/example/native";
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private DinamalarAdapter adapter;
    private ProgressBar progressBar;
    private int itemSelected = 0;
 //   private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //  recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        adapter = new DinamalarAdapter(this, new ArrayList<>());

        recyclerView.setAdapter(adapter);

        setTitle(getString(R.string.action_first_page_news));

        GetFeed feedTask = new GetFeed();
        feedTask.execute(0);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        GetFeed feedTask = new GetFeed();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_first_page:
                itemSelected = 0;
                setTitle(getString(R.string.action_first_page_news));
                feedTask.execute(0);
                return true;
            case R.id.action_politics:
                itemSelected = 1;
                setTitle(getString(R.string.action_politics_page_news));
                feedTask.execute(1);
                return true;
            case R.id.action_general:
                itemSelected = 2;
                setTitle(getString(R.string.action_general_page_news));
               feedTask.execute(2);
                return true;
            case R.id.action_incidents:
                itemSelected = 3;
                setTitle(getString(R.string.action_incidents_page_news));
                feedTask.execute(3);
                return true;
            case R.id.action_court:
                itemSelected = 4;
                setTitle(getString(R.string.action_court_page_news));
               feedTask.execute(4);
                return true;
            case R.id.action_tamilnadu:
                itemSelected = 5;
                setTitle(getString(R.string.action_tamilnadu_page_news));
                feedTask.execute(5);
                return true;
            case R.id.action_delhi:
                itemSelected = 6;
                setTitle(getString(R.string.action_delhi_page_news));
                feedTask.execute(6);
                return true;
            case R.id.action_international:
                itemSelected = 7;
                setTitle(getString(R.string.action_international_page_news));
                feedTask.execute(7);
                return true;
            case R.id.action_business:
                itemSelected = 8;
                setTitle(getString(R.string.action_business_page_news));
                feedTask.execute(8);
                return true;
            case R.id.action_cinema:
                itemSelected = 9;
                setTitle(getString(R.string.action_cinema_page_news));
                feedTask.execute(9);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class GetFeed extends AsyncTask<Integer, Void, List<Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setAdapter(new DinamalarAdapter(MainActivity.this, new ArrayList<>()));
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Object> doInBackground(Integer... params) {
            switch (params[0]) {
                case 0:
                    return DinamalarParser.parseFeed(HttpManager.getData(FRONT_PAGE_URI));
                case 1:
                    return DinamalarParser.parseFeed(HttpManager.getData(POLITICS_PAGE_URI));
                case 2:
                    return DinamalarParser.parseFeed(HttpManager.getData(GENERAL_PAGE_URI));
                case 3:
                    return DinamalarParser.parseFeed(HttpManager.getData(INCIDENTS_PAGE_URI));
                case 4:
                    return DinamalarParser.parseFeed(HttpManager.getData(COURT_NEWS_PAGE_URI));
                case 5:
                    return DinamalarParser.parseFeed(HttpManager.getData(TAMIL_NADU_PAGE_URI));
                case 6:
                    return DinamalarParser.parseFeed(HttpManager.getData(DELHI_NEWS_PAGE_URI));
                case 7:
                    return DinamalarParser.parseFeed(HttpManager.getData(INTERNATIONAL_NEWS_PAGE_URI));
                case 8:
                    return DinamalarParser.parseFeed(HttpManager.getData(BUSINESS_NEWS_PAGE_URI));
                case 9:
                    return DinamalarParser.parseFeed(HttpManager.getData(CINEMA_NEWS_PAGE_URI));
                default:
                    return DinamalarParser.parseFeed(HttpManager.getData(FRONT_PAGE_URI));

            }


        }

        @Override
        protected void onPostExecute(final List<Object> s) {
            super.onPostExecute(s);
            if (s == null) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.GONE);
            s.add(DFP_CONTENT_AD_INDEX,
                    new ContentAdPlacement(new ContentAdFetcher(DFP_NATIVE_AD_UNIT_ID)));
            adapter = new DinamalarAdapter(MainActivity.this, s);
            adapter.setOnItemClickListener(new DinamalarAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int p) {
                    Log.i(TAG, "pos" + p);
                    String link = ((Feed)s.get(p)).getLink();
                    Intent intent = new Intent(MainActivity.this, Detail.class);
                    intent.putExtra("link", link);
                    intent.putExtra("itemSelected", itemSelected);
                    startActivity(intent);
                   /* String link=s.get(p).getLink();
                    CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
                    CustomTabActivityHelper.openCustomTab(
                            MainActivity.this, customTabsIntent,
                            Uri.parse(link), new WebviewFallback());
*/
                    // Toast.makeText(MainActivity.this,"po: "+p,Toast.LENGTH_SHORT).show();
                }
            });
            recyclerView.setAdapter(adapter);

            LayoutAnimationController layoutAnimationController;

            Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left);
            fadeIn.setDuration(500);
            layoutAnimationController = new LayoutAnimationController(fadeIn);


            recyclerView.setLayoutAnimation(layoutAnimationController);
            recyclerView.startLayoutAnimation();


        }
    }
}
