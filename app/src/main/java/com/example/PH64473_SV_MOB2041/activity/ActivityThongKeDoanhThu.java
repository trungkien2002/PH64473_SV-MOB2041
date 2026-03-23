package com.example.PH64473_SV_MOB2041.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.PH64473_SV_MOB2041.R;

public class ActivityThongKeDoanhThu extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_ke_doanh_thu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Quản lý Doanh thu");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        EditText edtTuNgay = findViewById(R.id.edt_TuNgay);
        EditText edtDenNgay = findViewById(R.id.edt_DenNgay);

        edtTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                int year = calendar.get(java.util.Calendar.YEAR);
                int month = calendar.get(java.util.Calendar.MONTH);
                int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);

                android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(
                        ActivityThongKeDoanhThu.this,
                        new android.app.DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                                edtTuNgay.setText(date);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        edtDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                int year = calendar.get(java.util.Calendar.YEAR);
                int month = calendar.get(java.util.Calendar.MONTH);
                int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);

                android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(
                        ActivityThongKeDoanhThu.this,
                        new android.app.DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                                edtDenNgay.setText(date);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
}