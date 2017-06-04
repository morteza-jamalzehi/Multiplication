package com.morij.multiplicationtable;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MatchActivity extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4, tv5, tv6;
    EditText ed1;
    Button btn1;
    int point = 0;
    Timer timer;
    int sec, customSec;
    int totalSec = 0, min = 0;
    int totalQuestions = 0, correct = 0, incorrect, none = 0;
    ImageView back;
    TextView tv_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        sec = getIntent().getExtras().getInt("time");
        customSec = sec;

        tv1 = (TextView) findViewById(R.id.tView1);
        tv2 = (TextView) findViewById(R.id.tView2);
        tv3 = (TextView) findViewById(R.id.tView3);
        tv4 = (TextView) findViewById(R.id.tView4);
        tv5 = (TextView) findViewById(R.id.tView5);
        tv6 = (TextView) findViewById(R.id.tView6);
        //
        tv_toolbar = (TextView) findViewById(R.id.tv_toolbar);
        tv_toolbar.setText("ضرب زمان دار");
        tv_toolbar.setTextColor(Color.WHITE);

        //
        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btn1 = (Button) findViewById(R.id.btnNext);
        ed1 = (EditText) findViewById(R.id.etAnswer);
        ed1.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(ed1, InputMethodManager.SHOW_IMPLICIT);


        tv1.setText(rnd(1, 6) + " × " + rnd(1, 6) + " = ");
        totalQuestions++;
        tv3.setText(point + "");

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                sec--;
                tv4.setText(sec + "");
                if (sec == 0) {
                    point -= 1;
                    none++;
                    sec = customSec;
                    if (point >= 0) {
                        tv1.setText(rnd(1, 6) + " × " + rnd(1, 6) + " = ");
                        totalQuestions++;
                    }
                    tv3.setText(point + "");
                }
            }
        };

        final Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                totalSec++;
                if (totalSec == 60) {
                    min++;
                    totalSec = 0;
                }
                tv5.setText(min + ":" + totalSec);

            }
        };

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(runnable);
                runOnUiThread(runnable2);
            }
        }, 0, 1000);

        tv3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                lose();

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edValue = ed1.getText().toString();
                if (!edValue.equalsIgnoreCase("")) {
                    String str = tv1.getText().toString();
                    int num1, num2;
                    num1 = Integer.parseInt(str.split("\\s+")[0]);
                    num2 = Integer.parseInt(str.split("\\s+")[2]);
                    if (num1 * num2 == Integer.parseInt(ed1.getText() + "")) {
                        tv2.setText("درست");
                        point += 3;
                        correct++;
                        tv2.setTextColor(getResources().getColor(R.color.green));
                    } else {
                        tv2.setText("اشتباه");
                        point -= 1;
                        incorrect++;
                        tv2.setTextColor(getResources().getColor(R.color.red));
                    }
                    tv3.setText(point + "");
                    tv1.setText(rnd(1, 6) + " × " + rnd(1, 6) + " = ");
                    totalQuestions++;
                    ed1.setText("");
                    sec = customSec;
                } else {
                    tv1.setText(rnd(1, 6) + " × " + rnd(1, 6) + " = ");
                    totalQuestions++;
                    none++;
                    point -= 1;
                    tv3.setText(point + "");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(MatchActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    int rnd(int low, int high) {
        Random rand = new Random();
        return rand.nextInt(high - low + 1) + low;
    }

    void lose() {
        String dialog = "یازی به پایان رسبد\n";
        dialog += "تعداد کل سوالات " + totalQuestions + "\n";
        dialog += "پاسخ های درست " + correct + "\n";
        dialog += "پاسخ های اشتباه " + incorrect + "\n";
        dialog += "سوالات بی پاسخ " + none + "\n";
        dialog += "امتیاز " + point + "\n";

        AlertDialog.Builder builder = new AlertDialog.Builder(MatchActivity.this);
        builder.setMessage(dialog);
        builder.setPositiveButton("شروع مجدد", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(getIntent());
            }
        });
        builder.setNegativeButton("صفحه اصلی", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(MatchActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (point < 0) {
            tv6.setText("شما باختید");
            timer.cancel();
            AlertDialog alertDialog2 = builder.create();
            alertDialog2.show();
        }
        if (min == 2) {
            tv1.setText("پایان");
            timer.cancel();
            AlertDialog alertDialog2 = builder.create();
            alertDialog2.show();
        }
    }
}
