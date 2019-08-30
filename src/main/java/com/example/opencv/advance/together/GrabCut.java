package com.example.opencv.advance.together;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class GrabCut {
    public static void main(String[] args) {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat source = Imgcodecs.imread("C:\\opencv-data//lena.jpg",
                    Imgcodecs.CV_LOAD_IMAGE_COLOR);
            bgSubtracting(source);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private static void bgSubtracting(Mat img) {
        Mat firstMask = new Mat();
        Mat bgModel = new Mat();//grabCut內部運算使用
        Mat fgModel = new Mat();//grabCut內部運算使用
        Mat mask;
        Mat source = new Mat(1, 1, CvType.CV_8U, new Scalar(3.0));
        //定義矩形
        Rect rect = new Rect(0, 0, 150, 200);

        ///執行grabCut處理(分割)行
        Imgproc.grabCut(img, firstMask, rect, bgModel, fgModel, 1, 0);


        //得到前景
        Core.compare(firstMask, source, firstMask, Core.CMP_EQ);
        //生成輸出圖像
        Mat foreground = new Mat(img.size(), CvType.CV_8UC3, new Scalar(255,
                255, 255));
        //複製前景數據,符合挖出的區域
        img.copyTo(foreground, firstMask);

        mask = new Mat(foreground.size(), CvType.CV_8UC1, new Scalar(255, 255, 255));

        Imgproc.cvtColor(foreground, mask, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(mask, mask, 254, 255, Imgproc.THRESH_BINARY_INV);
        //挖出後的替代color
        Mat vals = new Mat(1, 1, CvType.CV_8UC3, new Scalar(255.0));

        img.setTo(vals, mask);
        Imgcodecs.imwrite("C:\\opencv-data//grabcut-lena.jpg", img);
    }
}
