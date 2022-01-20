package com.qoiu.dailytaskmotivator.presentation.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.data.TaskDb
import com.qoiu.dailytaskmotivator.domain.TaskCalendar
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.ProgressModifierDialog

class TaskAdapter(
    private var list: List<TaskDb>,
    private val show: DialogShow,
    private val update: Update<TaskDb>,
    private val doneAction: (TaskDb) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    fun update(data: List<TaskDb>) {
        list = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(task: TaskDb) {
            itemView.findViewById<TextView>(R.id.task_title).text = task.title
            if (task.body != "") {
                itemView.findViewById<TextView>(R.id.task_body).text = task.body
            } else {
                itemView.findViewById<TextView>(R.id.task_body).visibility = View.GONE
            }
            if (task.reward == 0) {
                itemView.findViewById<LinearLayout>(R.id.task_reward_row).visibility = View.GONE
            } else {
                itemView.findViewById<TextView>(R.id.task_reward).text = task.reward.toString()
            }
            if(task.dailyTask){
                itemView.findViewById<LinearLayout>(R.id.task_deadline_row).visibility = View.GONE
                itemView.findViewById<LinearLayout>(R.id.task_expired_row).visibility = View.GONE
            }else{
                if (task.expiredAt > 0) {
                    itemView.findViewById<TextView>(R.id.task_expired).text =
                        TaskCalendar().formatDate(task.expiredAt)
                } else {
                    itemView.findViewById<LinearLayout>(R.id.task_expired_row).visibility = View.GONE
                }
                if (task.deadline > 0) {
                    itemView.findViewById<TextView>(R.id.task_deadline).text =
                        TaskCalendar().formatDate(task.deadline)
                } else {
                    itemView.findViewById<LinearLayout>(R.id.task_deadline_row).visibility = View.GONE
                }
            }
            itemView.findViewById<Button>(R.id.task_done).setOnClickListener {
                if (task.progressMax == 0) {
                    doneAction(task)
                } else {
                    show.show(ProgressModifierDialog(task) {
                        if (task.progressMax == task.currentProgress) {
                            doneAction(task)
                        } else {
                            notifyItemChanged(list.indexOf(task))
                            update.update(task)
                        }
                    }.apply {
                        isCancelable = false
                    })
                }
            }
            if (task.progressMax > 0) {
                val progressText = "Progress: ${task.currentProgress}/${task.progressMax}"
                itemView.findViewById<TextView>(R.id.task_progress).text =
                    progressText
                itemView.findViewById<ProgressBar>(R.id.task_progress_bar).apply {
                    max = task.progressMax
                    progress = task.currentProgress
                }
            } else {
                itemView.findViewById<TextView>(R.id.task_progress).visibility = View.GONE
                itemView.findViewById<ProgressBar>(R.id.task_progress_bar).visibility = View.GONE
            }
        }
    }
}