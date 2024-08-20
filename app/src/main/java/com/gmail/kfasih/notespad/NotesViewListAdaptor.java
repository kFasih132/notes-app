package com.gmail.kfasih.notespad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesViewListAdaptor extends RecyclerView.Adapter<NotesViewListAdaptor.NotesViewListHolder> {
    private List<NotesModel> notesList;
    private OnViewClickListener onViewClickListener;

    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    public NotesViewListAdaptor(List<NotesModel> notesList) {
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NotesViewListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_view, parent, false);
        return new NotesViewListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewListHolder holder, int position) {
        NotesModel notesModel = notesList.get(position);
        holder.tvViewTitle.setText(notesModel.getTitle());
        holder.tvViewDescription.setText(notesModel.getDescription());
        holder.tvViewDate.setText(notesModel.getDate());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    class NotesViewListHolder extends RecyclerView.ViewHolder{
        AppCompatTextView tvViewTitle , tvViewDescription , tvViewDate;

        public NotesViewListHolder(@NonNull View itemView) {
            super(itemView);
            tvViewTitle = itemView.findViewById(R.id.etViewTitle);
            tvViewDescription = itemView.findViewById(R.id.tvViewDescription);
            tvViewDate = itemView.findViewById(R.id.tvViewDate);
            itemView.setOnClickListener(view -> {
                if (onViewClickListener != null){
                    onViewClickListener.onItemViewClick(notesList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}

interface OnViewClickListener{
    void onItemViewClick(NotesModel notesModel , int position);
}
