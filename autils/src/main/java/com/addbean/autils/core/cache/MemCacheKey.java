package com.addbean.autils.core.cache;

import com.addbean.autils.core.utils.bitmap.IBitmapConfig;

/**
 * Created by AddBean on 2016/3/10.
 * 此类为了保证缓存在内存里的唯一性；
 */
public class MemCacheKey {
    private String uri;
    private String subKey;

    public MemCacheKey(String uri, IBitmapConfig bitmapConfig) {
        this.uri = uri;
        this.subKey = bitmapConfig == null ? null : bitmapConfig.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemCacheKey)) return false;

        MemCacheKey that = (MemCacheKey) o;

        if (!uri.equals(that.uri)) return false;

        if (subKey != null && that.subKey != null) {
            return subKey.equals(that.subKey);
        }

        return true;
    }

    @Override
    public int hashCode() {
        return uri.hashCode();
    }
}
