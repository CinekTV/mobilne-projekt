package pl.edu.pb.projektsystemymobilne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener;


public class MainActivity extends AppCompatActivity implements ApiRequestTask.ApiRequestListener {

    private AnimeAdapter animeAdapter;
    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;
    /**
     * @brief Akcja sprawdza czy został wprowadzony tekst do wyszukiwarki
     * @return Zwraca odpowiedź w postaci alertu który zawiera dane o tym co zostało wpisane
     * @author Artur Leszczak
     */
    private EditText searchEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.Search_field);

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Zatwierdzono tekst, wywołaj funkcję obsługującą i wyświetl alert
                handleTextSubmission();
                return true; // Zwróć true, aby zatrzymać obsługę zdarzenia klawisza Enter
            }
            return false;
        });
        configureNextButton();
    }

    private void configureNextButton(){
        //Zmienianie widoku
        Button nextbutton = (Button) findViewById(R.id.changeview);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserProfile.class));
            }
        });
    }

    private void handleTextSubmission() {
        String enteredText = searchEditText.getText().toString();
        searchEditText.getText().clear(); // Wyczyść tekst w polu EditText

        new ApiRequestTask(this).execute(enteredText);

    }

    @Override
    public void onApiRequestSuccess(ApiResponse result) {
        List<ApiResponse.Anime> animeList = result.getData();

        animeAdapter = new AnimeAdapter(this);
        animeAdapter.setAnimeList(animeList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(animeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        // Dodaj obsługę kliknięcia w RecyclerView
        recyclerView.addOnItemTouchListener(new SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());

                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);

                    if (position != RecyclerView.NO_POSITION) {
                        // Przekazanie danych do AnimeDetailsActivity po kliknięciu
                        ApiResponse.Anime clickedAnime = animeAdapter.getAnimeList().get(position);
                        Intent intent = new Intent(MainActivity.this, AnimeDetails.class);
                        intent.putExtra("imageUrl", clickedAnime.getImages().getJpg().getImageUrl());
                        intent.putExtra("title", clickedAnime.getTitles().get(0).getTitle());
                        intent.putExtra("details", clickedAnime.getOpis());
                        intent.putExtra("episodes", clickedAnime.getEpisodes());
                        startActivity(intent);

                        return true;
                    }
                }

                return false;
            }
        });


    }



    @Override
    public void onApiRequestFailure() {
        Alert alert = new Alert("Error", "Nie udało się wykonać zapytania");
        alert.Show(this);
    }

}