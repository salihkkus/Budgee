package com.example.budgee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddExpenseActivity extends Activity {
    
    private EditText amountEditText, descriptionEditText;
    private Spinner categorySpinner;
    private Button saveButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // Initialize views
        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        amountEditText = findViewById(R.id.amountEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        categorySpinner = findViewById(R.id.categorySpinner);
        saveButton = findViewById(R.id.saveButton);
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
                saveExpense();
            }
        });
    }

    private void saveExpense() {
        String amountStr = amountEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();

        // Validation
        if (amountStr.isEmpty()) {
            amountEditText.setError("Tutar gerekli");
            return;
        }

        if (description.isEmpty()) {
            descriptionEditText.setError("Açıklama gerekli");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            
            // TODO: Save expense to database
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            // dbHelper.addExpense(amount, description, category);
            
            Toast.makeText(this, "Harcama başarıyla kaydedildi", Toast.LENGTH_SHORT).show();
            finish();
            
        } catch (NumberFormatException e) {
            amountEditText.setError("Geçerli bir tutar girin");
        }
    }
} 