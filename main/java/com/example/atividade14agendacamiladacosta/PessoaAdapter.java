package com.example.atividade14agendacamiladacosta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atividade14agendacamiladacosta.entity.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaAdapter extends RecyclerView.Adapter<PessoaAdapter.pessoaView> implements Filterable {
    private List<Pessoa> pessoaList = new ArrayList<>();

    private ArrayList<Pessoa> pessoaArrayList;

    private AuxiliarPessoa auxiliarPessoa;

    public PessoaAdapter(AuxiliarPessoa auxiliarPessoa, ArrayList<Pessoa> pessoaList) {
        this.auxiliarPessoa = auxiliarPessoa;
        this.pessoaList = pessoaList;
        this.pessoaArrayList = pessoaList;
    }

    @NonNull
    @Override
    public pessoaView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mostrar,parent,false);
        return new pessoaView(view);
    }

    @Override
    public void onBindViewHolder(pessoaView holder, int position) {
        Pessoa pessoa = pessoaList.get(position);
        holder.nomeRecebido.setText(pessoa.getNome());
        holder.telefoneRecebido.setText(pessoa.getTelefone());
        holder.emailRecebido.setText(pessoa.getEmail());
        holder.buttonEditar.setOnClickListener(new acaoEditar(pessoa));
        holder.buttonExcluir.setOnClickListener(new acaoExcluir(pessoa));
    }

    @Override
    public int getItemCount() {
        return pessoaList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String palavraPesquisa = constraint.toString();

                if (palavraPesquisa.isEmpty()) {
                    pessoaList = pessoaArrayList;
                } else {
                    ArrayList<Pessoa> filtrarLista = new ArrayList<>();
                    for (Pessoa p : pessoaArrayList) {
                        if (p.getNome().toLowerCase().contains(constraint)) {
                            filtrarLista.add(p);
                        }
                    }
                    pessoaList = filtrarLista;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = pessoaList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                pessoaList = (ArrayList<Pessoa>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class pessoaView extends RecyclerView.ViewHolder{

        private TextView nomeRecebido,telefoneRecebido,emailRecebido;
        private Button buttonEditar,buttonExcluir;

        public pessoaView(@NonNull View itemView) {
            super(itemView);

            nomeRecebido = itemView.findViewById(R.id.nomeRecebido);
            telefoneRecebido = itemView.findViewById(R.id.telefoneRecebido);
            emailRecebido = itemView.findViewById(R.id.emailRecebido);
            buttonEditar = itemView.findViewById(R.id.buttonEditar);
            buttonExcluir = itemView.findViewById(R.id.buttonExcluir);

        }
    }
    public void agregarPessoa(Pessoa pessoa){
        pessoaList.add(pessoa);
        this.notifyDataSetChanged();
    }
    public void eliminarPessoa(Pessoa pessoa) {
        pessoaList.remove(pessoa);
        this.notifyDataSetChanged();
    }

    class acaoEditar implements View.OnClickListener{
        private Pessoa pessoa;

        public acaoEditar(Pessoa pessoa){
            this.pessoa = pessoa;
        }

        @Override
        public void onClick(View v) {
            auxiliarPessoa.OptionEditar(pessoa);
        }
    }
    class acaoExcluir implements View.OnClickListener {
        private Pessoa pessoa;

        public acaoExcluir(Pessoa pessoa) {
            this.pessoa = pessoa;
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(context,"Selected",Toast.LENGTH_SHORT).show();
            auxiliarPessoa.OptionExcluir(pessoa);
        }
    }

}
