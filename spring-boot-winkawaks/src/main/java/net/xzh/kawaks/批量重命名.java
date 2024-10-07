package net.xzh.kawaks;

import java.io.File;

public class 批量重命名 {
	public static void main(String[] args) {
		getAllFiles(new File("C://Users//CR7//Desktop//cheats"));
	}

	private static void getAllFiles(File dir) {
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					getAllFiles(file);
				} else {
					System.out.println(file.getName());
					if(file.getName().endsWith("DAT")) {
						String new_name=file.getName().substring(0,file.getName().lastIndexOf(".")+1)+"dat";
						File newFile = new File("C://Users//CR7//Desktop//cheats_rename//"+new_name);
						if (file.renameTo(newFile)) {
						    System.out.println("文件重命名成功！");
						    file.delete();
						} else {
						    System.out.println("文件重命名失败！");
						}	
					}
				}
			}
		}
	}

}
