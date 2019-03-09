package com.ikami.qrsacnner;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class DashboardWallet extends AppCompatActivity {

    private TabLayout tab_layout;
    private NestedScrollView nested_scroll_view;
    CardView cardViewMainGate;
    CardView cardViewBooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_wallet);
        initToolbar();

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.booth_category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

        cardViewMainGate = findViewById(R.id.main_gate);
        cardViewBooth = findViewById(R.id.booth);
        cardViewMainGate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardWallet.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        } );

        cardViewBooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner sp = findViewById(R.id.spinner);
                String spinnerString = "";
                spinnerString = sp.getSelectedItem().toString();
                int nPos = sp.getSelectedItemPosition();

                if(nPos == 0){
                    Toast.makeText(getApplicationContext(), "Please Select Booth Category above", Toast.LENGTH_LONG).show();
                }else{
                    if(spinnerString.equals("")){
                        Toast.makeText(getApplicationContext(), "Please Select Booth Category above", Toast.LENGTH_LONG).show();
                    }else{
                        Intent intent = new Intent(DashboardWallet.this,MainActivity.class);
                        intent.putExtra("booth_category", spinnerString);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("Logout");
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}

