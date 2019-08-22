package cc.kaipao.dongjia.plugin.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import cc.kaipao.dongjia.plugin.panda.annotation.WatchIgnore;

@WatchIgnore
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        MainFragment fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content,fragment, "MainFragment")
                .commitAllowingStateLoss();

    }
}
