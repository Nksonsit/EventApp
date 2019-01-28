package com.nestnfly.eventapp.helper;

import android.app.ProgressDialog;

import com.nestnfly.eventapp.ui.BaseActivity;


/**
 * Created by raghavthakkar on 18-10-2016.
 */
public class ProgressBarHelper implements ProgressListener {

    private BaseActivity context;
    ProgressDialog fpd;
    //FragmentManager fragmentManager;


    public ProgressBarHelper(BaseActivity context) {
        this.context=context;
        fpd = new ProgressDialog(context);
        fpd.setCanceledOnTouchOutside(false);
        fpd.setCancelable(false);

//        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
//        fragmentManager = appCompatActivity.getSupportFragmentManager();
//
//        List<Integer> imageList = new ArrayList<Integer>();
//        imageList.add(R.drawable.ic_hourglass_empty_black_24dp);
//        imageList.add(R.drawable.ic_hourglass_full_black_24dp);
//        fpd.setImageList(imageList);
//        fpd.setCanceledOnTouchOutside(false);
//        fpd.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
//        fpd.setDimAmount(0.8f);

    }

    @Override
    public void showProgressDialog() {
//        if (fpd != null && fragmentManager != null) {
//
//            if (!fpd.isAdded()) {
//                fpd.show(fragmentManager, "FlipProgressDialog");
//            } else {
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.root, fpd, "FlipProgressDialog");
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        }
        if (fpd != null) {
            fpd.setMessage("Please wait..");
            fpd.show();
        }
    }

    @Override
    public void hidProgressDialog() {
        if (fpd != null && fpd.isShowing()) {
            try {
                fpd.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void showProgressDialog(String msg) {
//        if (progressDialog != null) {
//            if (!TextUtils.isEmpty(msg)) {
//                progressDialog.setMessage(msg);
//            }
//            progressDialog.show();
//        }
//    }

}
