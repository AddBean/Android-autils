package com.addbean.autils.core.http.download;

import android.net.Uri;

import java.io.OutputStream;

/**
 * Created by AddBean on 2016/2/13.
 */
public abstract class AbsDownloader {
    public abstract long downloadToStream(String uri, OutputStream os,IDownloadListener downloadListener);


}
