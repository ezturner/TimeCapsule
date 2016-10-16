package me.kevinkang.timecapsule.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.kevinkang.timecapsule.R;
import me.kevinkang.timecapsule.data.firebase.FbUser;
import me.kevinkang.timecapsule.data.firebase.FirebaseAttachment;
import me.kevinkang.timecapsule.data.firebase.FirebaseCapsule;
import me.kevinkang.timecapsule.data.models.Recipient;

public class NewCapsuleActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 24;
    private static final int PICKFILE_REQUEST_CODE = 12;
    // TODO: retrieve uid to get user
    private FbUser user;
    private static final String TAG = "NewCapsuleActivity";

    private List<FirebaseAttachment> attachments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_capsule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAttachmentDialog();
            }
        });
    }

    public void onClickCapsuleCreation(View view) {
        EditText name = (EditText) findViewById(R.id.editName);
        EditText message = (EditText) findViewById(R.id.editMessage);
        String nameText = name.getText().toString();
        String messageText = message.getText().toString();
        if (nameText.isEmpty() || messageText.isEmpty()) {
            Snackbar.make(view, "The name and message must be filled", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        long openDate = 1;
        List<Recipient> recipients = new ArrayList<>();
        FirebaseCapsule capsule = new FirebaseCapsule(nameText, System.currentTimeMillis() + 1000, recipients, messageText);
        //user.addCapsules(capsule);
        // Firebase Auth
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("capsules");
        // post capsule
        UUID key = UUID.randomUUID();
        String keyString = key.toString();
        Map<String, Object> capsuleValue = new HashMap<>();
        capsuleValue.put("name", capsule.getName());
        capsuleValue.put("message", capsule.getMessage());
        capsuleValue.put("user_id", fbUser.getUid());
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(keyString, capsuleValue);
        db.updateChildren(childUpdates);
        startActivity(new Intent(this, MainActivity.class));
    }

    public void openAttachmentDialog(){
        new MaterialDialog.Builder(this)
                .title(R.string.attachment_title)
                .positiveText(R.string.image)
                .negativeText(R.string.file)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        openImageChooser();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        openFileChooser();
                    }
                })
                .show();
    }

    public void openFileChooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    public void openImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            Uri uri = data.getData();
            String type = "image";

            if(isVideoFile(uri.getPath())){
                type = "video";
            }
            attachments.add(new FirebaseAttachment(type, new File(type, uri.getPath())));

            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        } else if(requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if (data == null) {
                //Display an error
                return;
            }
            Uri uri = data.getData();
            String type = "file";
            attachments.add(new FirebaseAttachment(type, new File(type, uri.getPath())));
        }
    }

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }
}
