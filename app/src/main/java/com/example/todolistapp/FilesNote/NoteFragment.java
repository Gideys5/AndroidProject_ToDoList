package com.example.todolistapp.FilesNote;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {

    private RecyclerView noteRecyclerView;
    private NoteAdapter noteAdapter;
    private NoteRepository noteRepository;
    private List<Nota> notaList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        Button addNoteButton = view.findViewById(R.id.add_note_button);
        noteRecyclerView = view.findViewById(R.id.note_recycler_view);

        noteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        noteRepository = new NoteRepository(requireContext());

        loadNotes();

        noteAdapter = new NoteAdapter(notaList, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                deleteNoteDialog(position);
            }

            @Override
            public void onNoteClick(int position) {
                showNoteDialog(position);
            }
        });

        noteRecyclerView.setAdapter(noteAdapter);

        addNoteButton.setOnClickListener(v -> showNoteDialog(-1));

        return view;
    }
    private void loadNotes() {
        notaList = new ArrayList<>(noteRepository.getAllNotes());
    }

    private void deleteNoteDialog(int position) {
        if (position < 0 || position >= notaList.size()) {
            Toast.makeText(getContext(), "Errore: Indice non valido.", Toast.LENGTH_SHORT).show();
            return;
        }

        Nota notaToDelete = notaList.get(position);

        new AlertDialog.Builder(getContext())
                .setTitle("Conferma eliminazione")
                .setMessage("Sei sicuro di voler eliminare questa nota?")
                .setPositiveButton("Elimina", (dialog, which) -> {
                    boolean success = noteRepository.deleteNote(notaToDelete.getId());
                    if (success) {
                        notaList.remove(position);
                        noteAdapter.notifyItemRemoved(position);
                        Toast.makeText(getContext(), "Nota eliminata con successo.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Errore: Impossibile eliminare la nota.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Annulla", null)
                .show();
    }

    private void showNoteDialog(int position) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_note, null);
        EditText titleInput = dialogView.findViewById(R.id.edit_note_title);
        EditText contentInput = dialogView.findViewById(R.id.edit_note_content);
        Button saveButton = dialogView.findViewById(R.id.save_button);
        Button cancelButton = dialogView.findViewById(R.id.cancel_button);

        if (position >= 0) {
            Nota notaToEdit = notaList.get(position);
            titleInput.setText(notaToEdit.getTitle());
            contentInput.setText(notaToEdit.getContent());
        }

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .create();

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            String content = contentInput.getText().toString().trim();

            if (title.isEmpty()) {
                titleInput.setError("Il titolo non può essere vuoto");
                return;
            }

            if (content.isEmpty()) {
                contentInput.setError("Il contenuto non può essere vuoto");
                return;
            }

            addOrUpdateNote(title, content, position);
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void addOrUpdateNote(String title, String content, int position) {
        if (position >= 0) {
            Nota updatedNote = notaList.get(position);
            updatedNote.setTitle(title);
            updatedNote.setContent(content);
            noteRepository.updateNote(updatedNote);
            noteAdapter.notifyItemChanged(position);
            Toast.makeText(getContext(), "Nota aggiornata con successo.", Toast.LENGTH_SHORT).show();
        } else {
            Nota newNote = new Nota(title, content);
            int newId = noteRepository.insertNote(newNote);
            newNote.setId(newId);
            notaList.add(newNote);
            noteAdapter.notifyItemInserted(notaList.size() - 1);
            Toast.makeText(getContext(), "Nota aggiunta con successo.", Toast.LENGTH_SHORT).show();
        }
    }
}
