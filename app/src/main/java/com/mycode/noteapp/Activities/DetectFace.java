package com.mycode.noteapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.mycode.noteapp.R;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class DetectFace extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    JavaCameraView javaCameraView;
    File cascFile;

    CascadeClassifier faceDetector;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            Log.d("tagcn", "Image not found" + e);
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            Log.d("tagcn", "Exception while reading the Image " + ioe);
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }

    private Mat mRgba,mGrey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detect_face);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String Host = "10.0.2.2";
        String port = "1503";
        String[] argument = new String[]{Host, port};
        String msg = "";
        ClientTCP obj= new ClientTCP();

        try {
            msg = obj.connect(argument);
            Log.d("tagcn", msg);
        } catch (Exception e) {
            Log.d("tagcn", e.toString());
        }

        try {
            obj.dout.writeUTF("apiAnh");
        } catch (IOException e) {
            Log.d("tagcn", e.toString());
        }
        javaCameraView = (JavaCameraView)findViewById(R.id.javaCamView);
        if(!OpenCVLoader.initDebug()){
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0,this,baseCallback);
        }
        else{
            try {
                baseCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        javaCameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mGrey = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
        mGrey.release();
    }
    int temp = 0;
    int count = 1;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        mGrey = inputFrame.gray();

        //detect face

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(mRgba,faceDetections);
        Rect rectCrop=null;
        for(Rect rect: faceDetections.toArray()) {
            if (count>10) {
                Log.d("tagcn", "dang cho ket qua");
                Imgproc.rectangle(mRgba, new Point(rect.x, rect.y),
                        new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(255, 0, 0));
                try {
                    String s = ClientTCP.din.readUTF();
                    Log.d("tagcn", "s: "+s);
                    if (s.equals("2")){
                        Log.d("tagcn", "2");
                        //ClientTCP.
                        //Toast.makeText(DetectFace.this, "Nhan dang thanh cong", Toast.LENGTH_SHORT).show();
                        //ClientTCP.disconnect();
                        startActivity(new Intent(DetectFace.this, MainActivity.class));
                    }
                    else {
                        Log.d("tagcn", "khong phai 2");
                        continue;
                    }
                } catch (IOException e) {
                    Log.d("tagcn", e.toString());
                }
            }
            else{
                Imgproc.rectangle(mRgba, new Point(rect.x, rect.y),
                        new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(255, 0, 0));
                rectCrop = new Rect(new Point(rect.x, rect.y),
                        new Point(rect.x + rect.width, rect.y + rect.height));
                Mat image_roi = new Mat(mRgba, rectCrop);
//            Mat mIntermediateMat = new Mat();
//            Imgproc.cvtColor(image_roi, mIntermediateMat, Imgproc.COLOR_RGBA2BGR, 3);
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String filename = "barry.png";
                File file = new File(path, filename);
                filename = file.toString();
                Imgcodecs.imwrite(filename, image_roi);
                String b6ase4_String = encoder(filename);
                String a = image_roi.size().toString();
                try {
                    ClientTCP.dout.writeUTF(b6ase4_String);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("tag", "FramSize : " + a);
                Log.d("tag", "B6ase4 String : " + b6ase4_String);
                Log.d("tagcn", "Lan thu : " + count);
                count=count+1;
            }
        }
        if(rectCrop!= null) {

        }
        return mRgba;
    }

    private BaseLoaderCallback baseCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) throws IOException {
            switch (status)
            {
                case LoaderCallbackInterface.SUCCESS:
                {
                    InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt2);
                    File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                    cascFile = new File(cascadeDir,"haarcascade_frontalface_alt2.xml");

                    FileOutputStream fos = new FileOutputStream(cascFile);

                    byte[] buffer = new byte[4096];
                    int byteReads;

                    while((byteReads = is.read(buffer))!=-1){
                        fos.write(buffer,0,byteReads);
                    }

                    is.close();
                    fos.close();

                    faceDetector = new CascadeClassifier(cascFile.getAbsolutePath());

                    if(faceDetector.empty()){
                        faceDetector = null;
                    }
                    else cascadeDir.delete();
                    javaCameraView.enableView();
                }
                break;
                default:{
                    super.onManagerConnected(status);
                }
                break;

            }
        }
    };
}
