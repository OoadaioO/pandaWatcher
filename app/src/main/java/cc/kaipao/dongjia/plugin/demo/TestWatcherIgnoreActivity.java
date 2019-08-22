package cc.kaipao.dongjia.plugin.demo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchIgnore;

/**
 *
 */
@WatchIgnore
public class TestWatcherIgnoreActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watcher_ignore);

        FragmentUtil.replace(getSupportFragmentManager(), R.id.content, new TestWatcherIgnoreFragment(), "TestWatcherIgnoreFragment");

    }
}
