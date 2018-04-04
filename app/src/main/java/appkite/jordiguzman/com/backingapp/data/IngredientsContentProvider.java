package appkite.jordiguzman.com.backingapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static appkite.jordiguzman.com.backingapp.data.IngredientsContract.IngredientsEntry.TABLE_NAME;


public class IngredientsContentProvider extends ContentProvider{


    public static final int INGREDIENT = 101;

    private static final UriMatcher sUrimatcher = buidUriMatcher();
    public static final String ACTION_DATA_UPDATED = "appkite.jordiguzman.com.backingapp.ACTION_DATA_UPDATED";

     public static UriMatcher buidUriMatcher(){
         UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

         uriMatcher.addURI(IngredientsContract.AUTHORITY, IngredientsContract.PATH_INGREDIENTS, INGREDIENT);

         return uriMatcher;
     }
     private IngredientAppDbHelper mIngredientAppDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mIngredientAppDbHelper = new IngredientAppDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mIngredientAppDbHelper.getReadableDatabase();
        int match = sUrimatcher.match(uri);
        Cursor retCursor;

        switch (match){
            case INGREDIENT:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
                default:
                    throw new UnsupportedOperationException("Unknow uri: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mIngredientAppDbHelper.getWritableDatabase();
        int match = sUrimatcher.match(uri);
        Uri returnUri;
        switch (match){
            case INGREDIENT:
                long id= db.insert(TABLE_NAME,  null, values);

                if (id > 0){
                    returnUri = ContentUris.withAppendedId(IngredientsContract.IngredientsEntry.CONTENT_URI, id);

                }else throw new SQLException("Failed to insert row into: " + uri);
                break;
                default:
                    throw new UnsupportedOperationException("Unknow uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mIngredientAppDbHelper.getWritableDatabase();
        int match = sUrimatcher.match(uri);
        int ingredientDeleted;
        switch (match){
            case INGREDIENT:
                String id = uri.getPathSegments().get(1);
                ingredientDeleted = db.delete(TABLE_NAME, IngredientsContract.IngredientsEntry._ID + "=?", new String[]{id});
                break;
                default:
                    throw new UnsupportedOperationException("Unknow uri: " + uri);
        }
        if (ingredientDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ingredientDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
