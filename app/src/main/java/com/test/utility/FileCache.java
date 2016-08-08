package com.test.utility;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * Created by pyeddula on 8/6/16.
 */
public class FileCache {

	private File cacheDir;
	
	public FileCache(Context context) {
		
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(Environment.getExternalStorageDirectory(), "LazyLoaderListTemp");
		} else {
			cacheDir = context.getCacheDir();
		}
		
		if(!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}
	
	public File getFile(String url) {
		
		String filename = String.valueOf(url.hashCode());
		
		File f = new File(cacheDir, filename);
		return f;
	}
	
	public void clear() {
		File[] files = cacheDir.listFiles();
		if(files == null) {
			return;
		}
		
		for(File f : files) {
			f.delete();
		}
	}
}