package com.test.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.test.listeners.AlertListener;
import com.test.R;
/**
 * Created by pyeddula on 8/6/16.
 */
public class BaseActivity extends AppCompatActivity {
    public View mCustomView=null;
    ProgressDialog progressBar=null;
    AlertUtil alertUtil = null;
    BroadcastReceiver broadcastReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);

        LayoutInflater mInflater = LayoutInflater.from(this);

        mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }

    public void ShowProgressBar(String strMsg)
    {
        if(progressBar != null)
        {
            progressBar.dismiss();
        }
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);
        progressBar.setMessage(strMsg);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OnCancelProgressBar();

            }
        });
        progressBar.show();
    }
    public void UpdateProgressBarText(String strMsg) {
        if(progressBar != null) {
            progressBar.setMessage(strMsg);
        }
    }

    public void OnCancelProgressBar()
    {

    }

    public void StopProgressBar()
    {
        if(progressBar != null) {
            progressBar.dismiss();
        }
        progressBar = null;
    }
    public AlertUtil getAlertUtil()
    {
        if(alertUtil == null)
        {
            alertUtil = new AlertUtil();
        }
        return alertUtil;
    }
    public class AlertUtil {

        private static final int MESSAGE_ALERT = 1;
        private static final int CONFIRM_ALERT = 2;
        private static final int DECISION_ALERT = 3;

        public void messageAlert(final Context ctx, final String title, final String message) {
            runOnUiThread(new Thread(new Runnable() {
                public void run() {
                    showAlertDialog(MESSAGE_ALERT, ctx, title, message, null, "OK");
                }
            }));
        }

        public  void confirmationAlert(final Context ctx, final String title, final String message, final AlertListener aAlertListener,final String... buttonNames) {
            runOnUiThread(new Thread(new Runnable() {
                public void run() {
                    showAlertDialog(CONFIRM_ALERT, ctx, title, message, aAlertListener, buttonNames);
                }
            }));
        }

        public void decisionAlert(final Context ctx, final String title, final String message, final AlertListener aAlertListener, final String... buttonNames) {
            runOnUiThread(new Thread(new Runnable() {
                public void run() {
                    showAlertDialog(DECISION_ALERT, ctx, title, message, aAlertListener, buttonNames);
                }
            }));
        }

        public void showAlertDialog(int alertType, Context ctx, String title, String message, AlertListener aAlertListener, String... buttonNames) {
            if (title == null) title = ctx.getResources().getString(R.string.app_name);
            if (message == null) message = "default message";
            final AlertListener alertListener = aAlertListener;
            final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(title)
                    .setMessage(message)

                    // false = pressing back button won't dismiss this alert
                    .setCancelable(false)

                    // icon on the left of title
                    .setIcon(android.R.drawable.ic_dialog_alert);
            switch (alertType) {
                case MESSAGE_ALERT: {
                    builder.setNegativeButton(buttonNames[buttonNames.length - 1], new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            if (alertListener != null) {
                                alertListener.onNegativeButtonClicked();
                            }
                        }
                    }).create().show();
                }
                break;

                case CONFIRM_ALERT: {
                    builder.setPositiveButton(buttonNames[0], new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            if (alertListener != null) {
                                alertListener.onPositiveButtonClicked();
                            }
                        }
                    });
                    builder.setNegativeButton(buttonNames[buttonNames.length - 1], new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            if (alertListener != null) {
                                alertListener.onNegativeButtonClicked();
                            }
                        }
                    });
                    builder.create().show();
                }
                break;

                case DECISION_ALERT: {
                    builder.setNegativeButton(buttonNames[buttonNames.length - 1], new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            if (alertListener != null) {
                                alertListener.onNegativeButtonClicked();
                            }
                        }
                    });
                    builder.create().show();
                }
                break;
            }
        }
    }

    public static void OnNegativeAlertButtonSelected()
    {

    }
}

