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
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText id_input, name_input, score_input, ep_input;
    Button update_button, delete_button;

    String mainID, id, name, score, episodes_watched, episodes_max, cordX_txt, cordY_txt ;
    TextView status, maxep, cordX, cordY, anime_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //id_input = findViewById(R.id.animeID_text2);
        name_input = findViewById(R.id.animeName_text2);
        score_input = findViewById(R.id.score_text2);
        maxep = findViewById(R.id.outOfEpisodes);
        status = findViewById(R.id.anime_status);
        cordX = findViewById(R.id.text_displayX_cord);
        cordY = findViewById(R.id.text_displayY_cord);
        anime_name = findViewById(R.id.animeName_display2);
        ep_input = findViewById(R.id.watched_episodes2);
        getAndSetIntentData();


        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        score_input.setFilters(new InputFilter[]{new MinMaxFilter("1", "10")});

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_button.setOnClickListener(v -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            //id = id_input.getText().toString().trim();
            name =  anime_name.getText().toString().trim();
            score = score_input.getText().toString().trim();
            cordX_txt = cordX.getText().toString().trim();
            cordY_txt = cordY.getText().toString().trim();
            episodes_watched = ep_input.getText().toString().trim();
            episodes_max = maxep.getText().toString().trim();
            myDB.updateData(mainID, name, score, cordX_txt, cordY_txt, episodes_watched, episodes_max);
            finish();
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
            cordX_txt = getIntent().getStringExtra("X");
            cordY_txt = getIntent().getStringExtra("Y");
            episodes_max = getIntent().getStringExtra("episodesMax");
            episodes_watched = getIntent().getStringExtra("episodesWat");

            //Setting Data
            //id_input.setText(id);
            maxep.setText(episodes_max);
            ep_input.setText(episodes_watched);
            cordX.setText(cordX_txt);
            cordY.setText(cordY_txt);
            anime_name.setText(name);
            score_input.setText(score);

            if (episodes_watched .equals(episodes_max)){
                status.setText("Completed");
            }else if (episodes_watched == "0"){
                status.setText("Watchn't");
            }else{
                status.setText("Watching");
            }

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