package br.com.filipepinato.atividadesdiarias;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import br.com.filipepinato.atividadesdiarias.adapter.AtividadeAdapter;
import br.com.filipepinato.atividadesdiarias.dao.AtividadeDAO;
import br.com.filipepinato.atividadesdiarias.model.Atividade;

public class AtividadesActivity extends AppCompatActivity {

    private RecyclerView rvMinhasAtividades;
    private AtividadeAdapter mAdapter;
    private FloatingActionMenu fMenu;

    private AtividadeDAO atividadeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividades);
        Intent intent = getIntent();
        fMenu = (FloatingActionMenu) findViewById(R.id.fMenu);
        atividadeDAO = new AtividadeDAO();
        inicializaLista();
        carregaMinhasAtividades();
    }

    private void inicializaLista() {
        rvMinhasAtividades = (RecyclerView) findViewById(R.id.rvMeusGames);
        mAdapter = new AtividadeAdapter(this, new ArrayList<Atividade>());
        rvMinhasAtividades.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMinhasAtividades.setItemAnimator(new DefaultItemAnimator());
        rvMinhasAtividades.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        rvMinhasAtividades.setAdapter(mAdapter);
    }

    private void carregaMinhasAtividades() {
        mAdapter.addAll(atividadeDAO.findAll());
        mAdapter.notifyDataSetChanged();
    }

    public boolean onContextItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menu_apagar:
                mAdapter.getAtividadeSelected().delete();
                mAdapter.removeGameSelected();
                Toast.makeText(this, "Atividade apagada com sucesso", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_editar:
                dialogAtividade(mAdapter.getAtividadeSelected());
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void novaAtividade(View v){
        fMenu.close(true);
        dialogAtividade(new Atividade());
    }

    private void dialogAtividade(final Atividade atividade) {
        final boolean isInsert = atividade.getId() == null? true : false;
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.new_atividade);

        dialog.setTitle("Nova atividade");

        final EditText etTitulo = (EditText)dialog.findViewById(R.id.etTitulo);

        etTitulo.setText(atividade.getDescricao());

        Button btConfirmar = (Button) dialog.findViewById(R.id.btConfirmar);

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Bundle bd = intent.getExtras();
                String getName = (String) bd.get("NOME");
                atividade.setDescricao(getName + " precisa " + etTitulo.getText().toString());
                atividade.save();

                if(isInsert)
                    mAdapter.add(atividade);

                mAdapter.notifyDataSetChanged();

                dialog.dismiss();
                Toast.makeText(AtividadesActivity.this, "Dado gravado com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        Button btCancelar = (Button) dialog.findViewById(R.id.btCancelar);

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
