package pokeyelp.grat.team.pokemonyelp.activity_collection.DBAsset;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Admin on 5/1/17.
 */

public class DBAssetHelper  extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "pokemonBusiness.db";
    private static final int DATABASE_VERSION = 1;


    public DBAssetHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


}
