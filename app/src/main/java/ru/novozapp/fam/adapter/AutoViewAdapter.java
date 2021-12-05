package ru.novozapp.fam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.novozapp.fam.activities.databinding.ItemAutoViewMonutBinding;
import ru.novozapp.fam.models.Monument;

public class AutoViewAdapter extends ArrayAdapter<Monument> {
    private Context autoViewContext;
    private List<Monument> allMonutList;
    private List<Monument> filteredMonutList;
    private ItemAutoViewMonutBinding monutBinding;

    public AutoViewAdapter(@NonNull Context context, @NonNull List<Monument> mmonutList) {
        super(context, 0, mmonutList);
        autoViewContext = context;
        allMonutList = new ArrayList<>(mmonutList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return monutFilter;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            // convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_auto_view_monut, parent, false);
            monutBinding = ItemAutoViewMonutBinding.inflate(LayoutInflater.from(parent.getContext()));
            convertView = monutBinding.getRoot();
        }
        Monument monut = getItem(position);
        if(monut != null) {
            monutBinding.imageMonument.setImageResource(autoViewContext.getResources()
                                      .getIdentifier("drawable/" + monut.getImage_monument(), null, autoViewContext.getPackageName()));
            monutBinding.nameMonument.setText(monut.getName_monument());
            monutBinding.adressMonument.setText(monut.getAdresse());
            // Glide.with(convertView).load(place.getImageUrl()).into(placeImage);
        }
/*

        ImageView placeImage = convertView.findViewById(R.id.autocomplete_item_place_image);
        TextView placeLabel = convertView.findViewById(R.id.autocomplete_item_place_label);

        Place place = getItem(position);
        if (place != null) {
            placeLabel.setText(place.getPlace());
            Glide.with(convertView).load(place.getImageUrl()).into(placeImage);
        }
        */

        return convertView;
    }

    private Filter monutFilter = new Filter() {

        /**
         * <p>Invoked in a worker thread to filter the data according to the
         * constraint. Subclasses must implement this method to perform the
         * filtering operation. Results computed by the filtering operation
         * must be returned as a {@link FilterResults} that
         * will then be published in the UI thread through
         * {@link #publishResults(CharSequence,
         * FilterResults)}.</p>
         *
         * <p><strong>Contract:</strong> When the constraint is null, the original
         * data must be restored.</p>
         *
         * @param constraint the constraint used to filter the data
         * @return the results of the filtering operation
         * @see #filter(CharSequence, FilterListener)
         * @see #publishResults(CharSequence, FilterResults)
         * @see FilterResults
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            filteredMonutList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0)    filteredMonutList.addAll(allMonutList);
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Monument monut: allMonutList) {
                    if (monut.getName_monument().toLowerCase().contains(filterPattern) || monut.getAdresse().toLowerCase().contains(filterPattern))
                        filteredMonutList.add(monut);
                }
            }
            results.values = filteredMonutList;
            results.count = filteredMonutList.size();
            return results;
        }

        /**
         * <p>Invoked in the UI thread to publish the filtering results in the
         * user interface. Subclasses must implement this method to display the
         * results computed in {@link #performFiltering}.</p>
         *
         * @param constraint the constraint used to filter the data
         * @param results    the results of the filtering operation
         * @see #filter(CharSequence, FilterListener)
         * @see #performFiltering(CharSequence)
         * @see FilterResults
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Monument) resultValue).getName_monument();
        }
    };

    protected static class MonumentViewHolder {
        private final ItemAutoViewMonutBinding monutBinding;

        public MonumentViewHolder(ItemAutoViewMonutBinding itemView) {
            super();
            this.monutBinding = itemView;
        }
    }
}