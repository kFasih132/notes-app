package com.gmail.kfasih.notespad;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.ai.client.generativeai.BuildConfig;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Executor;


public class EditNoteActivity extends AppCompatActivity {
    private AppCompatButton btnBack, btnChangeBackground, btnSave, btnPromptSend;
    private AppCompatTextView tvViewDateAndTime ;
    private ImageView ivPromptImage , ivImageView;
    private AppCompatEditText etViewTitle, etViewParagraph, etPromptInput;
    DB db = DB.getInstance(this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        NotesModel notesModel = new NotesModel();
        notesModel.setBackgroundResourceId(R.color.light_bg_color);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        btnBack = findViewById(R.id.btnBack);
        btnChangeBackground = findViewById(R.id.btnChangeBackground);
        btnSave = findViewById(R.id.btnSave);
        btnPromptSend = findViewById(R.id.btnPromptSend);

        ivPromptImage = findViewById(R.id.ivPromptImage);
        ivImageView =  findViewById(R.id.ivImageView);

        etPromptInput = findViewById(R.id.etPromptInput);

        etViewTitle = findViewById(R.id.etViewTitle);
        tvViewDateAndTime = findViewById(R.id.etViewDateAndTime);
        etViewParagraph = findViewById(R.id.etViewParagraph);



        //setting Current Date and time
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime ldt = LocalDateTime.now().plusDays(1);
            DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("HH:mm  dd-MM-yyyy", Locale.ENGLISH);
            String formatter = formmat1.format(ldt);
            tvViewDateAndTime.setText(formatter);
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
                findViewById(R.id.edit_note_main).setBackgroundResource(R.drawable.bg1);
                notesModel.setBackgroundResourceId(R.drawable.bg2);
                dialog.dismiss();
            });
            bg2.setOnClickListener(view1 -> {
                findViewById(R.id.edit_note_main).setBackgroundResource(R.drawable.bg2);
                notesModel.setBackgroundResourceId(R.drawable.bg2);
                dialog.dismiss();
            });
            bg3.setOnClickListener(view1 -> {
                findViewById(R.id.edit_note_main).setBackgroundResource(R.drawable.bg3);

                notesModel.setBackgroundResourceId(R.drawable.bg3);
                dialog.dismiss();
            });
            bg4.setOnClickListener(view1 -> {
                findViewById(R.id.edit_note_main).setBackgroundResource(R.drawable.bg4);
                notesModel.setBackgroundResourceId(R.drawable.bg4);
                dialog.dismiss();
            });
            bg5.setOnClickListener(view1 -> {
                findViewById(R.id.edit_note_main).setBackgroundResource(R.drawable.bg5);

                notesModel.setBackgroundResourceId(R.drawable.bg5);
                dialog.dismiss();
            });

        });
        final Bitmap[] image = new Bitmap[1];
        ActivityResultLauncher<PickVisualMediaRequest> getPicLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                try {
                    image[0] = MediaStore.Images.Media.getBitmap(getContentResolver(), o);
                    ivPromptImage.setImageBitmap(image[0]);
                }catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(EditNoteActivity.this, "Can't set Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivPromptImage.setOnClickListener(view -> {
            getPicLauncher.launch(new PickVisualMediaRequest());
        });

        btnPromptSend.setOnClickListener(view -> {
            // The Gemini 1.5 models are versatile and work with both text-only and multimodal prompts
            GenerativeModel gm = new GenerativeModel(/* modelName */ "gemini-1.5-flash",
// Access your API key as a Build Configuration variable (see "Set up your API key" above)
                    /* apiKey */ BuildConfig.API_KEY);
            GenerativeModelFutures model = GenerativeModelFutures.from(gm);

            Executor executor = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                executor = getMainExecutor();
            }

           if (image[0]!=null){

               Content content1 = new Content.Builder()
                       .addText(etPromptInput.getText().toString())
                       .addImage(image[0])
                       .build();


               ListenableFuture<GenerateContentResponse> response1 = model.generateContent(content1);
               Futures.addCallback(response1, new FutureCallback<GenerateContentResponse>() {
                   @Override
                   public void onSuccess(GenerateContentResponse result) {
                       String resultText = result.getText();
                       System.out.println(resultText);
                       etViewParagraph.setText(resultText);
                       etViewTitle.setText(etPromptInput.getText().toString());
                       ivImageView.setImageBitmap(image[0]);

                   }

                   @Override
                   public void onFailure(Throwable t) {
                       t.printStackTrace();
                       Toast.makeText(EditNoteActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                   }
               }, executor);
           }else {


               Content content = new Content.Builder()
                       .addText(etPromptInput.getText().toString())
                       .build();


               ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
               Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
                   @Override
                   public void onSuccess(GenerateContentResponse result) {
                       String resultText = result.getText();
                       System.out.println(resultText);
                       etViewParagraph.setText(resultText);
                       etViewTitle.setText(etPromptInput.getText().toString());
                   }

                   @Override
                   public void onFailure(Throwable t) {
                       t.printStackTrace();
                       Toast.makeText(EditNoteActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                   }
               }, executor);
           }

        });

        btnSave.setOnClickListener(view -> {
            String title = etViewTitle.getText().toString();
            String description = etViewParagraph.getText().toString();
            String date = tvViewDateAndTime.getText().toString();
            notesModel.setTitle(title);
            notesModel.setDescription(description);
            notesModel.setDate(date);


            ImageLogic imageLogic = new ImageLogic();
            String imagePath = imageLogic.saveToInternalStorage(image[0],this);
            notesModel.setImagePath(imagePath);

                if(db.insertNotes(notesModel)){
                    Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
                }
                finish();
        });

    }
}