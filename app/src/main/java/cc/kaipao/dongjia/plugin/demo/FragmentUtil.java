package cc.kaipao.dongjia.plugin.demo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 *
 */
public class FragmentUtil {

    public static void replace(FragmentManager fragmentManager,int containerId, Fragment fragment, String tag) {

        fragmentManager.beginTransaction()
                .replace(containerId,fragment, tag)
                .commitAllowingStateLoss();

    }


    public static void add(FragmentManager fragmentManager, Fragment fragment, String tag) {

        fragmentManager.beginTransaction()
                .add(fragment, tag)
                .commitAllowingStateLoss();

    }
}
