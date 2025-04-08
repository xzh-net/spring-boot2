# WinKawaks1.65

- 官网地址：https://www.kawaks.org/

1. 提取html内容

   > jsoup解析html获取标签内容，得到预览图下载地址和Rom文件下载地址

2. 文件批量下载

   ```bash
   !/bin/bash
    
   filename="cps1-172.txt"  
   
   while read line
     do
       wget -c -b -P roms $line
     done < $filename
   ```

   > 遍历文件，支持端点续传、后台执行、并下载到指定路径下

3. 图片格式转换

   > sshots格式为bmp

4. 图片处理（可选）
