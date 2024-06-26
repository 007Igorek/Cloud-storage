package com.netology.cloudservice.utils;

import org.apache.commons.io.FileUtils;

public class FileUtil {

    public static String bytesToHumanString(long bytes) {
        return FileUtils.byteCountToDisplaySize(bytes);
    }

    public static Long getSizeByBytes(byte[] bytes) {
        return (long) (bytes.length / 1024.0);
    }
}
