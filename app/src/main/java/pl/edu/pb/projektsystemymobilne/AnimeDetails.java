package pl.edu.pb.projektsystemymobilne;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

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
            String imageUrl = intent.getStringExtra("imageUrl");
            String title = intent.getStringExtra("title");
            String details = intent.getStringExtra("details");

            // Ustaw dane w widoku za pomocą Glide
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.baseline_gpp_maybe_24) // Zdjęcie domyślne, jeśli nie ma wartości
                    .into(animeImage);

            titleOfAnime.setText(title);
            detailsOfAnime.setText(details);
        }
    }
}
