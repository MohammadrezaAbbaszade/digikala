package Woo.Repository;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import com.example.digikala.model.productsModels.DaoMaster;
import com.example.digikala.model.productsModels.DaoSession;

public class DBApplication extends Application {


  private static DBApplication application;
  private DaoSession daoSession;

  @Override
  public void onCreate() {
    super.onCreate();

    OpenHelper daoOpenHelper = new OpenHelper(this, "digikala.db");
    SQLiteDatabase database = daoOpenHelper.getWritableDatabase();
    daoSession = new DaoMaster(database).newSession();

    application = this;
  }

  public static  DBApplication getInstance() {
    return application;
  }

  public DaoSession getDaoSession() {
    return daoSession;
  }
}
