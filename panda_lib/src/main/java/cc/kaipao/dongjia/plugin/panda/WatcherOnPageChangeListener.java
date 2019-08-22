package cc.kaipao.dongjia.plugin.panda;

import androidx.annotation.Keep;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 *
 */
@Keep
public class WatcherOnPageChangeListener implements ViewPager.OnPageChangeListener {

    private ViewPagerFragmentWatcher watcher;
    private ViewPager viewPager;

    public WatcherOnPageChangeListener(ViewPagerFragmentWatcher watcher) {
        this.watcher = watcher;
    }

    void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (this.viewPager != null && this.viewPager.getAdapter() != null) {
            Fragment fragment;
            CharSequence pageTitle;

            if (viewPager.getAdapter() instanceof FragmentPagerAdapter) {

                FragmentPagerAdapter adapter = (FragmentPagerAdapter) viewPager.getAdapter();
                fragment = adapter.getItem(viewPager.getCurrentItem());
                pageTitle = adapter.getPageTitle(viewPager.getCurrentItem());

                if (fragment != null) {
                    watcher.watch(fragment, pageTitle);
                }

            }
            else if (viewPager.getAdapter() instanceof FragmentStatePagerAdapter) {
                FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) viewPager.getAdapter();
                fragment = adapter.getItem(viewPager.getCurrentItem());
                pageTitle = adapter.getPageTitle(viewPager.getCurrentItem());
                if (fragment != null) {
                    watcher.watch(fragment, pageTitle);
                }
            }

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
