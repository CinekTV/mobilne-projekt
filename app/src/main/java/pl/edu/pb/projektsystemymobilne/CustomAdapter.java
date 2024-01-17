package pl.edu.pb.projektsystemymobilne;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList anime_main_id, anime_id, anime_name, anime_score, cordX, cordY, episodes_watched, episodes_max;

    CustomAdapter(Activity activity, Context context, ArrayList anime_main_id, ArrayList anime_id, ArrayList anime_name,
                  ArrayList anime_score, ArrayList cordX, ArrayList cordY, ArrayList episodes_watched, ArrayList episodes_max){
        this.activity = activity;
        this.context = context;
        this.anime_main_id = anime_main_id;
        this.anime_id = anime_id;
        this.anime_name = anime_name;
        this.anime_score = anime_score;
        this.cordX = cordX;
        this.cordY = cordY;
        this.episodes_max = episodes_max;
        this.episodes_watched = episodes_watched;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.anime_main_id_txt.setText(String.valueOf(anime_main_id.get(position)));
        holder.currep.setText(String.valueOf(episodes_watched.get(position)));
        holder.maxep.setText(String.valueOf(episodes_max.get(position)));
        //holder.anime_id_txt.setText(String.valueOf(anime_id.get(position)));
        holder.anime_name_txt.setText(String.valueOf(anime_name.get(position)));
        holder.anime_score_txt.setText(String.valueOf(anime_score.get(position)));
        holder.cordX_txt.setText(String.valueOf(cordX.get(position)));
        holder.cordY_txt.setText(String.valueOf(cordY.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("mainID", String.valueOf(anime_main_id.get(position)));
                //intent.putExtra("id", String.valueOf(anime_id.get(position)));
                intent.putExtra("episodesWat", String.valueOf(episodes_watched.get(position)));
                intent.putExtra("episodesMax", String.valueOf(episodes_max.get(position)));
                intent.putExtra("X", String.valueOf(cordX.get(position)));
                intent.putExtra("Y", String.valueOf(cordY.get(position)));
                intent.putExtra("name", String.valueOf(anime_name.get(position)));
                intent.putExtra("score", String.valueOf(anime_score.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return anime_main_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView anime_main_id_txt, anime_id_txt, anime_name_txt, anime_score_txt, cordX_txt, cordY_txt, maxep, currep;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            maxep = itemView.findViewById(R.id.max_episodes_row);
            currep = itemView.findViewById(R.id.watched_episodes_row);
            anime_main_id_txt = itemView.findViewById(R.id.anime_main_id_txt);
            //anime_id_txt = itemView.findViewById(R.id.anime_id_txt);
            anime_name_txt = itemView.findViewById(R.id.anime_name_txt);
            anime_score_txt = itemView.findViewById(R.id.anime_score_txt);
            cordX_txt = itemView.findViewById(R.id.cordX_txt);
            cordY_txt = itemView.findViewById(R.id.cordY_txt);

            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
