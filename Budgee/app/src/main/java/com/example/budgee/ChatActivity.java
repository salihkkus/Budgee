package com.example.budgee;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity; // Still needed for Activity.RESULT_OK
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher; // This import is no longer strictly needed if removing launcher
import androidx.activity.result.contract.ActivityResultContracts; // This import is no longer strictly needed if removing launcher
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity; // Correctly extending AppCompatActivity
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
// import androidx.core.app.ComponentActivity; // No longer needed as we are extending AppCompatActivity

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity { // Changed back to AppCompatActivity for broader compatibility and features.

    private static final int RECORD_AUDIO_PERMISSION_CODE = 102;
    private static final String TAG = "ChatActivity";

    // UI Elements
    private TextView chatOutput;
    private ScrollView chatScrollView;
    private ImageView mascotButton;
    private TextView statusTextView;
    private Button startSpeechButton; // This seems to be the button used for speech input

    // OpenRouter API Key
    private String openRouterApiKey = "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";

    // SpeechRecognizer API
    private SpeechRecognizer speechRecognizer;
    private Intent speechRecognizerIntent;

    // Chat history
    private StringBuilder chatHistory = new StringBuilder();

    // REMOVED: speechRecognizerLauncher - This is not needed if you're using the direct SpeechRecognizer API
    // for push-to-talk. It would only cause confusion or be unused.
    /*
    private ActivityResultLauncher<Intent> speechRecognizerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    ArrayList<String> matches = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (matches != null && !matches.isEmpty()) {
                        String recognizedText = matches.get(0);
                        Log.d(TAG, "onResults: Tanınan Metin: " + recognizedText);
                        appendMessage("Siz: " + recognizedText, true);
                        makeApiRequest(recognizedText);
                    } else {
                        Toast.makeText(this, "Metin tanınamadı.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Speech recognition failed or was cancelled. Result code: " + result.getResultCode());
                    Toast.makeText(this, "Ses tanıma iptal edildi veya başarısız oldu.", Toast.LENGTH_SHORT).show();
                }
                statusTextView.setText("Konuşmak için maskota dokunun.");
            });
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize UI elements
        chatOutput = findViewById(R.id.chatOutput);
        chatScrollView = findViewById(R.id.chatScrollView);
        mascotButton = findViewById(R.id.maskotIcon);
        statusTextView = findViewById(R.id.tvStatusMessage);
        startSpeechButton = findViewById(R.id.startSpeechButton);

        // Load GIF
        setGifToImageButton();

        // Check and request microphone permission
        checkPermissions();

        // Initialize SpeechRecognizer for direct control (push-to-talk)
        initializeSpeechRecognizer();

        // Setup touch listener for the speech input button
        setupSpeechButton();

        // Initial message
        appendMessage("Bot: Merhaba! Konuşmak için maskota dokunun ve basılı tutun.", false);
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_AUDIO_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Mikrofon izni verildi.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Mikrofon izni reddedildi. Ses tanıma yapılamaz.", Toast.LENGTH_LONG).show();
                mascotButton.setEnabled(false); // Disable mascot button if permission is denied
                startSpeechButton.setEnabled(false); // Also disable the startSpeechButton
                statusTextView.setText("Mikrofon izni gerekli.");
            }
        }
    }

    private void initializeSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            speechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    Log.d(TAG, "onReadyForSpeech: Konuşmaya hazır.");
                    statusTextView.setText("Dinliyorum...");
                }

                @Override
                public void onBeginningOfSpeech() {
                    Log.d(TAG, "onBeginningOfSpeech: Konuşma başladı.");
                    // Add a placeholder message when listening starts
                    appendMessage("Siz: (Dinleniyor...)", true);
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                    // Optional: Implement visual feedback based on sound level
                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                    // Seldom used, for raw audio data
                }

                @Override
                public void onEndOfSpeech() {
                    Log.d(TAG, "onEndOfSpeech: Konuşma bitti.");
                    statusTextView.setText("Konuşma bitti, metin işleniyor...");
                }

                @Override
                public void onError(int error) {
                    String errorMessage = getErrorText(error);
                    Log.e(TAG, "onError: " + errorMessage);
                    statusTextView.setText("Hata: " + errorMessage);
                    Toast.makeText(ChatActivity.this, "Ses tanıma hatası: " + errorMessage, Toast.LENGTH_LONG).show();

                    // Replace the placeholder with an error message
                    replaceLastUserMessage("(Hata: " + errorMessage + ")");

                    // Cancel listening and reset status after an error
                    if (speechRecognizer != null) {
                        speechRecognizer.cancel();
                    }
                    statusTextView.setText("Konuşmak için maskota dokunun.");
                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    String recognizedText = "";
                    if (matches != null && !matches.isEmpty()) {
                        recognizedText = matches.get(0); // Get the best matching text
                        Log.d(TAG, "onResults: Tanınan Metin: " + recognizedText);
                        // Replace the last "Listening..." message with the actual recognized text
                        replaceLastUserMessage(recognizedText);
                        makeApiRequest(recognizedText); // Send API request
                    } else {
                        Log.d(TAG, "onResults: Metin tanınamadı.");
                        replaceLastUserMessage("(Metin tanınamadı.)"); // Placeholder for empty speech
                        Toast.makeText(ChatActivity.this, "Metin tanınamadı.", Toast.LENGTH_SHORT).show();
                    }
                    statusTextView.setText("Konuşmak için maskota dokunun."); // Reset status
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
                    // Partial results (instant text as the user speaks)
                    ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    if (matches != null && !matches.isEmpty()) {
                        String partialText = matches.get(0);
                        Log.d(TAG, "onPartialResults: Kısmi Metin: " + partialText);
                        // Update the placeholder with partial text
                        replaceLastUserMessage("(Kısmi: " + partialText + "...)");
                    }
                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                    // Additional events (e.g., keyword recognition events)
                }
            });

            // Prepare the Intent for SpeechRecognizer
            speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().toString()); // Use device's default language
            speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName()); // Specifying package can improve performance
            speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1); // Get only the best result
            // speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true); // Prefer offline recognition (if language pack is installed)
        } else {
            Toast.makeText(this, "Cihazınızda konuşma tanıma desteği bulunmuyor.", Toast.LENGTH_LONG).show();
            mascotButton.setEnabled(false);
            startSpeechButton.setEnabled(false); // Disable the button if not supported
            statusTextView.setText("Konuşma tanıma desteklenmiyor.");
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupSpeechButton() {
        // We use setOnTouchListener to detect both press down (ACTION_DOWN) and release (ACTION_UP)
        startSpeechButton.setOnTouchListener((v, event) -> {
            // Permission check: Always ensure microphone permission is granted before trying to record.
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                checkPermissions();
                Toast.makeText(this, "Mikrofon izni gerekli.", Toast.LENGTH_SHORT).show();
                return true; // Consume the event, don't proceed without permission
            }

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // When the button is pressed down, start listening
                    if (speechRecognizer != null) {
                        speechRecognizer.startListening(speechRecognizerIntent);
                        statusTextView.setText("Dinliyorum..."); // Update status to reflect listening
                    } else {
                        Toast.makeText(this, "Ses tanıyıcı hazır değil. Lütfen tekrar deneyin.", Toast.LENGTH_LONG).show();
                        initializeSpeechRecognizer(); // Try to initialize it again if it's null
                        statusTextView.setText("Konuşmak için maskota dokunun."); // Reset status
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    // When the button is released, stop listening
                    if (speechRecognizer != null) {
                        speechRecognizer.stopListening(); // Stop listening when the finger is lifted
                        // The status will be updated by onEndOfSpeech() or onResults()/onError()
                    }
                    break;
            }
            return true; // Important: Return true to indicate that you've consumed the event
        });
    }

    // Helper function to get error text from SpeechRecognizer error codes
    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Ses kaydı hatası";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "İstemci tarafı hatası (uygulamanızla ilgili)";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "İzinler yetersiz";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Ağ hatası";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Ağ zaman aşımı";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "Eşleşme bulunamadı";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "Tanıyıcı meşgul";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "Sunucu hatası";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "Konuşma girişi yok (zaman aşımı)";
                break;
            default:
                message = "Bilinmeyen hata";
                break;
        }
        return message;
    }


    private void makeApiRequest(String message) {
        statusTextView.setText("Yapay zeka düşünüyor...");
        appendMessage("Bot: ...", false); // Add a placeholder while waiting for API response

        Message userMessage = new Message("user", message);
        ChatCompletionRequest request = new ChatCompletionRequest(
                "deepseek/deepseek-chat-v3-0324:free",
                Arrays.asList(userMessage)
        );

        Call<ChatCompletionResponse> call = RetrofitClient.getApiService().createChatCompletion(
                "Bearer " + openRouterApiKey,
                request
        );

        call.enqueue(new Callback<ChatCompletionResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChatCompletionResponse> call, @NonNull Response<ChatCompletionResponse> response) {
                statusTextView.setText("Konuşmak için maskota dokunun."); // Reset status after API response

                if (response.isSuccessful() && response.body() != null) {
                    ChatCompletionResponse chatResponse = response.body();
                    if (!chatResponse.getChoices().isEmpty()) {
                        String botContent = chatResponse.getChoices().get(0).getMessage().getContent();
                        Log.d(TAG, "API Yanıtı: " + botContent);
                        replaceLastBotMessage(botContent); // Replace placeholder with actual response
                    } else {
                        Log.d(TAG, "API Yanıtı: Seçenek bulunamadı.");
                        replaceLastBotMessage("Üzgünüm, bir yanıt alamadım.");
                    }
                } else {
                    String errorMessage = "API İsteği Başarısız: " + response.code() + " - " + response.message();
                    try {
                        if (response.errorBody() != null) {
                            errorMessage += " Hata Detayı: " + response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Hata gövdesi okunurken hata", e);
                    }
                    Log.e(TAG, errorMessage);
                    replaceLastBotMessage("Bir hata oluştu: " + errorMessage);
                    Toast.makeText(ChatActivity.this, "API Hatası: " + errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChatCompletionResponse> call, @NonNull Throwable t) {
                statusTextView.setText("Konuşmak için maskota dokunun."); // Reset status on network failure
                Log.e(TAG, "API İsteği Başarısız: " + t.getMessage(), t);
                replaceLastBotMessage("Ağ hatası oluştu: " + t.getMessage());
                Toast.makeText(ChatActivity.this, "Ağ Hatası: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setGifToImageButton() {
        Glide.with(this)
                .asGif()
                .load(R.drawable.watchapp_maskot)  // Assuming this is your GIF
                .into(mascotButton);
    }

    private void appendMessage(String message, boolean isUserMessage) {
        // Always append new messages with a new line and prefix
        chatHistory.append("\n\n").append(message);
        runOnUiThread(() -> {
            chatOutput.setText(chatHistory.toString());
            // Scroll to the bottom to show the latest message
            chatScrollView.post(() -> chatScrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
    }

    private void replaceLastUserMessage(String newMessage) {
        String currentChat = chatHistory.toString();
        // Find the start of the last user message placeholder
        int lastUserPrefixIndex = currentChat.lastIndexOf("Siz: ");

        if (lastUserPrefixIndex != -1) {
            // Replace the section from "Siz: " to the end with the new message
            String updatedChat = currentChat.substring(0, lastUserPrefixIndex + "Siz: ".length()) + newMessage;
            chatHistory.setLength(0); // Clear StringBuilder
            chatHistory.append(updatedChat); // Append updated text
        } else {
            // Fallback: if "Siz: " wasn't found (shouldn't happen with correct flow), just append
            appendMessage("Siz: " + newMessage, true);
        }

        runOnUiThread(() -> {
            chatOutput.setText(chatHistory.toString());
            chatScrollView.post(() -> chatScrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
    }

    private void replaceLastBotMessage(String newMessage) {
        String currentChat = chatHistory.toString();
        // Find the start of the last "Bot: ..." placeholder
        int lastBotMessageIndex = currentChat.lastIndexOf("Bot: ...");

        if (lastBotMessageIndex != -1) {
            // Replace "Bot: ..." with the actual new message
            String updatedChat = currentChat.substring(0, lastBotMessageIndex) + "Bot: " + newMessage;
            chatHistory.setLength(0); // Clear StringBuilder
            chatHistory.append(updatedChat); // Append updated text
        } else {
            // Fallback: if "Bot: ..." wasn't found, just append
            appendMessage("Bot: " + newMessage, false);
        }

        runOnUiThread(() -> {
            chatOutput.setText(chatHistory.toString());
            chatScrollView.post(() -> chatScrollView.fullScroll(ScrollView.FOCUS_DOWN));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Important: Release the SpeechRecognizer resources to prevent memory leaks
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }
}