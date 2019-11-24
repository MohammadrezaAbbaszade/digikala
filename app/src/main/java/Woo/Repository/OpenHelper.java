package Woo.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.digikala.model.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class OpenHelper extends DaoMaster.DevOpenHelper {

    public OpenHelper(Context context, String name) {
        super(context, name);
    }

    public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
