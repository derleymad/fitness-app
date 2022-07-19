package com.github.derleymad.fitnesstracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.derleymad.fitnesstracker.App
import com.github.derleymad.fitnesstracker.R
import com.github.derleymad.fitnesstracker.model.Calc
import java.text.SimpleDateFormat
import java.util.*

class ListCalcActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        imageView = findViewById(R.id.history_back)
        imageView.setOnClickListener {
            finish()
        }
        recyclerView = findViewById(R.id.rv_history_imc)
        var historyList = mutableListOf<Calc>()

        //recebendo dados da outra activity
        val type = intent?.extras?.getString("type") ?: throw IllegalStateException("Type not found")

        //adapter
        val adapter = HistoryAdapter(historyList)
        recyclerView.adapter = adapter

        //linear layout o recycler view vai ser reverse
        val linear = LinearLayoutManager(this@ListCalcActivity,LinearLayoutManager.VERTICAL,true)
        linear.stackFromEnd = true
        recyclerView.layoutManager = linear

        //buscando dados em thread
        Thread{
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            runOnUiThread {
                //atualizando adapter
                historyList.addAll(response)
                adapter.notifyDataSetChanged()
            }
        }.start()
    }

    private inner class HistoryAdapter(private val historyList: MutableList<Calc>) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.HistoryViewHolder{
            val view = layoutInflater.inflate(R.layout.history_imc_item,parent,false)
            return HistoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
            val atual = historyList[position]
            holder.bind(atual)
        }

        override fun getItemCount(): Int {
            return historyList.size
        }

        private inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view){
            fun bind(atual:Calc){
                //buscando layout no itemView
                val result = itemView.findViewById<TextView>(R.id.text_history)
                val type = itemView.findViewById<TextView>(R.id.tv_type)
                val date = itemView.findViewById<TextView>(R.id.tv_date)

                //padrao sdf
                //HH 0 23
                //hh 0 12
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt","BR"))

                //Atribuido dados da lista pro textviews
                date.text = sdf.format(atual.createdDate)
                result.text = getString(R.string.imc_response_alone,atual.res)
                type.text = getString(R.string.type_response,atual.type.uppercase())

            }


        }

    }
}