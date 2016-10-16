package me.kevinkang.timecapsule.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import me.kevinkang.timecapsule.R;
import me.kevinkang.timecapsule.data.firebase.FbUser;
import me.kevinkang.timecapsule.data.firebase.FirebaseCapsule;
import me.kevinkang.timecapsule.data.firebase.FirebaseCapsuleRecipient;
import me.kevinkang.timecapsule.data.models.Recipient;

public class NewCapsuleActivity extends AppCompatActivity {
    // TODO: retrieve uid to get user
    private FbUser user;
    private static final String TAG = "NewCapsuleActivity";
    List<Recipient> recipients;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onClickAddRecipient(View view) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompts, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog
                .Builder(this);
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        if (recipients == null) {
            recipients = new ArrayList<>(); // DUMMY DATA

        }

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                Recipient recp = new FirebaseCapsuleRecipient("Kevin Kang",
                                        userInput.getText().toString());
                                recipients.add(recp);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void onClickCapsuleCreation(View view) {
        // Get Data
        EditText name = (EditText) findViewById(R.id.editName);
        EditText message = (EditText) findViewById(R.id.editMessage);
        String nameText = name.getText().toString();
        String messageText = message.getText().toString();
        if (nameText.isEmpty() || messageText.isEmpty()) {
            Snackbar.make(view, "The name and message must be filled", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        if (recipients == null) {
            recipients = new ArrayList<>(); // DUMMY DATA

        }
        long timeToOpen = System.currentTimeMillis() + 1000; // DUMMY DATA
        FirebaseCapsule capsule = new FirebaseCapsule(nameText, timeToOpen, recipients, messageText);
        //user.addCapsules(capsule);
        // Firebase Auth
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("capsules");

        // create capsule
        UUID key = UUID.randomUUID();
        String keyString = key.toString();
        Map<String, Object> capsuleValue = new HashMap<>();
        capsuleValue.put("name", capsule.getName());
        capsuleValue.put("message", capsule.getMessage());
        capsuleValue.put("user_id", fbUser.getUid());
        capsuleValue.put("date_created", System.currentTimeMillis());
        capsuleValue.put("date_to_open", capsule.getOpenDate());
        capsuleValue.put("recipients", capsule.getRecipients());
        // NOTE: IF NO RECIPIENTS OR ATTACHMENTS EXISTS, NO NODE WILL BE CREATED
        // TODO: retrieve uuid for recipient
        // capsuleValue.put("recipients", capsule.getRecipientsUUID());
        // TODO: retrieve uuid for attachments
        // capsuleValue.put("attachments", capsule.getAttachmentsUUID());

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(keyString, capsuleValue);

        // adds to db
        db.updateChildren(childUpdates);

        startActivity(new Intent(this, MainActivity.class));
    }
}
