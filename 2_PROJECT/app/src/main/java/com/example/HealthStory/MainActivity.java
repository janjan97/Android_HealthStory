package com.example.HealthStory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kotlin.TimerActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    private TextView drawerfire;
    private String pathtext; // 파베 경로
    private FirebaseDatabase database;
    private FirebaseAuth mAuth ; //로그아웃
    public String getPathtext() {
        return pathtext;
    }

    public void setPathtext(String pathtext) {
        this.pathtext = pathtext;
    }
    private Button btnyoutub;
    private Button drawer_bag;
    private Button drawer_shop;
    private Button drawer_cal;
    private Button logout;
    private Button stopwatch;
    // 로그인
    private GoogleSignInAccount mGoogleSignInAccount;
    private GoogleSignInClient mGoogleSignInClient;

    //네비바 이미지
    private ImageView drawer_image;
    private TextView drawer_name;
    private TextView drawer_email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String today = Integer.toString(year) +month+day;




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        //로그인 이미지 삽입
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        drawer_image = (ImageView)findViewById(R.id.drawer_image);
        drawer_name = (TextView)findViewById(R.id.drawer_name);
        drawer_email = (TextView)findViewById(R.id.drawer_email);
        if(signInAccount != null){
            String url = signInAccount.getPhotoUrl().toString();
            Glide.with(this).load(url).into(drawer_image);
            drawer_name.setText(signInAccount.getDisplayName());
            drawer_email.setText(signInAccount.getEmail());
        }

        //운동량
        drawerfire = (TextView)findViewById(R.id.drawer_fire);
        //drawerfire.setText(today);
        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String dumb = snapshot.child(today).child("dumb").getValue(String.class);
                if(dumb == null) dumb="0";
                String leg = snapshot.child(today).child("leg").getValue(String.class);
                if(leg == null) leg="0";
                String squt = snapshot.child(today).child("squt").getValue(String.class);
                if(squt == null) squt="0";
                String data = "레그레이즈 : " + leg + "회\n" +
                        "덤벨 : " + dumb + "회\n"+
                        "스쿼트 : " + squt + "회\n";
                if(dumb.equals("0") && leg.equals("0") && squt.equals("0"))
                    data="금일 진행할 운동이 없습니다.";
                drawerfire.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);

        btnyoutub = (Button)findViewById(R.id.btnyoutub);
        btnyoutub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,YouTubeActivity.class);
                startActivity(intent);
            }
        });
        drawer_bag = (Button)findViewById(R.id.drawer_bag);
        drawer_shop = (Button)findViewById(R.id.drawer_shop);
        drawer_cal = (Button)findViewById(R.id.drawer_cal);

        Button btn_open = (Button)findViewById(R.id.todaybtn);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        drawer_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CalenderActivity.class);
                startActivity(intent);
            }
        });

        drawer_bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SelectProduct_Activity.class);
                setPathtext("Select_Product");
                intent.putExtra("path",getPathtext());
                startActivity(intent);
            }
        });
        drawer_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Sub_Activity.class);
                setPathtext("Product");
                intent.putExtra("path",getPathtext());
                ////////////////////////////////////////
                startActivity(intent);
            }
        });


        //로그아웃
        logout = (Button) findViewById(R.id.butt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        stopwatch = (Button)findViewById(R.id.stopwatch);
        stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                startActivity(intent);
            }
        });
     }
    }
