package com.example.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView:RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        //val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.getData()
        viewModel.uiState.observe(this){
            when(it){
                is MyViewModel.UIState.Empty -> Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show()
                is MyViewModel.UIState.Result -> {val adapter = RecyclerViewAdapter(it.heroes)
                    recyclerView.adapter = adapter }
                is MyViewModel.UIState.Processing ->  Toast.makeText(this, "Processing", Toast.LENGTH_LONG).show()
                is MyViewModel.UIState.Error -> Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }



        }

        //val recyclerView:RecyclerView = findViewById(R.id.recyclerView)
        //recyclerView.layoutManager = LinearLayoutManager(this)
        //val api = ApiClient.client.create(ApiInterface::class.java)
        //api.getHeroes()
         //   .subscribeOn(Schedulers.io())
          //  .observeOn(AndroidSchedulers.mainThread())
          //  .subscribe ( {
          //               if ( it.isNotEmpty()){
           //                  val adapter = RecyclerViewAdapter(it as List<Hero>) {
                                 //Toast.makeText(this, "clicked", Toast.LENGTH_LONG).show() }
            //                 recyclerView.adapter = adapter
                         }
        //}, {
    //        Toast.makeText(this,"Error ${it.message}", Toast.LENGTH_LONG).show()})
//}
}



