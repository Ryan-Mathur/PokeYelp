package pokeyelp.grat.team.pokemonyelp.activity_collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by Admin on 5/1/17.
 */

public class PokemonBusinessSQLiteOpenHelper extends SQLiteOpenHelper {
    //---- Define Database  -------

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "yelpPokemon.db";

    public static abstract class CollectionTable{

        public static final String TABLE_NAME = "collections";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_PKMNNAME = "collections_pokemon_name";
        public static final String COLUMN_PKMN_ID = "pokemon_id";
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
            CollectionTable.COLUMN_PKMN_ID + " INTEGER, " +
            CollectionTable.COLUMN_STORECAUGHT + " TEXT" + ")";

    private static final String SQL_DELETE_ENTRIES_POKEMON = "DROP TABLE IF EXISTS " +
            CollectionTable.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_STORE = "CREATE TABLE " +
            StoreTable.TABLE_NAME + " (" +
            StoreTable.COLUMN_ID + " INTEGER PRIMARY KEY, " +
            StoreTable.COLUMN_YELPID + " TEXT, " +
            StoreTable.COLUMN_PKMNNAME + " TEXT, " +
            StoreTable.COLUMN_BOOKMARK + " BOOLEAN" + ")";

    private static final String SQL_DELETE_ENTRIES_STORE = "DROP TABLE IF EXISTS " +
            StoreTable.TABLE_NAME;

    // -------- Singleton

    private static PokemonBusinessSQLiteOpenHelper sInstance;


    private PokemonBusinessSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        Log.d(TAG, "insertPokemonStore: " + StoreTable.COLUMN_YELPID);
        db.insert(StoreTable.TABLE_NAME, null, values);
        db.close();
    }

    // Take in an id and pokemonname to update the store table
    public void updatePokemon(String id, String newPokemon) {
        //----- stores the Pokemon with a Yelp Id.
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StoreTable.COLUMN_PKMNNAME, newPokemon);
        Log.d(TAG, "updatePokemon: " + StoreTable.COLUMN_PKMNNAME);
        db.update(StoreTable.TABLE_NAME, values, StoreTable.COLUMN_YELPID + " = ?", new String[]{id});
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
        Log.d(TAG, "searchForPokemon: " + pokemonName);
        return pokemonName;

    }

    // after 24 hours the store resets to empty, method to clear data from store table
    public void resetStores(){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES_STORE);
        db.execSQL(SQL_CREATE_ENTRIES_STORE);
        db.close();

    }

    // -------- Collection methods

    //---------- takes the Pokemon and Yelp Store ID from findPokemon
    //---------- add to collection
    public void addToCollection(String pokemonName, int id, String caughtAtYelpId) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CollectionTable.COLUMN_PKMNNAME, pokemonName);
        values.put(CollectionTable.COLUMN_PKMN_ID, id);
        values.put(CollectionTable.COLUMN_STORECAUGHT, caughtAtYelpId);
        Log.d(TAG, "addToCollection: " + CollectionTable.COLUMN_PKMNNAME);
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
                String pokemon_Name = cursor.getString(cursor.getColumnIndex(CollectionTable.COLUMN_PKMNNAME));
                int pkmnId = cursor.getInt(cursor.getColumnIndex(CollectionTable.COLUMN_PKMN_ID));
                String yelpStore = cursor.getString(cursor.getColumnIndex(CollectionTable.COLUMN_STORECAUGHT));

                Collection collection1 = new Collection(pokemon_Name, pkmnId, yelpStore);
                collection.add(collection1);

                cursor.moveToNext();
            }

        }cursor.close();
        Log.d(TAG, "getYourCollection: " + collection);
        return collection;

    }
    //todo test all dbhelper methods

    public boolean storeTableIsEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(StoreTable.TABLE_NAME, null, null, null, null, null, null);
        boolean isEmpty = !cursor.moveToFirst();
        cursor.close();
        return isEmpty;
    }

    public boolean collectionTableIsEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(CollectionTable.TABLE_NAME, null, null, null, null, null, null);
        boolean isEmpty = !cursor.moveToFirst();
        cursor.close();
        return isEmpty;
    }

}


