package ru.novozapp.fam.db;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import ru.novozapp.fam.db.cmn.DbNetwork;

/**
 * Created by dobrikostadinov on 6/13/15.
 */
public class DatabaseManager {
    public static final String TAG = DatabaseManager.class.getSimpleName();

    public static DatabaseManager instance;
    private Context mContext;
    private DatabaseHelper mHelper;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null)    instance = new DatabaseManager();
            }
        }
        return instance;
    }

    /**
     *
     * @param ctx
     */
    public static void initWithContext(Context ctx) {
        getInstance().mContext = ctx;
        getInstance().mHelper = new DatabaseHelper(ctx);
        // getInstance().requestAllFavPlaces();
    }

    private DatabaseManager() {}

    public void saveNetworkQuery(String key, String value) {
        try {
            List<DbNetwork> findedResult = mHelper.getNetworkDao().queryForEq("key", key);
            if (findedResult.size() > 0)    mHelper.getNetworkDao().delete(findedResult.get(0));
            DbNetwork dbNetwork = new DbNetwork();
            dbNetwork.setKey(key);
            dbNetwork.setValue(value);
            mHelper.getNetworkDao().createOrUpdate(dbNetwork);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DbNetwork findNetworkQuery(String key) {
        try {
            List<DbNetwork> result = mHelper.getNetworkDao().queryForEq("key", key);
            if (result != null && result.size() > 0)    return result.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}