package be.ehb.ipg13.fastjobs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static be.ehb.ipg13.fastjobs.ProfielSQL.KEY_ProfielAantalKM;
import static be.ehb.ipg13.fastjobs.ProfielSQL.KEY_ProfielGemeente;

/**
 * Created by Nadir on 27/04/2015.
 */
public class ProfielSQL {
    private static final String TAG = "MijnTag";
    public static final String KEY_ProfielID = "ProfielID";
    public static final String KEY_ProfielNaam = "ProfielNaam";
    public static final String KEY_ProfielTijdsregeling = "Tijdsregeling";

    public static final String KEY_ProfielSoortjob = "Soortjob";
    public static final String KEY_ProfielGemeente = "Gemeente";
    public static final String KEY_ProfielAantalKM = "AantalKM";

    public static final String KEY_TagID = "TagID";
    public static final String KEY_TagProfielID = "TagProfielID";
    public static final String KEY_Tag = "Tag";

    private static final String DATABASE_NAME = "FastJobdb";
    private static final String DATABASE_TABLE_PROFIEL = "Profiel";
    private static final String DATABASE_TABLE_TAGS = "Tags";
    private static final int DATABASE_VERSION = 1;

    private DbHelper ourHelper;
    private SQLiteDatabase ourDatabase;
    private final Context ourContext;

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "FastJobdb werd gemaakt!");
            db.execSQL("CREATE TABLE " + DATABASE_TABLE_PROFIEL + " (" +
                            KEY_ProfielID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            KEY_ProfielNaam + " TEXT NOT NULL, " +
                            KEY_ProfielTijdsregeling + " TEXT NOT NULL, " +
                            KEY_ProfielSoortjob + " TEXT NOT NULL, " +
                            KEY_ProfielGemeente + " TEXT NOT NULL, " +
                            KEY_ProfielAantalKM + " TEXT NOT NULL);"
            );
            db.execSQL("CREATE TABLE " + DATABASE_TABLE_TAGS + " (" +
                            KEY_TagID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            KEY_TagProfielID + " INTEGER NOT NULL, " +
                            KEY_Tag + " TEXT NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "DB EXISTS");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PROFIEL);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TAGS);
            onCreate(db);

        }
    }//END DB HELPER

    public ProfielSQL(Context context) {
        ourContext = context;
    }

    public ProfielSQL open() throws SQLException {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public void createEntry(String profielNaam, String profielTijdsregeling, String profielSoortjob, String profielGemeente, String profielAantalKM, ArrayList<String> lijstTags) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_ProfielNaam, profielNaam);
        cv.put(KEY_ProfielTijdsregeling, profielTijdsregeling);
        cv.put(KEY_ProfielSoortjob, profielSoortjob);
        cv.put(KEY_ProfielGemeente, profielGemeente);
        cv.put(KEY_ProfielAantalKM, profielAantalKM);

        ourDatabase.insert(DATABASE_TABLE_PROFIEL, null, cv);
        int pk_profiel = getProfielID();
        for (int i = 0; i < lijstTags.size(); i++) {
            ContentValues cv2 = new ContentValues();

            cv2.put(KEY_TagProfielID, pk_profiel);
            cv2.put(KEY_Tag, lijstTags.get(i).toString());

            ourDatabase.insert(DATABASE_TABLE_TAGS, null, cv2);
        }
        System.out.println("++++++++++++++++---->>> IK ZIT IN CREATE ENTRY");
    }

    public int getProfielID() {
        String[] columns = new String[]{KEY_ProfielID};
        Cursor c = ourDatabase.query(DATABASE_TABLE_PROFIEL, columns, null, null, null, null, null);
        int result = 0;

        int profID = c.getColumnIndex(KEY_ProfielID);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (c.isLast()) {
                result = c.getInt(profID);
                System.out.println("DE PROFIEL ID IS == " + result);
            }
        }
        return result;
    }

    public ArrayList<Profiel_model> getAll() {
        ArrayList<String> lijstTags = new ArrayList<String>();
        ArrayList<Profiel_model> lijstje = new ArrayList<Profiel_model>();
        Profiel_model profielen;
        String[] columns = new String[]{KEY_ProfielID, KEY_ProfielNaam, KEY_ProfielTijdsregeling, KEY_ProfielSoortjob, KEY_ProfielGemeente, KEY_ProfielAantalKM};
        Cursor c = ourDatabase.query(DATABASE_TABLE_PROFIEL, columns, null, null, null, null, null);

        String[] columns2 = new String[]{KEY_TagID, KEY_TagProfielID, KEY_Tag};
        Cursor c2 = ourDatabase.query(DATABASE_TABLE_TAGS, columns2, null, null, null, null, null);
        int result = 0;

        int profID = c.getColumnIndex(KEY_ProfielID);
        int profielNaam = c.getColumnIndex(KEY_ProfielNaam);
        int profielTijdsregeling = c.getColumnIndex(KEY_ProfielTijdsregeling);
        int profielSoortjob = c.getColumnIndex(KEY_ProfielSoortjob);
        int profielGemeente = c.getColumnIndex(KEY_ProfielGemeente);
        int profielAantalKM = c.getColumnIndex(KEY_ProfielAantalKM);

        int tagId = c2.getColumnIndex(KEY_TagID);
        int tagProfielID = c2.getColumnIndex(KEY_TagProfielID);
        int tag = c2.getColumnIndex(KEY_Tag);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            profielen = new Profiel_model(c.getString(profielNaam), c.getString(profielTijdsregeling), c.getString(profielSoortjob), c.getString(profielGemeente), c.getString(profielAantalKM));
            profielen.setProfielID(c.getString(profID));
            for (c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {
                if (c2.getInt(tagProfielID) == c.getInt(profID)) {
                    lijstTags.add(c2.getString(tag));
                    profielen.setTrefwoorden(c2.getString(tag));
                    //System.out.println("DE TAG is  = " + c2.getString(tag) );
                }
            }
            lijstje.add(profielen);
            /*System.out.println("1 = de size is " + profielen.getTrefwoorden().size());
            for(int m = 0;m < profielen.getTrefwoorden().size();m++){
                System.out.println("DE TAG IN DE LIJST  = " + profielen.getTrefwoorden().get(m));
            }*/
        }
        return lijstje;
    }

    public void delete(String naamProfiel,String profID){
        System.out.println("IK ZIT IN DELETE UIT DB. DE PROFIEL NAAM IS == " + naamProfiel);
        ourDatabase.delete(DATABASE_TABLE_PROFIEL, KEY_ProfielID + "=" + profID,null);
        ourDatabase.delete(DATABASE_TABLE_TAGS, KEY_TagProfielID + "=" + profID,null);
    }
    public void updateEntry(String profID, String profielNaam, String profielTijdsregeling, String profielSoortjob, String profielGemeente, String profielAantalKM, ArrayList<String> lijstTags) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_ProfielNaam, profielNaam);
        cv.put(KEY_ProfielTijdsregeling, profielTijdsregeling);
        cv.put(KEY_ProfielSoortjob, profielSoortjob);
        cv.put(KEY_ProfielGemeente, profielGemeente);
        cv.put(KEY_ProfielAantalKM, profielAantalKM);

        ourDatabase.update(DATABASE_TABLE_PROFIEL, cv,KEY_ProfielID + "=" + profID,null);
        //int pk_profiel = getProfielID();
        ourDatabase.delete(DATABASE_TABLE_TAGS, KEY_TagProfielID + "=" + profID,null);
        ContentValues cv2;
        for (int i = 0; i < lijstTags.size(); i++) {
            cv2 = new ContentValues();

            cv2.put(KEY_TagProfielID, profID);
            cv2.put(KEY_Tag, lijstTags.get(i).toString());

            ourDatabase.insert(DATABASE_TABLE_TAGS, null, cv2);
        }
        System.out.println("++++++++++++++++---->>> IK ZIT IN UPDATE ENTRY");
    }
