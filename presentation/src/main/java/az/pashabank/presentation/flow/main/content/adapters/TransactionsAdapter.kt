package az.pashabank.presentation.flow.main.content.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import az.pashabank.starter.presentation.databinding.ListItemTransactionBinding
import az.pashabank.domain.model.customer.Transaction
import az.pashabank.presentation.extensions.*
import az.pashabank.presentation.tools.ShimmerLoaderAdapter

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.ViewHolder>(),
    ShimmerLoaderAdapter {

    override var isLoading: Boolean = false
        set(value) {
            field = value
            if (value)
                notifyDataSetChanged()
        }


    private var data = arrayListOf<Transaction>()

    fun setData(list: List<Transaction>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }


    class ViewHolder(val binding: ListItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) = with(binding) {

            textTitle.text = transaction.title
            textCategory.text = transaction.category.name
            date.text = transaction.createdAt.getFormattedText()
            amount.text = transaction.formattedAmount()
        }

        fun showShimmer(state: Boolean) {
            if (state) {
                binding.textTitle.text = " "
                binding.textCategory.text = " "
                binding.date.text = " "
                binding.amount.text = " "
                binding.shimmerLayout.showShimmer(true)
            } else {
                binding.shimmerLayout.hideShimmer()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ListItemTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position in data.indices) {
            holder.showShimmer(false)
            holder.bind(data[position])
            if (position == data.size - 1) {
                holder.binding.divider.invisible()
            } else {
                holder.binding.divider.visible()
            }
        } else {
            holder.showShimmer(true)
            if (position == data.lastIndex) {
                holder.binding.divider.invisible()
            } else {
                holder.binding.divider.visible()
            }
        }
    }

    override fun getItemCount() = data.size
}