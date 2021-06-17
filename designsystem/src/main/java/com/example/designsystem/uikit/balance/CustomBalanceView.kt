package com.example.designsystem.uikit.balance

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.designsystem.R
import com.facebook.shimmer.ShimmerFrameLayout
import java.text.NumberFormat
import java.util.*

class CustomBalanceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val tvBalance by lazy{ findViewById<TextView>(R.id.tvBalance) }
    private val shimmer by lazy{ findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container) }

    init {
        LayoutInflater.from(context).inflate( R.layout.custom_balance_view, this, true)
    }

    fun showBalance(balance: Double) {
        tvBalance.text  = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(balance)
        tvBalance.visibility = View.VISIBLE
    }

    fun showLoading(isLoading: Boolean){
        shimmer.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}