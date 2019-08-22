package cc.kaipao.dongjia.plugin.panda;

import android.text.TextUtils;

import java.lang.annotation.Annotation;
import java.util.UUID;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchIgnore;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchName;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchPageTitle;

/**
 *
 */
@Keep
public class ViewPagerFragmentWatcher {

    private Fragment lastFragment = null;
    private String lastPageName = null;
    private String randomId;
    private String lastRandomId;

    public ViewPagerFragmentWatcher(String randomId) {
        this.randomId = randomId;
    }

    String getCurrentFragmentRandomId() {
        return this.randomId;
    }

    void watch(@NonNull Fragment fragment, @NonNull CharSequence pageTitle) {
        Class clazz = fragment.getClass();
        String className = clazz.getName();
        if (!WhiteNameList.contain(className)) {
            String pageName = className;
            boolean watchIgnore = clazz.isAnnotationPresent(WatchIgnore.class);
            if (!watchIgnore) {
                Annotation annotation = clazz.getAnnotation(WatchName.class);
                if (annotation != null) {
                    pageName = ((WatchName) annotation).value();
                }
                else if (clazz.isAnnotationPresent(WatchPageTitle.class) && !TextUtils.isEmpty(pageTitle)) {
                    pageName = pageTitle.toString();
                }

                if (this.lastFragment == null) {
                    this.randomId = UUID.randomUUID().toString();
                    Watcher.onShowPage(pageName, randomId);
                    this.lastFragment = fragment;
                    this.lastPageName = pageName;
                    this.lastRandomId = randomId;
                }
                else if (lastFragment == fragment) {
                    Watcher.onShowPage(pageName, randomId);
                }
                else {
                    Watcher.onHidePage(lastPageName, lastRandomId);
                    this.randomId = UUID.randomUUID().toString();
                    Watcher.onShowPage(pageName, randomId);
                    this.lastFragment = fragment;
                    this.lastPageName = pageName;
                    this.lastRandomId = randomId;
                }
            }
        }
    }
}
