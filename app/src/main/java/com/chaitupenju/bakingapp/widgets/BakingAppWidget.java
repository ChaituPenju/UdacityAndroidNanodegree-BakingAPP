package com.chaitupenju.bakingapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.chaitupenju.bakingapp.BakingActivity;
import com.chaitupenju.bakingapp.R;
import com.chaitupenju.bakingapp.networkutils.CakesJsonDataApi;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    RemoteViews wViews;
    String favRecipeName;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            wViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

            favRecipeName = CakesJsonDataApi.getFavouritePref(context);
            wViews.setTextViewText(R.id.tv_recipe_name, favRecipeName);
            Intent titleIntent = new Intent(context.getApplicationContext(), BakingActivity.class);
            PendingIntent titlePendingIntent = PendingIntent.getActivity(context, 0, titleIntent, 0);
            wViews.setOnClickPendingIntent(R.id.tv_recipe_name, titlePendingIntent);

            Intent intent = new Intent(context, BakingWidgetRemoteVuService.class);
            wViews.setRemoteAdapter(R.id.lv_ingredient_widget, intent);

            appWidgetManager.updateAppWidget(appWidgetId, wViews);
        }
    }

    public static void setServiceBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, BakingAppWidget.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        wViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        favRecipeName = CakesJsonDataApi.getFavouritePref(context);
        wViews.setTextViewText(R.id.tv_recipe_name, favRecipeName);
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            //New Code Logic
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appwidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass())
            );
            appWidgetManager.notifyAppWidgetViewDataChanged(appwidgetIds, R.id.lv_ingredient_widget);
            appWidgetManager.notifyAppWidgetViewDataChanged(appwidgetIds, R.id.tv_recipe_name);
            onUpdate(context,appWidgetManager,appwidgetIds);

            /*AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, BakingAppWidget.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(cn), R.id.lv_ingredient_widget);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(cn), R.id.tv_recipe_name);*/
        }

    }
    public static void setAppWidgetName(Context context, String name){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.tv_recipe_name,name);
    }

}

