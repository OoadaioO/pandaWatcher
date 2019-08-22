package cc.kaipao.dongjia.plugin.panda.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 * 此注解标记frament是在viewpager中使用的
 * 此注解标记的fragment，不会在fragment创建的时候回调onShow事件
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface WatchPagerFragment {
}
