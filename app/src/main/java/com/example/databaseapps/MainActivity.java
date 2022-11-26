package com.example.databaseapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static DatabaseHelper mDatabaseHelpper;
    EditText mNameEditText, mPriceEditText;
    Button mInsertBtn, mViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseHelpper = new DatabaseHelper(this);
        mNameEditText = findViewById(R.id.edtBarang);
        mPriceEditText = findViewById(R.id.edtHarga);
        mInsertBtn = findViewById(R.id.btn_insert);
        mViewBtn = findViewById(R.id.btn_view);

        //memanggil fungsi insertData
        mInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

        //berpindah ke SecondActivity
        mViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

    }

    //memasukkan data
    public void insertData(){
        String barang = mNameEditText.getText().toString();
        Integer price = Integer.valueOf(mPriceEditText.getText().toString());
        Product product = new Product(barang, price);

        //memanggil function insertRecord dari class DatabaseHelper
        mDatabaseHelpper.insertRecord(product);
        Toast.makeText(MainActivity.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
        mNameEditText.setText("");
        mPriceEditText.setText("");

    }
}