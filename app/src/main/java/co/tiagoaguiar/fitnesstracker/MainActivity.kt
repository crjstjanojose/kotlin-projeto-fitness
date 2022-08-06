package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItens = mutableListOf<MainItem>()

        mainItens.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.ic_baseline_wb_sunny_24,
                textStringId = R.string.label_imc,
                color = Color.LTGRAY
            ),

            )
        mainItens.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.ic_baseline_android_24,
                textStringId = R.string.label_tmb,
                color = Color.LTGRAY
            )
        )

        val adapter = MainAdapter(mainItens) { id ->
            when (id) {
                1 -> {
                    val intent = Intent(this@MainActivity, ImcActivity::class.java)
                    startActivity(intent)
                }
                2 -> {
                    // Abrir outra
                    Log.i("Click Ativado", "Item Clicado $id")
                }
                3 -> {
                    // Abrir outra
                }
            }
        }

        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)


    }

    private inner class MainAdapter(
        private val mainItens: List<MainItem>,
        private val onItemClickListener: (Int) -> Unit
    ) :
        RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

        // Responsavel para informar a Recycle o layout xml do item que sera apresentado
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        // Disparado quando houver um scroll na tela e que seja ncessário trocar o conteudo atualizar
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItens[position]
            holder.bind(itemCurrent)
        }

        // Retorna a quantidade de itens a lista tem
        override fun getItemCount(): Int {
            return mainItens.size
        }


        // Classe da Celula que sera apresentada na RecycleView
        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: MainItem) {

                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)
                val container: LinearLayout =
                    itemView.findViewById(R.id.item_container_imc) as LinearLayout

                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }

            }
        }

    }
}