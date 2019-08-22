package com.example.opencv;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpencvApplication {

    static double alpha = 2;
    static double beta = 50;

    public static void main(String[] args) {
        SpringApplication.run(OpencvApplication.class, args);

        try {
            String path = "C:\\opencv\\java-opencv-tutorials\\lib\\x64\\opencv_java347.dll";
            System.load(path);
            Mat source = Imgcodecs.imread("C:\\opencv-data//lena.jpg",
                    Imgcodecs.CV_LOAD_IMAGE_COLOR);
            Mat destination = new Mat(source.rows(), source.cols(),
                    source.type());
            source.convertTo(destination, -1, alpha, beta);
            Imgcodecs.imwrite("C:\\opencv-data//lena-brightWithAlpha2Beta50.jpg", destination);
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

}
