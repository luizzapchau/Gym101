package luiz.zapchau.gym101.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Exercise {
    public String id;
    public String machineId;
    public String days;
    public String machineName;
    public String machineNumber;
    public String machineColor;

    private Exercise(JSONObject object) {
        try {
            this.id            = object.getString("_id");
            this.machineId     = object.getString("exercisemachine");
            this.days          = object.getString("exercisedays");
            this.machineName   = object.getString("machinename");
            this.machineNumber = object.getString("machinenumber");
            this.machineColor  = object.getString("machinecolor");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Exercise> fromJson(JSONArray objects) {
        ArrayList<Exercise> exercise      = new ArrayList<>();
        JSONArray           sortedObjects = new JSONArray();

        for (int day = 0; day <= 3; day++) {
            for (int i = 0; i < objects.length(); i++) {
                try {
                    if (day == 0 && objects.getJSONObject(i).getString("exercisedays").contains("a")){
                        sortedObjects.put(new JSONObject(objects.getJSONObject(i).toString()));
                        sortedObjects.getJSONObject(sortedObjects.length() -1).put("exercisedays", "a");

                    } else if (day == 1 && objects.getJSONObject(i).getString("exercisedays").contains("b")){
                        sortedObjects.put(new JSONObject(objects.getJSONObject(i).toString()));
                        sortedObjects.getJSONObject(sortedObjects.length() -1).put("exercisedays", "b");

                    } else if (day == 2 && objects.getJSONObject(i).getString("exercisedays").contains("c")){
                        sortedObjects.put(new JSONObject(objects.getJSONObject(i).toString()));
                        sortedObjects.getJSONObject(sortedObjects.length() -1).put("exercisedays", "c");

                    } else if (day == 3 && objects.getJSONObject(i).getString("exercisedays").contains("d")){
                        sortedObjects.put(new JSONObject(objects.getJSONObject(i).toString()));
                        sortedObjects.getJSONObject(sortedObjects.length() -1).put("exercisedays", "d");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        for (int j = 0; j < sortedObjects.length(); j++) {
            try {
                exercise.add(new Exercise(sortedObjects.getJSONObject(j)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return exercise;
    }
}
