package ru.novozapp.fam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ru.novozapp.fam.activities.databinding.ItemMonumentBinding;
import ru.novozapp.fam.adapter.MonumentAdapter;

public class ItemMonumentActivity extends AppCompatActivity implements View.OnClickListener {

    private ItemMonumentBinding itemMonutBinding;
    private boolean action = false, maxAff = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemMonutBinding = ItemMonumentBinding.inflate(getLayoutInflater());
        setContentView(itemMonutBinding.getRoot());

        Intent intMonument= getIntent();
        Bundle bundle = intMonument.getExtras();

        if(bundle != null) {
            itemMonutBinding.includeHeadMonut.monumentNameImage.setText((String) bundle.get("NAME"));
            itemMonutBinding.includeHeadMonut.principalImage.setImageResource(this.getResources()
                    .getIdentifier("drawable/" + (String) bundle.get("IMAGE"), null, this.getPackageName()));
            itemMonutBinding.includeBodyMonut.principalImage.setImageResource(this.getResources()
                    .getIdentifier("drawable/" + (String) bundle.get("IMAGE"), null, this.getPackageName()));
            itemMonutBinding.includeBodyMonut.descriptionMonument.setText((String) bundle.get("DESCRIPTION"));
        }

        initMonument();
        itemMonutBinding.includeTitleMonut.btnShowHide.setOnClickListener(this);
        itemMonutBinding.includeBodyMonut.showAllDescription.setOnClickListener(this);
    }

    public void initMonument() {
        if(!action) {
            itemMonutBinding.includeBodyMonut.cellContent.setVisibility(View.GONE);
            itemMonutBinding.includeHeadMonut.cellHeadMonut.setVisibility(View.VISIBLE);
            itemMonutBinding.includeTitleMonut.btnShowHide.setImageResource(R.drawable.keyboard_arrow_down);
        } else {
            itemMonutBinding.includeBodyMonut.cellContent.setVisibility(View.VISIBLE);
            itemMonutBinding.includeHeadMonut.cellHeadMonut.setVisibility(View.GONE);
            itemMonutBinding.includeTitleMonut.btnShowHide.setImageResource(R.drawable.keyboard_arrow_up);
        }
    }

    public void initDescription() {
        if(!maxAff) {
            itemMonutBinding.includeBodyMonut.descriptionMonument.setMaxLines(2);
            itemMonutBinding.includeBodyMonut.showAllDescription.setImageResource(R.drawable.keyboard_arrow_down);
        } else {
            itemMonutBinding.includeBodyMonut.descriptionMonument.setMaxLines(Integer.MAX_VALUE);
            itemMonutBinding.includeBodyMonut.showAllDescription.setImageResource(R.drawable.keyboard_arrow_up);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        if(view == itemMonutBinding.includeTitleMonut.btnShowHide) {
            if(itemMonutBinding.includeHeadMonut.cellHeadMonut.getVisibility() == View.VISIBLE)    action = true;
            else {
                action = false;
                maxAff = false;
                initDescription();
            }
            initMonument();
        } else {
            if(view == itemMonutBinding.includeBodyMonut.showAllDescription) {
                if(itemMonutBinding.includeBodyMonut.descriptionMonument.getMaxLines() == 2)    maxAff = true;
                else   maxAff = false;
                initDescription();
            }
        }
    }
}
