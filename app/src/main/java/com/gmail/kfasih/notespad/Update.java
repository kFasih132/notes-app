package com.gmail.kfasih.notespad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;


public class Update extends AppCompatActivity {
    private AppCompatButton btnBack, btnChangeBackground, btnSave, btnDelete,btnShare;
    private AppCompatTextView tvViewDateAndTime ;
    private ImageView  ivImageView;
    private AppCompatEditText etViewTitle, etViewParagraph;
    DB db = DB.getInstance(this);


    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        btnBack = findViewById(R.id.btnBack);
        btnChangeBackground = findViewById(R.id.btnChangeBackground);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnShare = findViewById(R.id.btnShare);

        ivImageView =  findViewById(R.id.ivImageView);

        tvViewDateAndTime = findViewById(R.id.etViewDateAndTime);
        etViewTitle = findViewById(R.id.etViewTitle);
        etViewParagraph = findViewById(R.id.etViewParagraph);

        NotesModel notesModel = new NotesModel();
        Intent intent = getIntent();
        notesModel.setId(intent.getIntExtra(NotesModel.COL_ID,-2));
        notesModel.setTitle(intent.getStringExtra(NotesModel.COL_Title));
        etViewTitle.setText(notesModel.getTitle());
        notesModel.setDescription(intent.getStringExtra(NotesModel.COL_DESCRIPTION));
        etViewParagraph.setText(notesModel.getDescription());
        notesModel.setDate(intent.getStringExtra(NotesModel.COL_DATE));
        tvViewDateAndTime.setText(notesModel.getDate());
        notesModel.setImagePath(intent.getStringExtra(NotesModel.COL_IMAGE_PATH));
        notesModel.setBackgroundResourceId(intent.getIntExtra(NotesModel.COL_BACKGROUND_RESOURCE_ID,R.drawable.bg1));
        findViewById(R.id.update_main).setBackgroundResource(notesModel.getBackgroundResourceId());

        //setting Image
        if(notesModel.getImagePath()!=null){
            Bitmap bnImage = BitmapFactory.decodeFile(notesModel.getImagePath());
            ivImageView.setImageBitmap(bnImage);
        }


        btnBack.setOnClickListener(view -> {
            finish();
        });
        btnChangeBackground.setOnClickListener(view -> {
            // Create a new instance of the custom dialog
            AppCompatDialog dialog = new AppCompatDialog(this);
            dialog.setContentView(R.layout.custom_dialogue_for_bg);
            dialog.show();
            // Find the views in the custom dialog

            ImageView bg1 = dialog.findViewById(R.id.bg1);
            ImageView bg2 = dialog.findViewById(R.id.bg2);
            ImageView bg3 = dialog.findViewById(R.id.bg3);
            ImageView bg4 = dialog.findViewById(R.id.bg4);
            ImageView bg5 = dialog.findViewById(R.id.bg5);
            bg1.setOnClickListener(view1 -> {
                findViewById(R.id.update_main).setBackgroundResource(R.drawable.bg1);
                notesModel.setBackgroundResourceId(R.drawable.bg1);
                dialog.dismiss();
            });
            bg2.setOnClickListener(view1 -> {
                findViewById(R.id.update_main).setBackgroundResource(R.drawable.bg2);
                notesModel.setBackgroundResourceId(R.drawable.bg2);
                dialog.dismiss();
            });
            bg3.setOnClickListener(view1 -> {
                findViewById(R.id.update_main).setBackgroundResource(R.drawable.bg3);
                notesModel.setBackgroundResourceId(R.drawable.bg3);
                dialog.dismiss();
            });
            bg4.setOnClickListener(view1 -> {
                findViewById(R.id.update_main).setBackgroundResource(R.drawable.bg4);
                notesModel.setBackgroundResourceId(R.drawable.bg4);
                dialog.dismiss();
            });
            bg5.setOnClickListener(view1 -> {
                findViewById(R.id.update_main).setBackgroundResource(R.drawable.bg5);
                notesModel.setBackgroundResourceId(R.drawable.bg5);
                dialog.dismiss();
            });
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etViewTitle.getText().toString();
                String description = etViewParagraph.getText().toString();
                String date = tvViewDateAndTime.getText().toString();

                notesModel.setTitle(title);
                notesModel.setDescription(description);
                notesModel.setDate(date);

                if(db.updateNotes(notesModel)){
                    Toast.makeText(Update.this, "Note Updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Update.this, "Note Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(view -> {
            if(db.deleteNotes(notesModel)){
                Toast.makeText(Update.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Update.this, "Note Not Deleted", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
        btnShare.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, notesModel.getTitle()+" : \n"+notesModel.getDescription());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

    }
}