package ru.novozapp.fam.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ru.novozapp.fam.db.cmn.DbNetwork;

/**
 * Created by dobrikostadinov on 2/20/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getName();

    private static final String DATABASE_NAME = "novoz_app.db";
    private static final int DATABASE_VERSION = 1;
    private Dao<DbNetwork, Long> mDbNetworkDao = null;
    /**
     * @param context         Associated content from the application. This is needed to locate the database.
     */
    // public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
    public DatabaseHelper(Context context) {
        // super(context, databaseName, factory, databaseVersion);
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Log.d("############ DatabaseHelper First Action ##########", ":::############ -1- ############## Je veux Debuguer cette application");
    }

    /**
     * What to do when your database needs to be created. Usually this entails creating the tables and loading any
     * initial data.
     *
     * <p>
     * <b>NOTE:</b> You should use the connectionSource argument that is passed into this method call or the one
     * returned by getConnectionSource(). If you use your own, a recursive call or other unexpected results may result.
     * </p>
     *
     * @param database         Database being created.
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, DbNetwork.class);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Error creating database", e);
        }
    }

    /**
     * What to do when your database needs to be updated. This could mean careful migration of old data to new data.
     * Maybe adding or deleting database columns, etc..
     *
     * <p>
     * <b>NOTE:</b> You should use the connectionSource argument that is passed into this method call or the one
     * returned by getConnectionSource(). If you use your own, a recursive call or other unexpected results may result.
     * </p>
     *
     * @param database         Database being upgraded.
     * @param connectionSource To use get connections to the database to be updated.
     * @param oldVersion       The version of the current database so we can know what to do to the database.
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, DbNetwork.class, true);
            TableUtils.createTable(connectionSource, DbNetwork.class);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Error upgrading database", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<DbNetwork, Long> getNetworkDao() throws SQLException {
        if (mDbNetworkDao == null)    mDbNetworkDao = getDao(DbNetwork.class);
        return mDbNetworkDao;
    }
}