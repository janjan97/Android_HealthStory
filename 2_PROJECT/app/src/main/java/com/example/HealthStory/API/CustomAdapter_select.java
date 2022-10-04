package com.example.HealthStory.API;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.HealthStory.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomAdapter_select extends RecyclerView.Adapter<CustomAdapter_select.CustomViewHolder> {

    private ArrayList<Product> arrayList;
    private Context context;

    public CustomAdapter_select(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    Product prd = new Product();
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_select_product, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImage())
                .into(holder.iv_profile);   //이미지를 받아와서 넣어짐

        holder.tv_title.setText(arrayList.get(position).getTitle()); // id를 뿌려줌
        holder.tv_price.setText(String.valueOf(arrayList.get(position).getPrice())); // pw를 뿌려줌
        holder.tv_brand.setText(arrayList.get(position).getBrand()); // userName을 뿌려줌
        holder.tv_productId.setText(arrayList.get(position).getProductId());

        holder.butt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayList.size());
                String curproductId = holder.tv_productId.getText().toString();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("Select_Product").child(curproductId).removeValue();

            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_title;
        TextView tv_price;
        TextView tv_brand;
        Button butt_delete;
        TextView tv_productId;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_image);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_price = itemView.findViewById(R.id.tv_price);
            this.tv_brand = itemView.findViewById(R.id.tv_brand);
            this.tv_productId = itemView.findViewById(R.id.tv_productId);
            this.butt_delete = itemView.findViewById(R.id.butt_delete);
        }

    }
}
