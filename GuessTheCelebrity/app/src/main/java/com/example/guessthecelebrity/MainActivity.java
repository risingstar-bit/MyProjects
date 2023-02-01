package com.example.guessthecelebrity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    ArrayList<String>imageList=new ArrayList<>();
    ArrayList<String>nameList=new ArrayList<>();
    String finalAnsName;
    Button button1;
    Button button2;
    Button button3;
    Button button4;

    public class DownloadData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            URL url;
            HttpURLConnection httpURLConnection=null;
            StringBuilder builder = new StringBuilder();
            try {
                url=new URL(urls[0]);
                httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream in=httpURLConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(in);
                int data=reader.read();
                while (data!=-1){
                    char ch=(char) data;
                    builder.append(ch);
                    data=reader.read();
                }
                return builder.toString();
            }catch (Exception e){
                e.printStackTrace();
                return "Failed";
            }
        }
    }
    public class DownloadImage extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            URL url;
            HttpURLConnection httpURLConnection=null;
            Bitmap myBitmap;
            try {
                url=new URL(urls[0]);
                httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                httpURLConnection.connect();
                InputStream in=httpURLConnection.getInputStream();
                myBitmap=BitmapFactory.decodeStream(in);
                return myBitmap;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
    public void click(View view){
        Button button=view.findViewById(view.getId());
        if(finalAnsName.equals(button.getText().toString()))
            Toast.makeText(this, "Correct guess !", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Wrong guess !", Toast.LENGTH_SHORT).show();
    }
    public void next(View view){
        play();
    }
    public void play(){
        List<Button>buttons=new ArrayList<>(Arrays.asList(button1,button2,button3,button4));
        DownloadData myData=new DownloadData();
        DownloadImage myImage=new DownloadImage();
        String result=null;
        try {
            result=myData.execute("https://www.imdb.com/list/ls052283250/").get();
        }catch (Exception e){
            e.printStackTrace();
        }
        StringBuilder builder=new StringBuilder(result);
        Pattern imagePattern=Pattern.compile("src=\"(.*?)jpg");
        Matcher imageMatcher=imagePattern.matcher(builder);
        while (imageMatcher.find()){
            imageList.add(imageMatcher.group(1)+"jpg");
        }
        Pattern namePattern=Pattern.compile("img alt=\"(.*?)\"");
        Matcher nameMatcher=namePattern.matcher(builder);
        while (nameMatcher.find()){
            nameList.add(nameMatcher.group(1));
            System.out.println(nameMatcher.group(1));
        }
        imageList.remove(imageList.size()-1);
        imageList.remove(imageList.size()-2);
        imageList.remove(imageList.size()-3);
        Random random=new Random();
        int takeRandom=random.nextInt(imageList.size());
        String imgString=imageList.get(takeRandom);
        String ansName=nameList.get(takeRandom);
        finalAnsName=ansName;
        int randButtonAns=random.nextInt(4);
        Button buttonAns;
        buttonAns=buttons.get(randButtonAns);
        buttonAns.setText(finalAnsName);
        buttons.remove(randButtonAns);
        int count=3;
        while (count>0){
            int randButton = random.nextInt(count);
            int randButtonForNames = random.nextInt(imageList.size());
            String celebName = nameList.get(randButtonForNames);
            while (ansName.equals(celebName)){
                randButtonForNames = random.nextInt(imageList.size());
                celebName=nameList.get(randButtonForNames);
            }
            Button  buttonX=buttons.get(randButton);
            buttonX.setText(celebName);
            buttons.remove(randButton);
            count--;
        }
        try {
            Bitmap bitmap;
            bitmap=myImage.execute(imgString).get();
            ImageView imageView=(ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);
        play();
    }
}
