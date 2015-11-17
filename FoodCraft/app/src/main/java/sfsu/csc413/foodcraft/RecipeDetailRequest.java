package sfsu.csc413.foodcraft;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brook on 11/10/15.
 */
public class RecipeDetailRequest {

    Context mContext;
    ResultsListActivity mResultsList;


    RecipeDetailRequest(Context context, ResultsListActivity detailsActivity) {
        mContext = context;
        mResultsList = detailsActivity;
    }

    public void run(Recipe recipe) {

        String recipeID = recipe.id;

        String url = YummlyHandler.formatYummlyDetailURL(recipeID);

        Log.i("URL",url);

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            RecipeDetail detail = YummlyHandler.yummlyToDetail(response);

                            Log.i("ONR","1");
                            mResultsList.launchDetailActivity(detail);


                        } catch (Exception e) {
                            Log.i("RecipeDetail.run()", "Error.");
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("RECIPE_DETAIL", "Recipe Detail Request Unsuccessful");
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("ACCEPT", "application/json");
                return headers;
            }
        };

        VolleyRequest.getInstance(mContext).addToRequestQueue(req);

    }

}
