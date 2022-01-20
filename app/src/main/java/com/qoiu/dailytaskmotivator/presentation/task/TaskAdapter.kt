package com.qoiu.dailytaskmotivator.presentation.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qoiu.dailytaskmotivator.R
import com.qoiu.dailytaskmotivator.ResourceProvider
import com.qoiu.dailytaskmotivator.Update
import com.qoiu.dailytaskmotivator.data.TaskDb
import com.qoiu.dailytaskmotivator.domain.TaskCalendar
import com.qoiu.dailytaskmotivator.presentation.DialogShow
import com.qoiu.dailytaskmotivator.presentation.ProgressModifierDialog

class TaskAdapter(
    private var list: List<TaskDb>,
    private val show: DialogShow,
    private val update: Update<TaskDb>,
    private val stringProvider: ResourceProvider.StringProvider,
    private val dialog: (TaskDb) -> Unit,
    private val doneAction: (TaskDb) -> Unit
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    fun update(data: List<TaskDb>) {
        list = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false),
            stringProvider,
            dialog
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(
        view: View,
        private val stringProvider: ResourceProvider.StringProvider,
        private val dialog: (TaskDb) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bind(task: TaskDb) {
            itemView.setOnClickListener {
                dialog(task)
            }
            itemView.findViewById<TextView>(R.id.task_title).text = task.title
            itemView.findViewById<TextView>(R.id.task_body).apply {
                if (task.body != "") {
                    text = task.body
                    visibility = View.VISIBLE
                } else {
                    visibility = View.GONE
                }
            }
            itemView.findViewById<TextView>(R.id.task_reward).apply {
                if (task.reward == 0) {
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                    val str = "${stringProvider.string(R.string.reward)}: ${task.reward}"
                    text = str
                }
            }
            itemView.findViewById<TextView>(R.id.task_expired).apply {
                if (task.expiredAt == 0L || task.dailyTask) {
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                    val str = "${stringProvider.string(R.string.expired)}: " +
                            TaskCalendar().formatDate(task.expiredAt)
                    text = str
                }
            }
            itemView.findViewById<TextView>(R.id.task_deadline).apply {
                if (task.deadline == 0L || task.dailyTask) {
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                    val str = "${stringProvider.string(R.string.deadline)}: " +
                            TaskCalendar().formatDate(task.deadline)
                    text = str
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
            itemView.findViewById<ProgressBar>(R.id.task_progress_bar).apply {
                if (task.progressMax > 0) {
                    max = task.progressMax
                    progress = task.currentProgress
                    visibility = View.VISIBLE
                } else {
                    visibility = View.GONE
                }
            }
            itemView.findViewById<TextView>(R.id.task_progress).apply {
                if (task.progressMax > 0) {
                    val progressText =
                        "${stringProvider.string(R.string.progress)}: ${task.currentProgress}/${task.progressMax}"
                    visibility = View.VISIBLE
                    text = progressText
                } else {
                    visibility = View.GONE
                }
            }
        }
    }
}