package com.example.opencv.machineLearning.mahalanobisPca;

import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

public class MahalanobisForOnlineOCR {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JFrame frmOpencv;
    Mat paintMat = new Mat(140, 140, 0, new Scalar(255, 255, 255));
    Mat globalMat = new Mat();
    Mat numberMat = new Mat(11, 144, CvType.CV_32FC1);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MahalanobisForOnlineOCR window = new MahalanobisForOnlineOCR();
                    window.frmOpencv.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MahalanobisForOnlineOCR() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        Mat source;
        Mat totalNumberDataMat = new Mat(11, 144, CvType.CV_32FC1);
        for (int i = 0; i < 10; i++) {
            source = Imgcodecs.imread("C:\\opencv-data//ocr//number" + i + ".jpg", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

            Mat temp = source.reshape(1, 144);//change image's col & row
            for (int j = 0; j < 144; j++) {
                double[] data = new double[1];
                data = temp.get(j, 0);
                totalNumberDataMat.put(i, j, data);
            }
        }
        setNumberMat(totalNumberDataMat);

        final BufferedImage image = matToBufferedImage(paintMat);

        frmOpencv = new JFrame();
        frmOpencv.setTitle("opencv ML練習");
        frmOpencv.getContentPane().setBackground(Color.LIGHT_GRAY);
        frmOpencv.setBounds(100, 100, 450, 300);
        frmOpencv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmOpencv.getContentPane().setLayout(null);

        final JLabel lblShowXY = new JLabel("");
        lblShowXY.setBounds(10, 220, 124, 23);
        frmOpencv.getContentPane().add(lblShowXY);


        JPanel panel = new JPanel();
        final JLabel lblPaintLabel = new JLabel("");
        lblPaintLabel.setIcon(new ImageIcon(image));
        panel.add(lblPaintLabel);
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent arg0) {
                lblShowXY.setText("X:" + arg0.getX() + ",Y:" + arg0.getY());
                BufferedImage newImage = matToBufferedImage(paint(paintMat, arg0.getX(), arg0.getY()));
                lblPaintLabel.setIcon(new ImageIcon(newImage));
            }
        });
        panel.setBounds(22, 23, 140, 140);
        frmOpencv.getContentPane().add(panel);

        JButton btnNewButton = new JButton("清除");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                paintMat = new Mat(140, 140, 0, new Scalar(255, 255, 255));
                BufferedImage newImage = matToBufferedImage(paintMat);
                lblPaintLabel.setIcon(new ImageIcon(newImage));
            }
        });
        btnNewButton.setBounds(172, 23, 87, 23);
        frmOpencv.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("縮小至12x12");

        btnNewButton_1.setBounds(172, 66, 132, 23);
        frmOpencv.getContentPane().add(btnNewButton_1);

        final JLabel lblSmallImg = new JLabel("");
        lblSmallImg.setBounds(172, 116, 46, 35);
        frmOpencv.getContentPane().add(lblSmallImg);

        final JLabel lblMLresult = new JLabel("");
        lblMLresult.setBounds(159, 223, 273, 40);
        frmOpencv.getContentPane().add(lblMLresult);

        JButton btnMLpredict = new JButton("馬氏距離預測");
        btnMLpredict.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("y");
                //Mat t=ocr.handleForML(getGlobalMat());
