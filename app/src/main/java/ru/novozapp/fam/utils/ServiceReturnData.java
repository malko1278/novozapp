package ru.novozapp.fam.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ru.novozapp.fam.activities.R;
import ru.novozapp.fam.models.Monument;

/**
 * Created by Franck on 10/05/2016.
 */
public class ServiceReturnData {
    /**
     *
     * @param context
     * @return
     */
    public static ArrayList<Monument> getListeMonumentOfJson(Context context){
        // Récupération des évènements dans le fichier Json
        ArrayList<Monument> monuments = Constant.getMonumentData(context);
        return monuments;
    }

    /**
     *
     * @param context
     * @return
     */
    public static ArrayList<Monument> getRessaourceMonument(Context context) {
        // Récupération des évènements dans le fichier Json
        ArrayList<Monument> listMonuts = new ArrayList<>(0);
        Resources res = context.getResources();
        String[] lisName = res.getStringArray(R.array.monument_name);
        String[] lisDescript = res.getStringArray(R.array.monument_description);
        String[] lisYear = res.getStringArray(R.array.monument_year_const);
        String[] lisAdress = res.getStringArray(R.array.monument_adresse);
        String[] lisImage = res.getStringArray(R.array.monument_image);
        String[] lisLatitude = res.getStringArray(R.array.monument_latitude);
        String[] lisLongitude = res.getStringArray(R.array.monument_longitude);
        for(int i = 0; i < lisName.length; i++) {
            Monument monument = new Monument();
            monument.setName_monument(lisName[i]);
            monument.setDescription(lisDescript[i]);
            monument.setYear_construction(lisYear[i]);
            monument.setAdresse(lisAdress[i]);
            monument.setImage_monument(lisImage[i]);
            monument.setLatitude_monut(Double.parseDouble(lisLatitude[i]));
            monument.setLongitude_monut(Double.parseDouble(lisLongitude[i]));
            listMonuts.add(monument);
        }
        return listMonuts;
    }

    /**
     *
     * @param context
     * @return
     */
    public static ArrayList<Monument> getListeMonument(Context context) {
        // Récupération des évènements dans le fichier Json
        ArrayList<Monument> listMonuts = new ArrayList<>(0);
        List<TypedArray> listArray = getMultiTypedArray(context, "monument");
        for (TypedArray item : listArray) {
            Monument monument = new Monument();
            monument.setId(item.getInt(0, 0));
            /*
            monument.setName_monument(item.getString(1));
            monument.setDescription(item.getString(2));
            monument.setYear_construction(item.getString(3));
            monument.setAdresse(item.getString(4));
            monument.setImage_monument(item.getString(5));
            monument.setLatitude_monut(Double.parseDouble(item.getString(6)));
            monument.setLongitude_monut(Double.parseDouble(item.getString(7)));
            */
            listMonuts.add(monument);
        }
        return listMonuts;
    }

    /**
     *
     * @param context
     * @param key
     * @return
     */
    public static List<TypedArray> getMultiTypedArray(Context context, String key) {
        List<TypedArray> array = new ArrayList<>(0);
        try {
            Class<R.array> res = R.array.class;
            Field field;
            int counter = 0;
            do {
                field = res.getField(key + "_" + counter);
                array.add(context.getResources().obtainTypedArray(field.getInt(null)));
                counter++;
            } while (counter < 23);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }
}