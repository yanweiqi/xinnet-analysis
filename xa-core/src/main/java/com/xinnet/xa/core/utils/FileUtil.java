package com.xinnet.xa.core.utils;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	public static File createFile(String fileName) throws IOException {
		if (fileName == null) {
			return null;
		}
		File diskFile = new File(fileName);
		if (!diskFile.getParentFile().exists()) {
			diskFile.getParentFile().mkdirs();
		}
		if (diskFile.exists()) {
			diskFile.delete();
		}
		diskFile.createNewFile();
		return diskFile;
	}

}
