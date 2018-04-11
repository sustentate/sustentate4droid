package com.sustentate.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import com.sustentate.app.R;
import com.sustentate.app.models.Tip;

import java.util.List;

/**
 * Created by emzas on 28/3/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Tip> tips;
    private Context context;
    private RecyclerAdapter.Comunicador comunicador;


    public RecyclerAdapter(List<Tip> tips, Context context, RecyclerAdapter.Comunicador comunicador) {
        this.tips = tips;
        this.context = context;
        this.comunicador = comunicador;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.celda_tips, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Tip tip = tips.get(position);
        holder.cargarDatos(tip);

        holder.celda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                comunicador.enviarInfo(tip);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    public Tip getItem(Integer posicion){
        return tips.get(posicion);
    }

    public void cargarNuevaLista(List<Tip> tips1){
        tips.addAll(tips1);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private View celda;
        private TextView text;
        private TextView title;
        private ImageView imageUrl;

        public ViewHolder(View itemView) {
            super(itemView);
            celda = itemView;
            text = (TextView) itemView.findViewById(R.id.text_tip);
            title = (TextView) itemView.findViewById(R.id.title_tip);
            imageUrl = (ImageView) itemView.findViewById(R.id.image_tip);

        }

        public void cargarDatos(Tip tip){

            if (tip != null && tip.getTitle() != null && tip.getImageUrl() != null && !tip.getTitle().isEmpty() && !tip.getImageUrl().isEmpty()) {
                title.setText(tip.getTitle());
                //carga imagen por default si ausente
                Picasso.with(context).load(tip.getImageUrl()).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageUrl);
            }
            else {
                title.setText("Unable to reach the article");
                //Picasso.with(context).load(articulo.getUrlToImage()).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageViewRecycler);
                imageUrl.setImageResource(R.drawable.placeholder);
            }
        }
    }
    //Creo una interfaz para poder comunicarme con el adapter
    public interface Comunicador{
        //Creo un método para enviar la informacion
        public void enviarInfo(Tip tip);
    }
}
