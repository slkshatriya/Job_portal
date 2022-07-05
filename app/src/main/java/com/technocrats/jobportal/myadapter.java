package com.technocrats.jobportal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<model, myadapter.myviewholder>
{
    private OnClickListener onClickListener;

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options, OnClickListener onClickListener) {
        super(options);
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull final model model)
    {
        holder.name.setText(model.getTitle());
        holder.email.setText(model.getEmail());
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
        holder.name.setOnClickListener( view  -> onClickListener.onClick(holder.img,model));

        holder.email.setOnClickListener( view  -> onClickListener.onClick(holder.img,model));
        holder.img.setOnClickListener( view  -> onClickListener.onClick(holder.img,model));


    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView name,email;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img = itemView.findViewById(R.id.img1);
            name = itemView.findViewById(R.id.nametext);

            email = itemView.findViewById(R.id.emailtext);
        }
    }
}
interface OnClickListener {
    void onClick(View transitionView,model model);
}