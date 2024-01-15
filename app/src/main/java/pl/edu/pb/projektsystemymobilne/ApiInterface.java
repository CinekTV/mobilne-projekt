package pl.edu.pb.projektsystemymobilne;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("v4/anime")
    Call<ApiResponse> searchAnime(@Query("q") String query, @Query("sfw") boolean sfw, @Query("limit") int limit);
}
