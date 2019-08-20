package com.sailesh.weatherapp.View.Activities;

import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.sailesh.weatherapp.Model.WeatherModel;
import com.sailesh.weatherapp.R;
import com.sailesh.weatherapp.databinding.ActivityDetailBinding;

import java.util.Calendar;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    WeatherModel weatherModel;
    int postion =1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_detail);
        weatherModel= (WeatherModel) getIntent().getExtras().get("data");
        postion=getIntent().getExtras().getInt("postion",1);

        setUI(binding);
    }

    private void setUI(ActivityDetailBinding binding) {
        binding.date.setText("Date: "+weatherModel.getList().get(postion).getDtTxt());
        binding.desc.setText("Description:"+weatherModel.getList().get(postion).getWeather().get(0).getDescription());
        binding.main.setText("Main: "+weatherModel.getList().get(postion).getWeather().get(0).getMain());
        binding.hum.setText("humidity: "+weatherModel.getList().get(postion).getMain().getHumidity());
        binding.tem.setText("Tempature:"+weatherModel.getList().get(postion).getMain().getTemp() +" (Celsius)");
        binding.sunrise.setText("Sunrise:"+getDate(weatherModel.getCity().getSunrise()));
        binding.sunset.setText("Sunset:"+getDate(weatherModel.getCity().getSunset()));
        binding.place.setText(weatherModel.getCity().getName());

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("HH:mm", cal).toString();
        return date;
    }
}
