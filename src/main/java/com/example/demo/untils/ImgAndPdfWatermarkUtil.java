package com.example.demo.untils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @ClassName ImgAndPdfWatermarkUtil
 * @Author ImgAndPdfWatermarkUtil
 * @Date 2019/4/8 22:44
 * @Version 1.0
 **/
public class ImgAndPdfWatermarkUtil {


    /**
     * 水印类型
     *
     * @author hejiang
     * @version V1.0
     * @date 2019/2/13 11:08
     */
    public enum WatermarkStyle {
        /**
         * 平铺
         */
        TILED(10),
        /**
         * 居中
         */
        CENTER(20),
        /**
         * 右下
         */
        BOTTOM_RIGHT(30),
        /**
         * 自定义
         */
        SELF_DEF(90);

        private int value;

        WatermarkStyle(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public WatermarkStyle setValue(int value) {
            this.value = value;
            return this;
        }
    }

    /**
     * 给pdf添加文字水印
     *
     * @param srcPath        原文件位置
     * @param tarPath        加水印后文件位置
     * @param text           文字水印内容
     * @param watermarkStyle 水印格式
     * @author hejiang
     * @date 2019/4/9 15:40
     */
    public static void setTextWaterMarkForPdf(String srcPath, String tarPath, String text, WatermarkStyle watermarkStyle, int size) throws Exception {
        setTextWaterMarkForPdf(srcPath, tarPath, text, watermarkStyle, 0, 0, size);
    }

    /**
     * 给pdf添加文字水印
     *
     * @param srcPath        原文件位置
     * @param tarPath        加水印后文件位置
     * @param text           文字水印内容
     * @param watermarkStyle 水印格式
     * @param x              水印的横坐标
     * @param y              水印的纵坐标
     * @author hejiang
     * @date 2019/4/9 15:40
     */
    public static void setTextWaterMarkForPdf(String srcPath, String tarPath, String text, WatermarkStyle watermarkStyle, float x, float y, int size) throws Exception {
        size = (Object) size == null ? 30 : size;

        PdfReader reader = new PdfReader(srcPath);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(tarPath));

        PdfGState gs = new PdfGState();
        // 设置透明度
        gs.setFillOpacity(0.2f);
        BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        JLabel label = new JLabel();
        label.setText(text);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int textH = metrics.getHeight();
        int textW = metrics.stringWidth(text);
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        for (int i = 1; i < total; i++) {
            // 在文件上方添加水印
            content = stamper.getOverContent(i);
            content.beginText();
            content.setGState(gs);
            content.setColorFill(BaseColor.LIGHT_GRAY);
            content.setFontAndSize(font, size);
            // 开始写入水印
            float pageWidth = reader.getPageSize(i).getWidth();
            float pageHigh = reader.getPageSize(i).getHeight();
            switch (watermarkStyle) {
                case TILED:
                    // 旋转角度
                    float rotationDegrees = 15;
                    float heightAdd = textH * 3f;
                    float widthAdd = textW * 3f;
                    // 间距处理
                    double[] hAddAndWAdd = handleHAddAndWAdd(heightAdd, widthAdd, rotationDegrees);
                    heightAdd = (float) hAddAndWAdd[0];
                    widthAdd = (float) hAddAndWAdd[1];
                    for (float height = pageHigh * -0.5f, line = 1; height < pageHigh * 1.5f; height += heightAdd, line++) {
                        for (float width = line % 2 == 0 ? pageWidth * -0.5f - widthAdd / 2 : pageWidth * -0.5f; width < pageWidth * 1.5f; width +=
                                widthAdd) {
                            content.showTextAligned(Element.ALIGN_CENTER, text, width, height, rotationDegrees);
                        }
                    }
                    break;
                case CENTER:
                    content.showTextAligned(Element.ALIGN_CENTER, text, pageWidth / 2, pageHigh / 2, 0);
                    break;
                case BOTTOM_RIGHT:
                    content.showTextAligned(Element.ALIGN_CENTER, text, pageWidth - textW - 10, 5, 0);
                    break;
                case SELF_DEF:
                    content.showTextAligned(Element.ALIGN_CENTER, text, x, y, 0);
                    break;
                default:
            }
            content.endText();
        }
        stamper.close();
    }

