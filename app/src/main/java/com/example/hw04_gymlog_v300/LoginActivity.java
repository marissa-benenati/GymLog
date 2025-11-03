package com.example.hw04_gymlog_v300;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw04_gymlog_v300.database.GymLogRepository;
import com.example.hw04_gymlog_v300.database.entities.User;
import com.example.hw04_gymlog_v300.databinding.ActivityLoginBinding;

import java.util.Locale;

/**
 * @author Marissa Benenati
 * <br>⋆.˚｡⋆⚘⭒⋆✴︎˚｡⋆
 * <br>COURSE: CST 338 - Software Design
 * <br>DATE: 11/1/2025
 * <br>ASSIGNMENT: GymLog
 */
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private GymLogRepository repository;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!verifyUser()){
                    toastMaker("Invalid Username or Password");
                }
                else {
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                    startActivity(intent);
                }
            }
        });
    }

    private boolean verifyUser(){
        String username = binding.userNameLoginEditText.getText().toString();  // gets user's input from username field
        if(username.isEmpty()){
            toastMaker("Username May Not Be Blank");
            return false;
        }
        user = repository.getUserByUserName(username);
        if(user != null){
            String password = binding.passwordLoginEditText.getText().toString();  // gets user's input from password field
            if(password.equals(user.getPassword())){
                return true;
            }else{
                toastMaker("Invalid Password");
                return false;
            }
        }
        toastMaker(String.format("No %s Found", username));
        return false;
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }

}