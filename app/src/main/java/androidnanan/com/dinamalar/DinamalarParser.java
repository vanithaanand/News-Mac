package androidnanan.com.dinamalar;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anand on 8/30/15.
 */
public class DinamalarParser {
    private static final String TAG = DinamalarParser.class.getSimpleName();

    public static List<Object> parseFeed(String content){

        content = content.replaceAll("&","&amp;");
        content = content.replaceAll("%3F","&#63;");

        try {
            boolean inDataItemTag = false;
            String currentTagName = "";
            Feed newsItem = null;
            List<Object> newsList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("item")) {
                            inDataItemTag = true;
                            newsItem = new Feed();
                            newsList.add(newsItem);
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        if (inDataItemTag && newsItem != null) {
                            switch (currentTagName) {
                                case "title":
                                    newsItem.setTitle(parser.getText());
                                    break;
                                case "link":
                                    newsItem.setLink(parser.getText());
                                    break;

                                case "description":
                                    newsItem.setDescription(parser.getText());
                                    break;
                                case "pubDate":
                                    newsItem.setPubDate(parser.getText());
                                    break;

                                default:
                                    break;
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }
            return newsList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}



