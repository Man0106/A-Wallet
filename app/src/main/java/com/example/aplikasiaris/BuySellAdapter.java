package com.example.aplikasiaris;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import org.w3c.dom.Text;

import java.util.List;

public class BuySellAdapter extends RecyclerView.Adapter<BuySellAdapter.ViewHolder> {
    private final Context context;
    List<Users> userList;
    FirebaseFirestore mDatabase;
    String TAG = "BuySellAdapter";

    public BuySellAdapter(Context context,  List<Users> userList ){
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.list_buysell, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mName.setText( userList.get( position ).getName());
        holder.mJumlah.setText(userList.get(position).getAmount());
        holder.mDeskripsi.setText(userList.get(position).getDescription());
        holder.mKategori.setText(userList.get(position).getCategory());
        RefreshUsers();
    }


    private void RefreshUsers() {
        mDatabase = FirebaseFirestore.getInstance();
        mDatabase.collection( "Advertise" ).addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null ){
                    Log.d(TAG,"error : " + e.getMessage());
                }
                for (DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() != DocumentChange.Type.ADDED && doc.getType() != DocumentChange.Type.MODIFIED && doc.getType() != DocumentChange.Type.REMOVED) {
                        String person_id = doc.getDocument().getId();
                        Users users = doc.getDocument().toObject( Users.class).withId(person_id);
                        if (userList.size() > 0) {
                            userList.clear();
                        }

                        userList.add(users);
                        notifyDataSetChanged();
                    }
                }
            }
        } );
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public interface SuccessResponse {
        void onSuccess();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mName;
        TextView mJumlah;
        TextView mDeskripsi;
        TextView mKategori;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            mView = itemView;

            mName = mView.findViewById( R.id.txtnama );
            mJumlah = mView.findViewById( R.id.txtjumlah );
            mDeskripsi = mView.findViewById(R.id.txtDescription);
            mKategori = mView.findViewById(R.id.txtcategory);

        }
    }
}
