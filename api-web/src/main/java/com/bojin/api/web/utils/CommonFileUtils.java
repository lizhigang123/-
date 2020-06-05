/** 
 * @(#)ForFile.java 1.0.0 2017年12月18日 下午4:51:37  
 *  
 * Copyright © 2017 善林金融.  All rights reserved.  
 */

/**
 * 
 */
package com.bojin.api.web.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 夕橘子-O
 * @version 2016年7月8日 上午10:38:49
 */
public class CommonFileUtils {
	// 生成文件路径
	public static String path = "/api-temp/";

	// 文件路径+名称
	private static String filenameTemp;
	
	public static String generateZip(List<File> files, String targetZipName) {
		try {
			String filenameTemp = path +"/"+ targetZipName;
			byte[] buffer = new byte[1024];
			// 生成的ZIP文件名为Demo.zip
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filenameTemp));
			// 需要同时下载的两个文件result.txt ，source.txt
			for (int i = 0; i < files.size(); i++) {
				FileInputStream fis = new FileInputStream(files.get(i));
				out.putNextEntry(new ZipEntry(files.get(i).getName()));
				int len;
				// 读入需要下载的文件的内容，打包到zip文件
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path+targetZipName;
	}

	/**
	 * 创建文件
	 * 
	 * @param fileName
	 *            文件名称
	 * @param filecontent
	 *            文件内容
	 * @return 是否创建成功，成功则返回true
	 */
	public static String createFile(String fileName, String filecontent,String postfix) {
		filenameTemp = path + fileName + postfix;// 文件路径+名称+文件类型
		File fileDir = new File(path);
		if (!fileDir.exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			if (!fileDir.mkdirs()) {
				return "";
			}
		}
		File file = new File(filenameTemp);
		try {
			// 如果文件不存在，则创建新的文件
			if (!file.exists()) {
				file.createNewFile();
				// 创建文件成功后，写入内容到文件里
				writeFileContent(filenameTemp, filecontent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filenameTemp;
	}

	/**
	 * 向文件中写入内容
	 * 
	 * @param filepath
	 *            文件路径与名称
	 * @param newstr
	 *            写入的内容
	 * @return
	 * @throws IOException
	 */
	public static boolean writeFileContent(String filepath, String newstr) throws IOException {
		Boolean bool = false;
		String filein = newstr + "\r\n";// 新写入的行，换行
		String temp = "";

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			File file = new File(filepath);// 文件路径(包括文件名称)
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buffer = new StringBuffer();

			// 文件原有内容
			for (int i = 0; (temp = br.readLine()) != null; i++) {
				buffer.append(temp);
				// 行与行之间的分隔符 相当于“\n”
				buffer = buffer.append(System.getProperty("line.separator"));
			}
			buffer.append(filein);

			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buffer.toString().toCharArray());
			pw.flush();
			bool = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			// 不要忘记关闭
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return bool;
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public static boolean delFile(String fileName) {
		Boolean bool = false;
		filenameTemp = path + fileName + ".txt";
		File file = new File(filenameTemp);
		try {
			if (file.exists()) {
				file.delete();
				bool = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return bool;
	}
	
	public static boolean deleteDir(String path){
		File file = new File(path);
		if(!file.exists()){//判断是否待删除目录是否存在
			return false;
		}
		
		String[] content = file.list();//取得当前目录下所有文件和文件夹
		for(String name : content){
			File temp = new File(path, name);
			if(temp.isDirectory()){//判断是否是目录
				deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
				temp.delete();//删除空目录
			}else{
				temp.delete();
			}
		}
		return true;
	}

}