package com.example.rubafikri.myapplicationandriod;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ProudctRecyclerViewAdapter extends
        RecyclerView.Adapter<ProudctRecyclerViewAdapter.ViewHolder> {

    private List<proudct> eventsList;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public ProudctRecyclerViewAdapter(List<proudct> list, Context ctx, FirebaseFirestore firestore) {
        eventsList = list;
        context = ctx;
        firestoreDB = firestore;
    }
    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    @Override
    public ProudctRecyclerViewAdapter.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_event_item, parent, false);

        ProudctRecyclerViewAdapter.ViewHolder viewHolder =
                new ProudctRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProudctRecyclerViewAdapter.ViewHolder holder, int position) {
        final int itemPos = position;
        final proudct proudct = eventsList.get(position);
        holder.proname.setText(proudct.getProname());
        holder.price.setText(proudct.getPrice());
        holder.cat.setText( proudct.getCat());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProductFragment(proudct);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(proudct.getId(), itemPos);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView proname;
        public TextView price;
        public TextView cat;

        public Button edit;
        public Button delete;

        public ViewHolder(View view) {
            super(view);

            proname = (TextView) view.findViewById(R.id.proname_tv);
            price = (TextView) view.findViewById(R.id.price_tv);
            cat = (TextView) view.findViewById(R.id.cat_tv);

            edit = view.findViewById(R.id.edit_product_b);
            delete = view.findViewById(R.id.delete_product_b);
        }
    }

    private void editProductFragment(proudct proudct){
        FragmentManager fm = ((event_layout)context).getSupportFragmentManager();

        Bundle bundle=new Bundle();
        bundle.putParcelable("proudct",  proudct);

        AddProductFragment addFragment = new AddProductFragment();
        addFragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.product_content, addFragment).commit();
    }
    private void deleteProduct(String docId, final int position){
        firestoreDB.collection("proudct").document(docId).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        eventsList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, eventsList.size());
                        Toast.makeText(context,
                                "proudct  has been deleted",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}