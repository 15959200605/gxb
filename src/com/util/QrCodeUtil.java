package com.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import com.swetake.util.Qrcode;

public class QrCodeUtil {
	public static Logger log = Logger.getLogger(QrCodeUtil.class.getName());
	public static boolean buildQrcode(String content,String folder1,String folder,String fileName){
		try {
			File folderFile = new File(folder);
        	if(!folderFile.exists()){
        		folderFile.mkdirs();
        	}
			Qrcode qrcode = new Qrcode();
			qrcode.setQrcodeErrorCorrect('M');
			qrcode.setQrcodeEncodeMode('B');
			qrcode.setQrcodeVersion(7);
			String testString = content;
			byte[] d = testString.getBytes("UTF-8");
			BufferedImage bi = new BufferedImage(278, 278,BufferedImage.TYPE_BYTE_BINARY);
			Graphics2D g = bi.createGraphics();
			g.setBackground(Color.WHITE);
			g.clearRect(0, 0, 278, 278);
			g.setColor(Color.BLACK);
			// 限制最大字节数为119
			if (d.length > 0 && d.length < 120) {
				boolean[][] s = qrcode.calQrcode(d);
				for (int i = 0; i < s.length; i++) {
					for (int j = 0; j < s.length; j++) {
						if (s[j][i]) {
							g.fillRect(j * 6+2, i * 6+2, 6, 6);
						}
					}
				}
			}
			g.dispose();
			bi.flush();
            File file = new File(folder+"\\"+fileName+".jpeg");
			ImageIO.write(bi, "jpg", file);
			BufferedImage image = ImageIO.read(file);
			BufferedImage logo = ImageIO.read(new File(folder1+"\\logo.png"));
			Graphics2D g2 = image.createGraphics();
			int width=image.getWidth()/4;
			int height=image.getHeight()/4;
			int x=(image.getWidth()-width)/2;
	        int y=(image.getHeight()-height)/2;
	        g2.drawImage(logo, x, y, width, height, null);
	        g2.dispose();
	        ImageIO.write(image, "jpg", new File(folder+"\\"+fileName+".jpeg"));
	        file.delete();
		} catch (Exception e) {
			log.error("名称为："+fileName+"的二维码生成失败");
			return false;
		}
		return true;
	}
	public static void main(String[] args) {
		try {
			buildQrcode("http://cbp.88ip.cn:8085/upload/app/cbp_android.apk", "d:/tt", "d:/tt", "cbp_qrcode");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
