package net.xzh.kawaks;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cn.hutool.core.io.FileUtil;
 
public class 比较文件 {
    public static void main2(String[] args) {
    	HashMap<String, String> download=new HashMap<String, String>();
    	HashMap<String, String> src=new HashMap<String, String>();
    	
    	//下载的内容171
        File cps1 = new File("C://Users//CR7//Desktop//winkawaks//cps1-download.txt"); // 替换为你的文件路径
        List<String> lines = FileUtil.readLines(cps1, "UTF-8"); // 假设文件编码为UTF-8
        for (String line : lines) {
//        	System.out.println("1---"+line);
        	download.put(line, line);
        }
        
        //网站抓取的内容172
        File cps1_src = new File("C://Users//CR7//Desktop//winkawaks//cps1-172.txt"); // 替换为你的文件路径
        List<String> lines2 = FileUtil.readLines(cps1_src, "UTF-8"); // 假设文件编码为UTF-8
        for (String line : lines2) {
        	line=line.substring(line.lastIndexOf("/")+1);
//        	System.out.println("2---"+line);
        	src.put(line, line);
        	
        }
        
        //
        for (Entry<String, String> entry : src.entrySet()) {
        	if(!download.containsKey(entry.getKey())) {
        		System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        	}
        }
    }
}
