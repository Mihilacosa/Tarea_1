package com.example.tarea_1;

import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorNovelas extends RecyclerView.Adapter<AdaptadorNovelas.ViewHolderNovelas> implements View.OnClickListener{

    ArrayList<ListaNovelas> listaNovelas;
    private View.OnClickListener listener;

    public AdaptadorNovelas(ArrayList<ListaNovelas> listaNovelas) {
        this.listaNovelas = listaNovelas;
    }

    @NonNull
    @Override
    public ViewHolderNovelas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_novelas, parent,false);
        view.setOnClickListener(this);
        return new ViewHolderNovelas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNovelas holder, int position) {
        holder.Titulo.setText(listaNovelas.get(position).getTitulo());
        holder.id.setText(listaNovelas.get(position).getId());
        //holder.imagen.setImageURI(Uri.parse(listaNovelas.get(position).getImagen()));
        Picasso.get().load(listaNovelas.get(position).getImagen()).into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return listaNovelas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolderNovelas extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView Titulo, id;
        ImageView imagen;
        CardView card;

        public ViewHolderNovelas(@NonNull View itemView) {
            super(itemView);
            Titulo = itemView.findViewById(R.id.titulox);
            id = itemView.findViewById(R.id.id_novelax);
            imagen = itemView.findViewById(R.id.portadax);
            card = itemView.findViewById(R.id.cardx);
            card.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Seleccione una opción");
            menu.add(this.getAdapterPosition(), 120, 0, "Reseña");
        }
    }

    public String mostrarResena (int position){
        String resena = listaNovelas.get(position).getResena();
        return resena;
    }
}
