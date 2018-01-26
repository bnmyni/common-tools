
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class FileCompress {

	/**
	 * 压缩文件
	 * @param desZipFile
	 * @param srcFiles
	 * @throws IOException
	 */
	public static void zipFiles(OutputStream os, String ... srcFiles) throws IOException{
		if(srcFiles == null || srcFiles.length == 0){
			throw new IllegalArgumentException("srcFiles为空");
		}
		ZipOutputStream zipOut = null;
		try{
			zipOut = new ZipOutputStream(os);
			for(String srcFile : srcFiles){
				zipFile(zipOut, new File(srcFile),"");
				
			}
			zipOut.flush();
		}catch(Exception e){
			logger.error("zip压缩文件失败:原因:" + e.getMessage());
			throw new RuntimeException(e);
		}finally{
			if(zipOut != null){
				zipOut.close();
				zipOut = null;
			}
		}
		
		
	}
	
	/**
	 * 压缩单个文件
	 * @param zipOut
	 * @param file
	 * @param basePath
	 * @throws IOException
	 */
	private static void zipFile(ZipOutputStream zipOut, File file, String basePath) throws IOException{
		basePath =  StringUtils.isBlank(basePath) ? "" : basePath + File.separator;
		if(file.isDirectory()){//是目录
			File[] files = file.listFiles();
			if(files.length > 0){//目录不为空
				for(File f : files){
					zipFile(zipOut, f, basePath + file.getName());
				}
			}else{//目录为空,单独创建之
				if(logger.isDebugEnabled()){
					logger.debug("writing empty dir:" + file.getAbsolutePath());
				}
				zipOut.putNextEntry(new ZipEntry(basePath + file.getName() + File.separator));
				zipOut.closeEntry();
			}
		}else{//普通文件
			BufferedInputStream bfi = null;
			try{
				if(logger.isDebugEnabled()){
					logger.debug("writing file :" + file.getAbsolutePath());
				}
				bfi = new BufferedInputStream(new FileInputStream(file));
				zipOut.putNextEntry(new ZipEntry(basePath + file.getName()));
				byte[] buf = new byte[1024];
				int readedBytes = 0;
				while((readedBytes = bfi.read(buf)) != -1){
					zipOut.write(buf, 0, readedBytes);
				}
				zipOut.closeEntry();
			}finally{
				if(bfi != null){
					bfi.close();
					bfi = null;
				}
			}
		}
	}
}