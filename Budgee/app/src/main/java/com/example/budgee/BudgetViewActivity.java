package com.example.budgee;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BudgetViewActivity extends Activity {
    
    private TextView totalBudgetText, spentAmountText, remainingAmountText;
    private TextView foodProgressText, transportProgressText, entertainmentProgressText;
    private LinearLayout foodProgressBar, transportProgressBar, entertainmentProgressBar;
    private ImageButton backButton, editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_view);

        // Initialize views
        initializeViews();
        setupClickListeners();
        loadBudgetData();
    }

    private void initializeViews() {
        totalBudgetText = findViewById(R.id.totalBudgetText);
        spentAmountText = findViewById(R.id.spentAmountText);
        remainingAmountText = findViewById(R.id.remainingAmountText);
        foodProgressText = findViewById(R.id.foodProgressText);
        transportProgressText = findViewById(R.id.transportProgressText);
        entertainmentProgressText = findViewById(R.id.entertainmentProgressText);
        foodProgressBar = findViewById(R.id.foodProgressBar);
        transportProgressBar = findViewById(R.id.transportProgressBar);
        entertainmentProgressBar = findViewById(R.id.entertainmentProgressBar);
        backButton = findViewById(R.id.backButton);
        editButton = findViewById(R.id.editButton);
    }

    private void setupClickListeners() {
        // Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Edit button
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Open budget editing activity
                Toast.makeText(BudgetViewActivity.this, "Bütçe düzenleme yakında!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBudgetData() {
        // TODO: Load budget data from database
        // For now, show sample data
        
        double totalBudget = 5000.0;
        double spentAmount = 2450.0;
        double remainingAmount = totalBudget - spentAmount;
        
        // Update main budget info
        totalBudgetText.setText("₺" + String.format("%.0f", totalBudget));
        spentAmountText.setText("₺" + String.format("%.0f", spentAmount));
        remainingAmountText.setText("₺" + String.format("%.0f", remainingAmount));
        
        // Update category progress
        updateCategoryProgress("Yemek", 1200.0, 1500.0, foodProgressText, foodProgressBar);
        updateCategoryProgress("Ulaşım", 450.0, 800.0, transportProgressText, transportProgressBar);
        updateCategoryProgress("Eğlence", 800.0, 1000.0, entertainmentProgressText, entertainmentProgressBar);
    }

    private void updateCategoryProgress(String categoryName, double spent, double budget, TextView progressText, LinearLayout progressBar) {
        double percentage = (spent / budget) * 100;
        progressText.setText(categoryName + ": ₺" + String.format("%.0f", spent) + " / ₺" + String.format("%.0f", budget) + " (%" + String.format("%.0f", percentage) + ")");
        
        // Update progress bar using layout_weight
        View progressFill = progressBar.getChildAt(0);
        if (progressFill != null) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) progressFill.getLayoutParams();
            params.weight = (float) percentage;
            progressFill.setLayoutParams(params);
        }
        
        // Change color based on percentage
        if (percentage > 80) {
            progressText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        } else if (percentage > 60) {
            progressText.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        } else {
            progressText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }
    }
} 