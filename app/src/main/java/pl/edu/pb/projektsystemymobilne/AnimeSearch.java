package pl.edu.pb.projektsystemymobilne;

public class AnimeSearch {
        private String title;
        private String image_url;

        private int id;
        public AnimeSearch(int id, String title, String image_url){
            this.image_url = image_url;
            this.title = title;
            this.id = id;
        }

        public String getTitle() {
            return title;
        }
        public String getImage_url(){
            return image_url;
        }
        public int getId(){
            return id;
        }
        public void setTitle(String title){
            this.title = title;
        }
        public void setImage_url(String image_url){
            this.image_url = image_url;
        }
        public void setId(int id){
            this.id = id;
        }
    }
