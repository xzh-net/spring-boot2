package net.xzh.kawaks;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class 转换图片格式 {

	/**
	 * 遍历文件夹文件
	 * 
	 * @param srcPath    原图路径
	 * @param destPath   新图路径
	 * @param formatName 图片格式，支持bmp|gif|jpg|jpeg|png
	 * @return
	 */
	public static void traverseFile(String srcPath, String destPath, String formatToChange, String formatName) {
		boolean flag;
		File file = new File(srcPath);
		if (file.isFile()) {
			modifyImageFormat(srcPath, destPath + "." + formatName, formatName);
			System.out.println("转换单张图片,格式为" + formatName);
		} else {// 文件是一个文件夹
			File[] files = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if (pathname.getAbsolutePath().endsWith(formatToChange)) {
						return true;
					} else {
						return false;
					}
				}
			});
			for (File file1 : files) {
				String name = file1.toString().substring(file1.toString().lastIndexOf("\\"),
						file1.toString().lastIndexOf("."));
				flag = modifyImageFormat(file1.toString(), destPath + name + "." + formatName, formatName);
				if (flag) {
					System.out.println(file1.toString() + "转换成功!");
				} else {
					System.out.println(file1.toString() + "转换失败");
				}
			}
		}
	}

	/**
	 * 修改原图的文件格式
	 * 
	 * @param srcPath    原图路径
	 * @param destPath   新图路径
	 * @param formatName 图片格式，支持bmp|gif|jpg|jpeg|png
	 * @return true/false
	 */
	public static boolean modifyImageFormat(String srcPath, String destPath, String formatName) {
		boolean flag = false;
		try {
			BufferedImage bufferedImg = ImageIO.read(new File(srcPath));
			flag = ImageIO.write(bufferedImg, formatName, new File(destPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return flag;
	}

	public static void main(String[] args) {
		traverseFile("C://Users//CR7//Desktop//winkawaks//sshots/jpg/cps1", "C://Users//CR7//Desktop//winkawaks//sshots/bmp/cps11", "jpg", "bmp");// 转换某一文件夹的图片
//		traverseFile("C://Users//CR7//Desktop//winkawaks//sshots/jpg/cps2", "C://Users//CR7//Desktop//winkawaks//sshots/bmp/cps2", "jpg", "bmp");// 转换某一文件夹的图片
//		traverseFile("C://Users//CR7//Desktop//winkawaks//sshots/jpg/neogeo", "C://Users//CR7//Desktop//winkawaks//sshots/bmp/neogeo", "jpg", "bmp");// 转换某一文件夹的图片
	}

}
