
package com.example.myapplication;
import com.example.myapplication.Database.interfaceDb;
import com.example.myapplication.Models.*;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {favHero.class}, version = 2)
public abstract class CreateDb extends RoomDatabase {

    private static CreateDb instance;
    public abstract interfaceDb Dao();

    public static synchronized CreateDb getInstance(Context context) {
        if (instance == null) {
            instance =
                    Room.databaseBuilder(context.getApplicationContext(),
                            CreateDb.class, "faveDoge_db")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .allowMainThreadQueries()
                            .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(CreateDb instance) {
            interfaceDb dao = instance.Dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
