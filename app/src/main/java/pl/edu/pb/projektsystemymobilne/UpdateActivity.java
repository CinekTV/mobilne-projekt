package pl.edu.pb.projektsystemymobilne;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText id_input, name_input, score_input;
    Button update_button, delete_button;

    String mainID, id, name, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //id_input = findViewById(R.id.animeID_text2);
        name_input = findViewById(R.id.animeName_text2);
        score_input = findViewById(R.id.score_text2);
        score_input.setFilters(new InputFilter[]{new MinMaxFilter("1", "10")});
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_button.setOnClickListener(v -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            //id = id_input.getText().toString().trim();
            name =  name_input.getText().toString().trim();
            score = score_input.getText().toString().trim();
            myDB.updateData(mainID, name, score);
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("mainID") &&
                getIntent().hasExtra("name") &&
                getIntent().hasExtra("score")){
            //getting Data
            mainID = getIntent().getStringExtra("mainID");
            //id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            score = getIntent().getStringExtra("score");

            //Setting Data
            //id_input.setText(id);
            name_input.setText(name);
            score_input.setText(score);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(mainID);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}