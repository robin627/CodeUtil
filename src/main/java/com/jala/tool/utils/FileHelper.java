package com.jala.tool.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHelper {
	
	public static void createFile(String filePath, String content) throws IOException {

		File file = new File(filePath);
		File parent = file.getParentFile();
		if (parent != null && !parent.exists()) {
			parent.mkdirs();
		}

		file.createNewFile();

		FileWriter out = new FileWriter(new File(filePath));
		out.write(content);
		out.close();
	}
}
