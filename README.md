# pandaWatcher

### 简介
本库通过gradle plugin 自动化插桩。
自动化管理Fragment在ViewPager中生命周期避免重复打点。



###页面打点
```
Watcher.setWatchListener(new WatchListener() {
            @Override
            public void onShowPage(@NonNull String pageName, @NonNull String randomId, @NonNull String lastPageName) {
                Log.d(TAG, "app#onShowPage() called with: pageName = [" + pageName + "], randomId = [" + randomId + "], lastPageName = [" + lastPageName + "]");
            }

            @Override
            public void onHidePage(@NonNull String pageName, @NonNull String randomId, @NonNull String lastPageName) {
                Log.d(TAG, "app#onHidePage() called with: pageName = [" + pageName + "], randomId = [" + randomId + "], lastPageName = [" + lastPageName + "]");

            }
        })
```

###关于字节码插桩自动化方法
