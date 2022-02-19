package com.example.soutsoftappjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.soutsoftappjava.databinding.ActivityCadastrarBinding;
import com.google.firebase.auth.FirebaseAuth;

public class CadastrarActivity extends AppCompatActivity {
    private ActivityCadastrarBinding binding;

    private FirebaseAuth mAuth;//Importa o firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastrarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();//permite fazer a comunicação com o firebase

        binding.btnCriarConta.setOnClickListener(view -> {
            validaDados();
        });
    }

    //passa as informações que foram preenchidas e verifica se não estão vazias
    private void validaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                binding.progressBarCadastro.setVisibility(View.VISIBLE);
                criarContaFirebase(email, senha);//passa como parametro nome, email, senha
            } else {
                Toast.makeText(this, " Informe uma senha", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu e-mail", Toast.LENGTH_SHORT).show();
        }
    }

    private void criarContaFirebase(String email, String senha) {
        mAuth.createUserWithEmailAndPassword(
                email,senha
        ).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, "Conta criada com sucesso", Toast.LENGTH_SHORT).show();
            }else {
                binding.progressBarCadastro.setVisibility(View.GONE);
                Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
            }
        });
    }
}