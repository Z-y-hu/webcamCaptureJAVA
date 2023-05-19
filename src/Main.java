import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamUtils;
import com.github.sarxos.webcam.util.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// 按两次 Shift 打开“随处搜索”对话框并输入 `show whitespaces`，
// 然后按 Enter 键。现在，您可以在代码中看到空格字符。
public class Main {

    public static void main(String[] args) throws IOException {//添加异常到方法签名
        String filePath = "Paragramer.txt";
//        readTxtFile(filePath);
//        String[] array = readTxtFile(filePath);
        String[] array = new String[4];
        FileInputStream read = new FileInputStream(filePath);
        InputStreamReader inputStreamReader = new InputStreamReader(read);
        BufferedReader buffReader = new BufferedReader(inputStreamReader);
        String strTmp = null;
        for (int i = 0; i < array.length; i++) {

            if((strTmp = buffReader.readLine())!=null){
                array[i] = strTmp;
            }
        }
        buffReader.close();


        int displayType = Integer.parseInt(array[0]);
        String formatName =array[1] ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        System.out.println(formatter.format(date));
        String pathName = array[2] + formatter.format(date);
        String cameraName = array[3];
        //定义一个分辨率长宽数组
        Dimension[] nonStandardResolutions = new Dimension[]{
                WebcamResolution.VGA.getSize(),
                new Dimension(640,480)
        };
        Dimension[] nonStandardResolutions2 = new Dimension[]{
                WebcamResolution.PAL.getSize(),
                WebcamResolution.HD.getSize(),
                new Dimension(2000,1000),
                new Dimension(1000,500)
        };
        Dimension[] nonStandardResolutions3 = new Dimension[]{
                WebcamResolution.FHD.getSize(),
                new Dimension(1920,1080)
        };
        //getDefault
        Webcam webcam = Webcam.getDefault();//只能打开一个相机
        for (Webcam webcam1 : Webcam.getWebcams()){//循环打开所有相机
            if(webcam1 != null){
                System.out.println("网络摄像头的名字是：" + webcam1.getName());
                if(webcam1.getName().toString().equals(cameraName)){
//            //设置相机分辨率两种办法①
//            webcam1.setViewSize(new Dimension(640,480));
                    //设置相机分辨率两种办法②
                    switch (displayType){
                        case 0:
                            webcam1.setCustomViewSizes(nonStandardResolutions);
                            webcam1.setViewSize(WebcamResolution.VGA.getSize());
                        case 1:
                            webcam1.setCustomViewSizes(nonStandardResolutions2);
                            webcam1.setViewSize(WebcamResolution.HD.getSize());
                        case 2:
                            webcam1.setCustomViewSizes(nonStandardResolutions3);
                            webcam1.setViewSize(WebcamResolution.FHD.getSize());
                        default:
                            break;
                    }
                    if(formatName == null){
                        System.out.println("图片的格式未定义!");
                    }else if(formatName.equals("JPG") ){
                        pathName += ".jpg";
                    } else if (formatName.equals("PNG") ) {
                        pathName += ".png";
                    } else if (formatName.equals("BMP")) {
                        pathName += ".bmp";
                    } else{
                        System.out.println("图片保存格式出错，无法识别的格式");
                    }
                    //打开相机
                    webcam1.open();
                    //获取图片
                    BufferedImage image = webcam1.getImage();
                    //保存图片，两种方式，保存的效果没有区别
                    ImageIO.write(image,formatName,new File(pathName));
//            ImageIO.write(image,"PNG",new File("text1"));
//            WebcamUtils.capture(webcam1,"photo", ImageUtils.FORMAT_PNG);
                    //关闭相机
                    webcam1.close();
                }else{
                    System.out.println("现有连接的摄像头中没有你指定的那个，请检查Paragramer.txt第四行摄像头名称");
                }
            }else{
                System.out.println("没有摄像头连接");
            }
        }

    }

}