package com.example.opencv.machineLearning.sort;

import com.example.opencv.machineLearning.data.OcrDatabase;
import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.Ml;
import org.opencv.ml.SVM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SvmForOnlineOCR {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JFrame frmOpencv;
    Mat paintMat = new Mat(140, 140, 0, new Scalar(255, 255, 255));
    Mat globalMat = new Mat();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SvmForOnlineOCR window = new SvmForOnlineOCR();
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
    public SvmForOnlineOCR() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        //final Mat paintMat=Highgui.imread("C://opencv249//temp.png");
        final OcrDatabase ocr = new OcrDatabase();
        final SVM svm = SVM.create();
        TermCriteria criteria = new TermCriteria(TermCriteria.MAX_ITER, 100, 1e-6);
        svm.setKernel(svm.LINEAR);
        svm.setType(svm.C_SVC);
        svm.setGamma(0.5);
        svm.setNu(0.5);
        //1-->97.77778%,2-->100%
        svm.setC(2);
        svm.setTermCriteria(criteria);
        boolean r = svm.train(ocr.getTrainingDataMat(), Ml.ROW_SAMPLE, ocr.getTrainingLabelsMat());


        System.out.println("是否有訓練成功=" + r);


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

        JButton btnMLpredict = new JButton("支援向量機預測");
        btnMLpredict.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println("y");
                //Mat t=ocr.handleForML(getGlobalMat());
//				Mat t=getGlobalMat();
//				System.out.println(t.dump());//ok

                float result = svm.predict(ocr.handleForML(getGlobalMat()));
                System.out.println("預測結果=" + result);
                lblMLresult.setText("預測結果=" + result);

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
        //Mat element = Imgproc.getStructuringElement(0,new Size(10,10));
        //Imgproc.dilate(src, src, element);
        //Imgproc.resize(src, dst, size);
        //Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGR2GRAY);
        //System.out.println(dst.channels());
        //尋找輪廓
        Imgproc.Canny(src, dst, 5, 100);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat(src.rows(), src.cols(), CvType.CV_8UC1, new Scalar(0));
        Imgproc.findContours(dst, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        Mat drawing = Mat.zeros(dst.size(), CvType.CV_8UC3);
        // Rect r = Imgproc.boundingRect(contours.get(0));
        //for(int i=0;i<contours.size();i++){
        // for(int i=0;i<1;i++){
        //找到該輪廓最合適的外框
        Rect roiRect = Imgproc.boundingRect(contours.get(0));
        //Imgproc.drawContours(drawing, contours,i,new Scalar(255,0,0,255),3);

        // Core.rectangle(drawing, new Point(r.x, r.y), new Point(r.x + r.width, r.y + r.height), new Scalar(0, 255, 0),3);
        //  }
        //Mat roi=src.adjustROI(r.x, r.y, r.width, r.height);
        //Mat roi=src.submat(r.x, r.y, r.width, r.height);
        // Mat roi=src.submat(r.x,  r.x+r.width,r.y, r.y+r.height);

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
}
