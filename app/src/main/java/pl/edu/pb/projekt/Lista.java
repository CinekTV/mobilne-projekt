package pl.edu.pb.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.widget.ListView;
import android.view.Menu;

public class Lista extends AppCompatActivity {
    ListView simpleList;
    String countryList[] = {"SAO", "AOT", "Mushoku", "Hyouka", "Hametsu Oukou", "Re:Zero"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_picked_anime, R.id.textView, countryList);
        simpleList.setAdapter(arrayAdapter);
    }
}