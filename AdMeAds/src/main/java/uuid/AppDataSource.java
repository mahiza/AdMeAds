package uuid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AppDataSource {
	  private SQLiteDatabase database;
	  private SQLiteDatabase db;
	  private AppLiteHelper dbHelper=null;

	  public AppDataSource(Context context) {
		    dbHelper = new AppLiteHelper(context);
		  }

	  public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
		    db = dbHelper.getReadableDatabase();
		  }

	  public void close() {
		    dbHelper.close();
		  }

	  public long newUserID(String uuid) {
		    ContentValues values = new ContentValues();
		    values.put(AppLiteHelper.COLUMN_UUID, uuid);
		    long insertId = database.insert(AppLiteHelper.TABLE_NAME, null,values);
		    return insertId;
		  }

	  public void deleteUserID(Uuid uuid) {
		  try
          {
                  database.beginTransaction();
                  String delete = "DELETE FROM " + AppLiteHelper.TABLE_NAME + " WHERE id='"+ uuid.getId() +"'";
                  db.execSQL(delete);
                  db.setTransactionSuccessful();
          }
          finally
          {
                  db.endTransaction();
          }
		  }

	  public  List<Uuid> getUserID() {
		  Cursor cursor = null;
		  try
          {
                  List<Uuid> all = new ArrayList<Uuid>();
                  cursor = db.rawQuery("SELECT * FROM " + AppLiteHelper.TABLE_NAME, null);
                  if(cursor.getCount() > 0)
                  {       int idIndex = cursor.getColumnIndex(AppLiteHelper.COLUMN_ID);
                          int uIdIndex = cursor.getColumnIndex(AppLiteHelper.COLUMN_UUID);

                          cursor.moveToFirst();
                          do
                          {
                        	      int id = cursor.getInt(idIndex);
                                  String uuid = cursor.getString(uIdIndex);

                                  Uuid uid = new Uuid(id, uuid);
                                  all.add(uid);

                                  cursor.moveToNext();
                          } while(!cursor.isAfterLast());
                  }

                  return all;
          }
          finally
          {
                  if(cursor != null)
                  {
                          cursor.close();
                  }
          }

	  }

}