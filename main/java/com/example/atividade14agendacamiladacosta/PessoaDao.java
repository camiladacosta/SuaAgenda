package com.example.atividade14agendacamiladacosta;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.atividade14agendacamiladacosta.activities.MostrarActivity;
import com.example.atividade14agendacamiladacosta.entity.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaDao {
    private SQLite conexao;
    private SQLiteDatabase banco;

    public PessoaDao(Context context){
        conexao = new SQLite(context,"pessoa",null,1);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Pessoa pessoa){
        ContentValues values = new ContentValues();
        values.put("nome",pessoa.getNome());
        values.put("telefone",pessoa.getTelefone());
        values.put("email",pessoa.getEmail());
        return banco.insert("pessoa", null,values);
    }//chamada no main
    public List<Pessoa> mostrarTodos(){
        List<Pessoa> pessoas = new ArrayList<>();
        Cursor cursor = banco.query("pessoa", new String[]{"id","nome","telefone","email"},
                null,null,null,null,null);
        while(cursor.moveToNext()){
            Pessoa p = new Pessoa();
            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));
            p.setTelefone(cursor.getString(2));
            p.setEmail(cursor.getString(3));
            pessoas.add(p);
        }
        return pessoas;
    }
    public void excluir(Pessoa pessoa){
        banco.delete("pessoa","id = ?", new String[]{pessoa.getId().toString()});
    }

    public void atualizarDao(Pessoa pessoa){
        ContentValues values = new ContentValues();
        values.put("nome",pessoa.getNome());
        values.put("telefone",pessoa.getTelefone());
        values.put("email",pessoa.getEmail());
        banco.update("pessoa",values,"id = ?",new String[]{pessoa.getId().toString()});
        banco.close();
    }
}
