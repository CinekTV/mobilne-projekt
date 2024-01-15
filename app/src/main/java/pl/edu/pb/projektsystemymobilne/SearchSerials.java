package pl.edu.pb.projektsystemymobilne;
import androidx.appcompat.app.AlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SearchSerials {
    public static String API_URL = "https://api.jikan.moe/v4/anime?q=";
    public static String API_URL_END = "&sfw&limit=1";
    public String getApiResultSearch(String name) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResponse = null;

        try {
            // Tworzymy URL na podstawie podanego adresu
            URL url = new URL(API_URL + name + API_URL_END);
            System.out.println(API_URL + name + API_URL_END);
            // Otwieramy połączenie HTTP
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            // Sprawdzamy kod odpowiedzi HTTP
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Pobieramy InputStream z odpowiedzi serwera
                InputStream inputStream = urlConnection.getInputStream();

                // Tworzymy BufferedReader do czytania z InputStream
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                // Czytamy dane z InputStream do StringBuilder
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                // Konwertujemy StringBuilder do String (nasza odpowiedź w formacie JSON)
                jsonResponse = stringBuilder.toString();
            } else {
                // Obsługa błędów - np. logowanie, rzucanie wyjątku, zwracanie specjalnego komunikatu, etc.
                System.out.println("Błąd HTTP: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Zamykamy połączenie i BufferedReader, nawet jeśli wystąpił błąd
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Zwracamy odpowiedź w formacie JSON
        return jsonResponse;
    }
    public List<AnimeSearch> returnAnimeSearch(String jsonString){

        List<AnimeSearch> AnimeSearchList = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(jsonString);

            // Pobierz dane z obiektu "data"
            JSONArray dataArray = json.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject animeObject = dataArray.getJSONObject(i);

                // Pobierz tytuł
                int id = animeObject.getInt("mal_id");

                // Dodaj dane
                AnimeSearch as = new AnimeSearch(id,"","");

                AnimeSearchList.add(as);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return AnimeSearchList;
    }
}
