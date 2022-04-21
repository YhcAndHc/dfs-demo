package com.yhc.config;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Config {

	public final static String HDFS_PATH = "hdfs://192.168.92.92:9000";
	private final static String HDFS_HOME = "F:\\Hadoop\\hadoop-3.2.0\\";
	private final static String HDFS_USER = "root";

	@Bean
	public Configuration getHDFSConfig() {
		System.setProperty("hadoop.home.dir", HDFS_HOME);
		Configuration configuration = new Configuration();
		configuration.set("fs.defaultFS", HDFS_PATH);
		return configuration;
	}

	@Bean
	public FileSystem getFileSystem() throws Exception {
		FileSystem fileSystem = FileSystem.newInstance(new URI(HDFS_PATH), getHDFSConfig(), HDFS_USER);
		return fileSystem;
	}

}
