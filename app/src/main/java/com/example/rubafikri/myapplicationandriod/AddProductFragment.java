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


/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {

    private FirebaseFirestore firestoreDB;
    private boolean isEdit;

    private String docId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_event,
                container, false);

        Button button = (Button) view.findViewById(R.id.add_product);
         proudct proudct = null;
        if(getArguments() != null){
            proudct = getArguments().getParcelable("proudct");
            ((TextView)view.findViewById(R.id.add_tv)).setText("Edit proudct");
        }
        if(proudct != null){
            ((TextView) view.findViewById(R.id.product_name_a)).setText(proudct.getProname());
            ((TextView) view.findViewById(R.id.product_price_a)).setText(proudct.getPrice());
            ((TextView) view.findViewById(R.id.product_key_a)).setText(proudct.getKey());
            ((TextView) view.findViewById(R.id.cat_name_a)).setText(proudct.getCat());


            button.setText("Edit proudct");
            isEdit = true;
            docId = proudct.getId();
        }

        firestoreDB = FirebaseFirestore.getInstance();


        button.setOnClickListener(new View.OnClickListener()        {
            @Override
            public void onClick(View v)
            {
                if(!isEdit){
                    addProduct();
                }else {
                    updateProduct();
                }

            }
        });

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    public void addProduct(){
        proudct proudct = createProductObj();
        addDocumentToCollection(proudct);
    }
    public void updateProduct(){
        proudct proudct = createProductObj();
        updateDocumentToCollection(proudct);
    }
    private proudct createProductObj(){
        final proudct proudct = new proudct();
        proudct.setProname(((TextView)getActivity()
                .findViewById(R.id.product_name_a)).getText().toString());
        proudct.setPrice(((TextView)getActivity()
                .findViewById(R.id.product_price_a)).getText().toString());
        proudct.setKey(((TextView)getActivity()
                .findViewById(R.id.product_key_a)).getText().toString());

        proudct.setCat(((TextView)getActivity()
                .findViewById(R.id.cat_name_a)).getText().toString());
        return proudct;
    }
    private void addDocumentToCollection(proudct proudct){
        firestoreDB.collection("proudct")
                .add(proudct)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        restUi();
                        Toast.makeText(getActivity(),
                                "proudct  has been added",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),
                                "product  could not be added",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void updateDocumentToCollection(proudct product){
        firestoreDB.collection("proudct")
                .document(docId)
                .set(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(),
                                "product  has been updated",
                                Toast.LENGTH_SHORT).show();
                        showEventScreen();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),
                                "product  could not be added",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void restUi(){
        ((TextView)getActivity()
                .findViewById(R.id.product_name_a)).setText("");
        ((TextView)getActivity()
                .findViewById(R.id.product_price_a)).setText("");
        ((TextView)getActivity()
                .findViewById(R.id.product_key_a)).setText("");
        ((TextView)getActivity()
                .findViewById(R.id.cat_name_a)).setText("");
    }
    private void showEventScreen() {
        Intent i = new Intent();
        i.setClass(getActivity(), event_layout.class);
        startActivity(i);
    }
}