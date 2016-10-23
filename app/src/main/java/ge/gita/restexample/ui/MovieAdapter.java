package ge.gita.restexample.ui;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ge.gita.restexample.dataprovider.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import ge.gita.restexample.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    List<Movie> movieList;
    Context context;
    ItemClickListener listener;
    public MovieAdapter(List<Movie> movieList,Context context,ItemClickListener listener){
        this.movieList=movieList;
        this.listener=listener;
        this.context=context;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view=LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500/"+movieList.get(position).getPosterPath()).into(holder.imageView);
        holder.textView.setText(movieList.get(position).getOriginalTitle());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.itemImageview);
            textView=(TextView)itemView.findViewById(R.id.itemTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        if(listener!=null)
        {
            listener.onItemClicked(movieList.get(getAdapterPosition()).getId());
        }
        }
    }

}