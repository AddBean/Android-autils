# autils
该项目主要为解决项目中普遍存在的缓存问题，使用Lru（最近最少使用算法）进行缓存处理，简单易用，快速上手，且已在多个项目中使用，也已迭代了多个版本。
使用方法:

  1,引入autils的module项目，或直接找到相应jar包，在路径“\autils\autils\build\intermediates\bundles\release\autils.jar”；
  2,创建ABitmapUtils或HttpUtils对象，并使用相关API；
一，图片缓存（ABitmapUtils）：

该部分主要用于图片缓存，一行代码解决显示多图、大图内存溢出问题。


Demo代码：

 ABitmapUtils mBitmapUtils = new ABitmapUtils(getContext());
 mBitmapUtils.load(R.id.image_content, bean.getIcoUrl());


其他API：

     
   //使用不同缓存加载同一图片链接；
        mBitmapUtils.load(T view, String uri, String key) //参数依次为：imageView控件ID，显示图片url，key为自定义标识（可用于加载同一图片链接，但使用不同缓存，key用于区分）
        mBitmapUtils.clearDiskCache();//清空磁盘缓存；
        mBitmapUtils.clearMemCache();//清空内存缓存；
        //添加回调
        mBitmapUtils.setOnCallbackListener(new IBitmapCallback() {
            @Override
            public void onPreLoad(View container, String uri, IBitmapConfig config) {
                super.onPreLoad(container, uri, config);
            }


            @Override
            public void onLoadStarted(View container, String uri, IBitmapConfig config) {
                super.onLoadStarted(container, uri, config);
            }
            //加载进度监听
            @Override
            public void onLoading(View container, String uri, IBitmapConfig config, long current, long total) {
                super.onLoading(container, uri, config, current, total);
            }


            @Override
            public void onLoadCompleted(View container, String uri, Bitmap bitmap, IBitmapConfig config) {
                super.onLoadCompleted(container, uri, bitmap, config);
            }


            @Override
            public void onLoadFailed(View container, String uri) {
                super.onLoadFailed(container, uri);
            }
        });


配置项：

     
   IBitmapConfig bitmapConfig = mBitmapUtils.getBitmapConfig();//获取配置对象；
        bitmapConfig.setDiskCacheEnable(true);//使能磁盘缓存；
        bitmapConfig.setMemCacheEnable(true);//使能内存缓存；
        bitmapConfig.setLoadingEmptyImage(R.drawable.emty);//设置为空时默认图片
        bitmapConfig.setLoadingFailedImage(R.drawable.error);//设置加载失败时默认图片
        bitmapConfig.setLoadingImage(R.drawable.ic_launcher);//设置正在加载时默认图片
        bitmapConfig.setMinCacheMem(5*1024*1024);//设置内存缓存大小；
        bitmapConfig.setBitmapImageSize(new BitmapImageSize(50, 150));//设置图片大小（默认为要显示的控件大小）；
二，文本缓存（AHttpUtils):

文本缓存一般用于缓存String文件，例如http、json、xml等，也可作为网络请求框架的一部分集成到APP的http缓存里面；

Demo代码：

        AHttpUtils mHttpUtils = new AHttpUtils(getContext());
        mHttpUtils.save("这是保存内容", "这是该内容对应的Key");//缓存文本；
        String content = mHttpUtils.get("这是该内容对应的Key");//获取该key对应的文本缓存
        mHttpUtils.clearCache();//清空缓存；

