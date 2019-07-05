package luiz.zapchau.gym101.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Workout {
    public String id;
    public String date;
    public String exercise;
    public String sets;
    public String repetitions;
    public String weight;
    public String time;
    public String distance;

    private Workout(JSONObject object) {
        try {
            this.id          = object.getString("_id");
            this.date        = object.getString("workoutdate");
            this.exercise    = object.getString("workoutexercise");
            this.sets        = object.getString("workoutsets");
            this.repetitions = object.getString("workoutrepetitions");
            this.weight      = object.getString("workoutweight");
            this.time        = object.getString("workouttime");
            this.distance    = object.getString("workoutdistance");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Workout> fromJson(JSONArray jsonObjects) {

        ArrayList<Workout> workout = new ArrayList<>();

        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                workout.add(new Workout(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return workout;
    }
}
