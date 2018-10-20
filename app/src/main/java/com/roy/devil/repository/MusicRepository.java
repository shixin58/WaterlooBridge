package com.roy.devil.repository;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>Created by shixin on 2018/10/20.
 */
public class MusicRepository {
    public static List<String> getPathList() {
        List<String> pathList = new LinkedList<>();
        File musicDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "Favourite/");
        if(!musicDir.exists() || !musicDir.isDirectory()) return pathList;
        Log.i("getPathList", ""+musicDir);
        File[] paths = musicDir.listFiles();
        if(paths==null || paths.length<=0) return pathList;
        for(File path:paths) {
            if(path.getPath().endsWith(".mp3")||path.getPath().endsWith(".flac")) {
                pathList.add(path.getPath());
            }
        }
        return pathList;
    }
}
