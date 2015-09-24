package androidnanan.com.dinamalar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by anand on 8/30/15.
 */
public class HttpManager {
    private static final String TAG = HttpManager.class.getSimpleName();

    public static String getData(String uri){
        BufferedReader reader=null;
        try{
            URL url=new URL(uri);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            StringBuilder builder=new StringBuilder();
            reader=new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while((line=reader.readLine())!=null){
                builder.append(line+"\n");
            }
          //  Log.i(TAG,builder.toString());
            return builder.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

}
