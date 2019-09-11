package com.example.opencv.controller;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/showImage")
public class ShowImage {

    @GetMapping(value = "/normal", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage() throws IOException {
        File file = new File("C:\\opencv-data//lena.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    @GetMapping(value = "/opencv", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage1() throws IOException {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //System.load(arg0);

        Mat source = Imgcodecs.imread("C:\\opencv-data//lena.jpg");
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", source, buffer);//對影像編碼並且放置於緩衝記憶體
        byte data1[] = buffer.toArray();//轉陣列
        return data1;
    }
}
