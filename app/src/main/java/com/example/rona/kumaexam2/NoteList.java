package com.example.rona.kumaexam2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;

public class NoteList extends ArrayAdapter {
    private Activity con;
    private List<Notes> notelist;

    public NoteList(Activity con, List<Notes> list){
        super(con, R.layout.notes_layout,list);
        this.con = con;
        this.notelist = list;
    }

    @NonNull
    @Override
    public View getView(int position, View cview, ViewGroup parent){
        LayoutInflater inflater = con.getLayoutInflater();

        View ListViewBear = inflater.inflate(R.layout.notes_layout,null,true);

        TextView jdl = ListViewBear.findViewById(R.id.judul);
        TextView detail = ListViewBear.findViewById(R.id.detail);
        TextView tanggal = ListViewBear.findViewById(R.id.txt_tanggal);

        Notes note = notelist.get(position);

        jdl.setText(note.getJudul());
        detail.setText(note.getDeskripsi());
        tanggal.setText(note.getTanggal());

        return ListViewBear;
    }
}
