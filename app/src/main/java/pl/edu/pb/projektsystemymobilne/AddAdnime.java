package pl.edu.pb.projektsystemymobilne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddAdnime extends AppCompatActivity {
    EditText id_input, name_input, score_input, watched_input;
    Button add_button;
    TextView anime_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adnime);

        id_input = findViewById(R.id.animeID_text);
        //name_input = findViewById(R.id.animeName_text);
        score_input = findViewById(R.id.score_text);
        anime_name = findViewById(R.id.animeName_display);
        watched_input = findViewById(R.id.add_watched_episodes);
        score_input.setFilters(new InputFilter[]{new MinMaxFilter("1", "10")});

        add_button = findViewById(R.id.add_button);

        Bundle b = getIntent().getExtras();
        double X = b.getDouble("X");
        double Y = b.getDouble("Y");
        String title = b.getString("title");
        int episodes = b.getInt("episodes");
        anime_name.setText(title);
        watched_input.setFilters(new InputFilter[]{new MinMaxFilter("0", Integer.toString(episodes))});
//        if(b != null){
//            X = b.getDouble("X");
//            Y = b.getDouble("Y");
//        }


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String watchi = watched_input.getText().toString();
                String scorei = score_input.getText().toString();
                if (scorei.matches("")) {
                    Toast.makeText(AddAdnime.this, "Score needed", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (watchi.matches("")) {
                        Toast.makeText(AddAdnime.this, "Watched episodes quantity needed", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        MyDatabaseHelper myDB = new MyDatabaseHelper(AddAdnime.this);
                        myDB.addAnime(
                                0,
                                title,
                                Integer.valueOf(score_input.getText().toString().trim()),
                                X,
                                Y,
                                episodes,
                                Integer.valueOf(watched_input.getText().toString().trim())
                        );
                        Intent intent = new Intent(AddAdnime.this, UserProfile.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
    }
}