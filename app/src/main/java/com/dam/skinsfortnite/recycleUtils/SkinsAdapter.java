package com.dam.skinsfortnite.recycleUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.skinsfortnite.model.Skin;
import com.dam.skinsfortnite.R;

import java.util.ArrayList;

//2.Especificar el Adapter el ViewHolder que se va a utilizar
public class SkinsAdapter extends RecyclerView.Adapter<SkinsAdapter.SkinVH> implements View.OnClickListener {
    //4.Crear atributo para representar la fuente de datos
    ArrayList<Skin> datosSkin;

    //8.Añadir el botón Onclick del Listener
    private View.OnClickListener listener;

    //5.Añadir un constructor que inicialize la lista de datos previamente definida en el paso 4
    public SkinsAdapter(ArrayList<Skin> datosSkin) {
        this.datosSkin = datosSkin;
    }

    //3.Añadir los método que requieren mplementación por extender de Adapter
    @NonNull
    @Override
    public SkinVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //7.
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skin_layout,parent, false);
        SkinVH svh = new SkinVH(v);
        v.setOnClickListener(this);
        return svh;
    }


    @Override
    public void onBindViewHolder(@NonNull SkinVH holder, int position) {
        //6.Invocar al método Holder, pasandole la lista que se encuentre en esa posición
        holder.bindSkin(datosSkin.get(position));
    }

    @Override
    public int getItemCount() {
        //
        return datosSkin.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }

    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    //1.Crear el ViewHolder
   public class SkinVH extends RecyclerView.ViewHolder{
       //Depende de skin: skin_layout.xml
       TextView tvNomSkin;
       TextView tvRareza;

        public SkinVH(@NonNull View itemView) {
            super(itemView);
            tvNomSkin = itemView.findViewById(R.id.tvNombreSkin);
            tvRareza = itemView.findViewById(R.id.tvRarezaSkin);
        }

        public void bindSkin(Skin skin){
            tvNomSkin.setText(skin.getName());
            tvRareza.setText(skin.getRarity());
        }
    }
}
