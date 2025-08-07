package com.nbai.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件夹文件移动工具类
 * @author zhenmeng
 * @date 2021-01-28
 *
 */
@Slf4j
public class MoveFileUtil {
	
	/**
	 * 删除某个目录及目录下的所有子目录和文件
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean isDelete = deleteDir(new File(dir, children[i]));
				if (!isDelete) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	/**
	 * 复制某个目录及目录下的所有子目录和文件到新文件夹
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	private static boolean copyFolder(File oldPath, File newPath) {
		try {
			String[] file = oldPath.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				temp = new File(oldPath + "/" + file[i]);
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					// 复制并且改名
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] bufferarray = new byte[1024 * 64];
					int prereadlength;
					while ((prereadlength = input.read(bufferarray)) != -1) {
						output.write(bufferarray, 0, prereadlength);
					}
					output.flush();
					output.close();
					input.close();
				}
			}
		} catch (Exception e) {
			log.error("复制整个文件夹内容操作出错", e);
			return false ;
		}
		log.info("****************** 文件复制完毕 ********************");
		return true;
	}

	/**
	 * 移动文件目录所有文件
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public static boolean moveFolder(File oldPath, File newPath) {
		// 先复制文件
		boolean copyFolder = copyFolder(oldPath, newPath);
		Assert.isTrue(copyFolder, "文件复制失败");
		// 删除原文件
		boolean deleteDir = deleteDir(oldPath);
		Assert.isTrue(deleteDir, "原文件删除失败");
		log.info("****************** 原文件删除完毕 ********************");
		return true;
	}

//  public static void main(String[] args) { 
//	  File descDir = new File("D:\\gitlocal\\machine-vision\\mchn-vsn-local-run\\models\\OCR\\测试模型ocr1\\onnx_model");
//	  File newDir = new File(descDir.getParent());
//    moveFolder(descDir, newDir); 
//  } 

}