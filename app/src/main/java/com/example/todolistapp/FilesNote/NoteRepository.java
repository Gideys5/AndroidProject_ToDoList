package com.example.todolistapp.FilesNote;

import android.content.Context;
import java.util.List;

public class NoteRepository {

    private final DBHelperNote dbHelperNote;

    public NoteRepository(Context context) {
        dbHelperNote = new DBHelperNote(context);
    }

    public int insertNote(Nota nota) {
        return dbHelperNote.insertNote(nota);
    }

    public List<Nota> getAllNotes() {
        return dbHelperNote.getAllNotes();
    }

    public boolean deleteNote(int noteId) {
        if (noteId <= 0) {
            throw new IllegalArgumentException("Errore: ID non valido per la cancellazione.");
        }
        return dbHelperNote.deleteNote(noteId);
    }

    public void updateNote(Nota nota) {
        dbHelperNote.updateNote(nota);
    }
}