/*
    public ArrayList<Profiel_model> getAll() {
        ArrayList<String> lijstTags = new ArrayList<String>();
        ArrayList<Profiel_model> lijstje = new ArrayList<Profiel_model>();
        Profiel_model Profielen;
        //System.out.println("GET ALL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        String[] columns = new String[]{KEY_ProfielID, KEY_ProfielNaam, KEY_ProfielTijdsregeling, KEY_ProfielSoortjob, KEY_ProfielGemeente, KEY_ProfielAantalKM};
        Cursor c = ourDatabase.query(DATABASE_TABLE_PROFIEL, columns, null, null, null, null, null);

        String[] columns2 = new String[]{KEY_TagID, KEY_TagProfielID, KEY_Tag};
        Cursor c2 = ourDatabase.query(DATABASE_TABLE_TAGS, columns2, null, null, null, null, null);

        int result = 0;

        int profID = c.getColumnIndex(KEY_ProfielID);
        int profielNaam = c.getColumnIndex(KEY_ProfielNaam);
        int profielTijdsregeling = c.getColumnIndex(KEY_ProfielTijdsregeling);
        int profielSoortjob = c.getColumnIndex(KEY_ProfielSoortjob);
        int profielGemeente = c.getColumnIndex(KEY_ProfielGemeente);
        int profielAantalKM = c.getColumnIndex(KEY_ProfielAantalKM);

        int tagId = c2.getColumnIndex(KEY_TagID);
        int tagProfielID = c2.getColumnIndex(KEY_TagProfielID);
        int tag = c2.getColumnIndex(KEY_Tag);
        // System.out.println();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            for (c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {
                //System.out.println("IN DE C2!!!!!");
                if (c2.getInt(tagProfielID) == c.getInt(profID)) {
                    //  System.out.println("DE TAG IS == " + c2.getString(tag));
                    lijstTags.add(c2.getString(tag));
                }
            }
            //System.out.println("De profielNaam is == " + c.getString(profielNaam));
            Profielen = new Profiel_model(c.getString(profielNaam), c.getString(profielTijdsregeling), c.getString(profielSoortjob), c.getString(profielGemeente), c.getString(profielAantalKM), lijstTags);
            lijstje.add(Profielen);
        }
/*
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if (c.isLast()) {
                result = c.getInt(profID);
                System.out.println("DE PROFIEL ID IS == " + result);
            }
        }
        return lijstje;
    }
*/
}
