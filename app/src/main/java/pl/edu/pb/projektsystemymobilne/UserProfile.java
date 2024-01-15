package pl.edu.pb.projektsystemymobilne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> main_id, anime_id, anime_name, anime_score, cordX, cordY;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        recyclerView = findViewById(R.id.recycleView);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, AddAdnime.class);
                startActivity(intent);
            }
        });

        myDB = new MyDatabaseHelper(UserProfile.this);
        main_id = new ArrayList<>();
        anime_id = new ArrayList<>();
        anime_name = new ArrayList<>();
        anime_score = new ArrayList<>();
        cordX = new ArrayList<>();
        cordY = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(UserProfile.this, main_id, anime_id, anime_name, anime_score, cordX, cordY);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager( UserProfile.this));
    }

    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                main_id.add(cursor.getString(0));
                anime_name.add(cursor.getString(1));
                anime_id.add(cursor.getString(2));
                cordX.add(cursor.getString(5));
                cordY.add(cursor.getString(6));
                anime_score.add(cursor.getString(7));
            }
        }
    }
}