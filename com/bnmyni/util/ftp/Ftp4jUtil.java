package com.aspire.hdc.common.util;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import org.apache.log4j.Logger;
/**
 * 基于ftp4j 的 FTP文件处理工具类
 http://www.sauronsoftware.it/projects/ftp4j/download.php
 */
public class FTPUtils {
	private Logger log = Logger.getLogger(this.getClass());
	private static FTPUtils ftp;
	/**
	 * FTP服务地址
	 */
	private static String ADDRESS = PropUtils.getString("ftp_server_address");
	/**
	 * FTP登录用户名
	 */
	private static String USERNAME = PropUtils.getString("ftp_server_username");
	/**
	 * FTP登录密码
	 */
	private static String PASSWORD = PropUtils.getString("ftp_server_password");
	/**
	 * 构造方法
	 */
	protected FTPUtils() {
	}
	/**
	 * 实例化对象
	 * 
	 * @return
	 */
	public static FTPUtils getInstance() {
		if (ftp == null) {
			ftp = new FTPUtils();
		}
		return ftp;
	}
	/**
	 * 获取FTP客户端对象
	 * 
	 * @return
	 * @throws Exception
	 */
	private FTPClient getClient() throws Exception {
		FTPClient client = new FTPClient();
		client.setCharset("utf-8");
		client.setType(FTPClient.TYPE_BINARY);
		URL url = new URL(FTPUtils.ADDRESS);
		int port = url.getPort() < 1 ? 21 : url.getPort();
		log.info("Ftp server listening on address " + url.toString());
		client.connect(url.getHost(), port);
		client.login(FTPUtils.USERNAME, FTPUtils.PASSWORD);
		return client;
	}
	/**
	 * 注销客户端连接
	 * 
	 * @param client
	 *            FTP客户端对象
	 * @throws Exception
	 */
	private void logout(FTPClient client) throws Exception {
		if (client != null) {
			try {
				// 有些FTP服务器未实现此功能，若未实现则会出错
				client.logout(); // 退出登录
			} catch (FTPException fe) {
			} catch (Exception e) {
				throw e;
			} finally {
				if (client.isConnected()) { // 断开连接
					client.disconnect(true);
				}
			}
		}
	}
	/**
	 * 创建目录
	 * 
	 * @param client
	 *            FTP客户端对象
	 * @param dir
	 *            目录
	 * @throws Exception
	 */
	private void mkdirs(FTPClient client, String dir) throws Exception {
		if (dir == null) {
			return;
		}
		dir = dir.replace("//", "/");
		String[] dirs = dir.split("/");
		for (int i = 0; i < dirs.length; i++) {
			dir = dirs[i];
			if (!StringUtils.isEmpty(dir)) {
				if (!exists(client, dir)) {
					client.createDirectory(dir);
				}
				client.changeDirectory(dir);
			}
		}
	}
	/**
	 * 获取FTP目录
	 * 
	 * @param url
	 *            原FTP目录
	 * @param dir
	 *            目录
	 * @return
	 * @throws Exception
	 */
	private URL getURL(URL url, String dir) throws Exception {
		String path = url.getPath();
		if (!path.endsWith("/") && !path.endsWith("//")) {
			path += "/";
		}
		dir = dir.replace("//", "/");
		if (dir.startsWith("/")) {
			dir = dir.substring(1);
		}
		path += dir;
		return new URL(url, path);
	}
	/**
	 * 获取FTP目录
	 * 
	 * @param dir
	 *            目录
	 * @return
	 * @throws Exception
	 */
	private URL getURL(String dir) throws Exception {
		return getURL(new URL(FTPUtils.ADDRESS), dir);
	}
	/**
	 * 判断文件或目录是否存在
	 * 
	 * @param client
	 *            FTP客户端对象
	 * @param dir
	 *            文件或目录
	 * @return
	 * @throws Exception
	 */
	private boolean exists(FTPClient client, String dir) throws Exception {
		return getFileType(client, dir) != -1;
	}
	/**
	 * 判断当前为文件还是目录
	 * 
	 * @param client
	 *            FTP客户端对象
	 * @param dir
	 *            文件或目录
	 * @return -1、文件或目录不存在 0、文件 1、目录
	 * @throws Exception
	 */
	private int getFileType(FTPClient client, String dir) throws Exception {
		FTPFile[] files = null;
		try {
			files = client.list(dir);
		} catch (Exception e) {
			return -1;
		}
		if (files.length > 1) {
			return FTPFile.TYPE_DIRECTORY;
		} else if (files.length == 1) {
			FTPFile f = files[0];
			if (f.getType() == FTPFile.TYPE_DIRECTORY) {
				return FTPFile.TYPE_DIRECTORY;
			}
			String path = dir + "/" + f.getName();
			try {
				int len = client.list(path).length;
				if (len == 1) {
					return FTPFile.TYPE_DIRECTORY;
				} else {
					return FTPFile.TYPE_FILE;
				}
			} catch (Exception e) {
				return FTPFile.TYPE_FILE;
			}
		} else {
			try {
				client.changeDirectory(dir);
				client.changeDirectoryUp();
				return FTPFile.TYPE_DIRECTORY;
			} catch (Exception e) {
				return -1;
			}
		}
	}
	/**
	 * 上传文件或目录
	 * 
	 * @param dir
	 *            目标文件
	 * @param del
	 *            是否删除源文件，默认为false
	 * @param file
	 *            文件或目录对象数组
	 * @throws Exception
	 */
	public void upload(String dir, boolean del, File... files) throws Exception {
		if (StringUtils.isEmpty(dir) || StringUtils.isEmpty(files)) {
			return;
		}
		FTPClient client = null;
		try {
			client = getClient();
			mkdirs(client, dir); // 创建文件
			for (File file : files) {
				if (file.isDirectory()) { // 上传目录
					uploadFolder(client, getURL(dir), file, del);
				} else {
					client.upload(file); // 上传文件
					if (del) { // 删除源文件
						file.delete();
					}
				}
			}
		} finally {
			logout(client);
		}
	}
	/**
	 * 上传文件或目录
	 * 
	 * @param dir
	 *            目标文件
	 * @param files
	 *            文件或目录对象数组
	 * @throws Exception
	 */
	public void upload(String dir, File... files) throws Exception {
		upload(dir, false, files);
	}
	/**
	 * 上传文件或目录
	 * 
	 * @param dir
	 *            目标文件
	 * @param del
	 *            是否删除源文件，默认为false
	 * @param path
	 *            文件或目录路径数组
	 * @throws Exception
	 */
	public void upload(String dir, boolean del, String... paths)
			throws Exception {
		if (StringUtils.isEmpty(paths)) {
			return;
		}
		File[] files = new File[paths.length];
		for (int i = 0; i < paths.length; i++) {
			files[i] = new File(paths[i]);
		}
		upload(dir, del, files);
	}
	/**
	 * 上传文件或目录
	 * 
	 * @param dir
	 *            目标文件
	 * @param paths
	 *            文件或目录路径数组
	 * @throws Exception
	 */
	public void upload(String dir, String... paths) throws Exception {
		upload(dir, false, paths);
	}
	/**
	 * 上传目录
	 * 
	 * @param client
	 *            FTP客户端对象
	 * @param parentUrl
	 *            父节点URL
	 * @param file
	 *            目录
	 * @throws Exception
	 */
	private void uploadFolder(FTPClient client, URL parentUrl, File file,
			boolean del) throws Exception {
		client.changeDirectory(parentUrl.getPath());
		String dir = file.getName(); // 当前目录名称
		URL url = getURL(parentUrl, dir);
		if (!exists(client, url.getPath())) { // 判断当前目录是否存在
			client.createDirectory(dir); // 创建目录
		}
		client.changeDirectory(dir);
		File[] files = file.listFiles(); // 获取当前文件夹所有文件及目录
		for (int i = 0; i < files.length; i++) {
			file = files[i];
			if (file.isDirectory()) { // 如果是目录，则递归上传
				uploadFolder(client, url, file, del);
			} else { // 如果是文件，直接上传
				client.changeDirectory(url.getPath());
				client.upload(file);
				if (del) { // 删除源文件
					file.delete();
				}
			}
		}
	}
	/**
	 * 删除文件或目录
	 * 
	 * @param dir
	 *            文件或目录数组
	 * @throws Exception
	 */
	public void delete(String... dirs) throws Exception {
		if (StringUtils.isEmpty(dirs)) {
			return;
		}
		FTPClient client = null;
		try {
			client = getClient();
			int type = -1;
			for (String dir : dirs) {
				client.changeDirectory("/"); // 切换至根目录
				type = getFileType(client, dir); // 获取当前类型
				if (type == 0) { // 删除文件
					client.deleteFile(dir);
				} else if (type == 1) { // 删除目录
					deleteFolder(client, getURL(dir));
				}
			}
		} finally {
			logout(client);
		}
	}
	/**
	 * 删除目录
	 * 
	 * @param client
	 *            FTP客户端对象
	 * @param url
	 *            FTP URL
	 * @throws Exception
	 */
	private void deleteFolder(FTPClient client, URL url) throws Exception {
		String path = url.getPath();
		client.changeDirectory(path);
		FTPFile[] files = client.list();
		String name = null;
		for (FTPFile file : files) {
			name = file.getName();
			// 排除隐藏目录
			if (".".equals(name) || "..".equals(name)) {
				continue;
			}
			if (file.getType() == FTPFile.TYPE_DIRECTORY) { // 递归删除子目录
				deleteFolder(client, getURL(url, file.getName()));
			} else if (file.getType() == FTPFile.TYPE_FILE) { // 删除文件
				client.deleteFile(file.getName());
			}
		}
		client.changeDirectoryUp();
		client.deleteDirectory(url.getPath()); // 删除当前目录
	}
	/**
	 * 下载文件或目录
	 * 
	 * @param localDir
	 *            本地存储目录
	 * @param dirs
	 *            文件或者目录
	 * @throws Exception
	 */
	public void download(String localDir, String... dirs) throws Exception {
		if (StringUtils.isEmpty(dirs)) {
			return;
		}
		FTPClient client = null;
		try {
			client = getClient();
			File folder = new File(localDir);
			if (!folder.exists()) { // 如果本地文件夹不存在，则创建
				folder.mkdirs();
			}
			int type = -1;
			String localPath = null;
			for (String dir : dirs) {
				client.changeDirectory("/"); // 切换至根目录
				type = getFileType(client, dir); // 获取当前类型
				if (type == 0) { // 文件
					localPath = localDir + "/" + new File(dir).getName();
					client.download(dir, new File(localPath));
				} else if (type == 1) { // 目录
					downloadFolder(client, getURL(dir), localDir);
				}
			}
		} finally {
			logout(client);
		}
	}
	/**
	 * 下载文件夹
	 * 
	 * @param client
	 *            FTP客户端对象
	 * @param url
	 *            FTP URL
	 * @param localDir
	 *            本地存储目录
	 * @throws Exception
	 */
	private void downloadFolder(FTPClient client, URL url, String localDir)
			throws Exception {
		String path = url.getPath();
		client.changeDirectory(path);
		// 在本地创建当前下载的文件夹
		File folder = new File(localDir + "/" + new File(path).getName());
		if (!folder.exists()) {
			folder.mkdirs();
		}
		localDir = folder.getAbsolutePath();
		FTPFile[] files = client.list();
		String name = null;
		for (FTPFile file : files) {
			name = file.getName();
			// 排除隐藏目录
			if (".".equals(name) || "..".equals(name)) {
				continue;
			}
			if (file.getType() == FTPFile.TYPE_DIRECTORY) { // 递归下载子目录
				downloadFolder(client, getURL(url, file.getName()), localDir);
			} else if (file.getType() == FTPFile.TYPE_FILE) { // 下载文件
				client.download(name, new File(localDir + "/" + name));
			}
		}
		client.changeDirectoryUp();
	}
	/**
	 * 获取目录下所有文件
	 * 
	 * @param dir
	 *            目录
	 * @return
	 * @throws Exception
	 */
	public String[] list(String dir) throws Exception {
		FTPClient client = null;
		try {
			client = getClient();
			client.changeDirectory(dir);
			String[] values = client.listNames();
			if (values != null) {
				// 将文件排序(忽略大小写)
				Arrays.sort(values, new Comparator<String>(){
					public int compare(String val1, String val2) {
						return val1.compareToIgnoreCase(val2);
					}
				});
			}
			return values;
		} catch(FTPException fe) {
			// 忽略文件夹不存在的情况
			String mark = "code=550";
			if (fe.toString().indexOf(mark) == -1) {
				throw fe;
			}
		} finally {
			logout(client);
		}
		return new String[0];
	}
}
}
