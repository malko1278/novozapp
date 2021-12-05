package ru.novozapp.fam.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.novozapp.fam.activities.databinding.ItemWeatherBinding;
import ru.novozapp.fam.utils.UtilView;
import ru.novozapp.fam.weather.cmn.ForecastDay;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private final Context mContext;
    private final List<ForecastDay> mAdapterData;

    /**
     *
     * @param adapterData
     * @param context
     */
    // public WeatherAdapter(LayoutInflater layoutInflater, List<ForecastDay> adapterData, Context context) {
    public WeatherAdapter(List<ForecastDay> adapterData, Context context) {
        this.mAdapterData = adapterData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemWeatherBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForecastDay currentForecastDay = mAdapterData.get(position);
        if(position % 2 != 0)    holder.itemForcast.itemForecastContainer.setGravity(Gravity.RIGHT);
        holder.itemForcast.itemForecastDate.setText(currentForecastDay.getDate());
        holder.itemForcast.itemWeatherWeather.setText(currentForecastDay.getWeatherDesc());
        holder.itemForcast.itemForecastTemp.setText(currentForecastDay.getTemperature());
        holder.itemForcast.itemForecastIcon.setBackgroundResource(UtilView.getDrawableIdByString(mContext, currentForecastDay.getIconUrl()));
        // mImageLoader.displayImage(currentForecastDay.getIconUrl(), holder.icon, mDisplayImageOptions);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mContext);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemWeatherBinding itemForcast;

        public ViewHolder(@NonNull ItemWeatherBinding itemView) {
            super(itemView.getRoot());
            itemForcast = itemView;
        }
    }
}