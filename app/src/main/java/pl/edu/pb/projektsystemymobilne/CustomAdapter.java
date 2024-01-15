package pl.edu.pb.projektsystemymobilne;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList anime_main_id, anime_id, anime_name, anime_score, cordX, cordY;

    CustomAdapter(Context context, ArrayList anime_main_id, ArrayList anime_id, ArrayList anime_name, ArrayList anime_score, ArrayList cordX, ArrayList cordY){
        this.context = context;
        this.anime_main_id = anime_main_id;
        this.anime_id = anime_id;
        this.anime_name = anime_name;
        this.anime_score = anime_score;
        this.cordX = cordX;
        this.cordY = cordY;
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
        holder.anime_id_txt.setText(String.valueOf(anime_id.get(position)));
        holder.anime_name_txt.setText(String.valueOf(anime_name.get(position)));
        holder.anime_score_txt.setText(String.valueOf(anime_score.get(position)));
        holder.cordX_txt.setText(String.valueOf(cordX.get(position)));
        holder.cordY_txt.setText(String.valueOf(cordY.get(position)));
    }

    @Override
    public int getItemCount() {
        return anime_main_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView anime_main_id_txt, anime_id_txt, anime_name_txt, anime_score_txt, cordX_txt, cordY_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            anime_main_id_txt = itemView.findViewById(R.id.anime_main_id_txt);
            anime_id_txt = itemView.findViewById(R.id.anime_id_txt);
            anime_name_txt = itemView.findViewById(R.id.anime_name_txt);
            anime_score_txt = itemView.findViewById(R.id.anime_score_txt);
            cordX_txt = itemView.findViewById(R.id.cordX_txt);
            cordY_txt = itemView.findViewById(R.id.cordY_txt);
        }
    }
}
