package pl.edu.pb.projektsystemymobilne;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.ViewHolder> {

    private List<ApiResponse.Anime> animeList;
    private Context context;

    public AnimeAdapter(Context context) {
        this.context = context;
    }

    public void setAnimeList(List<ApiResponse.Anime> animeList) {
        this.animeList = animeList;
    }

    public List<ApiResponse.Anime> getAnimeList(){
        return this.animeList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anime, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApiResponse.Anime anime = animeList.get(position);

        // Ustawienie tytułu
        holder.titleTextView.setText(anime.getTitles().get(0).getTitle());

        // Ładowanie obrazu z internetu za pomocą Glide
        if (anime.getImages() != null) {
            String imageUrl = anime.getImages().getJpg().getImageUrl();
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return animeList != null ? animeList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleTextView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            titleTextView = view.findViewById(R.id.titleTextView);
        }
    }
}

