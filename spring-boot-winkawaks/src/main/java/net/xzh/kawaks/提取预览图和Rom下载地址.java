package net.xzh.kawaks;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;

public class 提取预览图和Rom下载地址 {
	public static void main(String[] args) {
//		getAllFiles(new File("C://Users//CR7//Desktop//winkawaks//roms//cps1//roms"));
//		getAllFiles(new File("C://Users//CR7//Desktop//winkawaks//roms//cps2//roms"));
		getAllFiles(new File("C://Users//CR7//Desktop//winkawaks//roms//neogeo//roms"));
	}

	private static void getAllFiles(File dir) {
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					getAllFiles(file);
				} else {
					String content = FileUtil.readString(file, CharsetUtil.charset("UTF-8"));
					Document document = Jsoup.parse(content);
					String img = document.select("#rom-screen img").attr("src");
					System.out.println("https://www.kawaks.org" + img);
			    	String href = document.select("#rom-url .rom-value a").attr("href");
					System.out.println(href);
				}
			}
		}
	}

}
