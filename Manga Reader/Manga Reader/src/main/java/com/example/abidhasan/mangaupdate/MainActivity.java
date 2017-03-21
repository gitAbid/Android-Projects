package com.example.abidhasan.mangaupdate;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);


        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listItems=new ArrayList<>();



       new  FetchData().execute();

    }

    public class FetchData extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {

            int i=1;
            String url = "http://www1.gogomanga.to/popular/";
            Document doc = null;
           while (i < 11) {

                try {
                    doc = Jsoup.connect(url+i+".html")
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
                Elements c=doc.select(".list-manga-2");
                Elements all = c.select(".img");


                //System.out.println(all);
//              System.out.println(png);

                String domin="http://www1.gogomanga.to";
                for (Element link : all) {

                    //String name=link.toString().substring(link.toString().lastIndexOf("alt")+5,link.toString().length()-10);

                    Elements element=link.select("img[src$=.jpg]");

                    String coverImage=element.toString();
                    coverImage=coverImage.substring(coverImage.indexOf("\"")+1,coverImage.indexOf("alt")-2);
                    Elements Directory=link.select("a[href]");
                    String mangaName=element.attr("alt");
                    String directory=Directory.toString();
                    directory=directory.substring(directory.indexOf("\"")+1,directory.indexOf("\">"));
                    directory=domin+directory;
//                    System.out.println(coverImage);
//                    System.out.println(directory);
//                    System.out.println(mangaName);
                    ListItem listItem = new ListItem(mangaName,"Description",coverImage,directory);
                    listItems.add(listItem);
                }


               i++;
                   }




            return null;
                }


        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            Log.e("Finished","Fetching");
            adapter=new MyAdapter(getApplicationContext(),listItems);
            recyclerView.setAdapter(adapter);


        }
    }
}

