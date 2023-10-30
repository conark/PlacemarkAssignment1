package org.wit.placemark.views.placemarklist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.placemark.R
import org.wit.placemark.adapters.PlacemarkAdapter
import org.wit.placemark.adapters.PlacemarkListener
import org.wit.placemark.databinding.ActivityPlacemarkListBinding
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel
//import org.wit.placemark.models.ProviderType


class PlacemarkListView : AppCompatActivity(), PlacemarkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityPlacemarkListBinding
    lateinit var presenter: PlacemarkListPresenter
    private var position: Int = 0
  //  private val providerTypes = arrayOf("GP", "Consultant", "Scan center") // プロバイダータイプのリスト

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = PlacemarkListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadPlacemarks()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    presenter.searchPlacemarks(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    presenter.searchPlacemarks(newText)
                } else {
                    presenter.searchPlacemarks("")
                }
                return true
            }
        })
//        // スピナーの宣言と初期化
//        val providerTypeSpinnerItem = menu.findItem(R.id.providerTypeSpinner)
//        if (providerTypeSpinnerItem != null) {
//            val spinner = providerTypeSpinnerItem.actionView as Spinner
//
//            // プロバイダータイプの選択肢を設定
//
//            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, providerTypes)
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner.adapter = adapter
//
//            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                    // 選択されたプロバイダータイプに基づいてフィルタリングを実行
//                    val selectedProviderType = when (position) {
//                        1 -> ProviderType.Consultant
//                        2 -> ProviderType.ScanCenter
//                        else -> ProviderType.GP
//                    }
//                    presenter.filterPlacemarksByProviderType(selectedProviderType)
//                }
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    // 何も選択されていない場合、デフォルトのプロバイダータイプを選択
//                    presenter.filterPlacemarksByProviderType(ProviderType.GP)
//                }
//            }
//        }


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddPlacemark() }
            R.id.item_map -> { presenter.doShowPlacemarksMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlacemarkClick(placemark: PlacemarkModel, position: Int) {
        this.position = position
        presenter.doEditPlacemark(placemark, this.position)
    }

    private fun loadPlacemarks() {
        binding.recyclerView.adapter = PlacemarkAdapter(presenter.getPlacemarks(), this)
        onRefresh()
    }


    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getPlacemarks().size)
    }

    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }

    fun showPlacemarks(placemarks: List<PlacemarkModel>) {
        binding.recyclerView.adapter = PlacemarkAdapter(placemarks, this)
        onRefresh()
    }




}