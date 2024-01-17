package pl.edu.pb.projektsystemymobilne;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements ApiRequestTask.ApiRequestListener {

    private AnimeAdapter animeAdapter;
    private RecyclerView recyclerView;

    private DrawerLayout drawerLayout;

    private boolean Navi;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private GestureDetector gestureDetector;
    /**
     * @brief Akcja sprawdza czy został wprowadzony tekst do wyszukiwarki
     * @return Zwraca odpowiedź w postaci alertu który zawiera dane o tym co zostało wpisane
     * @author Artur Leszczak
     */
    private EditText searchEditText;

    private ImageView hamburgerImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.Navi = false;

        searchEditText = findViewById(R.id.Search_field);
        hamburgerImage = findViewById(R.id.hamburgerIcon);

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Zatwierdzono tekst, wywołaj funkcję obsługującą i wyświetl alert
                handleTextSubmission();
                return true; // Zwróć true, aby zatrzymać obsługę zdarzenia klawisza Enter
            }
            return false;
        });
        configureNextButton();

        drawerLayout = findViewById(R.id.drawer_layout);
        lockDrawer();
    }

    public void onHamburgerClick(View view) {
        // Obsługa kliknięcia w hamburger
        if(this.Navi){
            drawerLayout.closeDrawer(GravityCompat.END);
            Drawable yourDrawable = VectorDrawableCompat.create(getResources(), R.drawable.hamburger, getTheme());
            if (yourDrawable != null) {
                hamburgerImage.setImageDrawable(yourDrawable);
            }
            lockDrawer();
            Navi = false;
        }else{
            drawerLayout.openDrawer(GravityCompat.END);
            Drawable yourDrawable = VectorDrawableCompat.create(getResources(), R.drawable.close, getTheme());
            if (yourDrawable != null) {
                hamburgerImage.setImageDrawable(yourDrawable);
            }
            unlockDrawer();
            Navi = true;
        }

    }

    // Metoda, która blokuje otwieranie Navigation Drawer
    private void lockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.setVisibility(View.GONE);
    }

    // Metoda, która odblokowuje otwieranie Navigation Drawer
    private void unlockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawerLayout.setVisibility(View.VISIBLE);
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