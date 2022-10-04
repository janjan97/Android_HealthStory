package com.example.HealthStory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.HealthStory.API.ExeAdapter;
import com.example.HealthStory.API.ExeList;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelectActivity extends AppCompatActivity {

    ListView listView;
    ExeAdapter adapter;
    TextView textView;
    String date;
    String datepath;
    Button button_ttt;
    Button butt_test;

    String squat; //스쿼트갯수
    String leg; //레그 갯수
    String arm; // 컬 갯수
    public String getDate() {return date;}

    public void setDate(String date) {this.date = date; }
    private FirebaseDatabase database;
    public String getSquat() {return squat;}
    public void setSquat(String squat) {this.squat = squat;}
    public String getLeg() {return leg;}
    public void setLeg(String leg) {this.leg = leg;}
    public String getArm() {return arm;}
    public void setArm(String arm) {this.arm = arm;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        datepath = intent.getStringExtra("datepath");
        textView = findViewById(R.id.select_date);
        textView.setText("선택한 날짜 : " + date);
        listView = (ListView)findViewById(R.id.select_list);
        adapter = new ExeAdapter(this);


        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.squt),"스쿼트");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.dumb),"덤벨컬");

        listView.setAdapter(adapter);
        database = FirebaseDatabase.getInstance();
        button_ttt = (Button)findViewById(R.id.butt);

        button_ttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String dumb = snapshot.child("count").child("arm").getValue(String.class);
                        if(dumb == null) dumb="0";
                        String leg = snapshot.child("count").child("leg").getValue(String.class);
                        if(leg == null) leg="0";
                        String squat = snapshot.child("count").child("squat").getValue(String.class);
                        if(squat == null) squat="0";

                        databaseReference.child(datepath).child("dumb").setValue(dumb);
                        databaseReference.child(datepath).child("leg").setValue(leg);
                        databaseReference.child(datepath).child("squt").setValue(squat);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent1 = new Intent(SelectActivity.this,MainActivity.class);
                        startActivity(intent1);
                    }
                },1000);

            }
        });
    }
}