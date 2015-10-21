package de.berufsschule_freising.pacasus.model.dataContext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import de.berufsschule_freising.pacasus.model.entity.User;

/**
 * Created by Jooly on 16.10.2015.
 */
public class DataContext extends SQLiteOpenHelper implements AutoCloseable {

	protected static final String DATABASE_NAME = "pacasus";
	protected static final String TABLE_USER = "user";
	protected static final String[] TABLE_USER_COLUMNS = {"ID", "login", "password"};

	public DataContext(Context context){
		super(context, DataContext.DATABASE_NAME, null, 1);
//		this.tableUserColumns = ;
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		String createUserTable =
				"CREATE TABLE " + DataContext.TABLE_USER +
						"(ID Integer Primary Key ," +
						"login varchar(10)," +
						"password Integer);";

		db.execSQL(createUserTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		// TODO: drop tables
		// => onCreate();
	}

	public void addUser(User user) throws Exception {
		try(SQLiteDatabase db = this.getWritableDatabase()) {
			ContentValues values = new ContentValues();
			values.put("ID", user.getID());
			values.put("login", user.getName());
			values.put("password", user.getPassword());
			db.insert(DataContext.TABLE_USER, null, values);
		}
		catch(Exception ex) {
			throw ex;
		}
	}

	public ArrayList<User> fetchUser(){
		SQLiteDatabase db = this.getReadableDatabase();

		ArrayList<User> userList = new ArrayList<>();
		String selectQuery = "Select * from " + DataContext.TABLE_USER;

		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
            do {
                User usr = new User();
                usr.setID(Integer.parseInt(cursor.getString(0)));
                usr.setName(cursor.getString(1));
                usr.setPassword(cursor.getString(2));

                userList.add(usr);
            } while (cursor.moveToNext());
        }
		return userList;
	}
}
