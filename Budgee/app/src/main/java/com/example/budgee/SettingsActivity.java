package com.example.budgee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends Activity {
    
    private EditText userNameEditText, monthlyBudgetEditText;
    private Switch darkModeSwitch, notificationsSwitch;
    private Button saveButton, resetButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize views
        initializeViews();
        setupClickListeners();
        loadSettings();
    }

    private void initializeViews() {
        userNameEditText = findViewById(R.id.userNameEditText);
        monthlyBudgetEditText = findViewById(R.id.monthlyBudgetEditText);
        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        notificationsSwitch = findViewById(R.id.notificationsSwitch);
        saveButton = findViewById(R.id.saveButton);
        resetButton = findViewById(R.id.resetButton);
        backButton = findViewById(R.id.backButton);
    }

    private void setupClickListeners() {
        // Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });

        // Reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSettings();
            }
        });
    }

    private void loadSettings() {
        // TODO: Load settings from database or SharedPreferences
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String userName = dbHelper.getUserName();
        if (userName != null && !userName.isEmpty()) {
            userNameEditText.setText(userName);
        }
        
        // Set default values
        monthlyBudgetEditText.setText("5000");
        darkModeSwitch.setChecked(false);
        notificationsSwitch.setChecked(true);
    }

    private void saveSettings() {
        String userName = userNameEditText.getText().toString().trim();
        String budgetStr = monthlyBudgetEditText.getText().toString().trim();
        boolean darkMode = darkModeSwitch.isChecked();
        boolean notifications = notificationsSwitch.isChecked();

        // Validation
        if (userName.isEmpty()) {
            userNameEditText.setError("Kullanıcı adı gerekli");
            return;
        }

        if (budgetStr.isEmpty()) {
            monthlyBudgetEditText.setError("Aylık bütçe gerekli");
            return;
        }

        try {
            double budget = Double.parseDouble(budgetStr);
            
            // TODO: Save settings to database or SharedPreferences
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            // dbHelper.updateUserName(userName);
            // dbHelper.updateMonthlyBudget(budget);
            
            Toast.makeText(this, "Ayarlar başarıyla kaydedildi", Toast.LENGTH_SHORT).show();
            
        } catch (NumberFormatException e) {
            monthlyBudgetEditText.setError("Geçerli bir bütçe girin");
        }
    }

    private void resetSettings() {
        // TODO: Reset all settings to default values
        userNameEditText.setText("");
        monthlyBudgetEditText.setText("5000");
        darkModeSwitch.setChecked(false);
        notificationsSwitch.setChecked(true);
        
        Toast.makeText(this, "Ayarlar varsayılan değerlere sıfırlandı", Toast.LENGTH_SHORT).show();
    }
} 