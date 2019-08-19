package com.example.opencv.webcam;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class TakeAPicByWebCam {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String args[]) {
		VideoCapture camera = new VideoCapture();
		camera.open(0);
		if (!camera.isOpened()) {
			System.out.println("Error");
		} else {
			Mat frame = new Mat();
			try {
				System.out.println("使用webcam拍照");
				camera.read(frame);
				Imgcodecs.imwrite("F:\\opencv-data\\cameraTakeApicture.jpg", frame);
				System.out.println("拍照完成!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		camera.release();
	}
}