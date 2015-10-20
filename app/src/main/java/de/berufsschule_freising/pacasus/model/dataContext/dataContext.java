package de.berufsschule_freising.pacasus.model.dataContext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import de.berufsschule_freising.pacasus.model.entity.User;

/**
 * Created by Jooly on 16.10.2015.
 */
public class DataContext extends SQLiteOpenHelper {

	protected static final String DATABASE_NAME = "pacasus";
	protected static final String TABLE_USER = "user";
	protected static final String[] TABLE_USER_COLUMNS = {"ID", "login", "password"};

	public DataContext(Context context){
		super(context, DATABASE_NAME, null, 1);
//		this.tableUserColumns = ;
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		String createUserTable =
				"CREATE TABLE " + TABLE_USER +
						"(ID Integer Primary Key ," +
						"login varchar(10)," +
						"password Integer);";

		db.execSQL(createUserTable);

	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

	}

	public void addUser(User user){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("ID", user.getID());
		values.put("login", user.getName());
		values.put("password", user.getPassword());

		db.insert(TABLE_USER, null, values);
	}

	public ArrayList<User> fetchUser(){
		SQLiteDatabase db = this.getReadableDatabase();

		ArrayList<User> userList = new ArrayList<>();
		String selectQuery = "Select * from " + TABLE_USER;

		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()){
			while (cursor.moveToNext()){
				User usr = new User();
				usr.setID(Integer.parseInt(cursor.getString(0)));
				usr.setName(cursor.getString(1));
				usr.setPassword(cursor.getString(2));

				userList.add(usr);
			}
		}


		return userList;
	}
}
