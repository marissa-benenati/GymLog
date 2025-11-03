package com.example.hw04_gymlog_v300;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyUser();
            }
        });
    }

    private void verifyUser(){
        String username = binding.userNameLoginEditText.getText().toString();  // gets user's input from username field

        if(username.isEmpty()){
            toastMaker("Username May Not Be Blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {  // "watches" the object for updates  |  pass in current activity (this keyword)
            if(user != null){
                String password = binding.passwordLoginEditText.getText().toString(); // gets user's input from password field
                if(password.equals(user.getPassword())){  // compares entered password w/user's password in the user database
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY,
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
                    sharedPrefEditor.putInt(MainActivity.SHARED_PREFERENCE_USERID_KEY, user.getId());
                    sharedPrefEditor.apply();
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                }else{
                    toastMaker("Invalid Password");
                    binding.passwordLoginEditText.setSelection(0);
                }
            }else{
                toastMaker(String.format("No User %s Found", username));
                binding.userNameLoginEditText.setSelection(0);
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }

}