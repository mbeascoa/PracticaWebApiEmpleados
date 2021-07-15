package com.example.practicawebapiempleados;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptador  extends RecyclerView.Adapter<Adaptador.ViewHolder>{

    private List<Empleados> listaEmpleados;
    int posicionseleccionada = 0;
    public Adaptador(List<Empleados> ListaEmpleado) {
    this.listaEmpleados=ListaEmpleado;
}

@Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_datos,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String apellido= listaEmpleados.get(position).getApellido();
        holder.txtapellido.setText(apellido);

        holder.txtapellido.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                posicionseleccionada=position;
                notifyDataSetChanged();
                //Notificamos cambios para que el contenedr se entere y refresque los datos
            }
        });
        if(posicionseleccionada ==position){
            holder.txtapellido.setTextColor(Color.RED);

            Intent i = new Intent(holder.itemView.getContext(), Ventana2.class);

            i.putExtra("NUMEROEMPLEADO", listaEmpleados.get(position).getIdEmpleado());
            holder.itemView.getContext().startActivity(i);

        } else{
            holder.txtapellido.setTextColor(Color.DKGRAY);
        }

    }

    @Override
    public int getItemCount(){
        return listaEmpleados.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtapellido;
        public ViewHolder(View v){
            super(v);
            txtapellido=(TextView) v.findViewById(R.id.txtApellido);
        }
    }
}
