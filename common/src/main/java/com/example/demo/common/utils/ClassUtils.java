package com.example.demo.common.utils;

import java.io.File;
import java.security.ProtectionDomain;

public class ClassUtils {

    public static String getClassFilePath(Class<?> clz) {
        ProtectionDomain domain = clz.getProtectionDomain();
        Package pkg = clz.getPackage();
        String filePath = null;
        if (domain != null && pkg != null) {
            String buildDir = domain.getCodeSource().getLocation().getFile();
            //Log.d("ngb_log", "buildDir=" + buildDir);
            String fileName = clz.getSimpleName() + ".class";
            File file = new File(buildDir + pkg.getName().replaceAll("[.]", "/") + "/", fileName);
            filePath = file.getAbsolutePath();
            //Log.d("ngb_log","filePath=" + filePath);
        }
        return filePath;
    }
}
