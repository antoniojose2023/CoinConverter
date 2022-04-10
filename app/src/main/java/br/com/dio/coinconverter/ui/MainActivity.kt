package br.com.dio.coinconverter.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import br.com.dio.coinconverter.R
import br.com.dio.coinconverter.core.extensions.createDialog
import br.com.dio.coinconverter.core.extensions.createProgressDialog
import br.com.dio.coinconverter.core.extensions.formatCurrency
import br.com.dio.coinconverter.core.extensions.text
import br.com.dio.coinconverter.data.enum.Coin
import br.com.dio.coinconverter.databinding.ActivityMainBinding
import br.com.dio.coinconverter.presentation.MainViewModel
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel by viewModel<MainViewModel>()
    private val dialogProgress by lazy { createProgressDialog() }

    private lateinit var list : Array<Coin>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

         bindAdapter()
         bindListener()
         bindObservers()

         setSupportActionBar(binding.toolbar)

    }

    fun bindAdapter(){
        list = Coin.values()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        binding.tileditFrom.setAdapter( adapter )
        binding.tileditFrom.setText(Coin.BRL.name, false)

        binding.tileditFrom.setOnItemClickListener(this)

    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        var coin = list[position]

        var itemRemover = list.find {
              it.name == coin.name
        }

        var _list = mutableListOf<Coin>()
        for(item in list){
             _list.add( item )
        }

        _list.remove( itemRemover )

        //------- item moeda para converter -------------------
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, _list)
        binding.tileditTo.setAdapter( adapter )

        //------- popular segundo campo com a nova lista----------
        binding.tileditTo.setText(_list[0].name, false)

    }


    fun bindListener(){
        binding.tileditValor.doAfterTextChanged {
            binding.buttonConverter.isEnabled = it != null && it.isNotEmpty()
        }

        binding.buttonConverter.setOnClickListener {

              binding.buttonSave.isEnabled = true

              var param = "${binding.tileditFrom.text}-${binding.tileditTo.text}"

              if(param.isNotEmpty()){
                  mainViewModel.getExchangeValue(param)
              }


        }

        binding.buttonSave.setOnClickListener {
            var valor = mainViewModel.state.value
            (valor as? MainViewModel.State.Sucess)?.let {
                   val res = it.response.copy(bid = it.response.bid * binding.tileditValor.text.toString().toDouble())
                   mainViewModel.save( res )
            }
        }


    }

    fun bindObservers(){

        mainViewModel.state.observe(this){
            when(it){
                MainViewModel.State.Loading -> dialogProgress.show()
                is MainViewModel.State.Error -> {
                    dialogProgress.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()

                }
                is MainViewModel.State.Sucess -> {
                    dialogProgress.dismiss()

                    var coin = Coin.values().find { it.name == binding.tileditTo.text.toString() }

                    var result = it.response.bid * binding.tileditValor.text.toString().toDouble()

                    if(coin != null) binding.tvResultado.text = result.formatCurrency(coin.locale)

                }
                MainViewModel.State.Saving -> {
                    dialogProgress.dismiss()
                    createDialog {
                        setMessage("Dados salvo com sucesso")
                    }.show()
                }
            }
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           R.id.menu_historico ->{
               startActivity(Intent(this, HistoryActivity::class.java))
           }
       }
       return super.onOptionsItemSelected(item)
    }
}