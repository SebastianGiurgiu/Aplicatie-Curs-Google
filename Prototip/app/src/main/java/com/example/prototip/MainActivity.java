package com.example.prototip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prototip.Animation.AnimationScreen;
import com.example.prototip.Calculator.CalculatorCalorii;
import com.example.prototip.local.DatabaseHelper;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.ObservableSnapshotArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference alimentRef = db.collection("Aliment");
    private AlimentAdapter adapter;
    private List<Pair<Aliment,Integer>> list;
    String rez;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DatabaseHelper(this);

        myDb.deleteData("asdsad");
        list = new ArrayList<>();


        Button buttonCalculatorCalorii = findViewById(R.id.buttonCalculatorCalorii);
        buttonCalculatorCalorii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalculatorCalorii.class);
                startActivity(intent);
            }
        });

        Button buttonAnimation = findViewById(R.id.buttonAnimation);
        buttonAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnimationScreen.class);
                startActivity(intent);
            }
        });

        FloatingActionButton buttonAddAliment = findViewById(R.id.button_add_aliment);
        buttonAddAliment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this,NewAlimentActivity.class),1000);
            }
        });

        FloatingActionButton buttonClearMeal = findViewById(R.id.buttonClearMeal);
        buttonClearMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
            }
        });

        FloatingActionButton buttonSendEmail = findViewById(R.id.buttonSendEmail);
        buttonSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(rez);
            }
        });


        Button buttonPreview = findViewById(R.id.buttonViewPreviewMeal);
        buttonPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               rez = "";

                for (Pair<Aliment,Integer> item :list) {
                    rez += item.first.getName() + " " + item.second.toString() + " gr\n";
                }
                Toast.makeText(MainActivity.this,rez,Toast.LENGTH_SHORT).show();

            }
        });

        setUpRecyclerView();



    }

    private void setUpRecyclerView() {

        final List<Aliment> arrList = new ArrayList<>();

        final Query query = alimentRef.orderBy("name",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Aliment> options = new FirestoreRecyclerOptions.Builder<Aliment>()
                .setQuery(query,Aliment.class)
                .build();


        Cursor res = myDb.getAllData();
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            Aliment aliment = new Aliment();
            aliment.setName(res.getString(1));
            aliment.setProteine(Integer.valueOf(res.getString(2)));
            aliment.setCarbohidrati(Integer.valueOf(res.getString(3)));
            aliment.setGrasimi(Integer.valueOf(res.getString(4)));
            arrList.add(aliment);
            buffer.append(res.getString(1)+"\n");
        }


        adapter = new AlimentAdapter(arrList);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteAliment(viewHolder.getAdapterPosition());
                AlimentAdapter.AlimentHolder alimentHolder = (AlimentAdapter.AlimentHolder) viewHolder;
                myDb.deleteData(alimentHolder.textViewName.getText().toString());
            }
        }).attachToRecyclerView(recyclerView);




        adapter.setOnClicListener(new AlimentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Aliment aliment, int position) {
                //final Aliment aliment = documentSnapshot.toObject(Aliment.class);
                final Aliment copie = aliment;
                final String name = aliment.getName();

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.window_dialog,null);
                final EditText mQuantity = mView.findViewById(R.id.editTextQuantity);
                final TextView mTextViewName = mView.findViewById(R.id.textviewNameDialog);
                final TextView mTextViewCaloriesNumber = mView.findViewById(R.id.textViewCaloriesNumber);

                int proteine = aliment.getProteine();
                int carbohidrati = aliment.getCarbohidrati();
                int grasimi = aliment.getGrasimi();
                final int calorii = 4*proteine + 4* carbohidrati + 9*grasimi;
                mTextViewCaloriesNumber.setText(String.valueOf(calorii));

                mTextViewName.setText(aliment.getName());

                PieChartView pieChartView = mView.findViewById(R.id.chart);
                List<SliceValue> pieData = new ArrayList<>();
                pieData.add(new SliceValue( (100*proteine)/calorii, Color.BLUE).setLabel("Proteine: "+proteine + " gr"));
                pieData.add(new SliceValue((100*carbohidrati)/calorii, Color.GRAY).setLabel("Carbohidrati: "+carbohidrati + " gr"));
                pieData.add(new SliceValue((100*grasimi)/calorii, Color.RED).setLabel("Grasimi: "+grasimi + " gr"));

                PieChartData pieChartData = new PieChartData(pieData);
                pieChartData.setHasLabels(true);
                pieChartView.setPieChartData(pieChartData);



                final Button mPreviewButton= mView.findViewById(R.id.buttonPreview);
                mPreviewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!mQuantity.getText().toString().isEmpty()){
                            int quantity = Integer.parseInt(mQuantity.getText().toString());
                            int rez = quantity*calorii/100;
                            Toast.makeText(MainActivity.this,"La aceasta cantitate sunt " + rez + " calorii",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                final Button mAddButon = mView.findViewById(R.id.buttonAddToMeal);
                mAddButon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int quantity = Integer.parseInt(mQuantity.getText().toString());
                        Toast.makeText(MainActivity.this, "Ai adaugat "+ quantity + " grame de " + name, Toast.LENGTH_SHORT).show();
                        list.add(new Pair<>(copie,quantity));
                    }
                });




                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
       // adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
      //  adapter.stopListening();
    }

    @SuppressLint("LongLogTag")
    protected void sendEmail(String rez) {
        Log.i("Send email", "");

        String[] TO = {"sebastiangiurgiu1998@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your meal");
        emailIntent.putExtra(Intent.EXTRA_TEXT, rez);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_file,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        String recv = data.getStringExtra("name");
        String[] arrOfStr = recv.split(",");
        myDb.insertData(arrOfStr[0],Integer.valueOf(arrOfStr[1]),Integer.valueOf(arrOfStr[2]),Integer.valueOf(arrOfStr[3]));
        setUpRecyclerView();
        //Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
    }
}
