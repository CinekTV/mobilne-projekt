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
        // Dodaj tu, jeśli są potrzebne dodatkowe informacje dotyczące paginacji
    }

    public static class Anime {
        @SerializedName("mal_id")
        private int malId;

        @SerializedName("titles")
        private List<Title> titles;

        @SerializedName("images")
        private Images images;

        @SerializedName("synopsis")
        private String opis;

        @SerializedName("episodes")
        private int episodes;

        // Getter dla malId
        public int getMalId() {
            return malId;
        }

        // Getter dla tytułów
        public List<Title> getTitles() {
            return titles;
        }

        // Getter dla obrazów
        public Images getImages() {
            return images;
        }

        public String getOpis(){return opis;}

        public int getEpisodes(){return episodes;}
    }

    public static class Title {
        @SerializedName("type")
        private String type;

        @SerializedName("title")
        private String title;

        // Getter dla typu tytułu
        public String getType() {
            return type;
        }

        // Getter dla tytułu
        public String getTitle() {
            return title;
        }
    }

    public static class Images {
        @SerializedName("jpg")
        private Jpg jpg;

        // Getter dla obrazów w formacie JPG
        public Jpg getJpg() {
            return jpg;
        }
    }

    public static class Jpg {
        @SerializedName("image_url")
        private String imageUrl;

        // Getter dla URL obrazu w formacie JPG
        public String getImageUrl() {
            return imageUrl;
        }
    }
}