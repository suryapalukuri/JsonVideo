package com.example.admin.jsonvideo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.io.Serializable;
class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    Context context;
    List<Data> data;
    public DataAdapter(Context context, List<Data> data) {
        this.context=context;
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.id.setText(data.get(position).getId());
        holder.title.setText(data.get(position).getTitle());
        holder.description.setText(data.get(position).getDescription());
        String url=data.get(position).getThumb();
      new DownloadPage(holder.thumb).execute(url);
      holder.thumb.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent i=new Intent(context,Main2Activity.class);
              i.putExtra("video",data.get(position).getUrl());
              i.putExtra("des",data.get(position).getDescription());
              i.putExtra("tit",data.get(position).getTitle());
              i.putExtra("data", (Serializable)data);
              context.startActivity(i);
          }
      });

    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView title;
        TextView description;
        ImageView thumb;


        public MyViewHolder(View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            thumb=itemView.findViewById(R.id.thumb);
        }
    }

    private class DownloadPage extends AsyncTask<String,Void,Bitmap>{
        ImageView thumb;
        public DownloadPage(ImageView thumb) {
            this.thumb=thumb;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String imageURL=strings[0];
            Bitmap bitmap=null;
            try{
                InputStream inputStream=new URL(imageURL).openStream();
                bitmap= BitmapFactory.decodeStream(inputStream);
                 inputStream.close();
                  return bitmap;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Bitmap bitmap) {

            super.onPostExecute(bitmap);

            thumb.setImageBitmap(bitmap);
        }
    }
}
