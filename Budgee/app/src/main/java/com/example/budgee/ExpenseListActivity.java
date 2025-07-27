package com.example.budgee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseListActivity extends Activity {
    
    private TextView totalAmount;
    private ImageButton backButton, addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        // Initialize views
        initializeViews();
        setupClickListeners();
        loadExpenseData();
    }

    private void initializeViews() {
        totalAmount = findViewById(R.id.totalAmount);
        backButton = findViewById(R.id.backButton);
        addButton = findViewById(R.id.addButton);
    }

    private void setupClickListeners() {
        // Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseListActivity.this, AddExpenseActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadExpenseData() {
        // TODO: Load expense data from database
        // For now, show sample data
        totalAmount.setText("₺2,450");
    }
} 