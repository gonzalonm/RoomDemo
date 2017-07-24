package com.lalosoft.roomdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.lalosoft.roomdemo.database.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // run the sentence in a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Product> products = App.get().getDB().productDao().getAll();
                boolean force = App.get().isForceUpdate();
                if (force || products.isEmpty()) {
                    retrieveProducts();
                } else {
                    populateProducts(products);
                }
            }
        }).start();
    }

    private void retrieveProducts() {
        List<Product> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setName(getString(R.string.name_format, String.valueOf(i)));
            product.setImageUrl("http://lorempixel.com/500/500/technics/" + i);
            product.setPrice(i == 0 ? 50 : i * 100);
            list.add(product);
        }

        // insert product list into database
        App.get().getDB().productDao().insertAll(list);

        // disable flag for force update
        App.get().setForceUpdate(false);

        populateProducts(list);
    }

    private void populateProducts(final List<Product> products) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(new ProductAdapter(products));
            }
        });
    }

}
