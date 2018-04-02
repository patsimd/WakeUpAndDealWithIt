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
        super(context,"ALARM",null,3);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table alarm(id integer primary key,name string not null, hour integer not null,minutes integer not null,game integer not null, difficulty string not null, repeat int not null)");
    }

    public long addAlarm(String name, int hour,int minutes,int game, String difficulty,boolean repeat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("hour",hour);
        values.put("minutes",minutes);
        values.put("game",game);
        values.put("difficulty",difficulty);
        if(repeat)
        {
            values.put("repeat",1);
        }else
            values.put("repeat",2);
        long newRowId = db.insert("alarm",null,values);
        if(newRowId == -1)
            Log.i("ALARM","Erreur lors de l'insertion de l'alarme");
        return newRowId;
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

    public void deleteArlarm(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM alarm WHERE id =" + String.valueOf(id));

    }
}
