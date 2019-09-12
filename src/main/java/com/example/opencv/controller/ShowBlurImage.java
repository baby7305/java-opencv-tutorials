package com.example.opencv.controller;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/showBlurImage")
public class ShowBlurImage {

    @GetMapping(value = "/blur", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage1() {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat source = Imgcodecs.imread("C:\\opencv-data//lena.jpg");
        Mat destination = new Mat(source.rows(), source.cols(), source.type());

        Imgproc.GaussianBlur(source, destination, new Size(9, 9), 0, 0);
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", destination, buffer);
        byte data1[] = buffer.toArray();

        return data1;
    }

    @GetMapping(value = "/blur2/{ksize}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage2(@PathVariable Integer ksize) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("ksize=" + ksize);
        Mat source = Imgcodecs.imread("C:\\opencv-data//lena.jpg");
        Mat destination = new Mat(source.rows(), source.cols(), source.type());

        Imgproc.GaussianBlur(source, destination, new Size(ksize, ksize), 0, 0);
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", destination, buffer);
        byte data1[] = buffer.toArray();

        return data1;
    }

    @GetMapping(value = "/blur3", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage3(String query) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        int ksize = Integer.parseInt(query);
        System.out.println("ksize=" + ksize);
        Mat source = Imgcodecs.imread("C:\\opencv-data//lena.jpg");
        Mat destination = new Mat(source.rows(), source.cols(), source.type());

        Imgproc.GaussianBlur(source, destination, new Size(ksize, ksize), 0, 0);
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", destination, buffer);
        byte data1[] = buffer.toArray();

        return data1;
    }
}
