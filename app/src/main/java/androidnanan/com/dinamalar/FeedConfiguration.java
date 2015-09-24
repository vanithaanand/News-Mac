package androidnanan.com.dinamalar;

import android.view.View;
import android.view.animation.LayoutAnimationController;


/**
 * Created by mikepenz on 20.05.15.
 */
public class FeedConfiguration {

    private static FeedConfiguration SINGLETON = null;

    private FeedConfiguration() {
    }

    public static FeedConfiguration getInstance() {
        if (SINGLETON == null) {
            SINGLETON = new FeedConfiguration();
        }
        return SINGLETON;
    }


    /**
     * LOGIC FOR THE LISTENER
     */
    private FeedListener listener = null;

    public void setListener(FeedListener libsListener) {
        this.listener = libsListener;
    }

    public FeedListener getListener() {
        return listener;
    }

    public void removeListener() {
        this.listener = null;
    }


    private LibsUIListener uiListener = null;

    public LibsUIListener getUiListener() {
        return uiListener;
    }

    public void setUiListener(LibsUIListener uiListener) {
        this.uiListener = uiListener;
    }

    public void removeUiListener() {
        this.uiListener = null;
    }


    private LayoutAnimationController layoutAnimationController = null;

    public LayoutAnimationController getLayoutAnimationController() {
        return layoutAnimationController;
    }

    public void setLayoutAnimationController(LayoutAnimationController layoutAnimationController) {
        this.layoutAnimationController = layoutAnimationController;
    }

    /**
     * helper to reset a current configuration
     * is only useful for the sample app
     */
    public void reset() {
        SINGLETON = null;
    }


    public interface LibsUIListener {
        View preOnCreateView(View view);

        View postOnCreateView(View view);
    }

    public interface FeedListener {

        /**
         * onClick listener if the Author of a Library is clicked
         *
         * @param v
         * @param feed
         * @return true if consumed and no further action is required
         */

        boolean onFeedClicked(View v, Feed feed);



    }


}
