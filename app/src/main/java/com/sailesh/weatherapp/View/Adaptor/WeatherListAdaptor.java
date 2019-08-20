package com.sailesh.weatherapp.View.Adaptor;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sailesh.weatherapp.Model.WeatherModel;
import com.sailesh.weatherapp.R;
import com.sailesh.weatherapp.View.Activities.MainActivity;
import com.sailesh.weatherapp.databinding.ItemWeatherlistBinding;

public class WeatherListAdaptor extends RecyclerView.Adapter<WeatherListAdaptor.Viewholder> {
    Context context;
    WeatherModel model;
    Onclick onclick;
    public WeatherListAdaptor(Context context, WeatherModel model) {
        this.context=context;
        this.model=model;

    }

    @Override
    public WeatherListAdaptor.Viewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ItemWeatherlistBinding binding= DataBindingUtil.inflate(layoutInflater, R.layout.item_weatherlist,viewGroup,false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(WeatherListAdaptor.Viewholder viewholder, final int i) {
     viewholder.binding.tem.setText("Temp:" +model.getList().get(i).getMain().getTemp() + " (Celsius)");
     viewholder.binding.date.setText(model.getList().get(i).getDtTxt());
     viewholder.binding.weather.setText(model.getList().get(i).getWeather().get(0).getMain());
        viewholder.binding.desc.setText(model.getList().get(i).getWeather().get(0).getDescription());
        viewholder.binding.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.send(model,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.getList().size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ItemWeatherlistBinding binding;
        public Viewholder(ItemWeatherlistBinding itemView) {
            super(itemView.getRoot());
            this.binding=itemView;
        }
    }


    public interface Onclick{
        void send(WeatherModel model,int postion);

    }

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }
}
