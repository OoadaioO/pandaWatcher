package cc.kaipao.dongjia.plugin.demo;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 *
 */
public class PagerActivity extends FragmentActivity {

    private ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PagerFragment fragment = new PagerFragment();
            String content = "page " + i;
            titles.add(content);
            fragment.setContent(content);
            fragments.add(fragment);
        }
        pager = findViewById(R.id.viewPager);
        pager.setAdapter(new InnerPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(pager);
    }

    private static class InnerPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments;
        private final List<String> titles;

        public InnerPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            if (fragments == null) {
                return 0;
            }
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
