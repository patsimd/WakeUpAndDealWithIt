package al.demo.alarmmanagerdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Utilisateur on 2018-04-02.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public Context context;
    public DatabaseHelper(Context context){
        super(context,"ALARM",null,2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table alarm(id integer primary key,name string no null, date integer not null,game integer not null, difficulty string not null)");
    }

    public void addAlarm(int id,String name, long millis,int game, String difficulty){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",id);
        int sec = (int)(millis / 1000);
        values.put("name",name);
        values.put("date",sec);
        values.put("game",game);
        values.put("difficulty",difficulty);
        long newRowId = db.insert("alarm",null,values);
        if(newRowId == -1)
            Log.i("ALARM","Erreur lors de l'insertion de l'alarme");

    }
    public Cursor getAlarm(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("alarm",null,null,null,null,null,"id ASC");

        return cursor;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists alarm");
        onCreate(db);

    }
}
