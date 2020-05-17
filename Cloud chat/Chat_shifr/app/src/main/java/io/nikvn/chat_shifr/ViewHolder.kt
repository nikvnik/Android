package layout

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.nikvn.chat_shifr.R

class ViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var message: TextView

    init {
        message = itemView.findViewById(R.id.message_item)
    }
}
