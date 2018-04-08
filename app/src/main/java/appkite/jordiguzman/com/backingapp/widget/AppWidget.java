package appkite.jordiguzman.com.backingapp.widget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import appkite.jordiguzman.com.backingapp.R;
import appkite.jordiguzman.com.backingapp.ui.Splash;


public class AppWidget extends AppWidgetProvider{



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }




    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId){
        Intent intent = new Intent(context, Splash.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        CharSequence widgetText = WidgetConfigureActivity.widgetText();


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.iv_widget_logo, pendingIntent);
        views.setTextViewText(R.id.data_recipe, WidgetConfigureActivity.widgetData);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



}

