package io.nikvn.chat_shifr

import android.content.Context
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import layout.ViewHolder
import java.util.*

class AdapterDate(context: Context?, private var messages: ArrayList<String>) :
    RecyclerView.Adapter<ViewHolder>() {

        private val inflater: LayoutInflater
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            return ViewHolder(layoutInflater.inflate(R.layout.item_message, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val msg = messages[position]
            holder.message.text = Editable.Factory.getInstance().newEditable(msg)
            Log.d("tag","pp")
        }

        override fun getItemCount(): Int {
            return messages.size
        }

        init {
            inflater = LayoutInflater.from(context)
        }
}