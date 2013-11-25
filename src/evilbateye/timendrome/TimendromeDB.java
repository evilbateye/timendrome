package evilbateye.timendrome;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimendromeDB extends SQLiteOpenHelper {

	private SQLiteDatabase instance = null;
	
	public static final String DATABASE_NAME = "timendrome.db";
	public static final int DATABASE_VERSION = 1;
	
	public static final String TABLE = "RegexItems";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ISENABLED = "isEnabled";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_REGEX = "regex";

	public TimendromeDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		instance = this.getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_ISENABLED + " INTEGER NOT NULL, " +
					COLUMN_NAME + " TEXT NOT NULL, " +
					COLUMN_REGEX + " TEXT NOT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE);
		this.onCreate(db);
	}
	
	public long insert(TimendromeRegexItem item) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ISENABLED, item.isEnabled());
		cv.put(COLUMN_NAME, item.name());
		cv.put(COLUMN_REGEX, item.regex());
		
		long id = instance.insert(TABLE, null, cv);
		item.setId(id);
		return id;
	}
	
	public int update(TimendromeRegexItem item) {
		if (item.id() < 0) return -1;
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ISENABLED, item.isEnabled());
		cv.put(COLUMN_NAME, item.name());
		cv.put(COLUMN_REGEX, item.regex());
		return instance.update(TABLE, cv, COLUMN_ID + "=" + item.id(), null);
	}
	
	public int delete(TimendromeRegexItem item) {
		if (item.id() < 0) return -1;
		return instance.delete(TABLE, COLUMN_ID + "=" + item.id(), null);
	}
	
	public TimendromeRegexItem select(long id) {
		Cursor c = instance.query(TABLE, null, COLUMN_ID + "=" + id, null, null, null, null);
		TimendromeRegexItem item = null;
		if (c.moveToFirst()) {
			item = new TimendromeRegexItem();
			item.setId(c.getInt(0));
			item.setEnabled(c.getInt(1) == 1);
			item.setName(c.getString(2));
			item.setRegex(c.getString(3));
		}
		c.close();
		return item;
	}
	
	public List<TimendromeRegexItem> selectAll() {
		List<TimendromeRegexItem> list = new ArrayList<TimendromeRegexItem>();
		Cursor c = instance.query(TABLE, null, null, null, null, null, null);
		if (c.moveToFirst()) {
			do {
				TimendromeRegexItem item = new TimendromeRegexItem();
				item.setId(c.getInt(0));
				item.setEnabled(c.getInt(1) == 1);
				item.setName(c.getString(2));
				item.setRegex(c.getString(3));
				list.add(item);
			} while (c.moveToNext());
		}
		c.close();
		return list;
	}
}
