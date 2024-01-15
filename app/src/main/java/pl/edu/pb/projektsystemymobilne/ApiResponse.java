package pl.edu.pb.projektsystemymobilne;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {
    @SerializedName("pagination")
    private Pagination pagination;

    @SerializedName("data")
    private List<Anime> data;

    // Getters...
    public List<Anime> getData() {
        return data;
    }

    static class Pagination {

    }

    public static class Anime {
        @SerializedName("mal_id")
        private int malId;

        // Getter dla malId
        public int getMalId() {
            return malId;
        }
    }
}