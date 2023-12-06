package com.example.notecalles.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notecalles.R;
import com.example.notecalles.model.Publicacion;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class OthersPubsAdapter extends FirebaseRecyclerAdapter<Publicacion,OthersPubsAdapter.myViewHolder> {


    public OthersPubsAdapter(@NonNull FirebaseRecyclerOptions<Publicacion> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Publicacion model) {

        holder.username.setText(model.getUsername());
        holder.fecha.setText(model.getFecha());
        holder.tipo.setText(model.getTipo());

        //Cargar imagen
        /*Glide.with(holder.img.getContext())
                .load(model.getSurl)
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.ic_menu_camera)
                .into(holder.img);*/
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.otherspubs_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView fecha,username,tipo;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            username = (TextView)itemView.findViewById(R.id.txtUsername);
            fecha = (TextView)itemView.findViewById(R.id.txtfecha);
            tipo = (TextView)itemView.findViewById(R.id.txttipo);
        }
    }


}