//				Mat t=getGlobalMat();
//				System.out.println(t.dump());//ok

                Mat handle = getNumberMat();//10組OCR dataset
                Mat tempSample = getGlobalMat().reshape(1, 144);//第11組影像,也就是要預測的手寫數字
                for (int j = 0; j < 144; j++) {
                    double[] data = new double[1];
                    data = tempSample.get(j, 0);
                    handle.put(10, j, data);//放在第11列
                }
                //System.out.println(handle.dump());
                Mat covar = new Mat(144, 144, CvType.CV_32F);

                Mat mean = new Mat(1, 144, CvType.CV_32F);

                Core.calcCovarMatrix(handle, covar, mean, Core.COVAR_ROWS | Core.COVAR_NORMAL, CvType.CV_32F);

                Mat line0 = handle.row(0);
                Mat line1 = handle.row(1);
                Mat line2 = handle.row(2);
                Mat line3 = handle.row(3);
                Mat line4 = handle.row(4);
                Mat line5 = handle.row(5);
                Mat line6 = handle.row(6);
                Mat line7 = handle.row(7);
                Mat line8 = handle.row(8);
                Mat line9 = handle.row(9);
                Mat line10 = handle.row(10);

                Map findMinium = new HashMap<String, Float>();
                Map findMiniumInv = new HashMap<String, Float>();


                double d = Core.Mahalanobis(line10, line0, covar);
                System.out.println("line10與line0的Mahalanobis距離=" + d);
                findMinium.put("=0", d);
                findMiniumInv.put(d, "=0");
                d = Core.Mahalanobis(line10, line1, covar);
                findMinium.put("=1", d);
                findMiniumInv.put(d, "=1");
                System.out.println("line10與line1的Mahalanobis距離=" + d);//NaN:不是數字
                d = Core.Mahalanobis(line10, line2, covar);
                findMinium.put("=2", d);
                findMiniumInv.put(d, "=2");
                System.out.println("line10與line2的Mahalanobis距離=" + d);//NaN:不是數字
                d = Core.Mahalanobis(line10, line3, covar);
                findMinium.put("=3", d);
                findMiniumInv.put(d, "=3");
                System.out.println("line10與line3的Mahalanobis距離=" + d);//NaN:不是數字
                d = Core.Mahalanobis(line10, line4, covar);
                findMinium.put("=4", d);
                findMiniumInv.put(d, "=4");
                System.out.println("line10與line4的Mahalanobis距離=" + d);//NaN:不是數字
                d = Core.Mahalanobis(line10, line5, covar);
                findMinium.put("=5", d);
                findMiniumInv.put(d, "=5");
                System.out.println("line10與line5的Mahalanobis距離=" + d);//NaN:不是數字
                d = Core.Mahalanobis(line10, line6, covar);
                findMinium.put("=6", d);
                findMiniumInv.put(d, "=6");
                System.out.println("line10與line6的Mahalanobis距離=" + d);//NaN:不是數字
                d = Core.Mahalanobis(line10, line7, covar);
                findMinium.put("=7", d);
                findMiniumInv.put(d, "=7");
                System.out.println("line10與line7的Mahalanobis距離=" + d);//NaN:不是數字
                d = Core.Mahalanobis(line10, line8, covar);
                findMinium.put("=8", d);
                findMiniumInv.put(d, "=8");
                System.out.println("line10與line8的Mahalanobis距離=" + d);//NaN:不是數字
                d = Core.Mahalanobis(line10, line9, covar);
                findMinium.put("=9", d);
                findMiniumInv.put(d, "=9");
                System.out.println("line10與line9的Mahalanobis距離=" + d);//NaN:不是數字
                double min = (double) Collections.min(findMinium.values());

                System.out.println("最小值=" + min);
                System.out.println("組合是:" + findMiniumInv.get(min));


                System.out.println("預測結果:" + findMiniumInv.get(min));
                lblMLresult.setText("預測結果:" + findMiniumInv.get(min));

            }
        });
        btnMLpredict.setBounds(172, 178, 238, 35);
        frmOpencv.getContentPane().add(btnMLpredict);

        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                BufferedImage newImage = matToBufferedImage(resize(paintMat));
                lblSmallImg.setIcon(new ImageIcon(newImage));
            }
        });

    }

    public Mat paint(Mat src, int x, int y) {
        Imgproc.circle(src, new Point(x, y), 2, new Scalar(0, 0, 0), 2);

        return src;

    }

    public Mat resize(Mat src) {
        Mat srcClone = src.clone();


        Mat dst = new Mat();
        Size size = new Size(12, 12);
        //尋找輪廓
        Imgproc.Canny(src, dst, 5, 100);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat(src.rows(), src.cols(), CvType.CV_8UC1, new Scalar(0));
        Imgproc.findContours(dst, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        Mat drawing = Mat.zeros(dst.size(), CvType.CV_8UC3);
        //找到該輪廓最合適的外框
        Rect roiRect = Imgproc.boundingRect(contours.get(0));

        //取的ROI的Mat
        Mat roi = new Mat(src, roiRect);
        Mat result = new Mat();
        Imgproc.resize(roi, result, size);//轉12x12
        setGlobalMat(result);
        return result;
    }


    public BufferedImage matToBufferedImage(Mat matrix) {
        int cols = matrix.cols();
        int rows = matrix.rows();
        int elemSize = (int) matrix.elemSize();
        byte[] data = new byte[cols * rows * elemSize];
        int type;
        matrix.get(0, 0, data);
        switch (matrix.channels()) {
            case 1:
                type = BufferedImage.TYPE_BYTE_GRAY;
                break;
            case 3:
                type = BufferedImage.TYPE_3BYTE_BGR;
                // bgr to rgb
                byte b;
                for (int i = 0; i < data.length; i = i + 3) {
                    b = data[i];
                    data[i] = data[i + 2];
                    data[i + 2] = b;
                }
                break;
            default:
                return null;
        }
        BufferedImage image2 = new BufferedImage(cols, rows, type);
        image2.getRaster().setDataElements(0, 0, cols, rows, data);
        return image2;
    }

    public Mat getGlobalMat() {
        return globalMat;
    }

    public void setGlobalMat(Mat globalMat) {
        this.globalMat = globalMat;
    }

    public Mat getNumberMat() {
        return numberMat;
    }

    public void setNumberMat(Mat numberMat) {
        this.numberMat = numberMat;
    }
}
