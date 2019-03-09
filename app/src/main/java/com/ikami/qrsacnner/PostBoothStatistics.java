package com.ikami.qrsacnner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ikami.api.APIClient;
import com.ikami.api.APIInterface;
import com.ikami.constants.ConstantString;
import com.ikami.pojo.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostBoothStatistics extends AppCompatActivity {

    Button postAttendance;
    ProgressDialog progressDialog;
    APIInterface apiInterface;
    TextView txt_name;
    TextView txt_id;
    TextView txt_category;
    TextView txt_booth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_scan_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        final Intent intent = getIntent();
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        txt_category = findViewById(R.id.delegate_category);
        txt_id = findViewById(R.id.delegate_id);
        txt_name = findViewById(R.id.delegate_name);
        txt_booth = findViewById(R.id.delegate_booth);
        final String name,number,category,booth;
        name = intent.getStringExtra("name");
        number = intent.getStringExtra("number");
        category = intent.getStringExtra("category");
        booth = intent.getStringExtra("booth");

        txt_category.setText(category);
        txt_name.setText(name);
        txt_id.setText(number);
        txt_booth.setText(booth);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        postAttendance = findViewById(R.id.btn_post_results);
        postAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postMainGateScan(name,number,category,booth);
            }
        });
    }

    private void postMainGateScan(String name, String number, String category,String booth) {
        final Context ctx = PostBoothStatistics.this;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Please wait while we Post Booth Statistics..."); // Setting Message
        progressDialog.setTitle("COG"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        Call<User> call2 = apiInterface.postStatistics(ConstantString.key,number,ConstantString.password,category,booth);
        final String nationalId = number;
        call2.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@Nullable Call<User> call, @Nullable Response<User> response) {


                if(response.body() != null){
                    User user = response.body();

                    if(user.getSuccess()){
                        Intent intent = new Intent(ctx,DashboardWallet.class);
                        showResultDialogue("Successful",user.getMsg(),ctx,false,intent);
                        progressDialog.dismiss();
                    }else{
                        Intent intent = new Intent(ctx,DashboardWallet.class);
                        showResultDialogue(" Attention ", user.getMsg() ,ctx,false,intent);
                        progressDialog.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
            }
        });
    }

    //method to construct dialogue with scan results
    public void showResultDialogue(final String title, final String msg, final Context ctx, final boolean cancel, final Intent intent) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(ctx, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(ctx);
        }
        if (cancel) {
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                    dialog.dismiss();
                }
            });
        }
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /*// continue with delete
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Result", result);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(ctx, "Result copied to clipboard", Toast.LENGTH_SHORT).show();*/
                        startActivity(intent);
                        finish();

                    }
                }).show();
    }


}
