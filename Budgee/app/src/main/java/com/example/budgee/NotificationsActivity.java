package com.example.budgee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class NotificationsActivity extends Activity {
    
    private Switch budgetAlertsSwitch, dailyRemindersSwitch, weeklyReportsSwitch;
    private LinearLayout emptyState, notificationContainer;
    private ImageButton backButton, clearAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Initialize views
        initializeViews();
        setupClickListeners();
        loadNotificationSettings();
    }

    private void initializeViews() {
        budgetAlertsSwitch = findViewById(R.id.budgetAlertsSwitch);
        dailyRemindersSwitch = findViewById(R.id.dailyRemindersSwitch);
        weeklyReportsSwitch = findViewById(R.id.weeklyReportsSwitch);
        emptyState = findViewById(R.id.emptyState);
        notificationContainer = findViewById(R.id.notificationContainer);
        backButton = findViewById(R.id.backButton);
        clearAllButton = findViewById(R.id.clearAllButton);
    }

    private void setupClickListeners() {
        // Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Clear all notifications
        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllNotifications();
            }
        });

        // Notification settings switches
        budgetAlertsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveNotificationSetting("budget_alerts", isChecked);
            Toast.makeText(NotificationsActivity.this, 
                "Bütçe uyarıları " + (isChecked ? "açıldı" : "kapatıldı"), 
                Toast.LENGTH_SHORT).show();
        });

        dailyRemindersSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveNotificationSetting("daily_reminders", isChecked);
            Toast.makeText(NotificationsActivity.this, 
                "Günlük hatırlatıcılar " + (isChecked ? "açıldı" : "kapatıldı"), 
                Toast.LENGTH_SHORT).show();
        });

        weeklyReportsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveNotificationSetting("weekly_reports", isChecked);
            Toast.makeText(NotificationsActivity.this, 
                "Haftalık raporlar " + (isChecked ? "açıldı" : "kapatıldı"), 
                Toast.LENGTH_SHORT).show();
        });
    }

    private void saveNotificationSetting(String setting, boolean enabled) {
        // TODO: Save notification settings to database or SharedPreferences
        // For now, just show a toast
    }

    private void loadNotificationSettings() {
        // TODO: Load notification settings from database or SharedPreferences
        // For now, use default values from layout
        
        // Check if there are notifications to show
        boolean hasNotifications = true; // TODO: Check from database
        if (!hasNotifications) {
            emptyState.setVisibility(View.VISIBLE);
            notificationContainer.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            notificationContainer.setVisibility(View.VISIBLE);
        }
    }

    private void clearAllNotifications() {
        // TODO: Clear all notifications from database
        notificationContainer.setVisibility(View.GONE);
        emptyState.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Tüm bildirimler temizlendi", Toast.LENGTH_SHORT).show();
    }
} 