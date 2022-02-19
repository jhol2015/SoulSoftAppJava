package com.example.soutsoftappjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.soutsoftappjava.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        binding.btnEntrar.setOnClickListener(v -> validaDados());

    }

    private void validaDados() {
        String email = binding.textEmailLogin.getText().toString().trim();
        String senha = binding.textSenhaLogin.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                binding.progressBarLogin.setVisibility(View.VISIBLE);
                loginFirebase(email, senha);//passa como parametro nome, email, senha
            } else {
                Toast.makeText(this, " Informe uma senha", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu e-mail", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginFirebase(String email, String senha) {
        mAuth.signInWithEmailAndPassword(
                email,senha
        ).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this, InicialActivity.class));

            }else {
                binding.progressBarLogin.setVisibility(View.GONE);
                Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
            }
        });
    }
}