package appkite.jordiguzman.com.backingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class IngredientAppDbHelper extends SQLiteOpenHelper {
    private static final String DATABSE_NAME = "bakingDb.db";
    private static final  int VERSION = 1;

    public IngredientAppDbHelper(Context context) {
        super(context, DATABSE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + IngredientsContract.IngredientsEntry.TABLE_NAME + " ("+
                IngredientsContract.IngredientsEntry._ID + " INTEGER PRIMARY KEY, " +
                IngredientsContract.IngredientsEntry.COLUMN_QUANTITY + " TEXT NOT NULL, " +
                IngredientsContract.IngredientsEntry.COLUMN_MEASUSE + " TEXT NOT NULL, " +
                IngredientsContract.IngredientsEntry.COLUMN_INGREDIENT + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE);
    }

    private static final  String DATABASE_ALTER_BAK_1 = "ALTER TABLE "
            + IngredientsContract.IngredientsEntry.TABLE_NAME + " ADD COLUMN " +
            IngredientsContract.IngredientsEntry.COLUMN_INGREDIENT + " string";

    private static final  String DATABASE_ALTER_BAK_2 = "ALTER TABLE "
            + IngredientsContract.IngredientsEntry.TABLE_NAME + " ADD COLUMN " +
            IngredientsContract.IngredientsEntry.COLUMN_MEASUSE + " string";
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < 2){
                db.execSQL(DATABASE_ALTER_BAK_1);
            }
            if (oldVersion < 3){
                db.execSQL(DATABASE_ALTER_BAK_2);
            }
    }
}
