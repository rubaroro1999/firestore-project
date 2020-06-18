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

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder> {
    private List<user> userList;
    private Context context;
    private FirebaseFirestore firestoreDB;
    public UsersRecyclerViewAdapter(List<user> list, Context ctx, FirebaseFirestore firestore) {
        userList = list;
        context = ctx;
        firestoreDB = firestore;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_user_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final int itemPos = i;
        final user user = userList.get(i);
        viewHolder.name.setText(user.getName());
        viewHolder.phone.setText(user.getPhone());


        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserFragment(user);
            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(user.getId(), itemPos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView phone;

        public Button edit;
        public Button delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.username_tv);
            phone = (TextView) itemView.findViewById(R.id.phone_tv);

            edit = itemView.findViewById(R.id.edit_user_b);
            delete = itemView.findViewById(R.id.delete_user_b);
        }
    }


    private void editUserFragment(user user){
        FragmentManager fm = ((event_layout2)context).getSupportFragmentManager();

        Bundle bundle=new Bundle();
        bundle.putParcelable("user",  user);

        AddUserFragment addFragment = new AddUserFragment();
        addFragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.user_content, addFragment).commit();
    }
    private void deleteUser(String docId, final int position){
        firestoreDB.collection("user").document(docId).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        userList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, userList.size());
                        Toast.makeText(context,
                                "User has been deleted",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
