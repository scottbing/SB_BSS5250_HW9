package com.example.sb_bssd5250_hw9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String LOGID = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callPreferences();
        makeData();
        NoteJSONSerializer noteJSONSerializer = new NoteJSONSerializer(this, "notes.json");
    }

    private void callPreferences() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String titlePref = prefs.getString( "UserTitle","Notebook");
        setTitle(titlePref);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString( "UserTitle", "Scott's Notebook");
        editor.commit();
    }


    private void makeData() {
        ArrayList<Note> notes = NotesData.getInstance(this).getNoteList();
        for(int i=0;  i<10;  i++) {
            Note note = new Note();
            note.setName("Note	#" + i);
            note.setDesc(String.valueOf(View.generateViewId()));
            notes.add(note);
        }
    }


    private void writeDataFile() {
        ArrayList<Note> notes = NotesData.getInstance(this).getNoteList();
        String filename = "notes.txt";
        File file = new File(getApplicationContext().getFilesDir(), filename);
        FileOutputStream fileOutputStream;
        try {    //try to open the  file for writing
            fileOutputStream = new FileOutputStream(file);
            //Turn the first note's name to bytes   in utf8 format and put in file
            fileOutputStream.write(notes.get(0).toString().getBytes("UTF-8"));
            fileOutputStream.flush();
            fileOutputStream.close();
            } catch (Exception e) {
                //catch any errors that occur from try and throw them back to whoever called this
                Log.d(LOGID, e.toString());
            }
        }

    private void readDataFile(String filename)  {

        File file = new File(getApplicationContext().getFilesDir(), filename);
        int length = (int)file.length();
        Log.d(LOGID,"File is bytes: " + String.valueOf(length));

        byte[] bytes = new byte[length]; //byte array to hold all read bytes

        FileInputStream fileInputStream;
        try { //try to open the file for reading
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
        } catch (Exception e) {//handle exception arising from above
            Log.d(LOGID, e.toString());
        }

        //Now try something different that also requires eception handling
        try {
            String s = new String(bytes,"UTF-8");
            Log.d(LOGID, s);
        } catch (Exception e) { //handle exception from string creation
            Log.d(LOGID, e.toString());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOGID,"onStart Called");
    }

    @Override
    protected void onStop() {
        super. onStop();
        Log.d(LOGID,  "onStop Called");
    }

    @Override
    protected void onResume() {
        super. onResume();
        Log.d(LOGID, "onResume Called");
    }

    @Override
    protected void onDestroy() {
        super. onDestroy();
        Log.d(LOGID,  "onDestroy Called");
    }

    @Override
    protected void onPause() {
        super. onPause();
        Log.d(LOGID,  "onPause Called");
    }

}
