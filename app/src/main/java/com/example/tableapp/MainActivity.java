package com.example.tableapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Struct;

public class MainActivity extends AppCompatActivity {
    TableLayout tableLayout;
    final int start=0;
    final int end=4;
    int rowend=3;
    final int rowstart=0;
    ImageButton btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        makeTable(start,end);
        }

    private void makeTable(int start, int end) {
        tableLayout = findViewById(R.id.tableLayout);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        for (int j = start; j <= end; j++) {
            final TableRow tr = new TableRow(this);
            tr.setPadding(5, 5, 5, 5);
            TableRow.LayoutParams layoutParams = new
                    TableRow.LayoutParams(450,
                    150);
            btn2 = new ImageButton(this);
            btn2.setImageResource(R.drawable.ic_add);
            btn2.setLayoutParams(layoutParams);
            //layoutParams.weight = 1;
            if(j!=end) {
                for (int i = rowstart; i < rowend; i++) {
                    TextView tv1 = new TextView(this);
                    //tv1.setLayoutParams(layoutParams);
                    if(i==0){
                        TableRow.LayoutParams layoutParams2 = new
                                TableRow.LayoutParams(150,150);
                        tv1.setLayoutParams(layoutParams2);
                        if(j!=0){
                        tv1.setText(String.valueOf(j));
                        tv1.setPadding(10,10,10,10);
                        tv1.setBackgroundResource(R.drawable.shape);}
                        else{
                            tv1.setText("");
                        }
                    }
                    else{
                        if(j==0){
                            String s="Column"+i;
                            tv1.setText(s);
                            tv1.setPadding(10,10,10,10);
                            tv1.setBackgroundResource(R.drawable.shape);
                            tv1.setLayoutParams(layoutParams);
                        }
                        else{
                    tv1.setText("ABCDEFG");
                            tv1.setPadding(10,10,10,10);
                    tv1.setLayoutParams(layoutParams);
                    tv1.setBackgroundResource(R.drawable.shape2);}}
                    tr.addView(tv1);
                    int finalI = i;
                    int finalJ = j;
                    tv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                            EditText et1 = findViewById(R.id.inputValue);
                            ImageButton b1 = findViewById(R.id.submitBtn);
                            et1.setVisibility(View.VISIBLE);
                            b1.setVisibility(View.VISIBLE);
                            b1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String s1 = et1.getText().toString();
                                    tv1.setText(s1);
                                    int a= finalI;
                                    int b= finalJ;
                                    DatabaseReference myRef = database.getReference("Row:"+b+","+"Column:"+a);
                                    myRef.setValue(s1);
                                    et1.setText("");
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                                    et1.setVisibility(View.GONE);
                                    b1.setVisibility(View.GONE);
                                }
                            });

                        }
                    });
                }
                if(j==0){
                    tr.addView(btn2);
                    //tr.setId(j);
                    tableLayout.addView(tr,j);
                }
                else{
                    tr.setId(j);
                tableLayout.addView(tr, j);}
            }
            else{
                final TableRow tr1 = new TableRow(this);
                tr1.setPadding(5,5,5,5);
                ImageButton btn = new ImageButton(this);
                TableRow.LayoutParams layoutParams1 = new
                        TableRow.LayoutParams(150,150);
                layoutParams1.weight = 0;
                btn.setImageResource(R.drawable.ic_add);
                //btn.setText("more");
                btn.setLayoutParams(layoutParams1);
                tr1.addView(btn);

                tableLayout.addView(tr1,end);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int first=start+4;
                        int last=end+4;
                        makeTable(first,last);
                        btn.setVisibility(View.GONE);
                    }
                });
            }
            btn2.setOnClickListener((View v)->{
                for(int i=0;i<tableLayout.getChildCount()-1;i++){
                TextView tv=new TextView(MainActivity.this);
                tv.setLayoutParams(layoutParams);
                tv.setBackgroundColor(getResources().getColor(R.color.purple_200));
                if(i==0)
                tv.setText("Column"+rowend);
                else
                    tv.setText("ABCDEFG");
                View view=tableLayout.getChildAt(i);
                TableRow row=(TableRow)view;
                row.addView(tv,rowend);
                }
                rowend+=1;
            });
        }

    }

}