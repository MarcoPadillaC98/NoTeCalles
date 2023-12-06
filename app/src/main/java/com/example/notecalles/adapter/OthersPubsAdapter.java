package com.example.notecalles.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notecalles.R;
import com.example.notecalles.model.Publicacion;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;

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

        holder.btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext()).setContentHolder(new ViewHolder(R.layout.ver_popup))
                        .setExpanded(true,1200)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText us = view.findViewById(R.id.txtUsernameUpdate);
                EditText fec = view.findViewById(R.id.txtFechaUpdate);
                EditText des = view.findViewById(R.id.txtHechoUpdate);
                EditText tip = view.findViewById(R.id.txt_TipoUpdate);

                us.setText(model.getUsername());
                tip.setText(model.getTipo());
                des.setText(model.getHecho());
                fec.setText(model.getFecha());

                dialogPlus.show();
            }
        });
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

        Button btnVer;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            username = (TextView)itemView.findViewById(R.id.txtUsername);
            fecha = (TextView)itemView.findViewById(R.id.txtfecha);
            tipo = (TextView)itemView.findViewById(R.id.txttipo);

            btnVer = (Button) itemView.findViewById(R.id.btnVer);
        }
    }


}
