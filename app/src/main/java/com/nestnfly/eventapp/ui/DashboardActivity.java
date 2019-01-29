package com.nestnfly.eventapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nestnfly.eventapp.R;
import com.nestnfly.eventapp.custom.TfTextView;

public class DashboardActivity extends BaseActivity {

    private LinearLayout container;
    private ImageView imgHome;
    private TfTextView txtHome;
    private LinearLayout llHome;
    private ImageView imgHost;
    private TfTextView txtHost;
    private LinearLayout llHost;
    private ImageView imgSchedule;
    private TfTextView txtSchedule;
    private LinearLayout llSchedule;
    private ImageView imgContactUs;
    private TfTextView txtContactUs;
    private LinearLayout llContactUs;
    private LinearLayout llLoginRegister;
    private ImageView imgRegister;
    private ImageView imgCancel;
    private RelativeLayout llRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
        actionListener();
    }

    private void actionListener() {
        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBottomUI(1);
            }
        });

        llHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBottomUI(2);
            }
        });
        llSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBottomUI(3);
            }
        });
        llContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBottomUI(4);
            }
        });
    }

    private void init() {
        llLoginRegister = (LinearLayout) findViewById(R.id.llLoginRegister);
        llContactUs = (LinearLayout) findViewById(R.id.llContactUs);
        txtContactUs = (TfTextView) findViewById(R.id.txtContactUs);
        imgContactUs = (ImageView) findViewById(R.id.imgContactUs);
        llSchedule = (LinearLayout) findViewById(R.id.llSchedule);
        txtSchedule = (TfTextView) findViewById(R.id.txtSchedule);
        imgSchedule = (ImageView) findViewById(R.id.imgSchedule);
        llHost = (LinearLayout) findViewById(R.id.llHost);
        llRegister= (RelativeLayout) findViewById(R.id.llRegister);
        txtHost = (TfTextView) findViewById(R.id.txtHost);
        imgHost = (ImageView) findViewById(R.id.imgHost);
        llHome = (LinearLayout) findViewById(R.id.llHome);
        txtHome = (TfTextView) findViewById(R.id.txtHome);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgRegister = (ImageView) findViewById(R.id.imgRegister);
        imgCancel = (ImageView) findViewById(R.id.imgCancel);
        container = (LinearLayout) findViewById(R.id.container);

        loadBottomUI(1);


        imgRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBottomUI(0);
            }
        });
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBottomUI(CURRENT_FRAGMENT+1);
            }
        });
    }
}
