package com.example.prototip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlimentAdapter extends RecyclerView.Adapter<AlimentAdapter.AlimentHolder> implements Filterable {

    private OnItemClickListener listener;
    private List<Aliment> list;
    private List<Aliment> listFull;
    private Map<String,Integer> images = new HashMap<String,Integer>();

    public AlimentAdapter(@NonNull List<Aliment> options) {
        this.list = options;
        this.listFull = new ArrayList<>(this.list);
        images.put("banana",R.drawable.banana);
        images.put("caisa",R.drawable.caisa);
        images.put("capsuni",R.drawable.capsuni);
        images.put("cirese",R.drawable.cirese);
        images.put("kiwi",R.drawable.kiwi);
        images.put("para",R.drawable.para);
        images.put("portocala",R.drawable.portocala);
        images.put("zmeura",R.drawable.zmeura);
        images.put("pui",R.drawable.pui);

    }


    @NonNull
    @Override
    public AlimentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.aliment_item,
                parent,false);
        return new AlimentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlimentHolder holder, int position) {
        holder.textViewName.setText(list.get(position).getName());
        holder.textViewProteine.setText(String.valueOf(list.get(position).getProteine()));
        holder.textViewCarbohidrati.setText(String.valueOf(list.get(position).getCarbohidrati()));
        holder.textViewGrasimi.setText(String.valueOf(list.get(position).getGrasimi()));

        holder.imageView.setImageResource(images.get(list.get(position).getName()));
//        C:\Users\Sebastian\Desktop\Aplicatie Curs Google\Prototip\app\src\main\res\drawable\banana.jpg
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteAliment(int position){
      //  getSnapshots().getSnapshot(position).getReference().delete();
     //   list.remove(position);
      //  new AlimentAdapter(this.list);
    }

    @Override
    public Filter getFilter() {
        return exempleFilter;
    }

    private Filter exempleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Aliment> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0 ){
                filteredList.addAll(listFull);
            } else{
              String filterPattern = constraint.toString().toLowerCase().trim();

              for(Aliment aliment : listFull){
                  if(aliment.getName().toLowerCase().contains(filterPattern)){
                      filteredList.add(aliment);
                  }
              }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    class AlimentHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewProteine;
        TextView textViewCarbohidrati;
        TextView textViewGrasimi;
        ImageView imageView;

        public AlimentHolder(View itemView){
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewProteine = itemView.findViewById(R.id.textViewProteine);
            textViewCarbohidrati = itemView.findViewById(R.id.textViewCarbohidrati);
            textViewGrasimi = itemView.findViewById(R.id.textViewGrasimi);
            imageView = itemView.findViewById(R.id.image);
//            int res = itemView.getContext().getResources().getIdentifier()

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        //listener.onItemClick(getSnapshots().getSnapshot(position),position);
                        listener.onItemClick(list.get(position),position);
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(Aliment aliment,int position);
    }

    public void setOnClicListener(OnItemClickListener listener){
        this.listener = listener;
    }


}
