package cc.kaipao.dongjia.plugin.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchIgnore;

/**
 *
 */
@WatchIgnore
public class TestWatcherIgnoreFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(inflater.getContext());
        textView.setText("这是忽视注解的fragment");
        return textView;
    }
}
