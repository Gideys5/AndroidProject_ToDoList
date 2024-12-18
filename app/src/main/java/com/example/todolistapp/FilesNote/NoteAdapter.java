package com.example.todolistapp.FilesNote;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolistapp.R;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final List<Nota> notes;
    private final OnNoteClickListener onNoteClickListener;

    public interface OnNoteClickListener {
        void onDeleteClick(int position);
        void onNoteClick(int position);
    }

    public NoteAdapter(List<Nota> notes, OnNoteClickListener onNoteClickListener) {
        this.notes = notes;
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (position < 0 || position >= notes.size()) return;

        Nota note = notes.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteContent.setText(note.getContent());

        holder.deleteButton.setOnClickListener(v -> onNoteClickListener.onDeleteClick(position));

        holder.itemView.setOnClickListener(v -> onNoteClickListener.onNoteClick(position));
    }

    @Override
    public int getItemCount() { return notes.size(); }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle;
        TextView noteContent;
        ImageButton deleteButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteContent = itemView.findViewById(R.id.note_content);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
