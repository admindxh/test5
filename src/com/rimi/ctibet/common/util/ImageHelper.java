package com.rimi.ctibet.common.util;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageHelper {
	/*
	 * 根据尺寸图片居中裁剪
	 */
	 public static void cutCenterImage(String src,String dest,int w,int h) throws IOException{ 
		 Iterator iterator = ImageIO.getImageReadersByFormatName("jpg"); 
         ImageReader reader = (ImageReader)iterator.next(); 
         InputStream in=new FileInputStream(src);
         ImageInputStream iis = ImageIO.createImageInputStream(in); 
         reader.setInput(iis, true); 
         ImageReadParam param = reader.getDefaultReadParam(); 
         int imageIndex = 0; 
         Rectangle rect = new Rectangle((reader.getWidth(imageIndex)-w)/2, (reader.getHeight(imageIndex)-h)/2, w, h);  
         param.setSourceRegion(rect); 
         BufferedImage bi = reader.read(0,param);   
         ImageIO.write(bi, "jpg", new File(dest));           
  
	 }
	/*
	 * 图片裁剪二分之一
	 */
	 public static void cutHalfImage(String src,String dest) throws IOException{ 
		 Iterator iterator = ImageIO.getImageReadersByFormatName("jpg"); 
         ImageReader reader = (ImageReader)iterator.next(); 
         InputStream in=new FileInputStream(src);
         ImageInputStream iis = ImageIO.createImageInputStream(in); 
         reader.setInput(iis, true); 
         ImageReadParam param = reader.getDefaultReadParam(); 
         int imageIndex = 0; 
         int width = reader.getWidth(imageIndex)/2; 
         int height = reader.getHeight(imageIndex)/2; 
         Rectangle rect = new Rectangle(width/2, height/2, width, height); 
         param.setSourceRegion(rect); 
         BufferedImage bi = reader.read(0,param);   
         ImageIO.write(bi, "jpg", new File(dest));   
	 }
	/*
	 * 图片裁剪通用接口
	 */

    @SuppressWarnings("unchecked")
	public static void cutImage(String src,String dest,int x,int y,int w,int h) throws IOException{ 
           Iterator iterator = ImageIO.getImageReadersByFormatName("jpg"); 
           ImageReader reader = (ImageReader)iterator.next(); 
           InputStream in=new FileInputStream(src);
           ImageInputStream iis = ImageIO.createImageInputStream(in); 
           reader.setInput(iis, true); 
           ImageReadParam param = reader.getDefaultReadParam(); 
           Rectangle rect = new Rectangle(x, y, w,h);  
           param.setSourceRegion(rect); 
           BufferedImage bi = reader.read(0,param);   
           // 保存新图片  
           String d  =  dest.substring(0, dest.lastIndexOf("/")+1);
           d = d+"//1.jpg";
           File tempOutFile = new File(dest);  
           if (!tempOutFile.exists()) {  
               tempOutFile.mkdirs();  
           } 
           ImageIO.write(bi, "jpg", tempOutFile);           

    } 
    /*
     * 图片缩放
     */
    public static void zoomImage(String src,String dest,int w,int h) throws Exception {
		double wr=0,hr=0;
		File srcFile = new File(src);
		File destFile = new File(dest);
		BufferedImage bufImg = ImageIO.read(srcFile);
		Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);
		wr=w*1.0/bufImg.getWidth();
		hr=h*1.0 / bufImg.getHeight();
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		Itemp = ato.filter(bufImg, null);
		try {
			ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
    /** 
     * 图像缩放 jpg格式 
     * 
     * @param imgsrc 
     *             :原图片文件路径 
     * @param imgdist 
     *             :生成的缩略图片文件路径 
     * @param widthdist 
     *             :生成图片的比例宽度 
     * @param heightdist 
     *             :生成图片的高度 
     */   
   public static void reduceImg(String imgsrc, String imgdist, int widthdist,    
           int heightdist) {
       try {    
            File srcfile = new File(imgsrc);    
           if (!srcfile.exists()) {    
               return;
            }    
            Image src = ImageIO.read(srcfile);  
            int imageWidth = src.getWidth(null);   
            int imageHeight = src.getHeight(null);
            widthdist = widthdist * imageWidth / 100;
            heightdist = imageHeight * heightdist /100;
            BufferedImage tag = new BufferedImage((int) widthdist,    
                    (int) heightdist, BufferedImage.TYPE_INT_RGB);  
            
           /* 
             * Image.SCALE_SMOOTH 的缩略算法   生成缩略图片的平滑度的 
             * 优先级比速度高 生成的图片质量比较好 但速度慢 
             */   
            tag.getGraphics().drawImage(    
                    src.getScaledInstance(widthdist, heightdist,    
                            Image.SCALE_SMOOTH), 0, 0, null);    
               
            File out = new File(imgdist);    
            
            ImageIO.write(tag,  "jpg",  out);//写图片   
            
            
            
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
//            encoder.encode(tag);    
//            out.close();    
  
        } catch (IOException ex) {    
            ex.printStackTrace();    
        }    
    }    
}
