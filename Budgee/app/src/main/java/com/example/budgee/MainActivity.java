package com.example.budgee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Button click listeners
        Button addExpenseButton = findViewById(R.id.addExpenseButton);
        Button viewBudgetButton = findViewById(R.id.viewBudgetButton);
        Button settingsButton = findViewById(R.id.settingsButton);
        
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Gider ekleme sayfası açılacak", Toast.LENGTH_SHORT).show();
                // TODO: Add expense activity'yi aç
            }
        });
        
        viewBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Bütçe görüntüleme sayfası açılacak", Toast.LENGTH_SHORT).show();
                // TODO: Budget view activity'yi aç
            }
        });
        
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Ayarlar sayfası açılacak", Toast.LENGTH_SHORT).show();
                // TODO: Settings activity'yi aç
            }
        });
    }
} 