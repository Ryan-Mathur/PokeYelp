package pokeyelp.grat.team.pokemonyelp.activity_collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import pokeyelp.grat.team.pokemonyelp.gson_yelp.BusinessDetail;

/**
 * Created by Admin on 5/1/17.
 */

public class PokemonBusinessSQLiteOpenHelper extends SQLiteOpenHelper {
    //---- Define Database  -------

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pokemonBusiness.db";
    private static final String POKEMONBUSINESS_TABLE_NAME = "POKEMONB";

    public static abstract class CollectionTable implements BaseColumns {

        public static final String TABLE_NAME = "collections";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PKMNNAME = "pokemon_name";
        public static final String COLUMN_STORECAUGHT = "store_caught";

    }

    public static abstract class StoreTable implements BaseColumns {

        public static final String TABLE_NAME = "storetable";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_YELPID = "yelp_id";
        public static final String COLUMN_PKMNNAME = "pokemon_name";
        public static final String COLUMN_BOOKMARK = "isBookmarked";
    }

    private static final String SQL_CREATE_ENTRIES_POKEMON = "CREATE TABLE " +
            CollectionTable.TABLE_NAME + " (" +
            CollectionTable.COLUMN_ID + " INTEGER PRIMARY KEY, " +
            CollectionTable.COLUMN_PKMNNAME + " TEXT, " +
            CollectionTable.COLUMN_STORECAUGHT + " TEXT" + ")";

    private static final String SQL_DELETE_ENTRIES_POKEMON = "DROP TABLE IF EXISTS " +
            CollectionTable.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_STORE = "CREATE TABLE " +
            StoreTable.TABLE_NAME + " (" +
            StoreTable.COLUMN_ID + " INTEGER PRIMARY KEY, " +
            StoreTable.COLUMN_YELPID + "TEXT, " +
            StoreTable.COLUMN_PKMNNAME + "TEXT, " +
            StoreTable.COLUMN_BOOKMARK + "TEXT" + ")";

    private static final String SQL_DELETE_ENTRIES_STORE = "DROP TABLE IF EXISTS " +
            StoreTable.TABLE_NAME;

    private static PokemonBusinessSQLiteOpenHelper sInstance;


    private PokemonBusinessSQLiteOpenHelper(Context context) {
        super(context, "db", null, 1);
    }

    public static PokemonBusinessSQLiteOpenHelper getInstance(Context context) {
        if (sInstance == null)
            sInstance = new PokemonBusinessSQLiteOpenHelper(context.getApplicationContext());
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_POKEMON);
        db.execSQL(SQL_CREATE_ENTRIES_STORE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_POKEMON);
        db.execSQL(SQL_DELETE_ENTRIES_STORE);
        onCreate(db);
    }

    public void addToCollection(String id, String pokemon_name) {
        //---------- takes the Pokemon and Yelp Store ID from findPokemon
        //---------- add to collection
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CollectionTable.COLUMN_PKMNNAME, pokemon_name);
        values.put(CollectionTable.COLUMN_STORECAUGHT, id);

        db.update(CollectionTable.TABLE_NAME, values,CollectionTable.COLUMN_PKMNNAME+ " = "+ pokemon_name, null);
        db.update(CollectionTable.TABLE_NAME, values,CollectionTable.COLUMN_STORECAUGHT+ " = "+ id, null);
        db.close();
    }

    public void updatePokemon(String id, String pokemon_name) {
        //----- stores the Pokemon with a Yelp Id.
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StoreTable.COLUMN_PKMNNAME, pokemon_name);
        values.put(StoreTable.COLUMN_YELPID, id);

        db.update(StoreTable.TABLE_NAME, values,StoreTable.COLUMN_PKMNNAME+ " = "+ pokemon_name, null);
        db.update(StoreTable.TABLE_NAME, values,StoreTable.COLUMN_YELPID+ " = "+ id, null);
        db.close();

    }

    public List<PokemonClass> searchForPokemon(String pokemon_name){
        SQLiteDatabase db = getReadableDatabase();

        String selection = StoreTable.COLUMN_PKMNNAME + " = ? ";

        Cursor cursor = db.query(StoreTable.TABLE_NAME,
                new String[]{},
                selection,
                new String[]{pokemon_name},
                null,
                null,
                null);

        List<PokemonClass> pokemon = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                long id = cursor.getLong(cursor.getColumnIndex(StoreTable.COLUMN_ID));
                String pokemon_Name = cursor.getString(cursor.getColumnIndex(StoreTable.COLUMN_PKMNNAME));
                String yelpID = cursor.getString(cursor.getColumnIndex(StoreTable.COLUMN_YELPID));
                String bookmark = cursor.getString(cursor.getColumnIndex(StoreTable.COLUMN_BOOKMARK));

                PokemonClass pokemonClass = new PokemonClass(id, pokemon_Name, yelpID, bookmark);
                pokemon.add(pokemonClass);
                // need custom class for the pokemon

                cursor.moveToNext();
            }

        }cursor.close();
        return pokemon;

    }

    public List<Collection> getYourCollection(){
        SQLiteDatabase db = getReadableDatabase();

        String selection = StoreTable.COLUMN_PKMNNAME + " = ? ";

        Cursor cursor = db.query(StoreTable.TABLE_NAME,
                new String[]{},
                selection,
                new String[]{},
                null,
                null,
                null);

        List<Collection> collection = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                long id = cursor.getLong(cursor.getColumnIndex(CollectionTable.COLUMN_ID));
                String pokemon_Name = cursor.getString(cursor.getColumnIndex(CollectionTable.COLUMN_PKMNNAME));
                String yelpStore = cursor.getString(cursor.getColumnIndex(CollectionTable.COLUMN_STORECAUGHT));


                Collection collection1 = new PokemonClass(id, pokemon_Name, yelpStore,);
                collection1.add(collection);


                cursor.moveToNext();
            }

        }cursor.close();
        return pokemon;

    }

    }


