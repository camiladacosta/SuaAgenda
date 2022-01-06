package com.example.atividade14agendacamiladacosta.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.atividade14agendacamiladacosta.AuxiliarPessoa;
import com.example.atividade14agendacamiladacosta.PessoaAdapter;
import com.example.atividade14agendacamiladacosta.PessoaDao;
import com.example.atividade14agendacamiladacosta.R;
import com.example.atividade14agendacamiladacosta.SQLite;
import com.example.atividade14agendacamiladacosta.entity.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class MostrarActivity extends AppCompatActivity implements AuxiliarPessoa {

    RecyclerView recyclerView;
    ArrayList<Pessoa> pessoas;
    SQLite sqLite;

    private PessoaAdapter pessoaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monstrar);
        //adiciona na actionbar botao voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        recyclerView = findViewById(R.id.reclycler);//mostrar
        pessoas = new ArrayList<>();
        sqLite = new SQLite(this, "pessoa", null, 1);

        pessoaAdapter = new PessoaAdapter(this, pessoas);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(pessoaAdapter);
        mostrarDados();
    }

    public void mostrarDados() {
        SQLiteDatabase sqLiteDatabase = sqLite.getReadableDatabase();
        Pessoa pessoa = null;

        Cursor cursor = sqLiteDatabase.rawQuery("select * from pessoa", null);
        while (cursor.moveToNext()) {
            pessoa = new Pessoa();
            pessoa.setId(cursor.getInt(0));
            pessoa.setNome(cursor.getString(1));
            pessoa.setTelefone(cursor.getString(2));
            pessoa.setEmail(cursor.getString(3));
            pessoaAdapter.agregarPessoa(pessoa);
        }
    }

    @Override
    public void OptionEditar(Pessoa pessoa) {
        Intent intent = new Intent(MostrarActivity.this, EditarActivity.class);
        intent.putExtra("pessoa", pessoa);
        startActivity(intent);
        finish();
    }

    @Override
    public void OptionExcluir(Pessoa pessoa) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Atenção");
        alerta.setMessage("Deseja excluir este Contato? ");
        alerta.setCancelable(false);
        alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarPersona(pessoa);
            }
        });
        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alerta.show();
    }

    private void eliminarPersona(Pessoa pessoa) {

        SQLite sqlLite = new SQLite(this, "pessoa", null, 1);
        SQLiteDatabase sqLiteDatabase = sqlLite.getWritableDatabase();
        Integer id = pessoa.getId();
        if (id != null) {
            sqLiteDatabase.delete("pessoa", "id=" + id, null);
            Toast.makeText(this, "Excluido", Toast.LENGTH_SHORT).show();
            pessoaAdapter.eliminarPessoa(pessoa);
            sqLiteDatabase.close();
        } else {
            Toast.makeText(this, "Não excluido ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buscar, menu);
        MenuItem buscars = menu.findItem(R.id.idPesquisar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(buscars);
        buscar(searchView);
        return true;
    }
    //voltar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //identificar a ação de voltar a tela
            case android.R.id.home:
                Intent intent = new Intent(MostrarActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buscar(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (pessoaAdapter != null)
                    pessoaAdapter.getFilter().filter((newText));
                return true;
            }
        });
    }
}