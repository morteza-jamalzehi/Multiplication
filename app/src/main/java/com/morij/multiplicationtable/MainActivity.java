package com.morij.multiplicationtable;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView imageView;
    ListView listView3;
    Spinner spinner;
    List<String> listSp, listSpSelect;
    SpinnerAdapter a3;
    ArrayAdapter<String> adapter3;
    Typeface myfont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myfont = Typeface.createFromAsset(getAssets(), "b.ttf");
        try {
            Field st = Typeface.class.getDeclaredField("MONOSPACE");
            st.setAccessible(true);
            st.set(null, myfont);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.navigation_activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        imageView = (ImageView) findViewById(R.id.hamburger);
        listView3 = (ListView) findViewById(R.id.listView3);
        spinner = (Spinner) findViewById(R.id.spinner);
        listSp = new ArrayList<>();
        listSpSelect = new ArrayList<>();
        //



        for (int i = 1; i <= 12; i++) {
            listSp.add("عدد" + i);
        }
        a3 = new ArrayAdapter<>(this,R.layout.list_item, listSp);
        spinner.setAdapter(a3);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listSpSelect.clear();
                for (int i = 1; i <= 12; i++) {
                    listSpSelect.add(i + "×" + (position + 1 + "=" + i * (position + 1)));
                }
                adapter3 = new ArrayAdapter<>(MainActivity.this, R.layout.listviewlearning,
                        R.id.txtListView, listSpSelect);
                listView3.setAdapter(adapter3);

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.practice) {

                    Intent intent = new Intent(MainActivity.this, PracticeActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.END);
                    finish();
                } else if (id == R.id.match) {

                    final Intent intent = new Intent(MainActivity.this, MatchActivity.class);
                    final Dialog dialog = new Dialog(MainActivity.this);

                    dialog.setContentView(R.layout.dialog_level);


                    dialog.findViewById(R.id.btn_easy).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra("time", 10);
                            startActivity(intent);
                            dialog.dismiss();
                            finish();
                        }
                    });
                    dialog.show();
                    drawerLayout.closeDrawer(GravityCompat.END);

                    final Intent intentHard=new Intent(MainActivity.this,HardActivity.class);
                    dialog.findViewById(R.id.btn_hard).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                          intentHard.putExtra("time",5);
                            startActivity(intentHard);
                            dialog.dismiss();
                            finish();
                        }
                    });
                    dialog.show();
                    drawerLayout.closeDrawer(GravityCompat.END);
                }else if (id==R.id.exit){
                    finish();
                    System.exit(0);
                }
                else if (id==R.id.about){
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.END);
                    finish();
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

}
