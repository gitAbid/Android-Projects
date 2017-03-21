package com.example.abidhasan.mangaupdate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Abid Hasan on 2/23/2017.
 */

public class MangaReaderAdapter extends RecyclerView.Adapter<MangaReaderAdapter.ViewHolder> {

    List<MangaImageReader> mangaReadersImage;
    Context context;
    public MangaReaderAdapter(Context context, List<MangaImageReader> mangaReadersImage) {
        this.context = context;
        this.mangaReadersImage = mangaReadersImage;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.manga_reader_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MangaImageReader image=mangaReadersImage.get(position);
        Picasso.with(context).load(image.getImageLink()).into(holder.readerImage);
    }

    @Override
    public int getItemCount() {
        return mangaReadersImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView readerImage;


        public ViewHolder(View imgView) {
            super(imgView);
            readerImage=(ImageView)imgView.findViewById(R.id.readerImageView);
        }
    }
}
