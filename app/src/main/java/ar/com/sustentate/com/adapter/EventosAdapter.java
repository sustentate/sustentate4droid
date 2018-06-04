package ar.com.sustentate.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ar.com.sustentate.com.R;
import ar.com.sustentate.com.models.Evento;

/**
 * Created by emzas on 18/3/2018.
 */

public class EventosAdapter extends RecyclerView.Adapter {
    private List<Evento> eventos;
    private Context context;
    private EventosAdapter.Comunicador comunicador;


    public EventosAdapter(List<Evento> eventos, Context context, EventosAdapter.Comunicador comunicador) {
        this.eventos = eventos;
        this.context = context;
        this.comunicador = comunicador;
    }

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Evento unEvento = eventos.get(position);
        ViewHolder1 viewHolder1 = (ViewHolder1) holder;
        viewHolder1.cargarDatos(unEvento);
        viewHolder1.celda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicador.enviarInfo(unEvento);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }
    public void cargarNuevaLista(List<Evento> eventos1){
        eventos.addAll(eventos1);
        notifyDataSetChanged();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{

        private View celda;
        private TextView title;
        private TextView date;
        private ImageView imageUrl;

        public ViewHolder1(View itemView) {
            super(itemView);
            celda = itemView;
            title = (TextView) itemView.findViewById(R.id.title_evento);
            date = (TextView) itemView.findViewById(R.id.textView_fechaEvento);
            imageUrl = (ImageView) itemView.findViewById(R.id.imageview_evento);
        }

        public void cargarDatos (Evento evento){
            if (evento != null && evento.getTitle() != null && evento.getImageUrl() != null && !evento.getTitle().isEmpty() && !evento.getImageUrl().isEmpty()) {
                title.setText(evento.getTitle());
                //carga imagen por default si ausente
                Picasso.with(context).load(evento.getImageUrl()).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageUrl);
            }
            else {
                title.setText("Unable to reach the article");
                imageUrl.setImageResource(R.drawable.placeholder);
            }
        }
    }

    public interface Comunicador {
        //Creo un método para enviar la informacion
        public void enviarInfo(Evento evento);
    }
}

