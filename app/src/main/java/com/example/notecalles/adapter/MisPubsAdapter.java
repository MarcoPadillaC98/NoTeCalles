package com.example.notecalles.adapter;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.notecalles.R;
import com.example.notecalles.model.Publicacion;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MisPubsAdapter extends FirebaseRecyclerAdapter<Publicacion,MisPubsAdapter.myViewHolder> {


    public MisPubsAdapter(@NonNull FirebaseRecyclerOptions<Publicacion> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Publicacion model) {
        //Listar
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

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext()).setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1200)
                        .create();
                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText us = view.findViewById(R.id.txtUsernameUpdate);
                EditText fec = view.findViewById(R.id.txtFechaUpdate);
                EditText des = view.findViewById(R.id.txtHechoUpdate);
                EditText tip = view.findViewById(R.id.txt_TipoUpdate);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                us.setText(model.getUsername());
                tip.setText(model.getTipo());
                des.setText(model.getHecho());
                fec.setText(model.getFecha());


                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("username",us.getText().toString().trim());
                        map.put("tipo",tip.getText().toString().trim());
                        map.put("fecha",des.getText().toString().trim());
                        map.put("hecho",fec.getText().toString().trim());

                        FirebaseDatabase.getInstance().getReference().child("publicaciones")
                                //.child(getRef(position).getKey()).updateChildren(map)
                                .child(model.getUsername()).child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.username.getContext(),"Update Success",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.username.getContext(),"Error Update",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();

                                    }
                                });

                    }
                });

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.username.getContext());
                builder.setTitle("¿Quiere eliminar la publicacion?");
                builder.setMessage("Será eliminado de su cuenta");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("publicaciones")
                                .child(model.getUsername()).child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.username.getContext(),"Cancelado",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mispubs_item,parent,false);
        return new myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView fecha,username,tipo;

        Button btnEdit,btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            username = (TextView)itemView.findViewById(R.id.txtUsername);
            fecha = (TextView)itemView.findViewById(R.id.txtfecha);
            tipo = (TextView)itemView.findViewById(R.id.txttipo);

            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);



        }
    }


}
