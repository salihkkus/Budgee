package com.example.budgee;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase Realtime Database bağlantısını test et
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("test").setValue("Firebase bağlantısı başarılı!")
            .addOnSuccessListener(aVoid -> {
                Log.d("FIREBASE_TEST", "Veri yazıldı, bağlantı başarılı!");
                Toast.makeText(MainActivity.this, "Firebase bağlantısı başarılı!", Toast.LENGTH_SHORT).show();
            })
            .addOnFailureListener(e -> {
                Log.e("FIREBASE_TEST", "Bağlantı hatası: " + e.getMessage());
                Toast.makeText(MainActivity.this, "Firebase bağlantı hatası: " + e.getMessage(), Toast.LENGTH_LONG).show();
            });
        
        // Menu item click listeners
        LinearLayout settingsButton = findViewById(R.id.settingsButton);
        LinearLayout viewBudgetButton = findViewById(R.id.viewBudgetButton);
        LinearLayout spendingHistoryButton = findViewById(R.id.spendingHistoryButton);
        LinearLayout addExpenseButton = findViewById(R.id.addExpenseButton);
        LinearLayout notificationsButton = findViewById(R.id.notificationsButton);
        
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Ayarlar sayfası açılacak", Toast.LENGTH_SHORT).show();
                // TODO: Settings activity'yi aç
            }
        });
        
        viewBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Bütçe durumu sayfası açılacak", Toast.LENGTH_SHORT).show();
                // TODO: Budget status activity'yi aç
            }
        });
        
        spendingHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Harcama geçmişi sayfası açılacak", Toast.LENGTH_SHORT).show();
                // TODO: Spending history activity'yi aç
            }
        });
        
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hesap hareketleri sayfası açılacak", Toast.LENGTH_SHORT).show();
                // TODO: Account transactions activity'yi aç
            }
        });
        
        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Bildirimler sayfası açılacak", Toast.LENGTH_SHORT).show();
                // TODO: Notifications activity'yi aç
            }
        });
    }
} 