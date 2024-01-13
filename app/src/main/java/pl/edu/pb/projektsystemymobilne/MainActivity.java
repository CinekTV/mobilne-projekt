package pl.edu.pb.projektsystemymobilne;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


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
    }

    private void handleTextSubmission() {
        String enteredText = searchEditText.getText().toString();
        searchEditText.getText().clear(); // Wyczyść tekst w polu EditText

        // Przygotuj alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wprowadzony tekst");
        builder.setMessage("Wprowadzony tekst: " + enteredText);
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Tutaj możesz dodać kod, który zostanie wykonany po naciśnięciu przycisku OK
            dialog.dismiss(); // Zamknięcie okna dialogowego
        });

        // Wyświetl alert
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



}