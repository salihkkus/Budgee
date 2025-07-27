package com.example.budgee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
            welcomeText.setText("Hoşgeldiniz " + userName);
        }
        
        // Menu item click listeners
        LinearLayout settingsButton = findViewById(R.id.settingsButton);
        LinearLayout viewBudgetButton = findViewById(R.id.viewBudgetButton);
        LinearLayout spendingHistoryButton = findViewById(R.id.spendingHistoryButton);
        LinearLayout addExpenseButton = findViewById(R.id.addExpenseButton);
        LinearLayout notificationsButton = findViewById(R.id.notificationsButton);
        
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        
        viewBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BudgetViewActivity.class);
                startActivity(intent);
            }
        });
        
        spendingHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SpendingHistoryActivity.class);
                startActivity(intent);
            }
        });
        
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpenseListActivity.class);
                startActivity(intent);
            }
        });
        
        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
        });
    }
} 