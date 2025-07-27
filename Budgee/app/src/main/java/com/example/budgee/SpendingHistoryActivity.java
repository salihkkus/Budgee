package com.example.budgee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SpendingHistoryActivity extends Activity {
    
    private TextView monthlyTotal, weeklyTotal;
    private Button allButton, foodButton, transportButton, entertainmentButton;
    private LinearLayout emptyState, expenseListContainer;
    private ImageButton backButton, filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_history);

        // Initialize views
        initializeViews();
        setupClickListeners();
        loadExpenseData();
    }

    private void initializeViews() {
        monthlyTotal = findViewById(R.id.monthlyTotal);
        weeklyTotal = findViewById(R.id.weeklyTotal);
        allButton = findViewById(R.id.allButton);
        foodButton = findViewById(R.id.foodButton);
        transportButton = findViewById(R.id.transportButton);
        entertainmentButton = findViewById(R.id.entertainmentButton);
        emptyState = findViewById(R.id.emptyState);
        expenseListContainer = findViewById(R.id.expenseListContainer);
        backButton = findViewById(R.id.backButton);
        filterButton = findViewById(R.id.filterButton);
    }

    private void setupClickListeners() {
        // Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Filter buttons
        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveFilter(allButton);
                filterExpenses("all");
            }
        });

        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveFilter(foodButton);
                filterExpenses("food");
            }
        });

        transportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveFilter(transportButton);
                filterExpenses("transport");
            }
        });

        entertainmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveFilter(entertainmentButton);
                filterExpenses("entertainment");
            }
        });

        // Filter button
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SpendingHistoryActivity.this, "Gelişmiş filtreleme seçenekleri yakında!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setActiveFilter(Button activeButton) {
        // Reset all buttons to secondary style
        allButton.setBackgroundResource(R.drawable.secondary_button_background);
        allButton.setTextColor(getResources().getColor(android.R.color.darker_gray));
        foodButton.setBackgroundResource(R.drawable.secondary_button_background);
        foodButton.setTextColor(getResources().getColor(android.R.color.darker_gray));
        transportButton.setBackgroundResource(R.drawable.secondary_button_background);
        transportButton.setTextColor(getResources().getColor(android.R.color.darker_gray));
        entertainmentButton.setBackgroundResource(R.drawable.secondary_button_background);
        entertainmentButton.setTextColor(getResources().getColor(android.R.color.darker_gray));

        // Set active button to primary style
        activeButton.setBackgroundResource(R.drawable.primary_button_background);
        activeButton.setTextColor(getResources().getColor(android.R.color.white));
    }

    private void filterExpenses(String category) {
        // TODO: Implement expense filtering logic
        Toast.makeText(this, category + " kategorisi filtrelendi", Toast.LENGTH_SHORT).show();
    }

    private void loadExpenseData() {
        // TODO: Load expense data from database
        // For now, show sample data
        monthlyTotal.setText("₺2,450");
        weeklyTotal.setText("₺680");
        
        // Check if there are expenses to show
        boolean hasExpenses = true; // TODO: Check from database
        if (!hasExpenses) {
            emptyState.setVisibility(View.VISIBLE);
            expenseListContainer.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            expenseListContainer.setVisibility(View.VISIBLE);
        }
    }
} 