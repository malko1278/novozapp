package ru.novozapp.fam.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import ru.novozapp.fam.activities.databinding.ItemMonumentBinding;
import ru.novozapp.fam.activities.databinding.RecyclerListBinding;
import ru.novozapp.fam.adapter.MonumentAdapter;
import ru.novozapp.fam.models.Monument;
import ru.novozapp.fam.utils.ServiceReturnData;

public class MonumentActivity extends AppCompatActivity implements MonumentAdapter.MyClickListener {

    private Context monutContext;
    private ArrayList<Monument> listMonument;
    private RecyclerListBinding recyclerBinding;
    private MonumentAdapter adapterMonut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerBinding = RecyclerListBinding.inflate(getLayoutInflater());
        setContentView(recyclerBinding.getRoot());
        Log.d(">>>:: onCreate ::<<< ", "<<<:: onCreate ::>>> ");

        monutContext = this;
        initContentElement();
        takeData();
        showListView();
    }
    
    /*
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(">>>:: onStart ::<<< ", "<<<:: onStart ::>>> ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(">>>:: onResume ::<<< ", "<<<:: onResume ::>>> ");
    }

    @Override
    public void onBackPressed() {}

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(">>>:: onStop ::<<< ", "<<<:: onStop ::>>> ");
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(">>>:: onPause ::<<< ", "<<<:: onPause ::>>> ");
    }

    @Override
    protected void onDestroy () {
        super.onDestroy ();
        Log.d(">>>:: onDestroy ::<<< ", "<<<:: onDestroy ::>>> ");
    }
    */

    private void initContentElement() {
        // Utiliser un gestionnaire de layout linéaire
        LinearLayoutManager layoutManager = new LinearLayoutManager(monutContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerBinding.complexeListView.setItemAnimator(new DefaultItemAnimator());
        recyclerBinding.complexeListView.setLayoutManager(layoutManager);
        // Amélioration des performances pour améliorer la présentation du RecyclerView
        recyclerBinding.complexeListView.setHasFixedSize(true);
        // Intialize adapter
        adapterMonut = null;
    }

    private void takeData() {
        // Initialisation de la variable qui contiendra toutes les échéances
        // listMonument = ServiceReturnData.getListeMonumentOfJson(monutContext);
        // listMonument = ServiceReturnData.getListeMonument(monutContext);
        listMonument = ServiceReturnData.getRessaourceMonument(monutContext);
    }

    protected void showListView() {
        // Vérification de la taille du conteneur
        if(listMonument.size() > 0) {
            // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
            adapterMonut = new MonumentAdapter(monutContext, listMonument, this);
            // set elements to adapter
            recyclerBinding.complexeListView.setAdapter(adapterMonut);
        }
    }

    /**
     *
     * @param view
     * @param position
     * @param itemMonut
     */
    @Override
    public void itemClickedMonument(View view, int position, ItemMonumentBinding itemMonut, List<Boolean> chargeFinished, List<Boolean> descripAhhich) {
        if(view == itemMonut.includeTitleMonut.btnShowHide) {
            // Snackbar.make(view, "Show Hide - Position = " + position, Snackbar.LENGTH_LONG).setAction("Show Hide", null).show();
            if(itemMonut.includeHeadMonut.cellHeadMonut.getVisibility() == View.VISIBLE) {
                itemMonut.includeBodyMonut.cellContent.setVisibility(View.VISIBLE);
                itemMonut.includeHeadMonut.cellHeadMonut.setVisibility(View.GONE);
                itemMonut.includeTitleMonut.btnShowHide.setImageResource(R.drawable.keyboard_arrow_up);
                MonumentAdapter.upDatePositionBool(position, true);
            } else {
                itemMonut.includeBodyMonut.cellContent.setVisibility(View.GONE);
                itemMonut.includeHeadMonut.cellHeadMonut.setVisibility(View.VISIBLE);
                itemMonut.includeTitleMonut.btnShowHide.setImageResource(R.drawable.keyboard_arrow_down);
                MonumentAdapter.upDatePositionBool(position, false);
                if(itemMonut.includeBodyMonut.descriptionMonument.getMaxLines() != 2)
                    ajustDescription(itemMonut.includeBodyMonut.descriptionMonument, itemMonut.includeBodyMonut.showAllDescription,
                            2, R.drawable.keyboard_arrow_down);
            }
        } else {
            if(view == itemMonut.includeBodyMonut.showAllDescription) {
                // Snackbar.make(view, "Max lines = " + itemMonut.includeBodyMonut.descriptionMonument.getMaxLines(), Snackbar.LENGTH_LONG)
                        // .setAction("Like", null).show();
                if(itemMonut.includeBodyMonut.descriptionMonument.getMaxLines() == 2) {
                    ajustDescription(itemMonut.includeBodyMonut.descriptionMonument, itemMonut.includeBodyMonut.showAllDescription,
                            Integer.MAX_VALUE, R.drawable.keyboard_arrow_up);
                    MonumentAdapter.upDateDescripVisible(position, true);
                } else {
                    ajustDescription(itemMonut.includeBodyMonut.descriptionMonument, itemMonut.includeBodyMonut.showAllDescription,
                            2, R.drawable.keyboard_arrow_down);
                    MonumentAdapter.upDateDescripVisible(position, false);
                }
            }
            /*if(view == itemMonut.includeTitleMonut.btnLikes) {
                Snackbar.make(view, "Like - Position = " + position, Snackbar.LENGTH_LONG).setAction("Like", null).show();
            } else {

            }*/
        }
    }

    /**
     *
     * @param description
     * @param showAllDesc
     * @param maxValue
     * @param keyboard_arrow
     */
    private void ajustDescription(TextView description, ImageView showAllDesc, int maxValue, int keyboard_arrow) {
        description.setMaxLines(maxValue);
        showAllDesc.setImageResource(keyboard_arrow);
    }
}