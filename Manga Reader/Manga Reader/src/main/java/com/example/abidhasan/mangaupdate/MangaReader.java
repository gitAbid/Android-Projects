package com.example.abidhasan.mangaupdate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MangaReader extends AppCompatActivity {

    private String Mangadirectory;
    TextView MangaImage;
    RecyclerView imageReaderView;
    RecyclerView.Adapter adapter;

    List<MangaImageReader> imageList;

    boolean loadingStatus=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_reader);

      imageReaderView=(RecyclerView)findViewById(R.id.readerImageRecyclerView);
        imageReaderView.setLayoutManager(new LinearLayoutManager(this));

        imageList=new ArrayList<>();

        Intent intent=getIntent();
        Mangadirectory=intent.getStringExtra("links");
        new downloadMangaImage().execute();

    }


    public class downloadMangaImage extends AsyncTask<Void,Void,Void>
    {

        //<editor-fold desc="Description">
        @Override
        protected Void doInBackground(Void... voids) {

            String url ="http://www1.gogomanga.to/noblesse/noblesse-445-v04.html";
            Document doc = null;
            try {
                doc = Jsoup.connect(Mangadirectory)
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                        .referrer("http://www.google.com")
                        .timeout(50000 * 5) //it's in milliseconds, so this means 5 seconds.
                        .get();
            } catch (NullPointerException e) {

                e.printStackTrace();
            } catch (HttpStatusException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            Elements chapterList = doc.select(".list-image");

            Elements c=chapterList.select("li");
            for (Element chap:c){

                String chapterImage=chap.toString();
                try {
                    chapterImage=chapterImage.substring(chapterImage.indexOf("https://2"),chapterImage.indexOf("alt")-2);
                } catch (Exception e) {
                    chapterImage=chapterImage.substring(chapterImage.indexOf("https://"),chapterImage.indexOf(".jpg")+4);
                }

                MangaImageReader reader=new MangaImageReader(chapterImage);
                imageList.add(reader);
              //  System.out.println(chapterImage);

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter=new MangaReaderAdapter(getApplicationContext(),imageList);
            imageReaderView.setAdapter(adapter);

        }
        //</editor-fold>
    }
}
