package com.example.abidhasan.mangaupdate;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Main2Activity extends AppCompatActivity {

    TextView Mname;
    TextView Mgenres;
    TextView Mstatus;
    TextView Mview;
    TextView Mdescription;
    ImageView McoverImage;

    String sname;
    String sgenres;
    String sstatus;
    String sview;
    String sdescription;

    Boolean threadStats=true;

    String Mdirectory;

    private RecyclerView ChapterRecyclerView;
    private RecyclerView.Adapter adapter;
    private List<chapterList> chapterLists;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Mname=(TextView)findViewById(R.id.mangaName) ;
        Mgenres=(TextView)findViewById(R.id.mangaGenres);
        Mstatus=(TextView)findViewById(R.id.mangaStatus);
        Mview=(TextView)findViewById(R.id.mangaView);
        Mdescription=(TextView)findViewById(R.id.mangaDesc);
        McoverImage=(ImageView)findViewById(R.id.mangaCover);

        ChapterRecyclerView=(RecyclerView)findViewById(R.id.chapterRecyclerView);
        ChapterRecyclerView.setHasFixedSize(true);
        ChapterRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Intent intent=getIntent();
        Mdirectory=intent.getStringExtra("links");
        String mangaCover=intent.getStringExtra("coverImage");

        Picasso.with(this).load(mangaCover).into(McoverImage);



        new fetchData().execute();
        chapterLists=new ArrayList<>();


    while (threadStats) {}

        Mname.setText(sname);
        Mgenres.setText(sgenres);
        Mstatus.setText(sstatus);
        Mview.setText(sview);
        Mdescription.setText(sdescription);

        adapter=new chapterAdapter(chapterLists,this);
        ChapterRecyclerView.setAdapter(adapter);


    }


    public class fetchData extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            String url ="";
            Document doc = null;
            try {
                doc = Jsoup.connect(Mdirectory)
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                        .referrer("http://www.google.com")
                        .timeout(50000 * 5) //it's in milliseconds, so this means 5 seconds.
                        .get();
            } catch (NullPointerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (HttpStatusException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //getting details of manga
            Elements al = doc.select(".description");
            Elements all = al.select(".genres");
            String genres = all.text();
            Elements name=al.select("h1");
            Elements description=al.select("p");


            String mangaName=name.toString();
            String mangaGenres=genres.toString();
            String mangaDesc=description.toString();

            String mangaView;
            String mangaDescription;
            String mangaStatus;
            //System.out.println(description);
            mangaName=mangaName.substring(4,mangaName.length()-5);
            mangaDescription=mangaDesc.substring(mangaDesc.lastIndexOf("Summary:"),mangaDesc.length()-5);
            mangaView=mangaDesc.substring(mangaDesc.lastIndexOf("Viewed:"),mangaDesc.lastIndexOf("Viewed:")+15);
            mangaStatus=mangaDesc.substring(mangaDesc.lastIndexOf("Status:"),mangaDesc.lastIndexOf("Status:")+17);

            if (mangaStatus.contains("<")){
                mangaStatus=mangaStatus.substring(0,mangaStatus.indexOf("<"));
            }

            if(mangaView.contains("<")) {
                mangaView=mangaView.substring(0,mangaView.indexOf("<"));
            }



              /*  mangaGenres=mangaGenres.substring(mangaDesc.indexOf("p"),mangaDesc.lastIndexOf("p"));
                mangaDesc=mangaDesc.substring(mangaDesc.indexOf("p"),mangaDesc.lastIndexOf("p"));*/


            sname=mangaName;
            sgenres=mangaGenres;
            sdescription=mangaDescription;
            sview=mangaView;
            sstatus=mangaStatus;

//getting all chapter
            Elements chapterList = doc.select(".list-chapter");
            Elements allChapter=chapterList.select("a[href");

            String domian="http://www1.gogomanga.to";

            for (Element chap:allChapter){
                String chapterName=chap.text();
                String chapter=chap.toString();
                chapter=chapter.substring(chapter.indexOf("\"")+1,chapter.indexOf("\">"));
                chapter=domian+chapter;
//                System.out.println(chapterName);
//                System.out.println(chapter);
                chapterList MangachapterList=new chapterList(chapterName,chapter);
                chapterLists.add(MangachapterList);

            }



            threadStats=false;
            System.out.println(mangaName);
            System.out.println(mangaGenres);
            System.out.println(mangaDescription);
            System.out.println(mangaView);
            System.out.println(mangaStatus);





            return null;
        }

    }



}
