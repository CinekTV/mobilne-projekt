package pl.edu.pb.projektsystemymobilne;

import android.os.AsyncTask;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequestTask extends AsyncTask<String, Void, ApiResponse> {
    //CONFIG
    private static final int SEARCH_LIMIT = 5;
    private static final boolean SFW = true;
    
    private ApiRequestListener listener;

    public ApiRequestTask(ApiRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected ApiResponse doInBackground(String... params) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.jikan.moe/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface apiService = retrofit.create(ApiInterface.class);
            Call<ApiResponse> call = apiService.searchAnime(params[0], SFW, SEARCH_LIMIT);
            Response<ApiResponse> response = call.execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                // Obsługa błędów HTTP...
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ApiResponse result) {
        if (result != null) {
            listener.onApiRequestSuccess(result);
        } else {
            listener.onApiRequestFailure();
        }
    }

    public interface ApiRequestListener {
        void onApiRequestSuccess(ApiResponse result);
        void onApiRequestFailure();
    }
}