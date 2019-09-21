package com.example.sb_bssd5250_hw9;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class NoteJSONSerializer {

    private static String LOGID = "MainActivity";

    private static final String JSON_NAME = "name";
    private static final String JSON_DATE = "date";
    private static final String JSON_DESC = "desc";

    private Context mContext;
    private String mFilename;

    // NoteJSONSerializer Constructor
    public NoteJSONSerializer(Context c, String filename) {
        mContext = c;
        mFilename = filename;
    }

    public void loadNotes()
            throws JSONException, IOException {
        byte[] bytes = readDataFile();
        ArrayList<Note> notes = NotesData.getInstance(null).getNoteList();

        String jsonString = new String(bytes, "UTF-8");
        try {
            JSONArray jsonArray = (JSONArray) new JSONTokener(jsonString).nextValue();
            for (int i=0; i<jsonArray.length(); i++) {
                notes.add(new Note(jsonArray.getJSONObject(i)));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void saveNotes(ArrayList<Note> notes)
            throws JSONException, IOException {
        //Build an array in JSON
        JSONArray jsonArray = new JSONArray();
        for (Note n : notes) {
            //use  the toJSON function we wrote on each note
            jsonArray.put(n.toJSON());
        }
        //JSONArray of all notes built
        try {
            writeDataFile(jsonArray);
        } catch (Exception e) {
            //catch any errors that occur from try and throw them back to whoever called this
            throw e;
        }
    }

    private byte[] readDataFile()
            throws IOException, JSONException {

        File file = new File(mContext.getFilesDir(), mFilename);
        int length = (int)file.length();


        byte[] bytes = new byte[length]; //byte array to hold all read bytes

        FileInputStream fileInputStream;
        try { //try to open the file for reading
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();
        } catch (Exception e) {//handle exception arising from above
            throw e;
        }
        return bytes;
    }

    private void writeDataFile(JSONArray jsonArray)
            throws JSONException, IOException {
        ArrayList<Note> notes = NotesData.getInstance(mContext).getNoteList();
        File file = new File(mContext.getFilesDir(), mFilename);
        FileOutputStream fileOutputStream;

        try {    //try to open the  file for writing
            fileOutputStream = new FileOutputStream(file);
            //Turn the first note's name to bytes   in utf8 format and put in file
            fileOutputStream.write(jsonArray.toString().getBytes("UTF-8"));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            //catch any errors that occur from try and throw them back to whoever called this
            throw e;
        }
    }

}
