package com.aspire.hdc.common.ftp;

import java.io.IOException;

import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteFileChangedReloadingStrategy extends FileChangedReloadingStrategy{
	
  private static final Logger logger = LoggerFactory.getLogger(RemoteFileChangedReloadingStrategy.class);

  private String lastModifiedDate = null;

  protected boolean hasChanged() {
    if (this.lastModifiedDate == null) {
      return true;
    }

    String tmpLastModifiedDate = getLastModifiedDate();
    return tmpLastModifiedDate != null && !tmpLastModifiedDate.equals(this.lastModifiedDate);

  }

  protected void updateLastModified()
  {
    String tmpLastModifiedDate = getLastModifiedDate();
    if (tmpLastModifiedDate != null)
      this.lastModifiedDate = tmpLastModifiedDate;
  }

  private String getLastModifiedDate()
  {
    HttpClient httpclient = new HttpClient();
    GetMethod getMethod = new GetMethod(this.configuration.getURL().toString());
    try {
      httpclient.executeMethod(getMethod);
      Header lastModifiedHeader = getMethod
        .getResponseHeader("Last-Modified");
      if (lastModifiedHeader != null)
        return lastModifiedHeader.getValue();
    }
    catch (HttpException e) {
      logger.error("打开URL失败，URL：" + this.configuration.getURL().toString());
    } catch (IOException e) {
      logger.error("打开URL失败：" + this.configuration.getURL().toString());
    } finally {
      getMethod.releaseConnection(); } getMethod.releaseConnection();

    return null;
  }
}