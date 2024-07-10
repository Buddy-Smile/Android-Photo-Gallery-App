package com.example.contactbookapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditContactActivity extends AppCompatActivity {

    private EditText contactNameEditText;
    private EditText contactPhoneEditText;
    private Button saveButton;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        contactNameEditText = findViewById(R.id.contact_name);
        contactPhoneEditText = findViewById(R.id.contact_phone);
        saveButton = findViewById(R.id.save_button);

        Intent intent = getIntent();
        if (intent.hasExtra("contact")) {
            String contact = intent.getStringExtra("contact");
            position = intent.getIntExtra("position", -1);
            String[] parts = contact.split(":");
            contactNameEditText.setText(parts[0]);
            contactPhoneEditText.setText(parts[1]);
        }

        saveButton.setOnClickListener(v -> {
            String name = contactNameEditText.getText().toString();
            String phone = contactPhoneEditText.getText().toString();
            String contact = name + ":" + phone;

            Intent resultIntent = new Intent();
            resultIntent.putExtra("contact", contact);
            resultIntent.putExtra("position", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}