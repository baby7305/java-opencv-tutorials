package com.example.opencv.detection.convex;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ConvexHull {
    public static void main(String[] args) {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat source = Imgcodecs.imread("C:\\opencv-data//palm_p.jpg",
                    Imgcodecs.CV_LOAD_IMAGE_COLOR);
            Mat srcClone = source.clone();
            Mat target = new Mat(source.size(), CvType.CV_8U);
            // 轉成灰階圖
            Imgproc.cvtColor(source, target, Imgproc.COLOR_RGB2GRAY);
            ///進行模糊處理以消除雜點除
            Imgproc.GaussianBlur(target, target, new Size(5, 5), 0, 0);

            Mat threshold_output = new Mat(source.rows(), source.cols(), source.type());
            //進行二值化處理
            Imgproc.threshold(target, threshold_output, 40, 255, Imgproc.THRESH_BINARY);

            Mat hierarchy = new Mat(target.rows(), target.cols(), CvType.CV_8UC1, new Scalar(0));
            List<MatOfPoint> contours = new ArrayList<MatOfPoint>();


            //尋找外圍輪廓
            Imgproc.findContours(threshold_output, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

            for (int i = 0; i < contours.size(); i++) {
                MatOfInt hull = new MatOfInt();
                MatOfPoint tempContour = contours.get(i);
                //針對每一個外圍輪廓進行凸包計算
                Imgproc.convexHull(tempContour, hull, false);


                //繪出該外圍輪廓的凸多邊形
                int index = (int) hull.get(((int) hull.size().height) - 1, 0)[0];
                Point pt, pt0 = new Point(tempContour.get(index, 0)[0], tempContour.get(index, 0)[1]);
                for (int j = 0; j < hull.size().height - 1; j++) {
                    index = (int) hull.get(j, 0)[0];
                    pt = new Point(tempContour.get(index, 0)[0], tempContour.get(index, 0)[1]);
                    Imgproc.line(srcClone, pt0, pt, new Scalar(255, 0, 100), 3);
                    pt0 = pt;
                }

            }

            Imgcodecs.imwrite("C:\\opencv-data//ConvexHull3a.jpg", srcClone);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
