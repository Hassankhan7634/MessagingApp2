package com.example.messagingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messagingapp.R;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsAdapterViewHolder> {
    String names[];
    String numbers[];
    public ContactsAdapter(String[] names,String[] numbers){
        this.names=names;
        this.numbers=numbers;

    }
    @NonNull
    @Override
    public ContactsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_viewholder,null,false);
        return new ContactsAdapter.ContactsAdapterViewHolder(view);
    }
    public static String selectedNumber="";
    public static String selectedNames="";
    int pos;
    @Override
    public void onBindViewHolder(@NonNull ContactsAdapterViewHolder holder, final int position) {
        holder.contactName.setText(names[position]);
        holder.contactNumber.setText(numbers[position]);
        pos=position;
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    selectedNumber=selectedNumber+numbers[position]+";";
                    selectedNames=selectedNames+names[position]+";";
                    System.out.println(selectedNumber);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class ContactsAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView contactName,contactNumber;
        CheckBox checkbox;
        public ContactsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName=itemView.findViewById(R.id.contact_name);
            contactNumber=itemView.findViewById(R.id.contact_num);
            checkbox=itemView.findViewById(R.id.checkbox);
        }
    }
}
