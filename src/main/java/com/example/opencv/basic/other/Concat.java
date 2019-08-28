package com.example.opencv.basic.other;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.ArrayList;
import java.util.List;

public class Concat {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat im = Imgcodecs.imread("C:\\opencv-data//ncku.jpg");
        List<Mat> matList = new ArrayList<Mat>();
        Mat hdst = new Mat();
        Mat vdst = new Mat();
        //3次
        matList.add(im);
        matList.add(im);
        matList.add(im);
        Core.hconcat(matList, hdst);
        Core.vconcat(matList, vdst);

        Imgcodecs.imwrite("C:\\opencv-data//hconcat_ncku.jpg", hdst);
        Imgcodecs.imwrite("C:\\opencv-data//vconcat_ncku.jpg", vdst);
    }
}
