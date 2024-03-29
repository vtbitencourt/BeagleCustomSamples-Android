package com.example.serverdriven.components.balance.widgets

import br.com.zup.beagle.android.action.Action
import br.com.zup.beagle.android.context.Bind
import br.com.zup.beagle.android.context.ContextComponent
import br.com.zup.beagle.android.context.ContextData
import br.com.zup.beagle.android.context.valueOf
import br.com.zup.beagle.android.utils.observeBindChanges
import br.com.zup.beagle.android.widget.RootView
import br.com.zup.beagle.android.widget.WidgetView
import br.com.zup.beagle.annotation.RegisterWidget
import com.example.designsystem.uikit.balance.CustomBalanceView
import com.example.serverdriven.ViewCycleListener

@RegisterWidget
class BalanceWidget(
    override var viewCycleState: Bind<String>,
    override var context: ContextData? = null,
    private val onInit: List<Action>? = null,
    private val state: Bind<BalanceState>,
    private val balance: Bind<Double> = valueOf(0.0),
    private val errorAction: List<Action>? = null
) : WidgetView(), ContextComponent, ViewCycleListener {

    override fun buildView(rootView: RootView) = CustomBalanceView(rootView.getContext()).apply {
          observeBindChanges(
                rootView = rootView,
                this,
                viewCycleState
            ) { state ->

               when(state){
                   ViewCycleListener.ViewState.RESUME.state ->{
                       onInit?.forEach {
                           it.execute(rootView, this)
                       }
                   }

                   ViewCycleListener.ViewState.PAUSE.state ->{

                   }
               }
            }

        observeBindChanges(
            rootView = rootView,
            this,
            balance
        ) { value ->
            value?.let {
                setBalance(it)
            }
        }
    }.also { view ->
        observeBindChanges(rootView, view, state) { balanceState ->
            when (balanceState) {

                BalanceState.LOADING -> view.showLoading(true)

                BalanceState.ERROR -> view.showError {
                    errorAction?.forEach {
                        it.execute(rootView, view)
                    }
                }

                BalanceState.SUCCESS -> view.showBalance()
            }
        }
    }.also { view ->
        onInit?.forEach {
            it.execute(rootView, view)
        }
    }
}

enum class BalanceState {
    LOADING,
    ERROR,
    SUCCESS
}




