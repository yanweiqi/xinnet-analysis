package com.xinnet.xa.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVWriter;

public class CSVUtil {
	private static Logger logger = Logger.getLogger(CSVUtil.class);
	public static File writeCSVByList(String filePath, List<String[]> columns,
			String[] title) {
		FileOutputStream os = null;
		CSVWriter writer = null;
		File file = null;
		try {
			 file = FileUtil.createFile(filePath);
			os = new FileOutputStream(file, true);
			writer = new CSVWriter(new OutputStreamWriter(os, "GBK"));
			if (title != null) {
				writer.writeNext(title);
			}
			writer.writeAll(columns);
			writer.flush();
		}catch(Exception e){  
			file = null;
			logger.error(e.getMessage(), e);
		}finally {
			try {
				if (os != null) {
                    os.close();
                }
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return file;
	}
	
	 public static void writeCSVByResultSet(String filePath,String[] title,ResultSet rs)throws Exception{
		 FileOutputStream os = null;
			CSVWriter writer = null;
			try {
				File file = FileUtil.createFile(filePath);
				os = new FileOutputStream(file, true);
				writer = new CSVWriter(new OutputStreamWriter(os, "GBK"));
				if (title != null) {
					writer.writeNext(title);
				}
				writer.writeAll(rs, false);
			}  finally {
				try {
					if (os != null) {
	                    os.close();
	                }
					if (writer != null) {
						writer.close();
					}
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
	 }
	 

}
