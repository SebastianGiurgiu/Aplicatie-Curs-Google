package com.example.prototip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prototip.local.DatabaseHelper;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewAlimentActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextProteine;
    private EditText editTextCarbohidrati;
    private EditText editTextGrasimi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_aliment_activty);

        editTextName = findViewById(R.id.editTextName);
        editTextProteine = findViewById(R.id.editTextProteine);
        editTextCarbohidrati = findViewById(R.id.editTextCarbohidrati);
        editTextGrasimi = findViewById(R.id.editTextGrasimi);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_aliment_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_aliment:
                saveAliment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveAliment() {

        String name = editTextName.getText().toString();
        Integer proteine = Integer.valueOf(editTextProteine.getText().toString());
        Integer carbohidrati = Integer.valueOf(editTextCarbohidrati.getText().toString());
        Integer grasimi = Integer.valueOf(editTextGrasimi.getText().toString());
        Intent previousScreen = new Intent(getApplicationContext(), MainActivity.class);
        //Sending the data to Activity_A
        String trim = name+","+proteine+","+carbohidrati+","+grasimi;
        previousScreen.putExtra("name",trim);

        setResult(1000, previousScreen);
        finish();
    }
}
