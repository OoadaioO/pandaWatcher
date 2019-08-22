package cc.kaipao.dongjia.plugin.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 *
 */
public class PagerFragment extends Fragment {

    private TextView text;
    private String content;

    public void setContent(String content) {
        this.content = content;
        if (text != null) {
            text.setText(content);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmet_pager, container, false);
        setContent(content);
        return view;
    }
}
