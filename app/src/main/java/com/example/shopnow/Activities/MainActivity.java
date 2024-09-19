package com.example.shopnow.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.shopnow.Adapters.CategoryAdapter;
import com.example.shopnow.Adapters.SlideAdapter;
import com.example.shopnow.Models.CategoryItems;
import com.example.shopnow.Models.SliderItems;
import com.example.shopnow.R;
import com.example.shopnow.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database ;

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        database = FirebaseDatabase.getInstance();

        initBanner();
        initCategory();
    }

    private void initCategory() {
        DatabaseReference reference = database.getReference("Category");
        List<CategoryItems> list = new ArrayList<>();
        binding.pbBrands.setVisibility(View.VISIBLE);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        list.add(dataSnapshot.getValue(CategoryItems.class));
                    }
                    CategoryAdapter adapter = new CategoryAdapter(list,getApplicationContext());
                    binding.rvOfficial.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
                    binding.rvOfficial.setAdapter(adapter);
                    binding.pbBrands.setVisibility(View.INVISIBLE);
                    binding.rvOfficial.setNestedScrollingEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initBanner() {
        DatabaseReference reference = database.getReference("Banner");
        List<SliderItems> list = new ArrayList<>();
        binding.pbSlider.setVisibility(View.VISIBLE);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        list.add(dataSnapshot.getValue(SliderItems.class));

                    }
                    binding.pbSlider.setVisibility(View.INVISIBLE);
                    Banner(list);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void Banner(List<SliderItems> item){
        item.addAll(item);
        binding.vp2Slider.setAdapter(new SlideAdapter(binding.vp2Slider,item));
        binding.vp2Slider.setCurrentItem(1,false);

        binding.vp2Slider.setClipToPadding(false);
        binding.vp2Slider.setClipChildren(false);
        binding.vp2Slider.setOffscreenPageLimit(3);
        binding.vp2Slider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        binding.vp2Slider.setPageTransformer(compositePageTransformer);

        circularSlide(item.size());
    }

    public void circularSlide(int size){

        binding.vp2Slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if(position==0){
                    binding.vp2Slider.post(()->binding.vp2Slider.setCurrentItem(size,false));
                }
                else if(position==size+1){
                    binding.vp2Slider.post(()->binding.vp2Slider.setCurrentItem(1,false));
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        new SlideAdapter().stopScrolling();
    }
}