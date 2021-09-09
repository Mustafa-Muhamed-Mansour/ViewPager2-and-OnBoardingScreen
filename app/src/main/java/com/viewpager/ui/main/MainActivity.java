package com.viewpager.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.viewpager.R;
import com.viewpager.adapter.onboarding.OnBoardingAapter;
import com.viewpager.databinding.ActivityMainBinding;
import com.viewpager.model.OnBoardingModel;
import com.viewpager.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private ActivityMainBinding binding;
    private List<OnBoardingModel> onBoardingModels;
    private OnBoardingAapter onBoardingAapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        onBoardingModels = new ArrayList<>();
        onBoardingModels.add(new OnBoardingModel(R.drawable.home, "Home", "This is Home, Welcome Sir"));
        onBoardingModels.add(new OnBoardingModel(R.drawable.favourite, "Favourite", "This is favourite from a home"));
        onBoardingModels.add(new OnBoardingModel(R.drawable.location, "Passengers", "This is Location home sir"));

        onBoardingAapter = new OnBoardingAapter(onBoardingModels);
        binding.viewPagerOnboarding.setAdapter(onBoardingAapter);

        ImageView[] indicators = new ImageView[onBoardingAapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);
        for (int i = 0; i < indicators.length; i++)
        {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            binding.linearOnboardingIndicator.addView(indicators[i]);
        }

        setCurrentOnBoardingIndicator(0);

        binding.viewPagerOnboarding.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback()
        {
            @Override
            public void onPageSelected(int position)
            {
                super.onPageSelected(position);
                setCurrentOnBoardingIndicator(position);
            }
        });

        binding.btnActionOnboarding.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (binding.viewPagerOnboarding.getCurrentItem() + 1 < onBoardingAapter.getItemCount())
                {
                    binding.viewPagerOnboarding.setCurrentItem(binding.viewPagerOnboarding.getCurrentItem() + 1);
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//                    getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, new TestFragment()).commit();
//                    binding.btnActionOnboarding.setVisibility(View.GONE);
//                    binding.viewPagerOnboarding.setVisibility(View.GONE);
//                    binding.linearOnboardingIndicator.setVisibility(View.GONE);
                }
            }
        });
    }


    private void setCurrentOnBoardingIndicator(int index)
    {
        int childCount = binding.linearOnboardingIndicator.getChildCount();
        for (int i = 0; i < childCount; i++)
        {
            ImageView imageView = (ImageView) binding.linearOnboardingIndicator.getChildAt(i);
               if (i == index)
               {
                   imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active));
               }
               else
               {
                   imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
               }
        }
        if (index == onBoardingAapter.getItemCount() - 1)
        {
            binding.btnActionOnboarding.setText("Start");
        }
        else
        {
            binding.btnActionOnboarding.setText("Next");
        }
    }
}