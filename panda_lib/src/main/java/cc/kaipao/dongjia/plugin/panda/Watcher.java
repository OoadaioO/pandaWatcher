package cc.kaipao.dongjia.plugin.panda;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
public class Watcher {

    private static WatchListener watchListener;

    public static void setWatchListener(WatchListener watchListener) {
        Watcher.watchListener = watchListener;
    }


    static void onShowPage(@NonNull String pageName, @NonNull String randomId) {
        WatchNameUtil.onShowPage(pageName, randomId);
        if (watchListener != null) {
            watchListener.onShowPage(pageName, randomId, WatchNameUtil.getLastPageName(pageName, randomId));
        }
    }


    static void onHidePage(@NonNull String pageName, @NonNull String randomId) {
        if (watchListener != null) {
            watchListener.onHidePage(pageName, randomId, WatchNameUtil.getLastPageName(pageName, randomId));
        }
    }


}
