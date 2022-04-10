package br.com.dio.coinconverter.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.coinconverter.core.extensions.createDialog
import br.com.dio.coinconverter.core.extensions.createProgressDialog
import br.com.dio.coinconverter.databinding.ActivityHistoryBinding
import br.com.dio.coinconverter.presentation.HistoryViewModel
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : AppCompatActivity() {

    private val binding by lazy { ActivityHistoryBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<HistoryViewModel>()
    private val adapter by lazy { AdapterHistory() }
    private val dialogProgress by lazy { createProgressDialog() }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setTitle("Historico Conversões")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bindObserver()
        lifecycle.addObserver(viewModel)


        binding.rvItens.layoutManager = LinearLayoutManager(this)
        binding.rvItens.adapter = adapter
      //  binding.rvItens.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))

    }

    fun bindObserver(){
        viewModel._state.observe(this){
              when(it){
                  HistoryViewModel.State.Loading ->{
                        dialogProgress.show()
                  }
                  is HistoryViewModel.State.Error -> {
                         dialogProgress.dismiss()
                        createDialog {
                            setMessage("${it.erro}")
                        }.show()
                  }

                  is HistoryViewModel.State.Sucess -> {
                        dialogProgress.dismiss()
                        adapter.submitList( it.list )
                  }
              }
        }
    }

}