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
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class ViewProductFragment extends Fragment {
    private static final String TAG = "ViewProductFragment";
    private FirebaseFirestore firestoreDB;
    private RecyclerView eventsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_events,
                container, false);

        firestoreDB = FirebaseFirestore.getInstance();

        firestoreDB.collection("proudct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<proudct> eventList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                proudct e = doc.toObject(proudct.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                            ProudctRecyclerViewAdapter recyclerViewAdapter = new
                                    ProudctRecyclerViewAdapter(eventList,
                                    getActivity(), firestoreDB);
                            eventsRecyclerView.setAdapter(recyclerViewAdapter);

                        }
                    }
                });




        Button button = (Button) view.findViewById(R.id.view_product);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewEvents();
            }
        });

        eventsRecyclerView = (RecyclerView) view.findViewById(R.id.product_lst);

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        eventsRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(eventsRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        eventsRecyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void viewEvents() {
        String cat = ((TextView) getActivity()
                .findViewById(R.id.cat_v)).getText().toString();
        getDocumentsFromCollection(cat);
    }

    private void getDocumentsFromCollection(String cat) {
        firestoreDB.collection("proudct")
                .whereEqualTo("cat", cat)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<proudct> eventList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                proudct e = doc.toObject(proudct.class);
                                e.setId(doc.getId());
                                eventList.add(e);
                            }
                            ProudctRecyclerViewAdapter recyclerViewAdapter = new
                                    ProudctRecyclerViewAdapter(eventList,
                                    getActivity(), firestoreDB);
                            eventsRecyclerView.setAdapter(recyclerViewAdapter);

                        }
                    }
                });

        firestoreDB.collection("proudct")
                .whereEqualTo("cat", cat)
                .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        for(DocumentChange doc : documentSnapshots.getDocumentChanges()){
                            doc.getDocument().toObject(proudct.class);
                            //do something...
                        }
                    }
                });
    }
}
