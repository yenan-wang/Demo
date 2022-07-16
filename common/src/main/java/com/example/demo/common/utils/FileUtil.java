package com.example.demo.common.utils;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    private static final String TAG = "FileUtil";

    public static String getFileMimeType(String filePath) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        String mime = "unKnow";
        if (!TextUtils.isEmpty(filePath)) {
            mediaMetadataRetriever.setDataSource(filePath);
            mime = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
        }
        return mime;
    }

    public static File createFile(String filePath, boolean isCover) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (file.createNewFile()) {
                    return file;
                } else {
                    return null;
                }
            } else {
                if (isCover) {
                    if (file.delete() && file.createNewFile()) {
                        return file;
                    }
                } else {
                    return createFile(renameAndAddSuffix(filePath), false);
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage());
        }
        return null;
    }

    /**
     * 创建文件夹
     *
     * @param filePath
     * @return
     */
    public static boolean createFileDir(File filePath) {
        if (filePath == null) {
            return true;
        }
        if (filePath.exists()) {
            return true;
        }
        File parentFile = filePath.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            //父文件夹不存在，则先创建父文件夹，再创建自身文件夹
            return createFileDir(parentFile) && createFileDir(filePath);
        } else {
            boolean mkDirs = filePath.mkdirs();
            boolean isSuccess = mkDirs || filePath.exists();
            if (!isSuccess) {
                LogUtil.e(TAG, "create file fail: " + filePath);
            }
            return isSuccess;
        }
    }

    public static String renameAndAddSuffix(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "" + System.currentTimeMillis();
        } else {
            int pointPos = fileName.lastIndexOf(".");
            if (pointPos == -1 && pointPos != fileName.length() - 1) {
                return fileName + " (1)";
            }
            return fileName.substring(0, pointPos) + " (1)" + fileName.substring(pointPos);
        }
    }

    public static byte[] fileToByte(String filePath) {
        byte[] buffer = null;
        FileInputStream fileInputStream = null;
        try {
            File file = new File(filePath);
            fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fileInputStream.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            LogUtil.e(TAG, "FileToByte, " + e.getMessage());
        } finally {
            closeSlienty(fileInputStream);
        }
        return buffer;
    }

    public static File byteToFile(byte[] bytes, String filePath, boolean isCover) {
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        File file = null;
        try {
            file = createFile(filePath, isCover);
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(bytes);
        } catch (Exception e) {
            LogUtil.e(TAG, "byteToFile, error: " + e.getMessage());
        } finally {
            closeSlienty(bufferedOutputStream);
            closeSlienty(bufferedOutputStream);
        }
        return file;
    }

    public static boolean saveByteFile(String filePath, byte[] bytes, boolean isCover) {
        FileOutputStream fileOutputStream = null;
        try {
            File file = createFile(filePath, isCover);
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            return true;
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        } finally {
            closeSlienty(fileOutputStream);
        }
        return false;
    }

    public static boolean saveFile(File file) {
        if (file == null || !file.isFile()) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileToByte(file.getAbsolutePath()));
            return true;
        } catch (Exception e) {
            LogUtil.e(TAG, "saveFile, error: " + e.getMessage());
        } finally {
            closeSlienty(fileOutputStream);
        }
        return false;
    }

    public static void closeSlienty(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            LogUtil.e(e.getMessage());
        }
    }

    /**
     * @return "/storage/emulated/0"
     */
    public static String getExternalStorageDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * '
     *
     * @param context 上下文
     * @param type    目录类型
     * @return "/storage/emulated/0/Android/data/com.example.demo/files" + "/type"
     * type为null，只返回前面的，如果type不为null，则返回type对应的文件目录
     */
    public static String getExternalFileDir(Context context, String type) {
        return context.getExternalFilesDir(type).getAbsolutePath();
    }

    /**
     * @param context 上下文
     * @return "/data/user/0/com.example.demo/files"
     */
    public static String getFileDir(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * @param context 上下文
     * @return "/data/user/0/com.example.demo/cache"
     */
    public static String getCacheFileDir(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * @return "/system"
     */
    public static String getRootDir() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

}
