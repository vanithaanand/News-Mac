package androidnanan.com.dinamalar;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

public class Detail extends AppCompatActivity {

    private static final String TAG = Detail.class.getSimpleName();
    private String link;
    private TextView detailText;
    private int itemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailText=(TextView)findViewById(R.id.detail);
        detailText.setMovementMethod(new ScrollingMovementMethod());
        link=getIntent().getStringExtra("link");
        itemSelected=getIntent().getIntExtra("itemSelected", -1);
        GetDetailTask detailTask=new GetDetailTask();
        detailTask.execute(itemSelected);
        Log.i(TAG, link);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class GetDetailTask extends AsyncTask<Integer,Void,String>{
        @Override
        protected String doInBackground(Integer... params) {
            switch (params[0]){
                case 8:
                   return readBusinessNews();
                case 9:
                    return readCinema();
                default:
                    return getNews();
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                detailText.setText(s);
            }else{
                detailText.setText("null");
            }
        }
    }

    @Nullable
    private String getNews() {
        Document doc;
        StringBuilder builder = new StringBuilder();
        try {
            doc = Jsoup.connect(link).get();

            Elements newsdetbd = doc.getElementsByClass("newsdetbd1");
            if (newsdetbd != null&&newsdetbd.size()>0) {

                Element description = doc.getElementById("clsclk");
                Elements paragraph = description.getElementsByTag("p");
                for (Element p : paragraph) {
                    if(!p.text().isEmpty())
                    builder.append(p.text()+"\n\n");

                }
            }else if(newsdetbd!=null&& newsdetbd.size()==0){
               Elements row= doc.getElementsByClass("row");
                for(Iterator<Element> element=row.iterator();element.hasNext();){
                    builder.append(element.next().getElementsByTag("p").text());
                }
               // builder.append(row.size());
            }
                return builder.toString();

            }catch(IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }


    }

    private  String readCinema() {
        // TODO Auto-generated method stub
        Document doc;
        StringBuilder builder=new StringBuilder();
        try{
            doc=Jsoup.connect(link).get();
            Element selImpNews=doc.getElementById("selImpnews");
            Elements para=selImpNews.getElementsByTag("p");
            for(Element p:para){
                builder.append(p.text()+"\n\n");
            }
            return builder.toString();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

    }

    private  String readBusinessNews() {
        // TODO Auto-generated method stub
        Document doc;
        StringBuilder builder=new StringBuilder();
        try{
            doc=Jsoup.connect(link).get();
            Element selNews=doc.getElementById("selNews");
            Elements paragraph=selNews.getElementsByTag("p");
            for(Element p:paragraph){
                builder.append(p.text()+"\n\n");
            }
            return builder.toString();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }


    }
}
