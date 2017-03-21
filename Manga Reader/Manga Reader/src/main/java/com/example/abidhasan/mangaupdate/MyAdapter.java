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

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Abid Hasan on 2/21/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(Context context, List<ListItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem listItem=listItems.get(position);
        holder.textViewManga.setText(listItem.getManga());
        holder.textViewMangaDesc.setText(listItem.getDesc());

        Picasso.with(context).load(listItem.getImageUrl()).into(holder.imageManga);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"You Clicked "+listItem.getManga(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,Main2Activity.class);
                intent.putExtra("links",listItem.getMangaViewLink());
                intent.putExtra("coverImage",listItem.getImageUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });




    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

       public TextView textViewManga;
       public TextView textViewMangaDesc;
        public ImageView imageManga;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewManga=(TextView)itemView.findViewById(R.id.textViewManga);
            textViewMangaDesc=(TextView)itemView.findViewById(R.id.textViewMangaDesc);
            imageManga=(ImageView)itemView.findViewById(R.id.imageManga);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.mainLinearLayout);
        }
    }
}
