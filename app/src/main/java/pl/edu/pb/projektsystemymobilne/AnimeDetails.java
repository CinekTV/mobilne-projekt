package pl.edu.pb.projektsystemymobilne;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AnimeDetails extends AppCompatActivity {

    private ImageView animeImage;
    private TextView titleOfAnime;
    private TextView detailsOfAnime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_details);

        animeImage = findViewById(R.id.AnimeImage);
        titleOfAnime = findViewById(R.id.TitleOfAnime);
        detailsOfAnime = findViewById(R.id.DetailsOfAnime);

        // Pobierz dane z Intentu
        Intent intent = getIntent();
        if (intent != null) {
            int imageResId = intent.getIntExtra("imageResId", R.drawable.default_image); // Zdjęcie domyślne, jeśli nie ma wartości
            String title = intent.getStringExtra("title");
            String details = intent.getStringExtra("details");

            // Ustaw dane w widoku
            animeImage.setImageResource(imageResId);
            titleOfAnime.setText(title);
            detailsOfAnime.setText(details);
        }
    }
}
