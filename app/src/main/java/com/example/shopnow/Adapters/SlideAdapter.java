package com.example.shopnow.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.shopnow.Models.SliderItems;
import com.example.shopnow.R;

import java.util.ArrayList;
import java.util.List;


public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

    Context context;
    List<SliderItems> items;
    ViewPager2 viewPager2;
    Handler handler = new Handler();

    public SlideAdapter() {
    }

    public SlideAdapter(ViewPager2 viewPager2, List<SliderItems> list) {
        this.viewPager2 = viewPager2;
        this.items = new ArrayList<>();
        this.items.add(list.get(list.size() - 1));
        this.items.addAll(list);
        this.items.add(list.get(0));
        startAutoScroll();
    }


    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d("slideImg", "insdie oncreateviewholder");
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.slider_layout, parent, false);
        return new SlideViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        int realPosition = (position%(items.size()-2));
        SliderItems sliderItems = items.get(realPosition+1);
        Glide.with(context).load(sliderItems.getUrl()).into(holder.imageView);

        holder.imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        stopScrolling();
                        view.setAlpha(0.8f);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        startAutoScroll();
                        view.setAlpha(1.0f);
                        break;
                }
                return true;

            }
        });
        Log.d("slideImg", sliderItems.getUrl() + " url");
    }

    @Override
    public int getItemCount() {
        //Log.d("slideImg",items.size()+" inside getItem");
        return items.size();
    }


    class SlideViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            //Log.d("slideImg"," inside imgeviewslide");
            imageView = itemView.findViewById(R.id.ivSlide);
        }
    }

    public void startAutoScroll() {
        handler.postDelayed(autoScrollRunnable, 2000);
    }

    private Runnable autoScrollRunnable = new Runnable() {
        @Override
        public void run() {
                int nextItem = viewPager2.getCurrentItem() + 1;
                viewPager2.setCurrentItem(nextItem);
                handler.postDelayed(this, 2000); // Auto-scroll every 3 seconds
            }
    };

    public void stopScrolling() {
        handler.removeCallbacksAndMessages(null);
    }

}
