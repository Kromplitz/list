package com.example.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView:RecyclerView = findViewById(R.id.recyclerView)
        val api = ApiClient.client.create(ApiInterface::class.java)
        api.getHeroes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ( {
                         if (it.world.heroes.isNotEmpty()){
                             val items = it.world.heroes
                             val adapter = RecyclerViewAdapter(items as List<Hero>) {
                                 Toast.makeText(this, "clicked", Toast.LENGTH_LONG).show()
                             }
                             recyclerView.adapter = adapter
                         }
        }, {
            Toast.makeText(this,"Error ${it.message}", Toast.LENGTH_LONG).show()

        })


        recyclerView.layoutManager = LinearLayoutManager(this)


    }
}



