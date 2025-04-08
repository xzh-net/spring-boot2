package net.xzh.kawaks;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class 图片处理 {

	public static void main(String[] args) throws IOException {

		String originalPic = "C://Users//CR7//Desktop//winkawaks//pexels-mike-b-130851.jpeg";
		String watermarkPic = "C://Users//CR7//Desktop//317776.png";
		String picturePath = "C://Users//CR7//Desktop//winkawaks//";

		// 缩放图片（缩小）
//		Thumbnails.of(originalPic).size(300, 200).toFile(picturePath + "climb-up.size.300X200.jpeg");

		// 缩放图片（放大）
//		Thumbnails.of(originalPic).size(6600, 4400).toFile(picturePath + "climb-up.size.6600X4400.jpeg");

		// 保持宽高比（缩小）
//		Thumbnails.of(originalPic).scale(0.1f).toFile(picturePath + "climb-up.scale.601X401.jpeg");

		// 保持宽高比（放大）
//		Thumbnails.of(originalPic).scale(1.1f).toFile(picturePath + "climb-up.scale.6601X4401.jpeg");

		// 图片裁剪 从中央裁剪一个600*400的区域
//		Thumbnails.of(originalPic).sourceRegion(Positions.BOTTOM_CENTER, 600, 400).size(600, 400)
//				.toFile(picturePath + "climb-up.crop.jpeg");

		// 图片旋转90度
//		Thumbnails.of(originalPic).scale(1).rotate(90).toFile(picturePath + "climb-up.rotate.90.jpeg");

		// 批量处理 保持宽高比2倍，并且重新命名
//		Thumbnails.of(Objects.requireNonNull(new File(picturePath).listFiles())).scale(2)
//				.toFiles(Rename.PREFIX_DOT_THUMBNAIL);

		// 转换格式
//		Thumbnails.of(originalPic).size(300, 200).outputFormat("png").toFile(picturePath + "climb-up.new");

		// 添加水印
//		Thumbnails.of(originalPic).size(600, 400)
//				.watermark(Positions.TOP_RIGHT, ImageIO.read(new File(watermarkPic)), 0.5f)
//				.toFile(picturePath + "climb-up.watermark.jpeg");

		// 调整输出质量，特别是在生成jpeg，需要根据图片质量动态设置质量阈值
//		Thumbnails.of(originalPic).size(6000, 4000).outputQuality(0.8)
//				.toFile(picturePath + "climb-up.outputQuality.jpeg");

		// 保存到输出流
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		Thumbnails.of(originalPic).size(300, 200).toOutputStream(baos);
//		byte[] imageBytes = baos.toByteArray();
		
		// 链式操作
		Thumbnails.of(originalPic).size(600, 400).rotate(45)
				.watermark(Positions.TOP_LEFT, ImageIO.read(new File(watermarkPic)), 0.5f).outputQuality(0.8)
				.toFile(picturePath + "climb-up.link.jpeg");

	}

}
