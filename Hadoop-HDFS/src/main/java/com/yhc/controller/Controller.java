package com.yhc.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("test")
public class Controller {

	Logger log = LoggerFactory.getLogger(Controller.class);

	@Autowired
	FileSystem fs;

	@PostMapping("upload")
	public String upload(@RequestParam("file") MultipartFile file)
			throws IOException, InterruptedException, URISyntaxException {
		log.info("# upload begin,[{}]", file.getSize());

		// fs.mkdirs(new Path("/home/yhc/hdfs/workSpace/test"));
		Path path = new Path("/test.doc");
		if (fs.exists(path)) {
			return "file exists";
		}
		FSDataOutputStream outputStream = fs.create(path);
		outputStream.write(file.getBytes());
		outputStream.close();
		fs.close();

		log.info("# upload end");
		return "success";
	}

	@GetMapping("download")
	public String download()
			throws IOException, InterruptedException, URISyntaxException {
		log.info("# download begin");

		Path path = new Path("/test.doc");
		fs.copyToLocalFile(path, new Path("D:/test.doc"));

		log.info("# download end");
		return "success";
	}
}
