package com.example.todolistapp.FilesNote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelperNote extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "note.db";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_NOTE_ID = "id";
    private static final String COLUMN_NOTE_TITLE = "title";
    private static final String COLUMN_NOTE_CONTENT = "content";

    private static final String CREATE_TABLE_NOTES =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOTE_TITLE + " TEXT NOT NULL, " +
                    COLUMN_NOTE_CONTENT + " TEXT NOT NULL" +
                    ")";

    public DBHelperNote(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {db.execSQL(CREATE_TABLE_NOTES);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public int insertNote(Nota nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, nota.getTitle());
        values.put(COLUMN_NOTE_CONTENT, nota.getContent());

        long id = db.insert(TABLE_NOTES, null, values);
        db.close();

        if (id == -1) {
            throw new RuntimeException("Errore: Impossibile aggiungere la nota al database.");
        }
        return (int) id;
    }

    public List<Nota> getAllNotes() {
        List<Nota> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NOTE_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE_CONTENT));
                notes.add(new Nota(id,title, content));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return notes;
    }

    public void updateNote(Nota nota){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", nota.getTitle());
        values.put("content", nota.getContent());

        db.update("notes", values, "id = ?", new String[]{String.valueOf(nota.getId())});
        db.close();
    }

    public boolean deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NOTES, COLUMN_NOTE_ID + " = ?", new String[]{String.valueOf(noteId)});
        db.close();
        return rowsDeleted > 0;
    }
}
