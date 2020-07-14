package com.example.messagingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MessageActivity extends AppCompatActivity {
TextView messageNames;
EditText msg;
Button send;
String strNumber,message,strName;
String[] numbers;

int msgID=0;
int contactID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        messageNames=findViewById(R.id.messageNumber);
        send=findViewById(R.id.send);

        Intent intent = getIntent();
        strNumber = intent.getStringExtra("numbers");
        strName=intent.getStringExtra("names");
        messageNames.setText(strName);
        msg=findViewById(R.id.writeMsg);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
        }else{

        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message=msg.getText().toString();
                numbers=strNumber.split(";");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<numbers.length;i++){
                            try{Thread.sleep(2000);
                                sendMSG(numbers[i],message);

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                }).start();
            }
        });


    }





    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }
        }
    }

    void sendMSG(final String number, final String sms ){

                try{
                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(number,null,sms,null,null);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }



    }
}