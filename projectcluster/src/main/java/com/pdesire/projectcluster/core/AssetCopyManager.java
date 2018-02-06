package com.pdesire.projectcluster.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.pdesire.projectcluster.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by pdesire on 10.12.17.
 */

public class AssetCopyManager extends Activity {

    private TextView status;
    private Context mContext;
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_layout);

        mContext = this;
        mActivity = this;
        status = findViewById(R.id.progress);
        AlertDialog.Builder start = new AlertDialog.Builder(this);
        try {
            if (System.getProperty("ro.cluster.version").equals("2.0")) {
                start.setTitle("Do you want to update ?")
                        .setMessage("This applies a new cluster to your system. \n \nThe dev is not responsible for any damages on your device.")
                        .setPositiveButton("Yes", (dialog, id) -> {
                            FullClusterApply apply = new FullClusterApply();
                            apply.execute();
                        })
                        .setNegativeButton("No", (dialog, id) -> finish())
                        .create()
                        .show();
            } else {
                start.setTitle("Update manually to newest version")
                        .setMessage("Your version is not compatible with Project Cluster update. \nManually update Project Shinka-PD via flashing the latest zip")
                        .create()
                        .show();
            }
        } catch (NullPointerException n0) {
            start.setTitle("Update manually to newest version")
                    .setMessage("Your version is not compatible with Project Cluster update. \nManually update Project Shinka-PD via flashing the latest zip")
                    .create()
                    .show();
        }
    }

    public class FullClusterApply extends AsyncTask<String, String, String> {

        private final String PREFS_NAME = "cluster_prefs";
        private final String PREF_CLUSTER_SUCCESS = "cluster_success";

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Initialize Asset Handler...");
            AssetHandler asst = new AssetHandler(getApplicationContext());
            SharedPreferences preferences = getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            String dest_path = "";

            try {
                publishProgress("Initialize Asset Copy Framework...");
                String arg_assetDir = getApplicationContext().getPackageName();
                String arg_destinationDir = getApplicationContext().getFilesDir().getPath() + arg_assetDir;
                File FolderInCache = new File(arg_destinationDir);
                publishProgress("Copy Assets to Cache...");
                if (!FolderInCache.exists()) {
                    dest_path = asst.copyDirorfileFromAssetManager(arg_assetDir, arg_destinationDir);
                }
                publishProgress("Let's add some magic...");
                Runtime.getRuntime().exec("su -c mkdir /data/meli");
                Runtime.getRuntime().exec("su -c cp -fr " + dest_path + " /data/meli");
                Runtime.getRuntime().exec("su -c /system/bin/meli_module_control --reinstall");

                publishProgress("Report Successful Execution...");
                editor.putBoolean(PREF_CLUSTER_SUCCESS, true);
                editor.apply();
                publishProgress("Finished");
                return "Success";
            } catch (IOException e1) {
                editor.putBoolean(PREF_CLUSTER_SUCCESS, false);
                editor.apply();
                publishProgress("Failed");
                e1.printStackTrace();
                return "Failure";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            status.setText("Finished");
            AlertDialog.Builder start = new AlertDialog.Builder(mContext);
            start.setTitle("Do you want to reboot now?")
                    .setMessage("To apply all changes, you need to reboot your device.")
                    .setPositiveButton("Yes", (dialog, id) -> {
                        FullClusterApply apply = new FullClusterApply();
                        apply.execute();
                    })
                    .setNegativeButton("No", (dialog, id) -> mActivity.finish())
                    .create()
                    .show();

        }

        @Override
        protected void onProgressUpdate(String... values) {
            status.setText(values[0]);
        }

        @Override
        protected void onPreExecute() {}
    }
}
