package cc.kaipao.dongjia.plugin.panda;

import java.lang.annotation.Annotation;
import java.util.UUID;

import androidx.annotation.Keep;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchIgnore;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchName;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchPagerFragment;

@Keep
public class WatcherLifecycleObserver implements LifecycleObserver {

    private final String randomId = UUID.randomUUID().toString();
    private boolean watchIgnore;
    private boolean watchInViewPager;
    private boolean inWhiteList;
    private String pageName;

    public WatcherLifecycleObserver(String clazzName) {
        this.pageName = clazzName;

        try {
            this.inWhiteList = WhiteNameList.contain(clazzName);
            Class clazz = Class.forName(clazzName);
            this.watchIgnore = clazz.isAnnotationPresent(WatchIgnore.class);
            this.watchInViewPager = clazz.isAnnotationPresent(WatchPagerFragment.class);
            Annotation annotation = clazz.getAnnotation(WatchName.class);
            if (annotation != null) {
                pageName = ((WatchName) annotation).value();
            }
        } catch (Exception ignore) {

        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {

        if (!this.inWhiteList && !this.watchIgnore && !this.watchInViewPager) {
            Watcher.onShowPage(pageName, randomId);
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        if (!this.inWhiteList && !this.watchIgnore && !this.watchInViewPager) {
            Watcher.onHidePage(pageName, randomId);
        }
    }

}
