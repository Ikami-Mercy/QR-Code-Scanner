package com.ikami.qrsacnner;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ikami.api.APIInterface;
import com.ikami.constants.Cache;
import com.ikami.constants.ConstantString;
import com.ikami.constants.NumberFromString;
import com.ikami.pojo.Scan;
import com.ikami.pojo.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ikami.constants.ConstantString.CREDENTIALS;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Scan Button
        Button buttonBarCodeScan = findViewById(R.id.buttonScan);
        buttonBarCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initiate scan with our custom scan activity
                new IntentIntegrator(MainActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean main_gate = true;

        Intent intent = getIntent();

        String booth = intent.getStringExtra("booth_category");

        if(booth != null) {
            if (booth.length() > 2)
                main_gate = false;
        }
        Log.wtf("****** BOOTH ****** ", " " + booth);
        Log.wtf("****** MAIN GATE ****** ", " " + main_gate);
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String number, name;
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //String j = "{\"name\":\"Mfuon Leonard\",\"token\":\"$#%fhfggs^&*^$#jfhfyrWEsEe%^789%RTYUggdWsx\"}";
                /*Gson gson = new Gson();
                Scan scan = gson.fromJson(j,Scan.class);*/
                //show dialogue with result
                 number = NumberFromString.convertNumber(result);
                 name = NumberFromString.convertString(result);

                 if(main_gate) {
                     showResultDialogue(number, name, ConstantString.DELEGATE_VERIFICATION);
                 }else{
                     showResultDialogue(number, name, ConstantString.BOOTH_STATISTICS,booth);
                 }

            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //method to construct dialogue with scan results
    public void showResultDialogue(final String number,final String name,final String category) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Confirm Scan Results")
                .setMessage( "Name : " + name  +"\nNational Id : " + number +"\nCategory : " + category )
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /*// continue with delete
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Token is ", name);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(MainActivity.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();*/
                        Intent intent = new Intent(MainActivity.this,PostScanDetails.class);
                        intent.putExtra("name", name);
                        intent.putExtra("number", number);
                        intent.putExtra("category", category);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .show();
    }

    //method to construct dialogue with scan results
    public void showResultDialogue(final String number,final String name,final String category,final String booth) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Confirm Scan Results")
                .setMessage( "Name : " + name  +"\nNational Id : " + number + "\n Booth : " + booth + "\nCategory : " + category )
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /*// continue with delete
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Token is ", name);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(MainActivity.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();*/
                        Intent intent = new Intent(MainActivity.this,PostScanDetails.class);
                        intent.putExtra("name", name);
                        intent.putExtra("number", number);
                        intent.putExtra("category", category);
                        intent.putExtra("booth", booth);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .show();
    }



}
