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

import java.text.SimpleDateFormat;
import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

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
        Program program = programList.get(position);
        holder.bind(program, listener);
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvCategory;
        TextView tvOpenDate;
        TextView tvCloseDate;
        TextView tvCriteria;
        TextView tvCriteria2;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.textTitle);
            tvCategory = itemView.findViewById(R.id.textCategory);
            img = itemView.findViewById(R.id.imageProgram);
            tvOpenDate = itemView.findViewById(R.id.textOpenDate);
            tvCloseDate = itemView.findViewById(R.id.textCloseDate);
            tvCriteria = itemView.findViewById(R.id.textCriteria);
            tvCriteria2 = itemView.findViewById(R.id.textCriteria2);
        }
        public void bind(final Program program, final OnItemClickListener listener) {
            tvTitle.setText(program.getTitle());
            tvCategory.setText(program.getCategory());
            Glide.with(itemView.getContext())
                    .load(program.getImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(img);

            if(program.getCriteria().length() < 13 ){
                tvCriteria.setText(program.getCriteria());
                tvCriteria2.setVisibility(itemView.GONE);

            } else if (program.getCategory() != "Scholarship" || program.getCriteria().length() >= 13){
                tvCriteria.setVisibility(itemView.GONE);
                tvCriteria2.setText(program.getCriteria());
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

            tvOpenDate.setText("Open: " + dateFormat.format(program.getOpenDate()));
            tvCloseDate.setText("Due To: " + dateFormat.format(program.getCloseDate()));

            if (program.getCategory().equals("Scholarship")) {
                tvCategory.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.category_item_background));
                tvCategory.setTextColor(Color.WHITE);
            } else if (program.getCategory().equals("Volunteer")) {
                tvCategory.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.category_item_volunteer));
                tvCategory.setTextColor(Color.parseColor("#6865FB"));
            } else if (program.getCategory().equals("Training")) {
                tvCategory.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.category_item_training));
                tvCategory.setTextColor(Color.WHITE);
            }

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
