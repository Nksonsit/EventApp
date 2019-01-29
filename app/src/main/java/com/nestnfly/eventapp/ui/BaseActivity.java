package com.nestnfly.eventapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nestnfly.eventapp.R;
import com.nestnfly.eventapp.custom.TfTextView;
import com.nestnfly.eventapp.fragment.BaseFragment;
import com.nestnfly.eventapp.fragment.ContactUsFragment;
import com.nestnfly.eventapp.fragment.HomeFragment;
import com.nestnfly.eventapp.fragment.HostFragment;
import com.nestnfly.eventapp.fragment.ScheduleFragment;
import com.nestnfly.eventapp.helper.ProgressBarHelper;

import java.util.Stack;

public class BaseActivity extends AppCompatActivity {
    public int screenWidth, screenHeight;
    private Stack<Fragment> fragmentBackStack;
    private boolean showBackMessage = false;
    private boolean doubleBackToExitPressedOnce;
    private ProgressDialog dialog;
    public static int CALL_PERMISSION_REQUEST_CODE = 1225;
    public static int Location_PERMISSION_REQUEST_CODE = 1210;
    public int CURRENT_FRAGMENT = 0;
    private ProgressBarHelper progressBarHelper;

    public void setShowBackMessage(boolean showBackMessage) {
        this.showBackMessage = showBackMessage;
    }


    public Stack<Fragment> getFragments() {
        return fragmentBackStack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentBackStack = new Stack<>();
        getWidthAndHeight();

        progressBarHelper = new ProgressBarHelper(BaseActivity.this);
    }

    public void getWidthAndHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }

    public synchronized void pushAddFragments(Fragment fragment) {
        try {
            if (fragment != null) {
                fragmentBackStack.push(fragment);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();

                ft.replace(R.id.container, fragment, String.valueOf(fragmentBackStack.size()));
                ft.commit();
                manager.executePendingTransactions();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void popFragments(boolean shouldAnimate) {
        Fragment fragment = null;
        if (fragmentBackStack.size() > 1) {
            fragment = fragmentBackStack.elementAt(fragmentBackStack.size() - 2);
        } else if (!fragmentBackStack.isEmpty()) {
            fragment = fragmentBackStack.elementAt(fragmentBackStack.size() - 1);
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (fragment != null) {
            if (fragment.isAdded()) {
                ft.remove(fragmentBackStack.elementAt(fragmentBackStack.size() - 1));
                if (fragmentBackStack.size() > 1) {
                    ft.show(fragment).commit();
                }
            } else {
                ft.replace(R.id.container, fragment).commit();
            }
            fragmentBackStack.pop();
            manager.executePendingTransactions();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragmentBackStack.size() <= 1) {
//            if (showBackMessage) {
//                doubleTapOnBackPress();
//            } else {
            finish();
//            }
        } else {
            if (!((BaseFragment) fragmentBackStack.get(fragmentBackStack.size() - 1)).onFragmentBackPress()) {
                Fragment currentFragment = fragmentBackStack.get(fragmentBackStack.size() - 1);
                if (currentFragment instanceof HomeFragment) {
//                    doubleTapOnBackPress();
                    finish();
                } else {
                    popFragments(false);
                    if (fragmentBackStack.get(fragmentBackStack.size() - 1) instanceof HostFragment) {
                        loadBottomUI(1);
                    } else if (fragmentBackStack.get(fragmentBackStack.size() - 1) instanceof ScheduleFragment) {
                        loadBottomUI(1);
                    } else if (fragmentBackStack.get(fragmentBackStack.size() - 1) instanceof ContactUsFragment) {
                        loadBottomUI(1);
                    }

                }

            }
        }
    }

    void doubleTapOnBackPress() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 10000);
    }

    public void showProgressDialog(boolean isCancelable) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait..");
        dialog.setCancelable(isCancelable);
        dialog.show();
    }

    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * this method load dashboard fragment
     */
    /*public void loadHomeFragment() {
        ((TfTextView) findViewById(R.id.txtCurrentLocation)).setText(Functions.getLocationOfaddressHeader(context, Preferences.getInstance(context).getString(Preferences.KEY_LATITUDE),Preferences.getInstance(context).getString(Preferences.KEY_LONGITUDE)));
        isVisibleLocation(true);
        isVisibleChangePassword(false);
        isVisibleSearch(true);
        isVisibleHeaderImage(false);
        setHeaderFRomBaseActivity(getString(R.string.app_name));
        getFragments().clear();
        Fragment fragmentToPush = DashBoardFragment.getFragment(this);
        pushAddFragments(fragmentToPush, true, true);
    }*/
    public void loadBottomUI(int selectedPosition) {
        switch (selectedPosition) {
            case 0:

                ((RelativeLayout) findViewById(R.id.llRegister)).setVisibility(View.VISIBLE);

                ((TfTextView) findViewById(R.id.txtHome)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));
                ((TfTextView) findViewById(R.id.txtHost)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));
                ((TfTextView) findViewById(R.id.txtSchedule)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));
                ((TfTextView) findViewById(R.id.txtContactUs)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));

                ((ImageView) findViewById(R.id.imgHome)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgHost)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgSchedule)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgContactUs)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);

                ((LinearLayout) findViewById(R.id.llHome)).setEnabled(false);
                ((LinearLayout) findViewById(R.id.llHost)).setEnabled(false);
                ((LinearLayout) findViewById(R.id.llSchedule)).setEnabled(false);
                ((LinearLayout) findViewById(R.id.llContactUs)).setEnabled(false);

