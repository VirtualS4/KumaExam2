package com.example.rona.kumaexam2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Locale;

public class ManageData extends AppCompatActivity {
    TextView act;
    EditText jdl,desc;
    Button confirm,delete,back;
    String id = "";

    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_data);

        db = FirebaseDatabase.getInstance().getReference("Notes");
        act = findViewById(R.id.action_txt);
        jdl = findViewById(R.id.edt_judul);
        desc = findViewById(R.id.edt_detail);
        confirm = findViewById(R.id.btn_confirm);
        delete = findViewById(R.id.btn_delete);
        back = findViewById(R.id.btn_back);

        Intent bearsense = getIntent();
        String action = bearsense.getStringExtra("action");
        id = bearsense.getStringExtra("id");
        act.setText(action);

        if(!id.isEmpty()){
            initUpdate(id);
            delete.setEnabled(true);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Delete(id);
                }
            });
        }
        initbtn(confirm,MainActivity.class,action);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bearsense = new Intent(ManageData.this,MainActivity.class);
                startActivity(bearsense);
            }
        });

    }

    private void initbtn(Button btn,final Class bearclass,final String act){
        confirm.setText(act);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(act.equals("Insert")){
                    Insert();
                }else if(act.equals("Update")){
                    Update(id);
                }
                Intent bearsense = new Intent(ManageData.this,bearclass);
                startActivity(bearsense);
            }
        });
    }

    private void initUpdate(final String id){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Notes").child(id);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Notes note = dataSnapshot.getValue(Notes.class);
                jdl.setText(note.getJudul());
                desc.setText(note.getDeskripsi());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



    }

    private void Insert(){
        String judul = jdl.getText().toString();
        String deskripsi = desc.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd  hh:mm:ss", Locale.getDefault());
        String tanggal = sdf.format(new Date());

        if(!TextUtils.isEmpty(judul)){

            String id = db.push().getKey();

            Notes newnotes = new Notes(id,judul,deskripsi,tanggal);

            db.child(id).setValue(newnotes);

            Toast.makeText(this,"Notes is in",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Notes Has No Name QwQ ",Toast.LENGTH_LONG).show();
        }
    }

    private boolean Update(String id){
        String judul = jdl.getText().toString();
        String deskripsi = desc.getText().toString();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Notes").child(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd  hh:mm:ss", Locale.getDefault());
        String tanggal = sdf.format(new Date());
        Notes note = new Notes(id,judul,deskripsi,tanggal);

        db.setValue(note);

        return true;
    }

    private void Delete(String id){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Notes").child(id);
        db.removeValue();
        Toast.makeText(this,"Note is gone QwQ",Toast.LENGTH_LONG).show();
    }
}
