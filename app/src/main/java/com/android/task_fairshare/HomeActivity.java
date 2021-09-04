package com.android.task_fairshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class HomeActivity extends AppCompatActivity{

    FloatingActionButton Add;
    RecyclerView recyclerView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private FirestoreRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerView);
        Add = findViewById(R.id.floatingActionButton);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddContact.class);
                startActivity(intent);
            }
        });

        init();
        getContact();
    }

    private void init()
    {
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getContact(){
        Query query = FirebaseFirestore.getInstance()
                .collection("contact")
               .whereEqualTo("userId", user.getUid());

        FirestoreRecyclerOptions<Contact> contact = new FirestoreRecyclerOptions.Builder<Contact>()
                .setQuery(query, Contact.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Contact, ContactHolder>(contact) {
            @Override
            public void onBindViewHolder(@NotNull ContactHolder holder, int position, @NotNull Contact model) {
                holder.nameraw.setText(model.getName());
                holder.numberraw.setText(model.getNumber());

                holder.itemView.setOnClickListener(v -> {
                    Snackbar.make(recyclerView, model.getName()+", "+model.getNumber(),Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                });
            }

            @Override
            public @NotNull ContactHolder onCreateViewHolder(@NotNull ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.contactrow, group, false);

                return new ContactHolder(view);
            }

            @Override
            public void onError(@NotNull FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public static class ContactHolder extends RecyclerView.ViewHolder {
        TextView nameraw;
        TextView numberraw;
        public ContactHolder(View itemView) {
            super(itemView);
            nameraw=itemView.findViewById(R.id.name_raw);
            numberraw=itemView.findViewById(R.id.number_raw);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}