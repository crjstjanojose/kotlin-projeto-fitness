package co.tiagoaguiar.fitnesstracker

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.model.Calc
import java.text.SimpleDateFormat
import java.util.*

class ListCalcActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)


        val result = mutableListOf<Calc>()
        val adapter = ListCalcAdapter(result)
        rv = findViewById(R.id.rv_list)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter


        val type =
            intent?.extras?.getString("type") ?: throw IllegalStateException("Type not found")

        Thread {
            val app = application as App
            val dao = app.db.CalcDAO()
            val response = dao.getRegisterByType(type)

            runOnUiThread {
                result.addAll(response)
                adapter.notifyDataSetChanged()
            }

        }.start()

    }


    private inner class ListCalcAdapter(
        private val listCalc: List<Calc>,
    ) :
        RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>() {

        // Responsavel para informar a Recycle o layout xml do item que sera apresentado
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return ListCalcViewHolder(view)
        }

        // Disparado quando houver um scroll na tela e que seja ncessário trocar o conteudo atualizar
        override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
            val itemCurrent = listCalc[position]
            holder.bind(itemCurrent)
        }

        // Retorna a quantidade de itens a lista tem
        override fun getItemCount(): Int {
            return listCalc.size
        }


        // Classe da Celula que sera apresentada na RecycleView
        private inner class ListCalcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: Calc) {

                val tv = itemView as TextView

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
                val data = sdf.format(item.createDate)
                val res = item.res

                tv.text = getString(R.string.list_response, res, data)

            }
        }

    }
}