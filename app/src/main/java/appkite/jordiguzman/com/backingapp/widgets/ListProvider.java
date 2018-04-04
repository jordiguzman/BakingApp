package appkite.jordiguzman.com.backingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.data.IngredientsContract;


public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<ListItem> listItemList = new ArrayList<>();
    private ArrayList<String> dataQuantity = new ArrayList<>();
    private ArrayList<String> dataMeasure = new ArrayList<>();
    private ArrayList<String> dataIngredient = new ArrayList<>();


    private Context mContext = null;

    ListProvider(Context context, Intent intent){
        this.mContext= context;
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);


        populateListItem();

    }

    private void populateListItem() {
        Cursor cursor = mContext.getContentResolver().query(IngredientsContract.IngredientsEntry.CONTENT_URI,
                null,
                null,
                null,
                IngredientsContract.IngredientsEntry._ID);

        if (cursor != null){
            while (cursor.moveToNext()){
                dataQuantity.add(cursor.getString(cursor.getColumnIndex(IngredientsContract.IngredientsEntry.COLUMN_QUANTITY)));
                dataMeasure.add(cursor.getString(cursor.getColumnIndex(IngredientsContract.IngredientsEntry.COLUMN_MEASUSE)));
                dataIngredient.add(cursor.getString(cursor.getColumnIndex(IngredientsContract.IngredientsEntry.COLUMN_INGREDIENT)));


            }
        }
        if (cursor != null)cursor.close();

        for (int i=0; i<dataQuantity.size();i++){
            ListItem listItem = new ListItem();
            listItem.quantity = mContext.getString(R.string.quantity).concat(" ").concat(dataQuantity.get(i)) ;
            listItem.measure = mContext.getString(R.string.measure).concat(" ").concat(dataMeasure.get(i));
            listItem.ingredients = mContext.getString(R.string.ingredient).concat(" ").concat(dataIngredient.get(i));
            listItemList.add(listItem);
        }
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteViews = new RemoteViews(
                mContext.getPackageName(), R.layout.list_row);
        ListItem listItem = listItemList.get(position);

         remoteViews.setTextViewText(R.id.quantity, listItem.quantity);
         remoteViews.setTextViewText(R.id.measure, listItem.measure);
         remoteViews.setTextViewText(R.id.ingredients, listItem.ingredients);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
