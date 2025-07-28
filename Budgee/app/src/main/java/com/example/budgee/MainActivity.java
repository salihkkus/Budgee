package com.example.budgee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Veritabanından kullanıcı adını al
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String userName = dbHelper.getUserName();

        // Hoşgeldin mesajını göster
        TextView welcomeText = findViewById(R.id.welcomeText);
        if (welcomeText != null) {
            welcomeText.setText(getString(R.string.welcome_message) + " " + userName);
        }

        // Menü butonları




        // Maskot tıklanınca ChatActivity'e geç
        ImageView maskot = findViewById(R.id.maskotIcon);
        if (maskot != null) {
            maskot.setOnClickListener(v ->
                    startActivity(new Intent(MainActivity.this, ChatActivity.class)));
        }
    }
}
