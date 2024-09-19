package com.example.shopnow.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopnow.Models.CategoryItems;
import com.example.shopnow.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewholder> {

    List<CategoryItems> list;
    Context context ;

    public CategoryAdapter(List<CategoryItems> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_holder,parent,false);
        return new CategoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewholder holder, int position) {
        CategoryItems categoryItems = list.get(position);
        Glide.with(context)
                .load(categoryItems.getPicUrl()).into(holder.ivCategory);
        holder.tvTitle.setText(categoryItems.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CategoryViewholder extends RecyclerView.ViewHolder{
        ImageView ivCategory;
        TextView tvTitle;
        public CategoryViewholder(@NonNull View itemView) {
            super(itemView);
            ivCategory = itemView.findViewById(R.id.ivCategory);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
