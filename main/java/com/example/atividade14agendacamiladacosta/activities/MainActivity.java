package com.example.atividade14agendacamiladacosta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.atividade14agendacamiladacosta.R;
import com.example.atividade14agendacamiladacosta.SQLite;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText editNome, editTelefone, editEmail;
    private Button buttonListar, buttonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editNome = findViewById(R.id.nomeEditar);
        editTelefone = findViewById(R.id.telefoneEditar);
        editEmail = findViewById(R.id.emailEditar);
        buttonCadastrar = findViewById(R.id.editarBotao);
        buttonListar = findViewById(R.id.buttonListar);
        buttonCadastrar.setOnClickListener(acaoCadastrar());
        buttonListar.setOnClickListener(acaoListar());
    }

    private View.OnClickListener acaoCadastrar() {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editNome.getText().toString().equals("") || editTelefone.getText().toString().equals("") || editEmail.getText().toString().equals("")) {
                    validarCampos();
                } else {
                    GuardarDados(view);
                    limparCampos();
                    Toast.makeText(context(), "Contato Registrado", Toast.LENGTH_SHORT).show();

                }
            }
        };
    }
    private View.OnClickListener acaoListar () {
        return new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MostrarActivity.class);
                startActivity(intent);


            }
        };
    }

    private void GuardarDados (View view) {
        SQLite sqLite = new SQLite(this, "pessoa", null, 1);
        SQLiteDatabase sqLiteDatabase = sqLite.getWritableDatabase();

        String nome = editNome.getText().toString();
        String telefone = editTelefone.getText().toString();
        String email = editEmail.getText().toString();

        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("telefone", telefone);
        values.put("email", email);
        sqLiteDatabase.insert("pessoa", null, values);
    }

    private void limparCampos () {
        editNome.setText("");
        editTelefone.setText("");
        editEmail.setText("");
    }

    public void validarCampos () {
        if (editNome.getText().toString().equals("")) {
            editNome.setText("Campo Nome não pode ser Nulo");
        }
        if (editTelefone.getText().toString().equals("")) {
            editTelefone.setText("Campo Telefone não pode ser Nulo");
        }
        if (editEmail.getText().toString().equals("")) {
            editEmail.setText("Campo Email não pode ser Nulo");
        }
    }
    private Context context () {
        return this;
    }
}