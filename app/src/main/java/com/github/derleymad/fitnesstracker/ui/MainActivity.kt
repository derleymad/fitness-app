package com.github.derleymad.fitnesstracker.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.derleymad.fitnesstracker.MainItem
import com.github.derleymad.fitnesstracker.R
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var  recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_main)

        val dataClass = mutableListOf<MainItem>()

        dataClass.add(
            MainItem(
                id = 1,
                color = Color.YELLOW,
                link = "https://lh3.googleusercontent.com/y5G9NIkLEBWfo-AYA00eH7DPtJGcvKqhi36pjpik0Y1oRYiwXXLyUHvvoMidPNL7JRU0",
                textStringId = R.string.label_imc
            )
        )
        dataClass.add(
            MainItem(
                id = 2,
                color = Color.RED,
                link = "https://lh3.googleusercontent.com/y5G9NIkLEBWfo-AYA00eH7DPtJGcvKqhi36pjpik0Y1oRYiwXXLyUHvvoMidPNL7JRU0",
                textStringId = R.string.label_tmb
            )
        )

        val adapter = MainAdapter(dataClass) {
            when (it) {
                1 -> startActivity(Intent(this@MainActivity, ImcActivity::class.java))
                2 -> startActivity(Intent(this@MainActivity, TmbActivity::class.java))
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this@MainActivity,2)

    }

    private inner class MainAdapter(
        private var dataClass:MutableList<MainItem>,
        private var onItemClickListener : (Int) -> Unit
        ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item,parent,false)
            return MainViewHolder(view)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = dataClass[position]
            holder.bind(itemCurrent)
        }

        override fun getItemCount(): Int {
            return dataClass.size
        }

        private inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view){
            fun bind(itemCurrent: MainItem) {
                val img:ImageView = itemView.findViewById(R.id.item_img_icon)
                val name:TextView = itemView.findViewById(R.id.item_txt_name)
                val container:LinearLayout = itemView.findViewById(R.id.item_container_imc)

                Picasso.get()
                    .load(itemCurrent.link)
                    .resize(100,100)
                    .into(img)

                name.setText(itemCurrent.textStringId)
                container.setBackgroundColor(itemCurrent.color)

                container.setOnClickListener {
                   onItemClickListener(itemCurrent.id)
                }

            }
        }


    }


}


