package com.example.HealthStory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HealthStory.API.ParserData;
import com.example.HealthStory.API.Product;
import com.example.HealthStory.API.Search_API;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Sub_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<com.example.HealthStory.API.Product> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public Sub_Activity() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();

        String path = intent.getStringExtra("path");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Product").removeValue();

        ArrayList<String> titlearr = new ArrayList<>();
        ArrayList<String> pricearr = new ArrayList<>();
        ArrayList<String> brandarr = new ArrayList<>();
        ArrayList<String> imagearr = new ArrayList<>();
        ArrayList<String> pdtIdarr = new ArrayList<>();
        ArrayList<Integer> indexArr = new ArrayList<>();

        ParserData psd = new ParserData();
        Product product = new Product();


        String Out = "Product";
        String json = null;

        try {
            json = new Search_API().execute().get();
            titlearr = psd.TitleParserData(json);
            pricearr = psd.PriceParser(json);
            brandarr = psd.BrandParserData(json);
            imagearr = psd.ImageParserData(json);
            pdtIdarr = psd.ProductIdParserData(json);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < titlearr.size(); i++) {  //index 배열 크기만큼 for
            String prO = Out + Integer.toString(i);
            product.setTitle(titlearr.get(i));
            product.setPrice(pricearr.get(i));
            product.setBrand(brandarr.get(i));
            product.setImage(imagearr.get(i));
            product.setProductId(pdtIdarr.get(i));
            mDatabase.child("Product").child(prO).setValue(product);
        }

        databaseReference = database.getReference(path); //db 테이블 연동         String.valueOf(path.getPath())
        Log.e("databaseFath", String.valueOf(path));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {  //db 데이터를 받아오는 역할

                arrayList.clear();                   //기존 배열 초기화
                for(DataSnapshot snapshot : datasnapshot.getChildren()){  // 반복문으로 데이터list 추출
                    com.example.HealthStory.API.Product pd = snapshot.getValue(com.example.HealthStory.API.Product.class);//만들어진 Product 클래스에 보내서 데이터 사용가능하게 만듦.
                    arrayList.add(pd);  // arraylist에 데이터 추가완료 recyclerview에 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {     //에러 발생시

                finish();

            }
        });

        adapter = new com.example.HealthStory.API.CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);                //recyclerview에 어댑터 연결

    }

}
