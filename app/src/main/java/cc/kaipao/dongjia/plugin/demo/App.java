package cc.kaipao.dongjia.plugin.demo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import cc.kaipao.dongjia.plugin.panda.WatchListener;
import cc.kaipao.dongjia.plugin.panda.Watcher;

/**
 *
 */
public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        Watcher.setWatchListener(new WatchListener() {
            @Override
            public void onShowPage(@NonNull String pageName, @NonNull String randomId, @NonNull String lastPageName) {
                Log.d(TAG, "onShowPage() called with: pageName = [" + pageName + "], randomId = [" + randomId + "], lastPageName = [" + lastPageName + "]");
            }

            @Override
            public void onHidePage(@NonNull String pageName, @NonNull String randomId, @NonNull String lastPageName) {
                Log.d(TAG, "onHidePage() called with: pageName = [" + pageName + "], randomId = [" + randomId + "], lastPageName = [" + lastPageName + "]");

            }
        });
    }
}
