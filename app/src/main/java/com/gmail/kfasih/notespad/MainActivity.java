package com.gmail.kfasih.notespad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FloatingActionButton floatingNotesAddBtn;
    RecyclerView rvNotesList;
    Button btnSignOut;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        rvNotesList = findViewById(R.id.rvNotesList);

        floatingNotesAddBtn = findViewById(R.id.floatingNotesAddBtn);
        rvNotesList = findViewById(R.id.rvNotesList);
        btnSignOut = findViewById(R.id.btnSignOut);

        NotesViewListAdaptor notesViewListAdaptor = new NotesViewListAdaptor(DB.getInstance(MainActivity.this).getAllNotes());
        rvNotesList.setAdapter(notesViewListAdaptor);
        rvNotesList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvNotesList.setHasFixedSize(true);
        notesViewListAdaptor.setOnViewClickListener(new OnViewClickListener() {
            @Override
            public void onItemViewClick(NotesModel notesModel, int position) {
                Intent intent = new Intent(MainActivity.this, Update.class);
                intent.putExtra(NotesModel.COL_Title,notesModel.getTitle());
                intent.putExtra(NotesModel.COL_DESCRIPTION,notesModel.getDescription());
                intent.putExtra(NotesModel.COL_DATE,notesModel.getDate());
                if(notesModel.getImagePath()!=null){
                    intent.putExtra(NotesModel.COL_IMAGE_PATH,notesModel.getImagePath());
                }
                intent.putExtra(NotesModel.COL_BACKGROUND_RESOURCE_ID,notesModel.getBackgroundResourceId());
                intent.putExtra(NotesModel.COL_ID,notesModel.getId());
                startActivity(intent);
            }
        });


        floatingNotesAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditNoteActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        NotesViewListAdaptor notesViewListAdaptor = new NotesViewListAdaptor(DB.getInstance(MainActivity.this).getAllNotes());
        rvNotesList.setAdapter(notesViewListAdaptor);
        rvNotesList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvNotesList.setHasFixedSize(true);



        notesViewListAdaptor.setOnViewClickListener(new OnViewClickListener() {
            @Override
            public void onItemViewClick(NotesModel notesModel, int position) {
                Intent intent = new Intent(MainActivity.this, Update.class);
                intent.putExtra(NotesModel.COL_Title,notesModel.getTitle());
                intent.putExtra(NotesModel.COL_DESCRIPTION,notesModel.getDescription());
                intent.putExtra(NotesModel.COL_DATE,notesModel.getDate());
                if(notesModel.getImagePath()!=null){
                    intent.putExtra(NotesModel.COL_IMAGE_PATH,notesModel.getImagePath());
                }
                intent.putExtra(NotesModel.COL_BACKGROUND_RESOURCE_ID,notesModel.getBackgroundResourceId());
                intent.putExtra(NotesModel.COL_ID,notesModel.getId());
                startActivity(intent);
            }
        });

    }
}