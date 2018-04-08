package appkite.jordiguzman.com.backingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.data.IngredientsContract;


public class WidgetConfigureActivity extends AppCompatActivity{

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    private static final String PREFS_NAME = "AppWidget";
    private static final String PREF_PREFIX_KEY = "appwidget";
    public static String widgetText, widgetData;
    private static int[] numIngredientsWidget = new int[4];


    public static ArrayList<String> dataIngredient = new ArrayList<>();
    public WidgetConfigureActivity(){
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.widget_configure);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        final ListView listView = findViewById(R.id.list);


        String[] values = new String[]{getResources().getString(R.string.nutella_pie),
                getResources().getString(R.string.brownies),
                getResources().getString(R.string.yellowcake),
                getResources().getString(R.string.cheesecake)};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 widgetText= (String)listView.getItemAtPosition(position);
                 loadListItem(getApplicationContext());
                 widgetData(getApplicationContext(), position);
                 createWidget(getApplicationContext());

            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null){
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID){
            finish();

        }


    }


    private void createWidget( Context context){


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        AppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);

        setResult(RESULT_OK, resultValue);
        finish();
    }

    public static String widgetText(){
        return widgetText;
    }

    public static void widgetData(Context context, int position){
        int controlPosInit =0;
        int controlPosFinal =0;
        loadNumIng(context);
        switch (position){
            case 0:
                controlPosInit= 0;
                controlPosFinal = numIngredientsWidget[position];
                break;
            case 1:
                controlPosInit=  numIngredientsWidget[0];
                controlPosFinal = numIngredientsWidget[position] + numIngredientsWidget[position+1]-1;

                break;
            case 2:
                controlPosInit = numIngredientsWidget[position-1] + numIngredientsWidget[position]-1;
                controlPosFinal = numIngredientsWidget[position-1] + numIngredientsWidget[position]-1 + numIngredientsWidget[position];
                break;
            case 3:
                controlPosInit = numIngredientsWidget[position-1] + numIngredientsWidget[position]+1 + numIngredientsWidget[position];
                controlPosFinal = numIngredientsWidget[position-1] + numIngredientsWidget[position]+1 + numIngredientsWidget[position] + numIngredientsWidget[position];

                break;
        }

        StringBuilder stringBuilder= new StringBuilder();
        int cont = 1;

        for (int i = controlPosInit; i< controlPosFinal; i++){
            stringBuilder.append(cont).append(" - ").append(dataIngredient.get(i)).append("\n");
            cont++;
        }
        WidgetConfigureActivity.widgetData = stringBuilder.toString();

    }

    private static void loadNumIng(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("numIng",0);
        numIngredientsWidget[0] = sharedPreferences.getInt("numIngIdex0", 0);
        numIngredientsWidget[1] = sharedPreferences.getInt("numIngIdex1", 0);
        numIngredientsWidget[2] = sharedPreferences.getInt("numIngIdex2", 0);
        numIngredientsWidget[3] = sharedPreferences.getInt("numIngIdex3", 0);

    }

    public static void loadListItem(Context context){
        Cursor cursor = context.getContentResolver().query(IngredientsContract.IngredientsEntry.CONTENT_URI,
                null,
                null,
                null,
                IngredientsContract.IngredientsEntry._ID);

        if (cursor != null){
            while (cursor.moveToNext()){
                dataIngredient.add(cursor.getString(cursor.getColumnIndex(IngredientsContract.IngredientsEntry.COLUMN_INGREDIENT)));

            }
        }
       cursor.close();
    }

}
