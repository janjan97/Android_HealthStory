package com.example.HealthStory.API;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.HealthStory.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<com.example.HealthStory.API.Product> arrayList;  // 유저정보를 담음.
    private Context context;
    Product pdt = new Product();

    public CustomAdapter(ArrayList<com.example.HealthStory.API.Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  //연결된 후 최초생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {  // 실제 아이템 매칭
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImage())
                .into(holder.iv_profile);   //이미지를 받아와서 넣어짐

        holder.tv_id.setText(arrayList.get(position).getTitle()); // id를 뿌려줌
        holder.tv_pw.setText(String.valueOf(arrayList.get(position).getPrice())); // pw를 뿌려줌
        holder.tv_userName.setText(arrayList.get(position).getBrand()); // userName을 뿌려줌
        holder.tv_productId.setText(arrayList.get(position).getProductId());
        holder.butt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curImage = arrayList.get(position).getImage().toString();
                String curtitle = holder.tv_id.getText().toString();
                String curPrice = holder.tv_pw.getText().toString();
                String curBrand = holder.tv_userName.getText().toString();
                String curProductId = holder.tv_productId.getText().toString();
                pdt.setImage(curImage);
                pdt.setTitle(curtitle);
                pdt.setBrand(curBrand);
                pdt.setPrice(curPrice);
                pdt.setProductId(curProductId);
                Toast.makeText(v.getContext(),"상품을 담았습니다.",Toast.LENGTH_SHORT).show();
                //DB에 삽입하기 위해 사용

                long now = System.currentTimeMillis();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("Select_Product").child(curProductId).setValue(pdt);
            }
        });
    }

    @Override
    public int getItemCount() {
        //삼항연산자
        return (arrayList !=null ? arrayList.size() : 0);  //arraylist가 null이 아니면 size를 반환/ 아니면 0 반환
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_profile;
        TextView tv_id;
        TextView tv_pw;
        TextView tv_userName;
        TextView tv_productId;
        Button butt_select;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_image);
            this.tv_id = itemView.findViewById(R.id.tv_title);
            this.tv_pw = itemView.findViewById(R.id.tv_price);
            this.tv_userName = itemView.findViewById(R.id.tv_brand);
            this.tv_productId = itemView.findViewById(R.id.tv_productId);
            this.butt_select = itemView.findViewById(R.id.butt_select);
        }
    }
}
