package pl.edu.pb.projektsystemymobilne;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ApiRequestTask.ApiRequestListener {


    /**
     * @brief Akcja sprawdza czy został wprowadzony tekst do wyszukiwarki
     * @return Zwraca odpowiedź w postaci alertu który zawiera dane o tym co zostało wpisane
     * @author Artur Leszczak
     */
    private EditText searchEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.Search_field);

        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Zatwierdzono tekst, wywołaj funkcję obsługującą i wyświetl alert
                handleTextSubmission();
                return true; // Zwróć true, aby zatrzymać obsługę zdarzenia klawisza Enter
            }
            return false;
        });
        configureNextButton();
    }

    private void configureNextButton(){
        //Zmienianie widoku
        Button nextbutton = (Button) findViewById(R.id.changeview);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Localization.class));
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
        // Obsługa sukcesu
        // Możesz korzystać z danych z result.getData()
        String informacja = " ";
        List<ApiResponse.Anime> animeList = result.getData();
        for (ApiResponse.Anime anime : animeList) {
            int malId = anime.getMalId();
            List<ApiResponse.Title> titles = anime.getTitles();
            String title = "";
            for(ApiResponse.Title tit : titles){
                if(tit.getType() == "default"){
                    title = tit.getTitle();
                    break;
                }else{
                    title = tit.getTitle();
                }
            }

            informacja += title+" , ";
        }
        Alert alert = new Alert("Znaleziono", "Znaleziono seriale : "+informacja);
        alert.Show(this);

    }

    @Override
    public void onApiRequestFailure() {
        Alert alert = new Alert("Error", "Nie udało się wykonać zapytania");
        alert.Show(this);
    }

}