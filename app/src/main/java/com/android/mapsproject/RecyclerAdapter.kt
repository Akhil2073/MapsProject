package com.android.mapsproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewTitle: TextView
        init {
            // Define click listener for the ViewHolder's View.
            textViewTitle = view.findViewById<TextView>(R.id.textViewTitle)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item, viewGroup, false)

        val lp = view.getLayoutParams()
        view.setLayoutParams(lp)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

//        holder.textView.setText(SubjectValues[position]);
        viewHolder.textViewTitle.setText((dataSet.get(position)))
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount():Int {
        return dataSet.size
    }

}