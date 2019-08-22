package cc.kaipao.dongjia.plugin.panda;

import java.util.HashMap;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;


@Keep
class WatchNameUtil {

    private static final HashMap<String, String> LAST_PAGE_MAP = new HashMap<>();

    private static String lastPageName = "AppLaunch";
    private static String lastKey = "";

    static void onShowPage(@NonNull String pageName, @NonNull String randomId) {
        String key = pageName + randomId;
        if (!key.equals(lastKey)) {
            LAST_PAGE_MAP.put(key, lastPageName);
        }

        lastPageName = pageName;
        lastKey = key;
    }

    static String getLastPageName(@NonNull String pageName, String randomId) {
        String key = pageName + randomId;
        return LAST_PAGE_MAP.containsKey(key) ? LAST_PAGE_MAP.get(key) : "unknownpage";
    }

}