    /**
     * 给pdf设置图片水印
     *
     * @param srcPath        原文件位置
     * @param watermarkPath  水印位置
     * @param tarPath        加水印后文件输出位置
     * @param watermarkStyle 水印格式
     * @author hejiang
     * @date 2019/4/9 15:40
     */
    public static void setImageWaterMarkForPdf(String srcPath, String watermarkPath, String tarPath, WatermarkStyle watermarkStyle) throws Exception {
        setImageWaterMarkForPdf(srcPath, watermarkPath, tarPath, watermarkStyle, 0, 0);
    }

    /**
     * 给pdf设置图片水印
     *
     * @param srcPath        原文件位置
     * @param watermarkPath  水印位置
     * @param tarPath        加水印后文件输出位置
     * @param watermarkStyle 水印格式
     * @param x              水印的横坐标
     * @param y              水印的纵坐标
     * @author hejiang
     * @date 2019/4/9 15:40
     */
    public static void setImageWaterMarkForPdf(String srcPath, String watermarkPath, String tarPath, WatermarkStyle watermarkStyle, float x, float y) throws Exception {
        PdfReader reader = new PdfReader(srcPath);
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(tarPath));
        Image img = Image.getInstance(watermarkPath);
        float watermarkWidth = img.getWidth();
        float watermarkHigh = img.getHeight();
        PdfGState gs = new PdfGState();
        // 设置透明度
        gs.setFillOpacity(0.2f);
        int total = reader.getNumberOfPages() + 1;
        PdfContentByte content;
        for (int i = 1; i < total; i++) {
            // 在内容下方加水印
            content = stamp.getUnderContent(i);
            content.setGState(gs);
            float pageWidth = reader.getPageSize(i).getWidth();
            float pageHigh = reader.getPageSize(i).getHeight();
            switch (watermarkStyle) {
                case TILED:
                    // 旋转角度
                    float rotationDegrees = 15;
                    float heightAdd = watermarkHigh * 1.5f;
                    float widthAdd = watermarkWidth * 1.5f;
                    // 间距处理
                    double[] hAddAndWAdd = handleHAddAndWAdd(heightAdd, widthAdd, rotationDegrees);
                    heightAdd = (float) hAddAndWAdd[0];
                    widthAdd = (float) hAddAndWAdd[1];
                    for (float height = pageHigh * -0.5f, line = 1; height < pageHigh * 1.5f; height += heightAdd, line++) {
                        for (float width = line % 2 == 0 ? pageWidth * -0.5f - widthAdd / 2 : pageWidth * -0.5f; width < pageWidth * 1.5f; width += widthAdd) {
                            img.setAbsolutePosition(width, height);
                            img.setRotationDegrees(rotationDegrees);
                            content.addImage(img);
                        }
                    }
                    break;
                case CENTER:
                    img.setAbsolutePosition((pageWidth - watermarkWidth) / 2, (pageHigh - watermarkHigh) / 2);
                    content.addImage(img);
                    break;
                case BOTTOM_RIGHT:
                    img.setAbsolutePosition(pageWidth - watermarkWidth - 3, 3);
                    content.addImage(img);
                    break;
                case SELF_DEF:
                    img.setAbsolutePosition(x, y);
                    content.addImage(img);
                    break;
                default:
            }
        }
        stamp.close();
        reader.close();
    }

    /**
     * 给图片添加文字水印
     *
     * @param srcPath          原图片位置
     * @param tarPath          加水印后图片输出位置
     * @param waterMarkContent 文字水印内容
     * @param watermarkStyle   水印格式
     * @author hejiang
     * @date 2019/4/9 15:40
     */
    public static void setTextWaterMarkForImage(String srcPath, String tarPath, String waterMarkContent, WatermarkStyle watermarkStyle) throws Exception {
        setTextWaterMarkForImage(srcPath, tarPath, waterMarkContent, watermarkStyle, 0, 0 );
    }

    /**
     * 给图片添加文字水印
     *
     * @param srcPath          原图片位置
     * @param tarPath          加水印后图片输出位置
     * @param waterMarkContent 文字水印内容
     * @param watermarkStyle   水印格式
     * @param x                水印的横坐标
     * @param y                水印的纵坐标
     * @author hejiang
     * @date 2019/4/9 15:40
     */
    public static void setTextWaterMarkForImage(String srcPath, String tarPath, String waterMarkContent, WatermarkStyle watermarkStyle, int x, int y) throws Exception {
        File srcImgFile = new File(srcPath);
        java.awt.Image srcImg = ImageIO.read(srcImgFile);
        int srcImgWidth = srcImg.getWidth(null);
        int srcImgHeight = srcImg.getHeight(null);
        // 加水印
        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufImg.createGraphics();
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
        g.setColor(Color.RED);
        // int fontSize = 30;
        int fontSize = srcImgWidth / 2;
        // g.setFont(new Font("宋体", Font.PLAIN, fontSize));
        g.setFont(new Font("宋体", Font.PLAIN, fontSize));
        //设置水印透明度
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.2f));
        //设置水印的坐标
        int textWidth = g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
        int textHight = g.getFontMetrics(g.getFont()).getHeight();
        switch (watermarkStyle) {
            case TILED:
                // 旋转角度
                int rotationDegrees = -15;
                //水印旋转
                g.rotate(Math.toRadians(rotationDegrees));
                float heightAdd = textHight * 1.5f;
                float widthAdd = textWidth * 1.5f;
                // 间距处理
                double[] hAddAndWAdd = handleHAddAndWAdd(heightAdd, widthAdd, rotationDegrees);
                heightAdd = (float) hAddAndWAdd[0];
                widthAdd = (float) hAddAndWAdd[1];
                for (float height = srcImgHeight * -0.5f, line = 1; height < srcImgHeight * 1.5f; height += heightAdd, line++) {
                    for (float width = line % 2 == 0 ? srcImgWidth * -0.5f - widthAdd / 2 : srcImgWidth * -0.5f; width < srcImgWidth * 1.5f; width += widthAdd) {
                        g.drawString(waterMarkContent, width, height);
                    }
                }
                break;
            case CENTER:
                g.drawString(waterMarkContent, (srcImgWidth - textWidth) / 2, (srcImgHeight + textHight) / 2 - 5);
                break;
            case BOTTOM_RIGHT:
                g.drawString(waterMarkContent, srcImgWidth - textWidth - 5, srcImgHeight - 10);
                break;
            case SELF_DEF:
                g.drawString(waterMarkContent, x, y);
                break;
            default:
        }
        g.dispose();
        // 输出图片
        FileOutputStream outImgStream = new FileOutputStream(tarPath);
        ImageIO.write(bufImg, tarPath.substring(tarPath.lastIndexOf(".") + 1), outImgStream);
        outImgStream.flush();
        outImgStream.close();
    }

    /**
     * 给图片添加图片水印
     *
     * @param srcPath        原图片位置
     * @param watermarkPath  图片水印的位置
     * @param tarPath        加水印后图片位置
     * @param watermarkStyle 水印格式
     * @author hejiang
     * @date 2019/4/9 15:40
     */
    public static void setImageWaterMarkForImage(String srcPath, String watermarkPath, String tarPath, WatermarkStyle watermarkStyle) throws Exception {
        setImageWaterMarkForImage(srcPath, watermarkPath, tarPath, watermarkStyle, 0, 0);
    }

    /**
     * 给图片添加图片水印
     *
     * @param srcPath        原图片位置
     * @param watermarkPath  图片水印的位置
     * @param tarPath        加水印后图片位置
     * @param watermarkStyle 水印格式
     * @param x              水印的横坐标
     * @param y              水印的纵坐标
     * @author hejiang
     * @date 2019/4/9 15:40
     */
    public static void setImageWaterMarkForImage(String srcPath, String watermarkPath, String tarPath, WatermarkStyle watermarkStyle, int x, int y) throws Exception {
        java.awt.Image img = ImageIO.read(new File(srcPath));
        java.awt.Image mark = ImageIO.read(new File(watermarkPath));

        int imgWidth = img.getWidth(null);
        int imgHeight = img.getHeight(null);
        BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = bufImg.createGraphics();
        g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
        //设置水印透明度
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.2f));
        int waterWidth = mark.getWidth(null);
        int waterHight = mark.getHeight(null);
        switch (watermarkStyle) {
            case TILED:
                // 旋转角度
                int rotationDegrees = -15;
                //水印旋转
                g.rotate(Math.toRadians(rotationDegrees));
                int heightAdd = (int) (waterHight * 1.5);
                int widthAdd = (int) (waterWidth * 1.5);
                // 间距处理
                double[] hAddAndWAdd = handleHAddAndWAdd(heightAdd, widthAdd, rotationDegrees);
                heightAdd = (int) hAddAndWAdd[0];
                widthAdd = (int) hAddAndWAdd[1];
                for (int height = (int) (imgHeight * -0.5f), line = 1; height < imgHeight * 1.5f; height += heightAdd, line++) {
                    for (int width = line % 2 == 0 ? (int) (imgWidth * -0.5f - widthAdd / 2) : (int) (imgWidth * -0.5f); width < imgWidth * 1.5f; width += widthAdd) {
                        g.drawImage(mark, width, height, null);
                    }
                }
                break;
            case CENTER:
                g.drawImage(mark, (imgWidth - waterWidth) / 2, (imgHeight - waterHight) / 2, null);
                break;
            case BOTTOM_RIGHT:
                g.drawImage(mark, imgWidth - waterWidth - 3, imgHeight - waterHight - 3, null);
                break;
            case SELF_DEF:
                g.drawImage(mark, x, y, null);
                break;
            default:
        }
        g.dispose();
        FileOutputStream outImgStream = new FileOutputStream(tarPath);
        ImageIO.write(bufImg, tarPath.substring(tarPath.lastIndexOf(".") + 1), outImgStream);
        outImgStream.flush();
        outImgStream.close();
    }

    /**
     * 横纵间距处理
     *
     * @param heightAdd       纵间距
     * @param widthAdd        横间距
     * @param rotationDegrees 旋转角度
     * @return double[]       [纵间距, 横间距]
     * @author hejiang
     * @date 2019/4/9 12:02
     */
    private static double[] handleHAddAndWAdd(double heightAdd, double widthAdd, double rotationDegrees) {
        // 确保旋转不影响间距
        heightAdd = (rotationDegrees - 90) % 180 == 0 ? widthAdd : heightAdd / Math.cos(Math.toRadians(rotationDegrees));
        // 确保横纵间距相差不要太大
        heightAdd = widthAdd > heightAdd * 2 ? widthAdd / 2 : heightAdd;
        widthAdd = heightAdd > widthAdd * 2 ? heightAdd / 2 : widthAdd;
        return new double[]{heightAdd, widthAdd};
    }

    public static void main(String[] args) {
        testWaterMark();
        testCopy();
    }


    /**
     * @author He
     * @description 水印测试
     * @date 2019/4/9 22:01
     * @param
     * @return void
     **/
    public static void testWaterMark(){
        try {
            for (WatermarkStyle s : WatermarkStyle.values()) {
                int style = s.getValue();
                setTextWaterMarkForPdf("C:\\Users\\He\\Desktop\\testPdf.pdf", "C:\\Users\\He\\Desktop\\pdf_text_" + style + ".pdf", " 江小白不喝酒", s, (float) (Math.random() * 100 + 100), (float) (Math.random() * 100 + 100),30);
                setImageWaterMarkForPdf("C:\\Users\\He\\Desktop\\testPdf.pdf", "C:\\Users\\He\\Desktop\\testWater.jpg", "C:\\Users\\He\\Desktop\\pdf_img_" + style + ".pdf", s, (float) (Math.random() * 100), (float) (Math.random() * 100 + 100));
                setTextWaterMarkForImage("C:\\Users\\He\\Desktop\\testImage.jpg", "C:\\Users\\He\\Desktop\\img_text_" + style + ".jpg", " 江小白不喝酒 ", s, (int) (Math.random() * 100 + 100), (int) (Math.random() * 100 + 100));
                setImageWaterMarkForImage("C:\\Users\\He\\Desktop\\testImage.jpg", "C:\\Users\\He\\Desktop\\testWater", "C:\\Users\\He\\Desktop\\img_img_" + style + ".jpg", s, (int) (Math.random() * 100 + 100), (int) (Math.random() * 100 + 100));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author He
     * @description 文件、文件夹复制
     * @date 2019/4/9 22:03
     * @param
     * @return void
     **/
    public static void testCopy(){
        try {
            copyFile(new File("C:\\Users\\He\\Desktop\\表情包\\2A-23.jpg"), new File("C:\\Users\\He\\Desktop\\2A-23.jpg"));
            copyDirectiory("C:\\Users\\He\\Desktop\\表情包","C:\\Users\\He\\Desktop\\Everything");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author He
     * @description 复制文件
     * @date 2019/4/9 22:15
     * @param sourceFile 源文件路径
     * @param targetFile 保存路径
     * @return void
     **/
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     * @author He
     * @description 复制文件夹
     * @date 2019/4/9 22:14
     * @param sourceDir 源文件路径
     * @param targetDir 保存路径
     * @return void
     **/
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

}
