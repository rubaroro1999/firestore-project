package com.example.rubafikri.myapplicationandriod;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserFragment extends Fragment {


    private FirebaseFirestore firestoreDB;
    private boolean isEdit;

    private String docId;
    public AddUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_user, container, false);

        Button button = (Button) view.findViewById(R.id.add_user);

        firestoreDB = FirebaseFirestore.getInstance();
        user  user = null;
        if(getArguments() != null){
            user = getArguments().getParcelable("user");
            ((TextView)view.findViewById(R.id.add_tv)).setText("Edit user");
        }
        if(user != null){
            ((TextView) view.findViewById(R.id.user_name_a)).setText(user.getName());
            ((TextView) view.findViewById(R.id.user_email_a)).setText(user.getEmail());
            ((TextView) view.findViewById(R.id.user_phone_a)).setText(user.getPhone());

            button.setText("Edit user");
            isEdit = true;
            docId = user.getId();
        }

        firestoreDB = FirebaseFirestore.getInstance();


        button.setOnClickListener(new View.OnClickListener()        {
            @Override
            public void onClick(View v)
            {
                if(!isEdit){
                    addUser();
                }else {
                    updateUser();
                }

            }
        });


        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void addUser(){
        user user = createUserObj();
        addDocumentToCollection(user);
    }
    public void updateUser(){
        user user = createUserObj();
        updateDocumentToCollection(user);
    }
    private user createUserObj(){
        final user user = new user();
        user.setName(((TextView )getActivity()
                .findViewById(R.id.user_name_a)).getText().toString());
        user.setEmail(((TextView)getActivity()
                .findViewById(R.id.user_email_a)).getText().toString());
        user.setPhone(((TextView)getActivity()
                .findViewById(R.id.user_phone_a)).getText().toString());

        return user;
    }

    private void addDocumentToCollection(user user){
        firestoreDB.collection("user")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                           restUi();
                        Toast.makeText(getActivity(),
                                "User has been added",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),
                                "User could not be added",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateDocumentToCollection(user user){
        firestoreDB.collection("user")
                .document(docId)
                .set(user, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(),
                                "User has been updated",
                                Toast.LENGTH_SHORT).show();
                        showUserScreen();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),
                                "User could not be added",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void restUi(){
        ((TextView)getActivity()
                .findViewById(R.id.user_name_a)).setText("");
        ((TextView)getActivity()
                .findViewById(R.id.user_email_a)).setText("");
        ((TextView)getActivity()
                .findViewById(R.id.user_phone_a)).setText("");

    }

    private void showUserScreen() {
        Intent i = new Intent();
        i.setClass(getActivity(), event_layout2.class);
        startActivity(i);
    }
}
