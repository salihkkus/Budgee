package com.example.budgee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.TypedValue;
import android.graphics.drawable.GradientDrawable;

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Ana layout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.CENTER);
        mainLayout.setBackgroundColor(0xFF1A1A1A); // Koyu gri arka plan
        mainLayout.setPadding(32, 48, 32, 48);
        
        // Welcome yazısı
        TextView welcomeText = new TextView(this);
        welcomeText.setText("Welcome");
        welcomeText.setTextColor(0xFFE0E0E0); // Açık gri yazı
        welcomeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        welcomeText.setGravity(Gravity.CENTER);
        welcomeText.setPadding(0, 0, 0, 16);
        
        // Budget App başlığı
        TextView titleText = new TextView(this);
        titleText.setText("Budgee");
        titleText.setTextColor(0xFFFFFFFF); // Beyaz yazı
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        titleText.setTypeface(null, android.graphics.Typeface.BOLD);
        titleText.setGravity(Gravity.CENTER);
        titleText.setPadding(0, 0, 0, 48);
        
        // Get Started butonu
        Button getStartedButton = new Button(this);
        getStartedButton.setText("Get Started");
        getStartedButton.setTextColor(0xFFFFFFFF); // Beyaz yazı
        getStartedButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        getStartedButton.setTypeface(null, android.graphics.Typeface.BOLD);
        getStartedButton.setPadding(40, 16, 40, 16);
        
        // Buton arka plan rengi ve köşe yuvarlaklığı
        GradientDrawable buttonBackground = new GradientDrawable();
        buttonBackground.setColor(0xFF2196F3); // Mavi arka plan
        buttonBackground.setCornerRadius(20); // Yuvarlak köşeler
        getStartedButton.setBackground(buttonBackground);
        
        // Responsive buton boyutu için LayoutParams
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.setMargins(0, 0, 0, 0);
        getStartedButton.setLayoutParams(buttonParams);
        
        // Buton tıklama olayı
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Bu activity'yi kapat
            }
        });
        
        // Layout'a elemanları ekle
        mainLayout.addView(welcomeText);
        mainLayout.addView(titleText);
        mainLayout.addView(getStartedButton);
        
        setContentView(mainLayout);
    }
} 