package com.example.HealthStory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HealthStory.API.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectProduct_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product);

        Intent select_intent = getIntent();

        recyclerView = findViewById(R.id.recycler_select);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();

        String path = select_intent.getStringExtra("path");
        //path="Select_Product"
        databaseReference = database.getReference(path);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                arrayList.clear();                   //기존 배열 초기화
                for(DataSnapshot snapshot : datasnapshot.getChildren()){  // 반복문으로 데이터list 추출
                    Product pd = snapshot.getValue(Product.class);//만들어진 Product 클래스에 보내서 데이터 사용가능하게 만듦.
                    arrayList.add(pd);  // arraylist에 데이터 추가완료 recyclerview에 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("select_list", error.toException());
            }
        });

        adapter = new  com.example.HealthStory.API.CustomAdapter_select(arrayList, this);
        recyclerView.setAdapter(adapter);
    }
}
