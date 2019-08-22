package cc.kaipao.dongjia.plugin.panda;

import java.lang.annotation.Annotation;
import java.util.UUID;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchIgnore;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchName;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchPageTitle;

@Keep
public class WatcherOnAdapterChangeListener implements ViewPager.OnAdapterChangeListener {

    private String uuid = UUID.randomUUID().toString();
    private ViewPagerFragmentWatcher watcher;
    private WatcherOnPageChangeListener watcherOnPageChangeListener;
    private FragmentLifecycleObserver lifecycleObserver;

    public WatcherOnAdapterChangeListener() {
        this.watcher = new ViewPagerFragmentWatcher(uuid);
        this.watcherOnPageChangeListener = new WatcherOnPageChangeListener(watcher);
        this.lifecycleObserver = new FragmentLifecycleObserver();
    }

    @Override
    public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
        this.lifecycleObserver.setViewPager(viewPager);
        Fragment fragment;
        CharSequence pageTitle;
        FragmentActivity activity;
        if (newAdapter instanceof FragmentStatePagerAdapter) {
            viewPager.removeOnPageChangeListener(this.watcherOnPageChangeListener);
            this.watcherOnPageChangeListener.setViewPager(viewPager);
            viewPager.addOnPageChangeListener(this.watcherOnPageChangeListener);
            FragmentPagerAdapter adapter = (FragmentPagerAdapter) newAdapter;
            this.lifecycleObserver.setFragmentPagerAdapter(adapter);
            fragment = adapter.getItem(viewPager.getCurrentItem());
            pageTitle = adapter.getPageTitle(viewPager.getCurrentItem());
            if (fragment != null) {
                this.watcher.watch(fragment, pageTitle);
            }

            if (viewPager.getContext() instanceof FragmentActivity) {
                activity = (FragmentActivity) viewPager.getContext();
                activity.getLifecycle().removeObserver(this.lifecycleObserver);
                activity.getLifecycle().addObserver(this.lifecycleObserver);

            }
        }else if (newAdapter instanceof FragmentPagerAdapter) {
            viewPager.removeOnPageChangeListener(this.watcherOnPageChangeListener);
            this.watcherOnPageChangeListener.setViewPager(viewPager);
            viewPager.addOnPageChangeListener(this.watcherOnPageChangeListener);
            FragmentPagerAdapter adapter = (FragmentPagerAdapter) newAdapter;
            this.lifecycleObserver.setFragmentPagerAdapter(adapter);
            fragment = adapter.getItem(viewPager.getCurrentItem());
            pageTitle = adapter.getPageTitle(viewPager.getCurrentItem());
            if (fragment != null) {
                this.watcher.watch(fragment, pageTitle);
            }

            if (viewPager.getContext() instanceof FragmentActivity) {
                activity = (FragmentActivity) viewPager.getContext();
                activity.getLifecycle().removeObserver(this.lifecycleObserver);
                activity.getLifecycle().addObserver(this.lifecycleObserver);

            }
        }


    }

    private void performFragmentOnStop(Fragment fragment, CharSequence pageTitle) {

        if(fragment != null) {
            Class clazz = fragment.getClass();
            String clazzName = clazz.getName();
            if (!WhiteNameList.contain(clazzName)) {

                if (!clazz.isAnnotationPresent(WatchIgnore.class)) {
                    String pageName = clazzName;
                    Annotation annotation = clazz.getAnnotation(WatchName.class);
                    if (annotation != null) {
                        pageName = ((WatchName) annotation).value();
                    }
                    else if (clazz.isAnnotationPresent(WatchPageTitle.class)) {
                        pageName = pageTitle.toString();
                    }

                    Watcher.onHidePage(pageName, watcher.getCurrentFragmentRandomId());
                }

            }
        }


    }

    private void performFragmentOnResume(Fragment fragment, CharSequence pageTitle) {

        if(fragment != null) {
            Class clazz = fragment.getClass();
            String clazzName = clazz.getName();
            if (!WhiteNameList.contain(clazzName)) {

                if (!clazz.isAnnotationPresent(WatchIgnore.class)) {
                    String pageName = clazzName;
                    Annotation annotation = clazz.getAnnotation(WatchName.class);
                    if (annotation != null) {
                        pageName = ((WatchName) annotation).value();
                    }
                    else if (clazz.isAnnotationPresent(WatchPageTitle.class)) {
                        pageName = pageTitle.toString();
                    }

                    Watcher.onShowPage(pageName, watcher.getCurrentFragmentRandomId());
                }

            }
        }

    }

    @Keep
    class FragmentLifecycleObserver implements LifecycleObserver {

        private boolean isStopped = false;
        private ViewPager viewPager;
        private FragmentPagerAdapter fragmentPagerAdapter;
        private FragmentStatePagerAdapter fragmentStatePagerAdapter;

        void setViewPager(ViewPager viewPager) {
            this.viewPager = viewPager;
        }

        void setFragmentPagerAdapter(FragmentPagerAdapter fragmentPagerAdapter) {
            this.fragmentPagerAdapter = fragmentPagerAdapter;
        }

        void setFragmentStatePagerAdapter(FragmentStatePagerAdapter fragmentStatePagerAdapter) {
            this.fragmentStatePagerAdapter = fragmentStatePagerAdapter;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        void onResume() {
            if (this.isStopped) {
                Fragment fragment;
                CharSequence pageTitle;
                if (this.fragmentPagerAdapter != null) {
                    fragment = this.fragmentPagerAdapter.getItem(viewPager.getCurrentItem());
                    pageTitle = this.fragmentPagerAdapter.getPageTitle(viewPager.getCurrentItem());
                    performFragmentOnResume(fragment, pageTitle);
                }
                else if (this.fragmentStatePagerAdapter != null) {
                    fragment = this.fragmentStatePagerAdapter.getItem(viewPager.getCurrentItem());
                    pageTitle = this.fragmentStatePagerAdapter.getPageTitle(viewPager.getCurrentItem());
                    performFragmentOnResume(fragment, pageTitle);
                }

            }

        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void onStop() {
            this.isStopped = true;
            Fragment fragment;
            CharSequence pageTitle;
            if (fragmentPagerAdapter != null) {
                fragment = this.fragmentPagerAdapter.getItem(viewPager.getCurrentItem());
                pageTitle = this.fragmentPagerAdapter.getPageTitle(viewPager.getCurrentItem());
                performFragmentOnStop(fragment, pageTitle);
            }
            else if(this.fragmentStatePagerAdapter != null) {
                fragment = this.fragmentStatePagerAdapter.getItem(viewPager.getCurrentItem());
                pageTitle = this.fragmentStatePagerAdapter.getPageTitle(viewPager.getCurrentItem());
                performFragmentOnStop(fragment, pageTitle);
            }


        }
    }
}
