package com.addbean.autils.core.http.download;

import android.content.Context;

import com.addbean.autils.tools.OtherUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by AddBean on 2016/2/13.
 */
public class DefaultDownloader extends AbsDownloader {
    private Context mContext;

    public DefaultDownloader(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public long downloadToStream(String uri, OutputStream outputStream, IDownloadListener downloadListener) {
        URLConnection urlConnection = null;
        BufferedInputStream bis = null;

        OtherUtils.trustAllHttpsURLConnection();
        downloadListener.downloadStart();
        long fileLen = 0;
        long currCount = 0;
        try {
            if (uri.startsWith("/")) {
                FileInputStream fileInputStream = new FileInputStream(uri);
                fileLen = fileInputStream.available();
                bis = new BufferedInputStream(fileInputStream);
            } else if (uri.startsWith("assets/")) {
                InputStream inputStream = mContext.getAssets().open(uri.substring(7, uri.length()));
                fileLen = inputStream.available();
                bis = new BufferedInputStream(inputStream);

            } else {
                final URL url = new URL(uri);
                urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(this.getDefaultConnectTimeout());
                urlConnection.setReadTimeout(this.getDefaultReadTimeout());
                bis = new BufferedInputStream(urlConnection.getInputStream());
                fileLen = urlConnection.getContentLength();
            }


            byte[] buffer = new byte[4096];
            int len = 0;
            BufferedOutputStream out = new BufferedOutputStream(outputStream);
            while ((len = bis.read(buffer)) != -1) {
                out.write(buffer, 0, len);
                currCount += len;
                downloadListener.downloadUpdate(currCount, fileLen);
            }
            out.flush();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null)
                    bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    private int getDefaultReadTimeout() {
        return 1 * 1000;
    }


    private int getDefaultConnectTimeout() {
        return 10 * 1000;
    }

}
