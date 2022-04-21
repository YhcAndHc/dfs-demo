package com.yhc.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

@RestController
@RequestMapping("test")
public class Controller {

	Logger log = LoggerFactory.getLogger(Controller.class);
	
	static String filePath = "";

	@Autowired
	private FastFileStorageClient fdfsClient;

	@PostMapping("upload")
	public String upload(@RequestParam("file") MultipartFile file) throws IOException {
		log.info("# upload begin");
		StorePath storePath =  fdfsClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
		filePath = storePath.getFullPath();
		log.info("# path : [{}]", filePath);
		log.info("# upload begin");
		return "success";
	}

	@GetMapping("download")
	public String download() throws IOException {
		log.info("# download begin");
		String group = filePath.substring(0, filePath.indexOf("/"));
        String path = filePath.substring(filePath.indexOf("/") + 1);
		byte[] bs = fdfsClient.downloadFile(group, path, new DownloadByteArray());
		FileUtils.writeByteArrayToFile(new File("D://fdfs.doc"), bs);
		log.info("# download end");
		return "success";
	}

}
