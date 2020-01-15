package com.example.myappgoogle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextProteine;
    EditText editTextCarbohidrati;
    EditText editTextGrasimi;
    Button addButton;
    DatabaseReference databaseAliments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseAliments = FirebaseDatabase.getInstance().getReference("aliment");



        editTextName = findViewById(R.id.editTextName);
        editTextProteine = findViewById(R.id.editTextProteine);
        editTextCarbohidrati = findViewById(R.id.editTextCarbohidrati);
        editTextGrasimi = findViewById(R.id.editTextGrasimi);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGrocer();
            }
        });


    }


    private void addGrocer(){

        String name = editTextName.getText().toString().trim();
        Integer proteine = Integer.valueOf(editTextProteine.getText().toString().trim());
        Integer carbohidrati = Integer.valueOf(editTextCarbohidrati.getText().toString().trim());
        Integer grasimi = Integer.valueOf(editTextGrasimi.getText().toString().trim());


        if(!TextUtils.isEmpty(name)){

            String id = databaseAliments.push().getKey();

            Aliment aliment = new Aliment(name,proteine,carbohidrati,grasimi);

            databaseAliments.child(id).setValue(aliment);

            Toast.makeText(this,"Aliment added",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"You should enter a name ", Toast.LENGTH_LONG).show();
        }

    }

}
