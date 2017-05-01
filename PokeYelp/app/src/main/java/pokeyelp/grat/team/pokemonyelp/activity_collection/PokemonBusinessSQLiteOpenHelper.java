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

    public static abstract class CollectionTable{

        public static final String TABLE_NAME = "collections";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PKMNNAME = "pokemon_name";
        public static final String COLUMN_STORECAUGHT = "store_caught";

    }

    public static abstract class StoreTable {

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

    // -------- Singleton

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

    // ----------- Store methods

    // Take in an id and pokemon name and inserts it into StoreTable
    public void insertPokemonStore(String id, String pokemon_name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StoreTable.COLUMN_YELPID, id);
        values.put(StoreTable.COLUMN_PKMNNAME, pokemon_name);

        db.insert(StoreTable.TABLE_NAME, null, values);
        db.close();
    }

    // Take in an id and pokemonname to update the store table
    public void updatePokemon(String id, String pokemon_name) {
        //----- stores the Pokemon with a Yelp Id.
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StoreTable.COLUMN_PKMNNAME, pokemon_name);
        db.update(StoreTable.TABLE_NAME, values,StoreTable.COLUMN_YELPID+ " = "+ id, null);
        db.close();

    }
    //  Take in id to check to see if Pokemon assigned already to store
    public String searchForPokemon(String yelp_id){
        SQLiteDatabase db = getReadableDatabase();

        String pokemonName = null;

        String selection = StoreTable.COLUMN_YELPID + " = ? ";

        Cursor cursor = db.query(StoreTable.TABLE_NAME,
                new String[]{},
                selection,
                new String[]{yelp_id},
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            pokemonName = cursor.getString(cursor.getColumnIndex(StoreTable.COLUMN_PKMNNAME));

        }

        cursor.close();
        return pokemonName;

    }

    // after 24 hours the store resets to empty, method to clear data from store table
    public void resetPokemon(){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.remove(StoreTable.COLUMN_YELPID);
        values.remove(StoreTable.COLUMN_PKMNNAME);
        db.update(StoreTable.TABLE_NAME,values,null,null );
        db.close();

    }

    // -------- Collection methods

    //---------- takes the Pokemon and Yelp Store ID from findPokemon
    //---------- add to collection
    public void addToCollection(String id, String pokemon_name) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CollectionTable.COLUMN_PKMNNAME, pokemon_name);
        values.put(CollectionTable.COLUMN_STORECAUGHT, id);

        db.insert(CollectionTable.TABLE_NAME, null, values);

        db.close();
    }

    //  pulls the collection of pokemon for the Collection view
    public List<Collection> getYourCollection(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(CollectionTable.TABLE_NAME,
                null, null, null, null, null, null);

        List<Collection> collection = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                long id = cursor.getLong(cursor.getColumnIndex(CollectionTable.COLUMN_ID));
                String pokemon_Name = cursor.getString(cursor.getColumnIndex(CollectionTable.COLUMN_PKMNNAME));
                String yelpStore = cursor.getString(cursor.getColumnIndex(CollectionTable.COLUMN_STORECAUGHT));


                Collection collection1 = new Collection(pokemon_Name, yelpStore);
                collection.add(collection1);


                cursor.moveToNext();
            }

        }cursor.close();
        return collection;

    }


    //todo test all dbhelper methods
}


