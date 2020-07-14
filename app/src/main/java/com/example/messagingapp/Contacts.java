package com.example.messagingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import com.example.messagingapp.Adapters.ContactsAdapter;
import com.example.messagingapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Contacts extends AppCompatActivity {
    RecyclerView ContactsRV;
    FloatingActionButton done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ContactsRV=findViewById(R.id.contactsRV);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);
        }else{
            getContacts();
        }

done=findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numbers=ContactsAdapter.selectedNumber;
                String names=ContactsAdapter.selectedNames;
                openMessageSendingActvity(numbers,names);

            }
        });
    }

    private void openMessageSendingActvity(String numbers,String Names) {
        Intent intent=new Intent(this,MessageActivity.class);
        intent.putExtra("numbers",numbers);
        intent.putExtra("names",Names);
        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getContacts();
            }
        }
    }

    private void getContacts() {
        Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        String names[]=new String[cursor.getCount()];
        String numbers[]=new String[cursor.getCount()];
        int value=0;
        while (cursor.moveToNext()){
            names[value]=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            numbers[value]=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            value++;
        }
        ContactsRV.setLayoutManager(new LinearLayoutManager(this));
        ContactsRV.setAdapter(new ContactsAdapter(names,numbers));
    }
}