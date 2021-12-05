package ru.novozapp.fam.utils;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import ru.novozapp.fam.activities.R;
import ru.novozapp.fam.models.Monument;

public class Constant {

    public static Resources getResources(Context context) {
        return context.getResources();
    }

    public static String getFromAsset(Context ctx, String path) {
        String json = null;

        try {
            InputStream is = ctx.getAssets().open(path);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();

            return null;
        }
        return json;
    }

    /**
     *
     * @param ctx
     * @return
     */
    public static ArrayList<Monument> getMonumentData(Context ctx) {
        String usersPath = "data/monument.json";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

        Gson gson = gsonBuilder.create();

        Type contactTypeList = new TypeToken<Collection<Monument>>(){}.getType();
        ArrayList<Monument> items = gson.fromJson(Constant.getFromAsset(ctx, usersPath), contactTypeList);
        return items;
    }

    public enum PLACE_TYPES {
        GOOGLE_PLACES_MONUMENT("Favourite in", R.color.purple_700, "");
        /**
         * Title of the type. Set in the actionbar in list places screen
         */
        private String title;
        /**
         * The color for the current type. Use to set actionbar in the list screen and other views if required
         */
        int resourceId;
        /**
         * Google type. We use this to create the URL for fetching the places
         */
        String type;

        PLACE_TYPES(String title, int resourceId, String value) {
            this.title = title;
            this.resourceId = resourceId;
            this.type = value;
        }

        public static int getTagsBg(PLACE_TYPES type) {
            return R.drawable.tags_bg_green;
        }
    }
}
