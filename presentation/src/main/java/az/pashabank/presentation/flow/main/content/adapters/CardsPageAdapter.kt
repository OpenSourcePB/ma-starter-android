package az.pashabank.presentation.flow.main.content.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import az.pashabank.starter.presentation.R
import az.pashabank.starter.presentation.databinding.ListCardItemBinding
import az.pashabank.domain.model.customer.Card
import az.pashabank.domain.model.customer.ECardType

class CardsPageAdapter(val select: (Card) -> Unit) :
    RecyclerView.Adapter<CardsPageAdapter.ViewHolder>() {

    private var items = arrayListOf<Card>()
    private var isLoading = false

    var selectedCard: Card? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setData(list: List<Card>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }


    fun setLoading(state: Boolean) {
        isLoading = state
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ListCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card, selected: Boolean) {
            val context = binding.root.context
            binding.run {
                tvBalance.text = card.balanceText()
                tvCardNo.text = card.lastPanDigits()
                when (card.type) {
                    ECardType.VISA -> {
                        cardContentLayout.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorVisa
                            )
                        )
                        imageCardType.setImageResource(R.drawable.ic_visa)
                    }
                    ECardType.MASTER -> {
                        cardContentLayout.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorMaster
                            )
                        )
                        imageCardType.setImageResource(R.drawable.ic_mastercard)
                    }
                    ECardType.UNION_PAY -> {
                        cardContentLayout.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorUnionPay
                            )
                        )
                        imageCardType.setImageResource(R.drawable.ic_unionpay)
                    }
                }
                if (selected) {
                    constraintCard.setBackgroundResource(R.drawable.bg_selection_border)
                } else {
                    constraintCard.background = null
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, item.id == selectedCard?.id)
        holder.binding.root.setOnClickListener {
            select(item)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getFirstCard(): Card? {
        return items.firstOrNull()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}