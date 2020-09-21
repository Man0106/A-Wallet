package com.example.aplikasiaris;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {
    public List<Pengguna> Plist;
    public Context context;
    private FirebaseFirestore mDatabase;
    final String TAG = "TransaksiAdapter";
    private String Filename;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_buysell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mNama.setText( Plist.get( position ).getNama() );
        holder.mJumlah.setText( Plist.get( position ).getJumlah() );
        holder.mDesc.setText(Plist.get(position).getDeskripsi());
        holder.mCategory.setText(Plist.get(position).getKategory());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mNama, mJumlah, mDesc, mCategory;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            mView =itemView;

            mNama = mView.findViewById( R.id.txtnama );
            mJumlah = mView.findViewById( R.id.txtjumlah );
            mDesc = mView.findViewById(R.id.txtDescription);
            mCategory = mView.findViewById(R.id.txtcategory);
        }
    }
}
