package net.xzh.kawaks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 第1步
 * @author CR7
 *
 */
public class 获取游戏列表 {

	public static void main(String[] args) {
		// 生成cps1-172.txt
		getRomsList("https://www.kawaks.org/roms/cps1/index.htm");
		// 生成cps2-252.txt
//		getRomsList("https://www.kawaks.org/roms/cps2/index.htm");
		// 生成neogeo-285.txt
//		getRomsList("https://www.kawaks.org/roms/neogeo/index.htm");
	}

	static void getRomsList(String url) {
		try {
			// 从URL解析
			Document doc = Jsoup.connect(url).get();
			// 选择所有 class="rom-system-index-entry" 的元素
			Elements romEntries = doc.select("div.rom-system-index-entry");
			// 存储结果的列表
			List<String> hrefList = new ArrayList<>();
			// 直接选择有href属性的a标签
			for (Element entry : romEntries) {
			    Element link = entry.selectFirst("a[href]");
			    if (link != null) {
			        String href = link.attr("href");
			        String download=href.substring(0,href.lastIndexOf("."))+"-download.htm";
			        String prifx=url.substring(0,url.lastIndexOf("/"));
			        System.out.println(prifx+"/"+download);
			        hrefList.add(href);
			    }
			}
			System.out.println("\n总共找到 " + hrefList.size() + " 个游戏链接");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
