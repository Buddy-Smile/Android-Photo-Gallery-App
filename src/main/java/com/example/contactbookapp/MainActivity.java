package com.example.contactbookapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView contactListView;
    private Button addContactButton;
    private ArrayList<String> contactList;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactListView = findViewById(R.id.contact_list);
        addContactButton = findViewById(R.id.add_contact_button);

        sharedPreferences = getSharedPreferences("ContactBook", MODE_PRIVATE);
        contactList = new ArrayList<>(sharedPreferences.getStringSet("contacts", new HashSet<>()));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
        contactListView.setAdapter(adapter);

        addContactButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
            startActivityForResult(intent, 1);
        });

        contactListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
            intent.putExtra("contact", contactList.get(position));
            intent.putExtra("position", position);
            startActivityForResult(intent, 2);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String contact = data.getStringExtra("contact");
            if (requestCode == 1) {
                contactList.add(contact);
            } else if (requestCode == 2) {
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    contactList.set(position, contact);
                }
            }
            adapter.notifyDataSetChanged();
            saveContacts();
        }
    }

    private void saveContacts() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(contactList);
        editor.putStringSet("contacts", set);
        editor.apply();
    }
}