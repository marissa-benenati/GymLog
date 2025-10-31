package com.example.hw04_gymlog_v300;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.hw04_gymlog_v300.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private static final String TAG = "MAIN_ACTIVITY";
    String mExercise = "";
    double mWeight = 0.0;
    int mReps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInformationFromDisplay();
                updateDisplay();
            }
        });
    }

    private void updateDisplay(){
        String currentInfo = binding.logDisplayTextView.getText().toString();
        Log.d(TAG, "Current Info: " + currentInfo);
        String newDisplay = String.format(Locale.US,"Exercise: %s%nWeight: %.2f%nReps: %d%n=-=-=-=%n%s", mExercise, mWeight, mReps, currentInfo);

        binding.logDisplayTextView.setText(newDisplay);
    }

    private void getInformationFromDisplay(){
        mExercise = binding.exerciseInputEditText.getText().toString();
        try{
            mWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        }catch (NumberFormatException e){
            Log.d(TAG, "Error reading value from weightInputEditText");
        }
        try{
            mReps = Integer.parseInt(binding.repInputEditText.getText().toString());
        }catch (NumberFormatException e){
            Log.d(TAG, "Error reading value from repInputEditText");
        }
    }
}

