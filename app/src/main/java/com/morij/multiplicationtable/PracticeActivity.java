package com.morij.multiplicationtable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PracticeActivity extends AppCompatActivity {
    List<String> list1, list2, list3;
    ArrayAdapter<String> adapter1, adapter2;
    ListView listView1, listView2;
    String strq, stra;
    boolean select = false;
    View v;
    int indexv1;
    TextView tv1;
    ImageView back;

    TextView tv_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        listView1 = (ListView) findViewById(R.id.listView);
        listView2 = (ListView) findViewById(R.id.listView2);
        tv1 = (TextView) findViewById(R.id.textview1);
        tv_toolbar= (TextView) findViewById(R.id.tv_toolbar);
        tv_toolbar.setText("تمرین");
        tv_toolbar.setTextColor(Color.WHITE);

        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PracticeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        String str, str2;
        int num1, num2;
        for (int i = 0; i < 12; i++) {
            num1 = rnd(1, 12);
            num2 = rnd(1, 12);
            str = num1 + " ‍‍× " + num2 + " =";
            str2 = num1 * num2 + "";
            list1.add(str);
            list2.add(str2);
        }
        adapter1 = new ArrayAdapter<>(this, R.layout.list_item, list1);
        boolean temp = true;
        int index;
        int count = 0;
        do {
            index = rnd(0, 11);
            if (list2.get(index) != "") {
                list3.add(list2.get(index));
                list2.set(index, ""); // tavize onsore morede nazar ba reshte
                count++;

            }
            if (count == 12) {
                temp = false;
            }
        } while (temp);
        adapter2 = new ArrayAdapter<>(this,R.layout.list_item, list3);

        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!select) {
                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    strq = list1.get(position);
                    select = true;
                    indexv1 = position;
                    v = view;
                } else if (select) {
                    v.setBackgroundColor(getResources().getColor(android.R.color.white));
                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    strq = list1.get(position);
                    select = true;
                    indexv1 = position;
                    v = view;

                }

            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (select) {
                    stra = list3.get(position);
                    String[] parts = strq.split("\\s+");
                    String part1 = parts[0];
                    String part2 = parts[2];
                    if (Integer.parseInt(part1) * Integer.parseInt(part2) == Integer.parseInt(stra)) {
                        list3.remove(position);
                        list1.remove(indexv1);

                        adapter2.notifyDataSetChanged();
                        adapter1.notifyDataSetChanged();
                        v.setBackgroundColor(getResources().getColor(android.R.color.white));
                        tv1.setText("درست");
                        end();
                    } else
                        tv1.setText("اشتباه");
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent =new Intent(PracticeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    int rnd(int low, int high) {
        Random rand = new Random();
        return rand.nextInt(high - low + 1) + low;
    }

    void end() {
        if (list1.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PracticeActivity.this);
            builder.setMessage("تمرین به پایان رسید");
            builder.setPositiveButton("تمرین مجدد", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    startActivity(getIntent());
                }
            });

            builder.setNegativeButton("صفحه اصلی", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(PracticeActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            AlertDialog alertDialog2 = builder.create();
            alertDialog2.show();


        }
    }
}
