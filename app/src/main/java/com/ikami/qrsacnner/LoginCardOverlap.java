package com.ikami.qrsacnner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ikami.api.APIClient;
import com.ikami.api.APIInterface;
import com.ikami.constants.Cache;
import com.ikami.constants.ConstantString;
import com.ikami.pojo.User;

import retrofit2.Call;

import retrofit2.Callback;
import retrofit2.Response;

import static com.ikami.constants.ConstantString.CREDENTIALS;

public class LoginCardOverlap extends AppCompatActivity {

    APIInterface apiInterface;
    private View parent_view;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_card_overlap);
        parent_view = findViewById(android.R.id.content);
        Button btn_sign_in = findViewById(R.id.btn_sign_in);
        final EditText editText = findViewById(R.id.user_name_text);
        final String TAG = " ****** Login Card Activity ******** ";
        apiInterface = APIClient.getClient().create(APIInterface.class);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editText.getText().toString();
                String password = "mOC-0HqSiapc_pr9eiz9Fe1Yj_QNGGkP";

                progressDialog = new ProgressDialog(LoginCardOverlap.this);
                progressDialog.setMessage("Authenticating..."); // Setting Message
                progressDialog.setTitle("COG"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);

                Call<User> call2 = apiInterface.checkIfExist(username, ConstantString.key,ConstantString.password);
                call2.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@Nullable Call<User> call, @Nullable Response<User> response) {
                        /*assert userList != null;
                        Toast.makeText(getApplicationContext(), " ****** HERE *** " + userList.getMsg(), Toast.LENGTH_SHORT).show();*/
                        Context ctx = LoginCardOverlap.this;
                        if(response.body() != null){
                            User user = response.body();

                            if(user.getSuccess()){

                                if(Cache.getSessionUser(ctx,CREDENTIALS,"username").isEmpty())
                                    Cache.save(ctx,CREDENTIALS,username);
                                Intent intent = new Intent(getApplicationContext(),DashboardWallet.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                            }else {
                                Util.showResultDialogue("Authentication Failure","Username "+ username + " Does not Exist..!",LoginCardOverlap.this,false);
                                progressDialog.dismiss();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        call.cancel();
                    }
                });



//                    /*final String URL ="https://expoadmin.cog.go.ke/mobile-app/";
//                    RequestQueue requestQueue = Volley.newRequestQueue(LoginCardOverlap.this);
//
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL + "check-if-admin-exist?username=" + username , new com.android.volley.Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.wtf(TAG, "onResponse: ******* ");
//                           *//* if(validateUserName(apiInterface)){
//                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                                intent.putExtra(EXTRA_MESSAGE, message);
//                                startActivity(intent);
//                            }*//*
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                        }
//                    });
//                    requestQueue.add(stringRequest);
//
//            *//*if(validateUserName(apiInterface)){
//                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                    intent.putExtra(EXTRA_MESSAGE, message);
//                    startActivity(intent);
//                }*//*
//                    *//*//*/registration logic
//                    Intent intent = new Intent(getApplicationContext(), EmployeeDashboard.class);
//                    startActivity(intent);*/
            }
        });
    }




}
