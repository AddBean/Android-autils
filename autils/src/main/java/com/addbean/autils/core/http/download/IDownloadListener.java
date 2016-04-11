package com.addbean.autils.core.http.download;

/**
 * Created by AddBean on 2016/3/14.
 */
public interface IDownloadListener {
    public void downloadStart();

    public void downloadUpdate(long currentLen, long totalLen);
}
