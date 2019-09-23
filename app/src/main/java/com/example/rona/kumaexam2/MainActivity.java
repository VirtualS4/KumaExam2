package com.example.rona.kumaexam2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    List<Notes> nlist;

    DatabaseReference db;

    @Override
    protected void onStart() {
        super.onStart();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nlist.clear();
                for(DataSnapshot notess: dataSnapshot.getChildren()){
                    Notes note = notess.getValue(Notes.class);
                    nlist.add(note);
                }

                ArrayAdapter adapter = new NoteList(MainActivity.this,nlist);
                lv = findViewById(R.id.list);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bearsense = new Intent(MainActivity.this,ManageData.class);
                bearsense.putExtra("action","Insert");
                bearsense.putExtra("id","");
                startActivity(bearsense);
            }
        });

        db = FirebaseDatabase.getInstance().getReference("Notes");

        lv = findViewById(R.id.list);
        nlist = new ArrayList<Notes>();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notes bear = nlist.get(position);
                showUpdateDialog(bear.getId());
            }
        });

    }

    private void showUpdateDialog(final String id){
        AlertDialog.Builder dialogb = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogv = inflater.inflate(R.layout.dialog,null);

        dialogb.setView(dialogv);

        final Button btn_edit = dialogv.findViewById(R.id.Edit);
        final Button btn_delete = dialogv.findViewById(R.id.Delete);
        final AlertDialog ad = dialogb.create();
        ad.show();

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bearsense = new Intent(MainActivity.this,ManageData.class);
                bearsense.putExtra("action","Update");
                bearsense.putExtra("id",id);
                startActivity(bearsense);
                ad.dismiss();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete(id);
            }
        });

    }

    private void Delete(String id){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Notes").child(id);
        db.removeValue();
        Toast.makeText(this,"Note is gone QwQ",Toast.LENGTH_LONG).show();
    }
}
