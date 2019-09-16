package com.ztf.realkot.ui.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ztf.realkot.R
import com.ztf.realkot.bean.User

import java.util.ArrayList
import java.util.Locale

/**
 * @author ztf
 * @date 2019/9/3
 */
internal class OneListAdapter(internal var context: Context, private var users: ArrayList<User>?) :
    RecyclerView.Adapter<OneListAdapter.Holder>() {
    internal var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        return Holder(inflater.inflate(R.layout.one_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvId.text = String.format(Locale.CHINESE, "%d 、", position + 1)
        holder.tvName.text = users!![position].userName
    }

    override fun getItemCount(): Int {
        return if (users == null) 0 else users!!.size
    }

    internal inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvId: TextView = itemView.findViewById(R.id.tv_id)

        var tvName: TextView = itemView.findViewById(R.id.tv_name)

    }
}
