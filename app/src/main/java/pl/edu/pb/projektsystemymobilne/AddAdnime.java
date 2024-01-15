package pl.edu.pb.projektsystemymobilne;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddAdnime extends AppCompatActivity {
    EditText id_input, name_input, score_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adnime);

        id_input = findViewById(R.id.animeID_text);
        name_input = findViewById(R.id.animeName_text);
        score_input = findViewById(R.id.score_text);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddAdnime.this);
                myDB.addAnime(Integer.valueOf(id_input.getText().toString().trim()),
                        name_input.getText().toString().trim(),
                        Integer.valueOf(score_input.getText().toString().trim()),
                        6,9
                );
            }
        });
    }
}