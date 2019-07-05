package luiz.zapchau.gym101.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import luiz.zapchau.gym101.Model.StringWithTag;

public class SQLiteHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "gym.db";

    private static final String TB_MACHINE     = "machine";
    private static final String MACHINE_ID     = "_id";
    private static final String MACHINE_NUMBER = "machinenumber";
    private static final String MACHINE_NAME   = "machinename";
    private static final String MACHINE_COLOR  = "machinecolor";

    private static final String TB_USER         = "user";
    private static final String USER_ID         = "_id";
    private static final String USER_NAME       = "username";
    private static final String USER_WEIGHT     = "userweight";
    private static final String USER_HEIGHT     = "userheight";
    private static final String USER_AGE        = "userage";
    private static final String USER_TIMES_WEEK = "usertimesweek";

    private static final String TB_EXERCISE      = "exercise";
    private static final String EXERCISE_ID      = "_id";
    private static final String EXERCISE_MACHINE = "exercisemachine";
    private static final String EXERCISE_DAYS    = "exercisedays";

    private static final String TB_WORKOUT          = "workout";
    private static final String WORKOUT_ID          = "_id";
    private static final String WORKOUT_DATE        = "workoutdate";
    private static final String WORKOUT_EXERCISE    = "workoutexercise";
    private static final String WORKOUT_SETS        = "workoutsets";
    private static final String WORKOUT_REPETITIONS = "workoutrepetitions";
    private static final String WORKOUT_WEIGHT      = "workoutweight";
    private static final String WORKOUT_TIME        = "workouttime";
    private static final String WORKOUT_DISTANCE    = "workoutdistance";

    private static final String TB_WEIGHT    = "weight";
    private static final String WEIGHT_ID    = "_id";
    private static final String WEIGHT_DATE  = "weightdate";
    private static final String WEIGHT_VALUE = "weightvalue";

    public SQLiteHelper(Context context) {

        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String createTableMachine   = "CREATE TABLE " + TB_MACHINE + "(" +
                                      MACHINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                      MACHINE_NUMBER + " INTEGER, " +
                                      MACHINE_NAME + " TEXT NOT NULL, " +
                                      MACHINE_COLOR + " TEXT NOT NULL); ";

        String createTableUser      = "CREATE TABLE " + TB_USER + "(" +
                                      USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                      USER_NAME + " TEXT NOT NULL, " +
                                      USER_AGE + " INTEGER, " +
                                      USER_HEIGHT + " REAL, " +
                                      USER_WEIGHT + " REAL NOT NULL, " +
                                      USER_TIMES_WEEK + " INTEGER);";

        String createTableExercise = "CREATE TABLE " + TB_EXERCISE + "(" +
                                      EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                      EXERCISE_MACHINE + " INTEGER NOT NULL, " +
                                      EXERCISE_DAYS + " TEXT, " +
                                      "FOREIGN KEY( " + EXERCISE_MACHINE + ") " +
                                      "REFERENCES " + TB_MACHINE + "(" + MACHINE_ID + "));";

        String createTableWorkout  = "CREATE TABLE " + TB_WORKOUT + "(" +
                                     WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                     WORKOUT_DATE + " TEXT NOT NULL, " +
                                     WORKOUT_EXERCISE + " INTEGER NOT NULL, " +
                                     WORKOUT_SETS + " INTEGER, " +
                                     WORKOUT_REPETITIONS + " INTEGER, " +
                                     WORKOUT_WEIGHT + " REAL, " +
                                     WORKOUT_TIME + " TEXT, " +
                                     WORKOUT_DISTANCE + " REAL, " +
                                     "FOREIGN KEY (" + WORKOUT_EXERCISE + ") " +
                                     "REFERENCES " + TB_EXERCISE + "(" + EXERCISE_ID + "));";

        String createTableWeight   = "CREATE TABLE " + TB_WEIGHT + "(" +
                                     WEIGHT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                     WEIGHT_DATE + " TEXT NOT NULL, " +
                                     WEIGHT_VALUE + " REAL NOT NULL);";

        db.execSQL(createTableMachine);
        db.execSQL(createTableUser);
        db.execSQL(createTableExercise);
        db.execSQL(createTableWorkout);
        db.execSQL(createTableWeight);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TB_MACHINE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TB_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_WORKOUT);
        db.execSQL("DROP TABLE IF EXISTS " + TB_WEIGHT);

        onCreate(db);
    }

    public boolean insertMachineData(int number, String name, String color) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MACHINE_NUMBER, number);
        contentValues.put(MACHINE_NAME, name);
        contentValues.put(MACHINE_COLOR, color);

        return db.insert(TB_MACHINE, null, contentValues) != -1;
    }

    public boolean insertUserData(String name, int age, float height, float weight, int timesWeek) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, name);
        contentValues.put(USER_AGE, age);
        contentValues.put(USER_HEIGHT, height);
        contentValues.put(USER_WEIGHT, weight);
        contentValues.put(USER_TIMES_WEEK, timesWeek);

        if (db.insert(TB_USER, null, contentValues) == -1)
            return false;
        else
            insertWeightData(getDate(), weight);

            return true;
    }

    public boolean insertExerciseData(int machine, String days) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EXERCISE_MACHINE, machine);
        contentValues.put(EXERCISE_DAYS, days);

        return db.insert(TB_EXERCISE, null, contentValues) != -1;
    }

    public boolean insertWorkoutData(String date, int exercise, int sets, int repetitions, float weight, String time, float distance) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(WORKOUT_DATE, date);
        contentValues.put(WORKOUT_EXERCISE, exercise);
        contentValues.put(WORKOUT_SETS, sets);
        contentValues.put(WORKOUT_REPETITIONS, repetitions);
        contentValues.put(WORKOUT_WEIGHT, weight);
        contentValues.put(WORKOUT_TIME, time);
        contentValues.put(WORKOUT_DISTANCE, distance);

        return db.insert(TB_WORKOUT, null, contentValues) != -1;
    }

    public boolean insertWeightData(String date, float value) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(WEIGHT_DATE, date);
        contentValues.put(WEIGHT_VALUE, value);

        return db.insert(TB_WEIGHT, null, contentValues) != -1;
    }

    public JSONObject selectMachine(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String select = "SELECT * " +
                        "FROM " + TB_MACHINE +
                        " WHERE " + MACHINE_ID + " = " + id;

        Cursor mCursor = db.rawQuery(select,null);
        mCursor.moveToFirst();
        return cursorToJSO(mCursor);
    }

    public JSONObject selectUser(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String select = "SELECT * " +
                        "FROM " + TB_USER +
                        " WHERE " + USER_ID + " = " + id;

        Cursor mCursor = db.rawQuery(select,null);
        mCursor.moveToFirst();
        return cursorToJSO(mCursor);
    }

    public JSONObject selectExercise(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String select = "SELECT * " +
                        "FROM " + TB_EXERCISE +
                        " WHERE " + EXERCISE_ID + " = " + id;

        Cursor mCursor = db.rawQuery(select,null);
        mCursor.moveToFirst();
        return cursorToJSO(mCursor);
    }

    public JSONObject selectWorkout(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        String select = "SELECT * " +
                        "FROM " + TB_WORKOUT +
                        " WHERE " + WORKOUT_ID + " = " + id;

        Cursor mCursor = db.rawQuery(select,null);
        mCursor.moveToFirst();
        return cursorToJSO(mCursor);
    }

    public List<StringWithTag> selectAllMachine() throws JSONException {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        List<StringWithTag> machineList = new ArrayList<>();

        String select = "SELECT * " +
                        "FROM " + TB_MACHINE +
                        " ORDER BY " + MACHINE_NUMBER;

        JSONArray jaMachines = cursorToJSA(sqLiteDatabase.rawQuery(select, null));

        for(int i = 0; i < jaMachines.length(); i++) {
            JSONObject mObject = jaMachines.getJSONObject(i);

            machineList.add(new StringWithTag(mObject.getInt(MACHINE_NUMBER) + " - " + mObject.getString(MACHINE_NAME),
                    mObject.getInt(MACHINE_ID)));
        }

        return machineList;
    }

    public JSONArray selectAllWorkout() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String select = "SELECT " + TB_WORKOUT + "." + WORKOUT_ID + ", " + WORKOUT_DATE + ", " + TB_MACHINE + "." + MACHINE_NUMBER +
                        ", " + TB_MACHINE + "." + MACHINE_NAME + ", " + TB_MACHINE + "." + MACHINE_COLOR +
                        ", " + WORKOUT_SETS + ", " + WORKOUT_REPETITIONS + ", " + WORKOUT_WEIGHT + ", " + WORKOUT_TIME +
                        ", " + WORKOUT_DISTANCE +
                        " FROM " + TB_WORKOUT +
                        " JOIN " + TB_EXERCISE +
                        " ON " + TB_WORKOUT + "." + WORKOUT_EXERCISE + " = " + TB_EXERCISE + "." + EXERCISE_ID +
                        " JOIN " + TB_MACHINE +
                        " ON " + TB_EXERCISE + "." + EXERCISE_MACHINE + " = " + TB_MACHINE + "." + MACHINE_ID +
                        " ORDER BY " + WORKOUT_DATE + " DESC";

        return cursorToJSA(sqLiteDatabase.rawQuery(select, null));
    }

    public JSONArray selectAllWeight() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String select = "SELECT * " +
                        " FROM " + TB_WEIGHT +
                        " ORDER BY " + WEIGHT_DATE;

        return cursorToJSA(sqLiteDatabase.rawQuery(select, null));
    }

    public boolean updateUser(int id, String name, int age, float height, float weight, int timesWeek){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, name);
        contentValues.put(USER_AGE, age);
        contentValues.put(USER_HEIGHT, height);
        contentValues.put(USER_WEIGHT, weight);
        contentValues.put(USER_TIMES_WEEK, timesWeek);

        if (db.update(TB_USER, contentValues,USER_ID + " = ? ", new String[]{Integer.toString(id)}) == -1)
            return false;
        else
            insertWeightData(getDate(), weight);

            return true;
    }

    public boolean updateMachine(int id, int number, String name, String color){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MACHINE_NUMBER, number);
        contentValues.put(MACHINE_NAME, name);
        contentValues.put(MACHINE_COLOR, color);

        return db.update(TB_MACHINE, contentValues, MACHINE_ID + " = ? ", new String[]{Integer.toString(id)}) != -1;
    }

    public boolean updateExercise(int id, int machine, String days){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(EXERCISE_MACHINE, machine);
        contentValues.put(EXERCISE_DAYS, days);

        if (db.update(TB_EXERCISE, contentValues,EXERCISE_ID + " = ? ", new String[]{Integer.toString(id)}) == -1)
            return false;
        else
            return true;
    }

    public boolean updateWorkout(int id, String date, int exercise, int sets, int repetitions, float weight, String time, float distance){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(WORKOUT_DATE, date);
        contentValues.put(WORKOUT_EXERCISE, exercise);
        contentValues.put(WORKOUT_SETS, sets);
        contentValues.put(WORKOUT_REPETITIONS, repetitions);
        contentValues.put(WORKOUT_WEIGHT, weight);
        contentValues.put(WORKOUT_TIME, time);
        contentValues.put(WORKOUT_DISTANCE, distance);

        return db.update(TB_WORKOUT, contentValues, WORKOUT_ID + " = ? ", new String[]{Integer.toString(id)}) != -1;
    }

    public void wipeDB() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TB_MACHINE, null, null);
        db.delete(TB_USER, null, null);
        db.delete(TB_EXERCISE, null, null);
        db.delete(TB_WORKOUT, null, null);
        db.delete(TB_WEIGHT, null, null);
    }

    public void deleteMachineEntry(int id){
        this.getWritableDatabase().delete(TB_WEIGHT, MACHINE_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public void deleteExerciseEntry(int id){
        this.getWritableDatabase().delete(TB_EXERCISE, EXERCISE_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public void deleteWorkoutEntry(int id){
        this.getWritableDatabase().delete(TB_WORKOUT, WORKOUT_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public void deleteWeightEntry(int id){
        this.getWritableDatabase().delete(TB_WEIGHT, WEIGHT_ID + " = ? ", new String[]{Integer.toString(id)});
    }

    public void deleteAllWeightData() {
        this.getWritableDatabase().delete(TB_WEIGHT, WEIGHT_ID + " != ? ", new String[]{Integer.toString(0)});
    }

    private String getDate(){
        return new SimpleDateFormat("dd-MMM-yyyy").format(Calendar.getInstance().getTime());
    }

    private JSONObject cursorToJSO(Cursor mCursor) {
        int columNumbers = mCursor.getColumnCount();
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < columNumbers; i++){
            if (mCursor.getColumnName(i) != null){
                try{
                    if (mCursor.getString(i) != null){
                        jsonObject.put(mCursor.getColumnName(i), mCursor.getString(i));
                    }else {
                        jsonObject.put(mCursor.getColumnName(i), "");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return jsonObject;
    }

    private JSONArray cursorToJSA(Cursor mCursor){
        JSONArray jsonArray = new JSONArray();

        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()){
            jsonArray.put(cursorToJSO(mCursor));
            mCursor.moveToNext();
        }
        mCursor.close();

        return jsonArray;
    }

    public void temp(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("drop table train");
    }
}
