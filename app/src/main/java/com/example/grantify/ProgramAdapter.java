package com.example.grantify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder>{

    private List<Program> programList;

    public ProgramAdapter(List<Program> programList) {
        this.programList = programList;
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
    }

}
