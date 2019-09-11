package com.example.opencv.other.stereo;

import org.opencv.calib3d.StereoBM;
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class StereoBM1 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat imgLeft = Imgcodecs.imread("C:\\opencv-data//scene_l.bmp", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat imgRight = Imgcodecs.imread("C:\\opencv-data//scene_r.bmp", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);


        //創建我們修正的差距圖片
        Mat imgDisparity16S = new Mat(imgLeft.rows(), imgLeft.cols(), CvType.CV_16S);
        Mat imgDisparity8U = new Mat(imgLeft.rows(), imgLeft.cols(), CvType.CV_8UC1);


        //準備StereoBM建構子所需參數
        int RangeOfDisparity = 16 * 5;   // 視差範圍
        int BlockWindowSize = 21; // block window區塊視窗之大小,須奇數

        // 計算視差影像
        StereoBM sbm = StereoBM.create(RangeOfDisparity, BlockWindowSize);
        sbm.compute(imgLeft, imgRight, imgDisparity16S);

        //檢查極端值
        MinMaxLocResult minMaxVal = new MinMaxLocResult();
        double minVal;
        double maxVal;


        minMaxVal = Core.minMaxLoc(imgDisparity16S);
        minVal = minMaxVal.minVal;
        maxVal = minMaxVal.maxVal;

        System.out.println("Min value=" + minVal + ", Max value=" + maxVal);

        //使用 CV_8UC1格式之影像
        imgDisparity16S.convertTo(imgDisparity8U, CvType.CV_8UC1, 255 / (maxVal - minVal));

        Imgcodecs.imwrite("C:\\opencv-data//SBM_sample.png", imgDisparity16S);
    }
}
