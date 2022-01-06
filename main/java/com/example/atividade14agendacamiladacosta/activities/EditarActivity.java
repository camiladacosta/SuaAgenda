package com.example.atividade14agendacamiladacosta.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.atividade14agendacamiladacosta.PessoaAdapter;
import com.example.atividade14agendacamiladacosta.PessoaDao;
import com.example.atividade14agendacamiladacosta.R;
import com.example.atividade14agendacamiladacosta.SQLite;
import com.example.atividade14agendacamiladacosta.entity.Pessoa;
import com.google.android.material.textfield.TextInputEditText;

public class EditarActivity extends AppCompatActivity {
    private TextInputEditText nomeEditar,telefoneEditar,emailEditar;
    private Pessoa pessoa;
    private Button editarBotao;
    PessoaDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        nomeEditar = findViewById(R.id.nomeEditar);
        telefoneEditar = findViewById(R.id.telefoneEditar);
        emailEditar = findViewById(R.id.emailEditar);
        editarBotao = findViewById(R.id.editarBotao);
        editarBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarDados(v);
            }
        });
        pessoa = (Pessoa) getIntent().getSerializableExtra("pessoa");
        lerDados();
    }
    private void lerDados(){
        nomeEditar.setText(pessoa.getNome());
        telefoneEditar.setText(pessoa.getTelefone());
        emailEditar.setText(pessoa.getEmail());
    }
    private void editarDados(View view){
        SQLite sqLite = new SQLite(this,"pessoa",null,1);

        SQLiteDatabase banco = sqLite.getWritableDatabase();
        Integer id = pessoa.getId();
        String nome = nomeEditar.getText().toString();
        String telefone = telefoneEditar.getText().toString();
        String email = emailEditar.getText().toString();

        ContentValues values = new ContentValues();
        values.put("nome",nome);
        values.put("telefone",telefone);
        values.put("email",email);

        banco.update("pessoa",values,"id="+id,null);
        banco.close();
        finish();

        Intent intent = new Intent(EditarActivity.this, MostrarActivity.class);
        startActivity(intent);
        Toast.makeText(this,"Contato Atualizado",Toast.LENGTH_SHORT).show();
        finish();
    }
}