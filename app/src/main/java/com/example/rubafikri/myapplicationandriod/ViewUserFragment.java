package com.example.rubafikri.myapplicationandriod;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewUserFragment extends Fragment {
    private FirebaseFirestore firestoreDB;
    private RecyclerView usersRecyclerView;

    public ViewUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_user,
                container, false);

        firestoreDB = FirebaseFirestore.getInstance();

        firestoreDB.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<user> eventList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                user e = doc.toObject(user.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                            UsersRecyclerViewAdapter recyclerViewAdapter = new
                                    UsersRecyclerViewAdapter(eventList,
                                    getActivity(), firestoreDB);
                            usersRecyclerView.setAdapter(recyclerViewAdapter);

                        }
                    }
                });

        usersRecyclerView = (RecyclerView) view.findViewById(R.id.user_lst);

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        usersRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(usersRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        usersRecyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }


}
