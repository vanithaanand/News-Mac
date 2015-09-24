package androidnanan.com.dinamalar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import androidnanan.com.dinamalar.util.RippleForegroundListener;
import androidnanan.com.dinamalar.util.TimeUtils;

/**
 * Created by anand on 8/30/15.
 */
public class DinamalarAdapter extends RecyclerView.Adapter<DinamalarAdapter.ViewHolder> {

    private static final String TAG = DinamalarAdapter.class.getSimpleName();
    private final Context context;
    private final List<Object> mDataSet;
    private OnItemClickListener onItemClickListener;
    private RippleForegroundListener rippleForegroundListener = new RippleForegroundListener(R.id.rippleForegroundListenerView);

    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");
    private SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yy");
    private SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yy");

    public DinamalarAdapter(Context ctx, List<Object> results) {
        this.context = ctx;
        this.mDataSet = results;
        Collections.sort(mDataSet, new Comparator<Object>() {
            @Override
            public int compare(Object lhs, Object rhs) {
                try {
                    if(lhs instanceof Feed && rhs instanceof Feed) {
                        return Long.valueOf(getMilliSecs(((Feed) rhs).getPubDate())).compareTo(getMilliSecs(((Feed) lhs).getPubDate()));
                    }
                    return 0;
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });


    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
      Object item=  mDataSet.get(i);
        if(item instanceof  Feed) {
            View view = LayoutInflater.from(context).inflate(R.layout.dinamalar_feed_entry, viewGroup, false);

            return new ViewHolder(view);
        }else{
           // View convertView=LayoutInflater.from(context).inflate(R.layout.item_content_ad,viewGroup,false);
           return new ViewHolder(((AdPlacement) item).getView(null, viewGroup)) ;
        }


    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        //  viewHolder.getTitleText().setOnTouchListener(rippleForegroundListener);
        viewHolder.titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, i);
                }
            }
        });

        Object feed=mDataSet.get(i);
        if(feed instanceof Feed) {
            Feed feed1=(Feed)feed;
            String description = feed1.getDescription();
            //  Log.i(TAG,description);
            //Log.i(TAG,""+));
            String imgSrc = null;
            if (description.contains("src")) {
                imgSrc = description.substring(description.indexOf("src") + 5, description.indexOf("jpg") + 3);
            }

            String titleText = Jsoup.parse(feed1.getTitle().trim()).text();


            String descriptionText = Jsoup.parse(description).text();
            descriptionText = descriptionText.replaceAll("\\<.*?>", "");


            // Log.i(TAG, descriptionText);
            viewHolder.getTitleText().setText(titleText);
            if (imgSrc != null) {
                viewHolder.thumbnail.setVisibility(View.VISIBLE);
                Glide.with(context).load(imgSrc).into(viewHolder.thumbnail);
            } else {
                viewHolder.thumbnail.setVisibility(View.GONE);
            }
            // viewHolder.getDescriptionText().setText(descriptionText);
            try {
                String pubDate = feed1.getPubDate();
                //   Log.i(TAG, pubDate);
                long milliSecs = getMilliSecs(pubDate);
                viewHolder.timeText.setText(TimeUtils.getTimeAgo(milliSecs, context));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    private long getMilliSecs(String pubDate) throws ParseException {
        long milliSecs;
        if (!(pubDate.length() > 8) && pubDate.contains("-")) {
         //   Log.i(TAG, "pub date is format dd-MM-yy");
            milliSecs = format1.parse(pubDate).getTime();
        } else if (!(pubDate.length() > 8) && pubDate.contains("/")) {
           // Log.i(TAG, "pub date is format dd/MM/yy");
            milliSecs = format2.parse(pubDate).getTime();
        } else {
            //Log.i(TAG, "pub date is format dd-MM-yy HH:mm");
            milliSecs = format.parse(pubDate).getTime();
        }
        return milliSecs;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView titleText;
        private final TextView timeText;
        private final ImageView thumbnail;
        //   private final TextView descriptionText;

        public ViewHolder(View itemView) {
            super(itemView);


            titleText = (TextView) itemView.findViewById(R.id.dinamalarTitle);
          //  descriptionText = (TextView) itemView.findViewById(R.id.dinamalarDescription);
            timeText = (TextView) itemView.findViewById(R.id.time);
            thumbnail=(ImageView)itemView.findViewById(R.id.thumbnail);


        }

        public TextView getTitleText() {
            return titleText;
        }

   //     public TextView getDescriptionText() {
     //       return descriptionText;
   //     }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(v, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int p);
    }
}
