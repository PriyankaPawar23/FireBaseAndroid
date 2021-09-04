package com.android.task_fairshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddContact extends AppCompatActivity implements FirebaseAuth.AuthStateListener {

    TextInputEditText Name,Number;
    AppCompatButton AddContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Name=findViewById(R.id.Name);
        Number=findViewById(R.id.Number);
        AddContact=findViewById(R.id.AddContact);

        AddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData(Name.getText().toString(),Number.getText().toString());
            }
        });
    }

    private void addData(String Name,String Number){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Contact contact = new Contact(Name, Number, userId);

        FirebaseFirestore.getInstance().collection("contact")
                .add(contact)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddContact.this, "contact added successfully!", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddContact.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onAuthStateChanged(@NonNull @org.jetbrains.annotations.NotNull FirebaseAuth firebaseAuth) {

    }
    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
}