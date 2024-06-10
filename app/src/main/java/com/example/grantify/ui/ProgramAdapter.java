package com.example.grantify.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.grantify.R;
import com.example.grantify.model.Program;

import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder>{

    private List<Program> programList;
    private OnItemClickListener listener;

    public ProgramAdapter(List<Program> programList, OnItemClickListener listener) {
        this.programList = programList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ProgramAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_program, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(programList.get(position).getTitle());
        holder.tvCategory.setText(programList.get(position).getCategory());
        String imageUrl = programList.get(position).getImage();
        holder.bind(programList.get(position), listener);

        if (programList.get(position).getCategory().equals("Scholarship")) {
            holder.tvCategory.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_item_background));
        } else if (programList.get(position).getCategory().equals("Volunteer")) {
            holder.tvCategory.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_item_volunteer));
            holder.tvCategory.setTextColor(Color.parseColor("#6865FB"));
        } else if (programList.get(position).getCategory().equals("Training")) {
            holder.tvCategory.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_item_training));
        }

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background) // Gambar yang ditampilkan sementara sedang diunduh
                .error(R.drawable.ic_launcher_background) // Gambar yang ditampilkan jika terjadi kesalahan
                .into(holder.img);
    }
    @Override
    public int getItemCount() {
        return programList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvCategory;
        ImageView img;
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            tvTitle = itemView.findViewById(R.id.textTitle);
            tvCategory = itemView.findViewById(R.id.textCategory);
            img = itemView.findViewById(R.id.imageProgram);
        }
        public void bind(final Program program, final OnItemClickListener listener) {
            tvTitle.setText(program.getTitle());
            tvCategory.setText(program.getCategory());
            Glide.with(itemView.getContext())
                    .load(program.getImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(program);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Program program);
    }
}
