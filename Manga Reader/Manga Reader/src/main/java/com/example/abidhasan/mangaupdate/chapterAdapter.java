package com.example.abidhasan.mangaupdate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Abid Hasan on 2/23/2017.
 */

public class chapterAdapter extends RecyclerView.Adapter<chapterAdapter.ViewHolder> {

    private List<chapterList> chapterLists;
    private Context context;

    public chapterAdapter(List<chapterList> chapterLists, Context context) {
        this.chapterLists = chapterLists;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View viewChapter= LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter,parent,false);

        return new chapterAdapter.ViewHolder(viewChapter);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final chapterList chapterNo=chapterLists.get(position);
        holder.textViewMangaChapter.setText(chapterNo.getChapterName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,chapterNo.getChapterName(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,MangaReader.class);
                intent.putExtra("links",chapterNo.getChapterLink());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView textViewMangaChapter;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewMangaChapter=(TextView)itemView.findViewById(R.id.chapterText);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.chapterLayout);
        }
    }
}
