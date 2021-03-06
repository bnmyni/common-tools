package com.aspire.hdc.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
/**
 * 过滤指路歌曲文件
 * @author dylan_xu
 * @date Mar 11, 2012
 * @modified by
 * @modified date
 * @since JDK1.6
 * @see com.woyo.utils.FileUtil
 */
public class FileUtil {
	
	public static Logger LOGGER = Logger.getLogger(FileUtil.class);
	
	public static Set<String> sets = new HashSet<String>();

	/**
	 * 过滤MP3文件
	 * 
	 * @param strPath
	 */
	public static void refreshFileList(String strPath) {
		File dir = new File(strPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				refreshFileList(files[i].getAbsolutePath());
			} else {
				String strFilePath = files[i].getAbsolutePath().toLowerCase();
				String strName = files[i].getName();
				if (strName.endsWith(".mp3")) {
					boolean bFlag = sets.add(strName);
					if (bFlag == Boolean.FALSE) {
						// 删除重复文件
						removeFile(strFilePath);
					}
				}
				// System.out.println("FILE_PATH:" + strFilePath + "|strName:" +
				// strName);
			}
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param strFilePath
	 *            文件夹路径
	 */
	public boolean mkdirFolder(String strFilePath) {
		boolean bFlag = false;
		try {
			File file = new File(strFilePath.toString());
			if (!file.exists()) {
				bFlag = file.mkdir();
			}
		} catch (Exception e) {
			LOGGER.error("新建目录操作出错" + e.getLocalizedMessage());
			LOGGER.info("系统错误:"+e.getMessage(),e);
		}
		return bFlag;
	}

	public boolean createFile(String strFilePath, String strFileContent) {
		boolean bFlag = false;
		try {
			File file = new File(strFilePath.toString());
			if (!file.exists()) {
				bFlag = file.createNewFile();
			}
			if (bFlag == Boolean.TRUE) {
				FileWriter fw = new FileWriter(file);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(strFileContent.toString());
				pw.close();
			}
		} catch (Exception e) {
			LOGGER.error("新建文件操作出错" + e.getLocalizedMessage());
			LOGGER.info("系统错误:"+e.getMessage(),e);
		}
		return bFlag;
	}

	/**
	 * 删除文件
	 * 
	 * @param strFilePath
	 * @return
	 */
	public static boolean removeFile(String strFilePath) {
		boolean result = false;
		if (strFilePath == null || "".equals(strFilePath)) {
			return result;
		}
		File file = new File(strFilePath);
		if (file.isFile() && file.exists()) {
			result = file.delete();
			if (result == Boolean.TRUE) {
				LOGGER.debug("[REMOE_FILE:" + strFilePath + "删除成功!]");
			} else {
				LOGGER.debug("[REMOE_FILE:" + strFilePath + "删除失败]");
			}
		}
		return result;
	}

	/**
	 * 删除文件夹(包括文件夹中的文件内容，文件夹)
	 * 
	 * @param strFolderPath
	 * @return
	 */
	public static boolean removeFolder(String strFolderPath) {
		boolean bFlag = false;
		try {
			if (strFolderPath == null || "".equals(strFolderPath)) {
				return bFlag;
			}
			File file = new File(strFolderPath.toString());
			bFlag = file.delete();
			if (bFlag == Boolean.TRUE) {
				LOGGER.debug("[REMOE_FOLDER:" + file.getPath() + "删除成功!]");
			} else {
				LOGGER.debug("[REMOE_FOLDER:" + file.getPath() + "删除失败]");
			}
		} catch (Exception e) {
			LOGGER.error("FLOADER_PATH:" + strFolderPath + "删除文件夹失败!");
			LOGGER.info("系统错误:"+e.getMessage(),e);
		}
		return bFlag;
	}

	/**
	 * 移除所有文件
	 * 
	 * @param strPath
	 */
	public static void removeAllFile(String strPath) {
		File file = new File(strPath);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] fileList = file.list();
		File tempFile = null;
		for (int i = 0; i < fileList.length; i++) {
			if (strPath.endsWith(File.separator)) {
				tempFile = new File(strPath + fileList[i]);
			} else {
				tempFile = new File(strPath + File.separator + fileList[i]);
			}
			if (tempFile.isFile()) {
				tempFile.delete();
			}
			if (tempFile.isDirectory()) {
				removeAllFile(strPath + "/" + fileList[i]);// 下删除文件夹里面的文件
				removeFolder(strPath + "/" + fileList[i]);// 删除文件夹
			}
		}
	}

	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				fs.flush();
				fs.close();
				inStream.close();
				LOGGER.debug("[COPY_FILE:" + oldfile.getPath() + "复制文件成功!]");
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错 ");
			LOGGER.info("系统错误:"+e.getMessage(),e);
		}
	}

	public static void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/ " + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
					LOGGER.debug("[COPY_FILE:" + temp.getPath() + "复制文件成功!]");
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/ " + file[i], newPath + "/ "
							+ file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错 ");
			LOGGER.info("系统错误:"+e.getMessage(),e);
		}
	}

	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		//removeFile(oldPath);
	}

	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		//removeFolder(oldPath);
	}	
	
	/**
	 * 读取远程文件的最后修改时间 
	 * 如果为本地文件： File file = new File(url); file.lastModefied();
	 */
	public String getLastModifiedDate() {
        HttpClient httpclient = new HttpClient();
        GetMethod getMethod = new GetMethod(configuration.getURL().toString());
        try {
            httpclient.executeMethod(getMethod);
            Header lastModifiedHeader = getMethod
                    .getResponseHeader("Last-Modified");
            if (lastModifiedHeader != null) {
                return lastModifiedHeader.getValue();
            }
        } catch (HttpException e) {
            logger.error("打开URL失败，URL：" + configuration.getURL().toString());
        } catch (IOException e) {
            logger.error("打开URL失败：" + configuration.getURL().toString());
        } finally {
            getMethod.releaseConnection();            
        }
        return null;
    }
}

