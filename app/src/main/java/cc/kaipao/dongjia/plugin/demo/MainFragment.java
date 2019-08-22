package cc.kaipao.dongjia.plugin.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchName;

/**
 *
 */
@WatchName("主界面")
public class MainFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        view.findViewById(R.id.btnToPager).setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), TestPagerActivity.class);
            startActivity(intent);
        });
        view.findViewById(R.id.btnToWatchIgnorePage).setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), TestWatcherIgnoreActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
