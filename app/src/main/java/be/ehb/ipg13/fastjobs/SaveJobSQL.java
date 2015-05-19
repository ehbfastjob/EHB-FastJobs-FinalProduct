package be.ehb.ipg13.fastjobs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Jermo on 15/04/2015.
 */
public class SaveJobSQL {

    private static final String TAG = "jermoTag";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_JobID = "Vacature_nummer";
    public static final String KEY_Title = "Tital";
    public static final String KEY_Gemeente = "gemeente";
    private static final String DATABASE_NAME = "RateThePlatedb";
    private static final String DATABASE_TABLE = "plates";
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
            Log.i(TAG, "DB  gemaakt");
            db.execSQL("CREATE TABLE " + DATABASE_TABLE +" ("+
                            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                            KEY_JobID + " TEXT NOT NULL, " +
                            KEY_Gemeente + " TEXT NOT NULL, " +
                            KEY_Title +" TEXT NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "DB EXISTS");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }//END DB HELPER

    public SaveJobSQL (Context context){
        ourContext=context;
    }
    public SaveJobSQL open() throws SQLException {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        ourHelper.close();
    }
    public long createEntry(String VacID,String title,String gemeente){
        ContentValues cv = new ContentValues();
        cv.put(KEY_JobID,VacID);
        cv.put(KEY_Title,title);
        cv.put(KEY_Gemeente,gemeente);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public ArrayList<ModelTest> getFavorites(){
        ArrayList<ModelTest> lijstje= new ArrayList<ModelTest>();
        ModelTest vacature;

        String [] columns = new String [] {KEY_ROWID,KEY_JobID, KEY_Title,KEY_Gemeente};
        Cursor c =  ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iJOBID = c.getColumnIndex(KEY_JobID);
        int iTitle = c.getColumnIndex(KEY_Title);
        int iGemeente = c.getColumnIndex(KEY_Gemeente);
        //System.out.println("DATABASE COUNT == " + c.getCount());
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            vacature=new ModelTest();
            System.out.println("DATABASE CURSOR IRoW == " + c.getInt(iRow));
            System.out.println("DATABASE CURSOR JOBID == " + c.getString(iJOBID));
            vacature.setId(c.getString(iJOBID));
            vacature.setTitel(c.getString(iTitle));
            vacature.setGemeente(c.getString(iGemeente));
            lijstje.add(vacature);
        }
        for (int i = 0; i < lijstje.size(); i++){
            System.out.println("array ID == " + lijstje.get(i).getId());
            System.out.println("array ID == " + lijstje.get(i).getGemeente());
            System.out.println("array ID == " + lijstje.get(i).getTitel());
        }
        /*
        for(int j = 0; j < c.getCount();j++){
            System.out.println("ddddddddddddddddddd");
            //System.out.println("DATABASE CURSOR JOBID == " + c.getString(iJOBID));
            vacature.setId(c.getString(iJOBID));
            vacature.setTitel(c.getString(iTitle));
            vacature.setGemeente(c.getString(iGemeente));
            lijstje.add(vacature);
        }*/
        return lijstje;
    }

    public String getData(){
        String result="";
        String [] columns = new String [] {KEY_ROWID,KEY_JobID, KEY_Title,KEY_Gemeente};
        Cursor c =  ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iJOBID = c.getColumnIndex(KEY_JobID);
        int iTitle = c.getColumnIndex(KEY_Title);
        int iSummary = c.getColumnIndex(KEY_Gemeente);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = result + c.getString(iRow) + " " + c.getString(iJOBID)+ " "+ c.getString(iTitle) + c.getString(iSummary) +"\n";
        }
        return result;
    }

    public String getJOBID()
    {
        String [] columns = new String [] {KEY_JobID};
        Cursor c =  ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int iJOBID = c.getColumnIndex(KEY_JobID);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = result +  c.getString(iJOBID) + "\n";
        }
        return result;
    }

    public void deleteFavorite(String lRow1){

        ourDatabase.delete(DATABASE_TABLE,KEY_JobID + "=" + lRow1,null);
    }
    public String getTitle() {
        String [] columns = new String [] { KEY_Title};
        Cursor c =  ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int iTitle = c.getColumnIndex(KEY_Title);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = result +  c.getString(iTitle) + "\n";
        }
        return result;
    }

    public String getSummary() {
        String [] columns = new String [] { KEY_Gemeente};
        Cursor c =  ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int iTitle = c.getColumnIndex(KEY_Gemeente);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = result +  c.getString(iTitle) + "\n";
        }
        return result;
    }
}