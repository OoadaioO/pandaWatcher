package cc.kaipao.dongjia.plugin.panda;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

@Keep
public interface WatchListener {


    void onShowPage(@NonNull  String pageName, @NonNull  String randomId, @NonNull  String lastPageName);

    void onHidePage(@NonNull  String pageName, @NonNull String randomId,@NonNull String lastPageName);

}
