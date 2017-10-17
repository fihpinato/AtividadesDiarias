package br.com.filipepinato.atividadesdiarias.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.filipepinato.atividadesdiarias.R;
import br.com.filipepinato.atividadesdiarias.model.Atividade;

public class AtividadeAdapter extends
        RecyclerView.Adapter<AtividadeAdapter.AtividadeViewHolder> {

    private List<Atividade> atividades;
    private Activity activity;
    private int lastPositionSelected;

    public class AtividadeViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnCreateContextMenuListener {

        public TextView tvTitulo;

        public AtividadeViewHolder(View v){
            super(v);
            tvTitulo = (TextView) v.findViewById(R.id.tvTitulo);
            v.setOnClickListener(this);
            v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v){
            activity.openContextMenu(v);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo){
            lastPositionSelected = getLayoutPosition();
            menu.setHeaderIcon(R.mipmap.ic_launcher);
            menu.setHeaderTitle(activity.getString(R.string.app_name));
            MenuInflater inflater = activity.getMenuInflater();
            inflater.inflate(R.menu.context_lista, menu);
        }
    }

    public int getLastPositionSelected(){
        return  lastPositionSelected;
    }

    public AtividadeAdapter(Activity activity, List<Atividade> atividades){
        this.activity = activity;
        this.atividades = atividades;
    }

    @Override
    public AtividadeViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.atividade_list_row, parent, false);
        return new AtividadeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AtividadeViewHolder holder, int position){
        Atividade movie = atividades.get(position);
        holder.tvTitulo.setText(movie.getDescricao());
    }

    @Override
    public int getItemCount() {
        return atividades.size();
    }

    public void add(Atividade atividade){
        atividades.add(atividade);
        notifyDataSetChanged();
    }

    public void addAll(List<Atividade> atividadesList){
        atividades.addAll(atividadesList);
        notifyDataSetChanged();
    }

    public Atividade getAtividadeSelected(){
        return  atividades.get(lastPositionSelected);
    }

    public void removeGameSelected(){
        atividades.remove(atividades.get(lastPositionSelected));
        notifyItemRemoved(lastPositionSelected);
    }


}
