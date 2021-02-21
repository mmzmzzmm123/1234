package com.ruoyi.business.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.file.FileUploadUtils;

import io.swagger.annotations.ApiOperation;

/**
 * 文件上传/下载/删除
 *
 */
@RestController
@RequestMapping("/business/file")
public class FileController {

	@ApiOperation("文件上传")
	@PostMapping(value = { "/upload" })
	public AjaxResult uploadImage(HttpServletRequest request) throws IOException {
		StandardMultipartHttpServletRequest httpServletRequest = (StandardMultipartHttpServletRequest) request;
		Iterator<String> iterator = httpServletRequest.getFileNames();
		AjaxResult ajaxResult = AjaxResult.success();
		while (iterator.hasNext()) {
			MultipartFile file = httpServletRequest.getFile(iterator.next());
			String uploadPath = FileUploadUtils.upload(file);
			ajaxResult.put("uri", uploadPath);
		}
		return ajaxResult;
	}

	@ApiOperation("附件下载/图片查看")
	@GetMapping(value = { "/download" })
	public void downloadByURL(@RequestParam(value = "uri", required = true) String uri, HttpServletResponse response) {
		String defaultBaseDir = RuoYiConfig.getProfile();
		File localFile = new File(defaultBaseDir + uri);
		if (!localFile.exists()) {
			throw new CustomException("文件不存在！");
		}
		String fileName = FilenameUtils.getName(uri);
		response.setContentType("application/x-download");
		response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try (InputStream inputStream = new FileInputStream(localFile);
				ServletOutputStream servletOutputStream = response.getOutputStream()) {
			IOUtils.copy(inputStream, servletOutputStream);
		} catch (IOException e) {
			throw new CustomException("文件下载失败，IO错误！", e);
		}
	}

}