//                ((TfTextView) findViewById(R.id.txtTitle)).setText("Home");
                break;
            case 1:
                ((RelativeLayout) findViewById(R.id.llRegister)).setVisibility(View.GONE);

                ((TfTextView) findViewById(R.id.txtHome)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.white));
                ((TfTextView) findViewById(R.id.txtHost)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));
                ((TfTextView) findViewById(R.id.txtSchedule)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));
                ((TfTextView) findViewById(R.id.txtContactUs)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));

                ((ImageView) findViewById(R.id.imgHome)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgHost)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgSchedule)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgContactUs)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);

                ((LinearLayout) findViewById(R.id.llHome)).setEnabled(false);
                ((LinearLayout) findViewById(R.id.llHost)).setEnabled(true);
                ((LinearLayout) findViewById(R.id.llSchedule)).setEnabled(true);
                ((LinearLayout) findViewById(R.id.llContactUs)).setEnabled(true);

//                ((TfTextView) findViewById(R.id.txtTitle)).setText("Home");

                CURRENT_FRAGMENT = 0;
                pushAddFragments(new HomeFragment(BaseActivity.this));
                break;
            case 2:
                ((RelativeLayout) findViewById(R.id.llRegister)).setVisibility(View.GONE);

                ((ImageView) findViewById(R.id.imgHome)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgHost)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgSchedule)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgContactUs)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);

                ((TfTextView) findViewById(R.id.txtHome)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));
                ((TfTextView) findViewById(R.id.txtHost)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.white));
                ((TfTextView) findViewById(R.id.txtSchedule)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));
                ((TfTextView) findViewById(R.id.txtContactUs)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));

                ((LinearLayout) findViewById(R.id.llHome)).setEnabled(true);
                ((LinearLayout) findViewById(R.id.llHost)).setEnabled(false);
                ((LinearLayout) findViewById(R.id.llSchedule)).setEnabled(true);
                ((LinearLayout) findViewById(R.id.llContactUs)).setEnabled(true);

//                ((TfTextView) findViewById(R.id.txtTitle)).setText(PrefUtils.getLanguageObj(BaseActivity.this).getSbm());

                CURRENT_FRAGMENT = 1;
                pushAddFragments(new HostFragment(BaseActivity.this));
                break;
            case 3:
                ((RelativeLayout) findViewById(R.id.llRegister)).setVisibility(View.GONE);

                ((ImageView) findViewById(R.id.imgHome)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgHost)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgSchedule)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgContactUs)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);

                ((TfTextView) findViewById(R.id.txtHome)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));
                ((TfTextView) findViewById(R.id.txtHost)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));
                ((TfTextView) findViewById(R.id.txtSchedule)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.white));
                ((TfTextView) findViewById(R.id.txtContactUs)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));

                ((LinearLayout) findViewById(R.id.llHome)).setEnabled(true);
                ((LinearLayout) findViewById(R.id.llHost)).setEnabled(true);
                ((LinearLayout) findViewById(R.id.llSchedule)).setEnabled(false);
                ((LinearLayout) findViewById(R.id.llContactUs)).setEnabled(true);

                CURRENT_FRAGMENT = 2;
//                ((TfTextView) findViewById(R.id.txtTitle)).setText(PrefUtils.getLanguageObj(BaseActivity.this).getMy_downloads());

                pushAddFragments(new ScheduleFragment(BaseActivity.this));
                break;
            case 4:
                ((RelativeLayout) findViewById(R.id.llRegister)).setVisibility(View.GONE);

                ((ImageView) findViewById(R.id.imgHome)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgHost)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgSchedule)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.inactive), android.graphics.PorterDuff.Mode.MULTIPLY);
                ((ImageView) findViewById(R.id.imgContactUs)).setColorFilter(ContextCompat.getColor(BaseActivity.this, R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);

                ((TfTextView) findViewById(R.id.txtHome)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));
                ((TfTextView) findViewById(R.id.txtHost)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));
                ((TfTextView) findViewById(R.id.txtSchedule)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.inactive));
                ((TfTextView) findViewById(R.id.txtContactUs)).setTextColor(ContextCompat.getColor(BaseActivity.this, R.color.white));

                ((LinearLayout) findViewById(R.id.llHome)).setEnabled(true);
                ((LinearLayout) findViewById(R.id.llHost)).setEnabled(true);
                ((LinearLayout) findViewById(R.id.llSchedule)).setEnabled(true);
                ((LinearLayout) findViewById(R.id.llContactUs)).setEnabled(false);

                CURRENT_FRAGMENT = 3;
//                ((TfTextView) findViewById(R.id.txtTitle)).setText(PrefUtils.getLanguageObj(BaseActivity.this).getProfile());

                pushAddFragments(new ContactUsFragment(BaseActivity.this));
                break;

        }
    }

    public void requestFocus(EditText editText) {
        if (editText != null) {
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void hideKeyBoard(EditText editText) {
        if (editText != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!getFragments().empty()) {
            Fragment fragment = getFragments().get(getFragments().size() - 1);
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    /*public void hideBackBtn() {
        ((ImageView) findViewById(R.id.imgBack)).setVisibility(View.GONE);
    }

    public void showBackBtn() {
        ((ImageView) findViewById(R.id.imgBack)).setVisibility(View.VISIBLE);
    }

    public void setHeaderTitle(String title) {
        ((TfTextView) findViewById(R.id.txtTitle)).setText(title);
    }*/


    public void showProgress() {

        progressBarHelper.showProgressDialog();
    }


    public void closeProgress() {

        progressBarHelper.hidProgressDialog();
    }
}
