package com.chaitupenju.bakingapp.widgets;

import android.content.Intent;

import android.widget.RemoteViewsService;

import com.chaitupenju.bakingapp.networkutils.CakesJsonDataApi;

public class BakingWidgetRemoteVuService extends RemoteViewsService {

    @Override
    public void onCreate() {
        super.onCreate();
        BakingAppWidget.setAppWidgetName(this.getApplicationContext(), CakesJsonDataApi.getFavouritePref(this.getApplicationContext()));
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingWidgetRemoteViewFactry(this.getApplicationContext(), intent);
    }

}

