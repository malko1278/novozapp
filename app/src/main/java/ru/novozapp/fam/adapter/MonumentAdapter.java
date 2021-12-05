package ru.novozapp.fam.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ru.novozapp.fam.activities.R;
import ru.novozapp.fam.activities.databinding.ItemMonumentBinding;
import ru.novozapp.fam.models.Monument;

public class MonumentAdapter extends RecyclerView.Adapter<MonumentAdapter.MonumentViewHolder> {
    private final Context tContext;
    private final ArrayList<Monument> dataMonument;
    private final MyClickListener monutClickListener;
    public static List<Boolean> listCharge;
    public static List<Boolean> descriptVisible;

    /**
     *
     * @param context
     * @param dataList
     * @param clickListener
     */
    public MonumentAdapter(@NonNull Context context, ArrayList<Monument> dataList, MyClickListener clickListener) {
        super();
        tContext = context;
        dataMonument = dataList;
        monutClickListener = clickListener;
        listCharge = new ArrayList<>(0);
        descriptVisible = new ArrayList<>(0);
        initListBoolean();
    }

    @NonNull
    @Override
    public MonumentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new MonumentViewHolder(ItemMonumentBinding.inflate(LayoutInflater.from(viewGroup.getContext())));
    }

    private void initListBoolean() {
        for(int i = 0; i < dataMonument.size(); i++) {
            listCharge.add(false);
            descriptVisible.add(false);
        }
    }

    /**
     *
     * @param posit
     * @param val
     */
    public static void upDatePositionBool(int posit, boolean val) {
        listCharge.set(posit, val);
    }

    /**
     *
     * @param posit
     * @param val
     */
    public static void upDateDescripVisible(int posit, boolean val) {
        descriptVisible.set(posit, val);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MonumentViewHolder holder, int position) {
        Monument element = dataMonument.get(position);
        holder.updateWithMonument(element, monutClickListener, tContext, position);
    }

    @Override
    public int getItemCount() {
        return dataMonument.size();
    }

    /**
     *
     */
    protected static class MonumentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemMonumentBinding monumentBinding;
        // 2 - Declare a Weak Reference to our Callback
        private WeakReference<MyClickListener> callbackWeakRef;

        /**
         * Contructeur permettant de récupérer les identifiants des widgets
         * @param itemView
         */
        public MonumentViewHolder(ItemMonumentBinding itemView) {
            super(itemView.getRoot());
            monumentBinding = itemView;
        }

        /**
         * @param monut
         * @param callback
         * @param context
         * @param post
         */
        @SuppressLint("CheckResult")
        public void updateWithMonument(Monument monut, MyClickListener callback, Context context, int post) {
            if(monut != null) {
                monumentBinding.includeHeadMonut.monumentNameImage.setText(monut.getName_monument());
                monumentBinding.includeHeadMonut.principalImage.setImageResource(context.getResources()
                               .getIdentifier("drawable/" + monut.getImage_monument(), null, context.getPackageName()));
                monumentBinding.includeBodyMonut.principalImage.setImageResource(context.getResources()
                               .getIdentifier("drawable/" + monut.getImage_monument(), null, context.getPackageName()));
                monumentBinding.includeBodyMonut.descriptionMonument.setText(monut.getDescription());
                /*
                GlideApp.with(context)
                        .load(url)
                        .placeholder(R.drawable.placeholder)
                        .fitCenter()
                        .centerCrop()
                        .error(R.drawable.imagenotfound)
                        .into(monumentBinding.includeHeadMonut.principalImage);

                GlideApp.with(context)
                        .load(context.getResources()
                                     .getIdentifier("drawable/" + monut.getImage_monument(),
                                                    null, context.getPackageName()))
                        .fitCenter()
                        .centerCrop()
                        // .override(monumentBinding.includeHeadMonut.principalImage.getWidth(),450)
                        .into(monumentBinding.includeHeadMonut.principalImage);
                GlideApp.with(context)
                        .load(context.getResources()
                                .getIdentifier("drawable/" + monut.getImage_monument(),
                                        null, context.getPackageName()))
                        .fitCenter()
                        .centerCrop()
                        // .override(monumentBinding.includeBodyMonut.principalImage.getWidth(),150)
                        .into(monumentBinding.includeBodyMonut.principalImage);
                */
            }
            // Implement Listener on different view
            // monumentBinding.includeTitleMonut.btnLikes.setOnClickListener(this);
            monumentBinding.includeTitleMonut.btnShowHide.setOnClickListener(this);
            monumentBinding.includeBodyMonut.showAllDescription.setOnClickListener(this);
            // Create a new weak Reference to our Listener
            callbackWeakRef = new WeakReference<>(callback);
            upDateRecyclerView();
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            MyClickListener callback = callbackWeakRef.get();
            if (callback != null)    callback.itemClickedMonument(v, getAdapterPosition(), monumentBinding, listCharge, descriptVisible);
            upDateRecyclerView();
            upDateDescription();
        }

        public void upDateRecyclerView() {
            if(!listCharge.get(getAdapterPosition())) {
                monumentBinding.includeBodyMonut.cellContent.setVisibility(View.GONE);
                monumentBinding.includeHeadMonut.cellHeadMonut.setVisibility(View.VISIBLE);
                monumentBinding.includeTitleMonut.btnShowHide.setImageResource(R.drawable.keyboard_arrow_down);
            } else {
                monumentBinding.includeBodyMonut.cellContent.setVisibility(View.VISIBLE);
                monumentBinding.includeHeadMonut.cellHeadMonut.setVisibility(View.GONE);
                monumentBinding.includeTitleMonut.btnShowHide.setImageResource(R.drawable.keyboard_arrow_up);
            }
        }

        public void upDateDescription() {
            if(!descriptVisible.get(getAdapterPosition())) {
                monumentBinding.includeBodyMonut.descriptionMonument.setMaxLines(2);
                monumentBinding.includeBodyMonut.showAllDescription.setImageResource(R.drawable.keyboard_arrow_down);
            } else {
                monumentBinding.includeBodyMonut.descriptionMonument.setMaxLines(Integer.MAX_VALUE);
                monumentBinding.includeBodyMonut.showAllDescription.setImageResource(R.drawable.keyboard_arrow_up);
            }
        }
    }

    public interface MyClickListener {
        void itemClickedMonument(View v, int position, ItemMonumentBinding itemMonut, List<Boolean> chargeFinished, List<Boolean> descripAhhich);
    }
}