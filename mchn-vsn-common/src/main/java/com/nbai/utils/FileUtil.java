package com.nbai.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zz
 * @version 1.0
 * @date 2021-1-8
 */
@Slf4j
public class FileUtil {

	@ApiOperation("以byte[]的方式读取一个文件的内容")
	public static final byte[] readBytes(String filename) throws IOException {
		File file = new File(filename);
		return readBytes(file);
	}
	
    @ApiOperation("以byte[]的方式读取一个文件的内容")
    public static final byte[] readBytes(File file) throws IOException {
        return Files.toByteArray(file);
    }

    public static final String readString(String filename) throws IOException {
        File file = new File(filename);
        return Files.asCharSource(file, Charsets.UTF_8).read();
    }

    /**
     * 向文件中写入字节流
     *
     * @param fileName 要写入文件的文件名
     * @param contents 要写入的文件内容
     */
    public static void fileWrite(final String fileName, final String contents) {
        //heckNotNull(fileName, "Provided file name for writing must NOT be null.");
        //checkNotNull(contents, "Unable to write null contents.");
        final File newFile = new File(fileName);
        try {
            Files.write(contents.getBytes(), newFile);
        } catch (IOException fileIoEx) {
            System.err.println("ERROR trying to write to file '" + fileName + "' - "
                    + fileIoEx.toString());
        }
    }

    /**
     * 向文件中写入字节流
     */
    public static String fileRead(File file) {
        List<String> lines = null;
        String whole = "";
        try {
            lines = Files.readLines(file, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : lines) {
            whole += line;
        }
        return whole;
    }

//    @ApiOperation("获取资源路径下的文本资源文件内容")
//    public static final String getResouseContent(String fileName) throws IOException {
//        Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader()).getResources("classpath:" + fileName);
//        InputStream inputStream = resources[0].getInputStream();
//        return IOUtils.toString(inputStream, Charset.forName("UTF-8"));
//    }

    /**
     * 
     * @param file 
     * @param filePath 目标文件
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static boolean writeFile(InputStream input, File filePath) throws IOException, FileNotFoundException {
		try(OutputStream out = new FileOutputStream(filePath)) {
			byte[] buf = new byte[1024 * 64];
			int bytesRead;
			while ((bytesRead = input.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
		}catch (Exception e) {
			log.error("文件写入磁盘失败", e);
			return false;
		} finally {
			input.close();
		}
		log.info("****************** 文件写入磁盘成功 ********************");
		return true;
	}
    
    
    /**
     * 
     * @param currentFile
     * @param filePath
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static boolean writeFile(File currentFile, File filePath) throws IOException, FileNotFoundException {
    	try(InputStream input = new FileInputStream(currentFile); OutputStream out = new FileOutputStream(filePath)) {
    		byte[] buf = new byte[1024 * 64];
    		int bytesRead;
    		while ((bytesRead = input.read(buf)) != -1) {
    			out.write(buf, 0, bytesRead);
    		}
    	}catch (Exception e) {
    		log.error("文件写入磁盘失败", e);
    		return false;
    	}
    	log.info("****************** 文件写入磁盘成功 ********************");
    	return true;
    }
    
    public static String getFileContent(String fileName) {
        Resource[] resources;
        try {
            resources = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader()).getResources("classpath:" + fileName);
            InputStream is = resources[0].getInputStream();
            return fileToString(is, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    public static String fileToString(InputStream inputStream, Charset charset) {
        if (inputStream == null)
            return "";

        try (StringWriter writer = new StringWriter();
             InputStreamReader reader = new InputStreamReader(inputStream, charset);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            char[] chars = new char[1024];
            int readChars;
            while ((readChars = bufferedReader.read(chars)) != -1) {
                writer.write(chars, 0, readChars);
            }
            return writer.toString();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
    
}
