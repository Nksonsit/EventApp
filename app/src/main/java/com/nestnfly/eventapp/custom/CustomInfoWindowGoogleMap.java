package com.nestnfly.eventapp.custom;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.nestnfly.eventapp.R;
import com.nestnfly.eventapp.model.InfoWindowData;
import com.nestnfly.eventapp.ui.BaseActivity;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private BaseActivity context;

    public CustomInfoWindowGoogleMap(BaseActivity ctx) {
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.map_dialog, null);
        ImageView imageView = view.findViewById(R.id.imgClose);

        return view;
    }
}
