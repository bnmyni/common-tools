package com.aspire.hdc.common.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class ConfigurationHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationHelper.class);

	@SuppressWarnings("unused")
	private static final String XML = ".xml";
	@SuppressWarnings("unused")
	private static final String PROPERTIES = ".properties";
	@SuppressWarnings("unused")
	private static final String HTTP = "HTTP://";
	private static String basePath = null;

	private static String deployBasePath = null;

	public static void setBasePath(String basePath) {
		ConfigurationHelper.basePath = basePath;
	}

	public static String getBasePath() {
		if (basePath == null) {
			return System.getProperty("user.dir") + "/config";
		}
		return basePath;
	}

	public static InputStream readConfiguration(String configurationFileName) {
		String fileName = getFullFileName(configurationFileName);
		return readBaseConfiguration(fileName);
	}

	private static InputStream readBaseConfiguration(String fileName) {
		if (fileName == null) {
			return null;
		}
		boolean isUrl = isUrlFile(fileName);

		if (isUrl) {
			try {
				URL url = new URL(fileName);
				return url.openStream();
			} catch (MalformedURLException e) {
				LOGGER.error("打开URL配置文件失败，URL=" + fileName, e);
			} catch (IOException e) {
				LOGGER.error("打开URL配置文件失败，URL=" + fileName, e);
			}
			return null;
		}
		try {
			File file = new File(fileName);
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			LOGGER.error("打开配置文件失败，filename=" + fileName, e);
		}
		return null;
	}

	public static Configuration getConfiguration(String configurationFileName) {
		return getConfiguration(configurationFileName, 0L, false);
	}

	public static Configuration getConfiguration(String configurationFileName, long refreshDelay) {
		return getConfiguration(configurationFileName, refreshDelay, false);
	}

	public static Configuration getConfiguration(String configurationFileName, long refreshDelay, boolean delimiterParsingDisabled) {
		String fileName = getFullFileName(configurationFileName);
		return getBaseConfiguration(fileName, refreshDelay, delimiterParsingDisabled);
	}

	private static Configuration getBaseConfiguration(String fileName,long refreshDelay, boolean delimiterParsingDisabled) {
		LOGGER.info("fileName:{}",fileName);
		if (StringUtils.isEmpty(fileName)) {
			return null;
		}

		boolean isXmlFile = false;
		if (fileName.endsWith(".xml"))
			isXmlFile = true;
		else if (fileName.endsWith(".properties"))
			isXmlFile = false;
		else {
			return null;
		}

		boolean isUrl = isUrlFile(fileName);

		if (isXmlFile) {
			XMLConfiguration xmlConfiguration = new XMLConfiguration();
			xmlConfiguration.setDelimiterParsingDisabled(delimiterParsingDisabled);
			if (isUrl) {
				try {
					xmlConfiguration.load(new URL(fileName));
				} catch (ConfigurationException e) {
					LOGGER.error("打开URL配置文件失败，URL=" + fileName, e);
					return null;
				} catch (MalformedURLException e) {
					LOGGER.error("打开URL配置文件失败，URL=" + fileName, e);
					return null;
				}
			} else {
				try {
					xmlConfiguration.load(fileName);
				} catch (ConfigurationException e) {
					LOGGER.error("打开配置文件失败，filename=" + fileName, e);
					return null;
				}
			}

			if (refreshDelay > 0L) {
				FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
				fileChangedReloadingStrategy.setConfiguration(xmlConfiguration);
				fileChangedReloadingStrategy.setRefreshDelay(refreshDelay);
				xmlConfiguration
						.setReloadingStrategy(fileChangedReloadingStrategy);
			}
			return xmlConfiguration;
		}
		PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
		propertiesConfiguration.setDelimiterParsingDisabled(delimiterParsingDisabled);
		if (isUrl)
			try {
				propertiesConfiguration.load(new URL(fileName));
			} catch (ConfigurationException e) {
				LOGGER.error("打开URL配置文件失败，URL=" + fileName, e);
				return null;
			} catch (MalformedURLException e) {
				LOGGER.error("打开URL配置文件失败，URL=" + fileName, e);
				return null;
			}
		else {
			try {
				propertiesConfiguration.load(fileName);
			} catch (ConfigurationException e) {
				LOGGER.error("打开配置文件失败，filename=" + fileName, e);
				return null;
			}

		}

		if (refreshDelay > 0L) {
			FileChangedReloadingStrategy reloadingStrategy = null;
			if (isUrl){
				reloadingStrategy = new RemoteFileChangedReloadingStrategy();
			}else {
				reloadingStrategy = new FileChangedReloadingStrategy();
			}
			reloadingStrategy.setConfiguration(propertiesConfiguration);
			reloadingStrategy.setRefreshDelay(refreshDelay);
			propertiesConfiguration.setReloadingStrategy(reloadingStrategy);
		}

		return propertiesConfiguration;
	}

	public static void setDeployBasePath(String deployBasePath) {
		ConfigurationHelper.deployBasePath = deployBasePath;
	}

	public static String getDeployBasePath() {
		if (deployBasePath == null) {
			return System.getProperty("user.dir");
		}
		return deployBasePath;
	}

	public static InputStream readDeployConfiguration(String configurationFileName) {
		String fileName = getDeployFullFileName(configurationFileName);
		return readBaseConfiguration(fileName);
	}

	public static Configuration getDeployConfiguration(String configurationFileName) {
		return getDeployConfiguration(configurationFileName, 0L, false);
	}

	public static Configuration getDeployConfiguration(String configurationFileName, long refreshDelay) {
		return getDeployConfiguration(configurationFileName, refreshDelay,false);
	}

	public static Configuration getDeployConfiguration(String configurationFileName, long refreshDelay, 
		boolean delimiterParsingDisabled) {
		String fileName = getDeployFullFileName(configurationFileName);
		return getBaseConfiguration(fileName, refreshDelay, delimiterParsingDisabled);
	}

	public static String getFullFileName(String fileName) {
		if (fileName == null)
			return null;
		if (getBasePath() != null) {
			return getBasePath() + File.separator + fileName;
		}
		return fileName;
	}

	public static String getDeployFullFileName(String fileName) {
		if (fileName == null)
			return null;
		if (deployBasePath != null) {
			return deployBasePath + File.separator + fileName;
		}
		return fileName;
	}

	private static boolean isUrlFile(String fileName) {
		if (fileName.toUpperCase().startsWith("HTTP://")) {
			return true;
		}
		return false;
	}
}