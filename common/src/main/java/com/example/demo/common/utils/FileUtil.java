package com.example.demo.common.utils;

import android.media.MediaMetadataRetriever;
import android.text.TextUtils;

public class FileUtil {

    public static String getFileMimeType(String filePath) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        String mime = "unKnow";
        if (!TextUtils.isEmpty(filePath)) {
            mediaMetadataRetriever.setDataSource(filePath);
            mime = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
        }
        return mime;
    }
}
