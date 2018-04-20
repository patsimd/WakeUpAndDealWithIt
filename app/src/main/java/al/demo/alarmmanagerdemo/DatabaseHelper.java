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
        super(context,"ALARM",null,5);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table alarm(id integer primary key,name string not null, hour integer not null,minutes integer not null,enabled integer not null, difficulty string not null, repeat int not null, music string not null)");
    }

    public long addAlarm(String name, int hour,int minutes,boolean enabled, String difficulty,boolean repeat,String musicUri){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("hour",hour);
        values.put("minutes",minutes);
        if(enabled)
        {
            values.put("enabled",1);
        }else
            values.put("enabled",0);
        values.put("difficulty",difficulty);
        if(repeat)
        {
            values.put("repeat",1);
        }else
            values.put("repeat",0);
        values.put("music", musicUri);

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

    public Cursor getAlarmByID(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {String.valueOf(id)};
        Cursor cursor = db.query("alarm",null,"id = ?", args ,null,null,"id ASC");

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

    public void updateAlarmEnable(int id, boolean enable){
        SQLiteDatabase db = this.getWritableDatabase();
        int enabled;
        if(enable)
            enabled = 1;
        else
            enabled = 0;
        db.execSQL("UPDATE alarm SET enabled =" + String.valueOf(enabled) + " WHERE id =" + String.valueOf(id));
    }

    public void updateAlarmRepeat(int id, boolean repeat){
        SQLiteDatabase db = this.getWritableDatabase();
        int isRepeat;
        if(repeat)
            isRepeat = 1;
        else
            isRepeat = 0;
        db.execSQL("UPDATE alarm SET repeat =" + String.valueOf(isRepeat) + " WHERE id =" + String.valueOf(id));
    }
}
