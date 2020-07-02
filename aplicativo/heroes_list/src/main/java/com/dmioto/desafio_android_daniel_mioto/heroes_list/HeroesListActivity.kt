package com.dmioto.desafio_android_daniel_mioto.heroes_list

import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.dmioto.desafio_android_daniel_mioto.heroes_list.list.HeroBundle
import com.dmioto.desafio_android_daniel_mioto.heroes_list.list.ItemClickListener
import com.dmioto.desafio_android_daniel_mioto.heroes_list.list.ListAdapter
import com.dmioto.desafio_android_daniel_mioto.util.BaseActivity
import com.dmioto.desafio_android_daniel_mioto.util.ModuleHelper
import com.dmioto.request_api.ConnectionListener
import com.dmioto.request_api.models.Character
import com.dmioto.request_api.services.character.CharacterService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_heroes_list.*
import kotlinx.android.synthetic.main.heroes_list_toolbar.*


class HeroesListActivity : BaseActivity() {

    private lateinit var heroL: Character
    private val heroesList: MutableList<Character> = mutableListOf()
    private val adapter = ListAdapter(heroesList)
    private var offsetDownloadIndex = 0
    private var isLoadingCharacter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heroes_list)
        setToolbarPadding()
        setMenuItems()
        nextPage()
        backPage()
        createRecycler()
        showHowToUse()
        onClickImage()
        returnToFirstPage()
    }

    private fun returnToFirstPage(){
        hero_logo.setOnClickListener {
            offsetDownloadIndex = 0
            getCharacters()
        }
    }

    private fun bde(a : Character) : Bundle {
        val bundle = Bundle()
        bundle.apply {
            bundle.putString(HeroBundle.TAG_HERO_BUNDLE, Gson().toJson(a))
        }
        return bundle
    }


    private fun onClickImage() {
        adapter.setOnItemClickListener(object : ItemClickListener {
            override fun onItemClick(position: Int) {
                heroL = heroesList[position]
                openModule(ModuleHelper.HERO_DETAIL, bde(heroL))
            }
        })
    }

    private fun showHowToUse(){
        AlertDialog.Builder(this).create().apply {
            setTitle(R.string.greeting_title)
            setMessage(getString(R.string.greetings_content))
            setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.greetings_confirm))
            { dialog, which -> dialog.dismiss() }
            show()
        }
    }

    private fun nextPage() {
        getCharacters()
        next_page.setOnClickListener{
            offsetDownloadIndex += CharacterService.requestQtyLimit // update offset value
            getCharacters()
        }
    }

    private fun backPage() {
        back_before_page.setOnClickListener {
            if (offsetDownloadIndex > 0) {
                offsetDownloadIndex -= CharacterService.requestQtyLimit
                getCharacters()
            }
        }
    }

    private fun getCharacters(){
        isLoadingCharacter = true
        CharacterService(this).getAllCharacters(offsetDownloadIndex, object : ConnectionListener<Character>{
            override fun onSuccess(response: List<Character>) {
                heroesList.clear()
                response.forEach {
                    heroesList.add(it)
                }
                updateList()
                isLoadingCharacter = false
            }

            override fun onFail(error: String?) {
                isLoadingCharacter = false
            }

            override fun noInternet() {
                isLoadingCharacter = false
            }
        })
    }

    private fun createRecycler() {
        characters_recycler_view.let {
            it.layoutManager = GridLayoutManager(this, 2)
            it.adapter = adapter
        }
    }

    private fun updateList() {
        adapter.notifyDataSetChanged()
    }

    private fun setMenuItems() {
        val dropDownMenu = PopupMenu(this, open_menu)
        dropDownMenu.menuInflater.inflate(R.menu.main_menu, dropDownMenu.menu)
        dropDownMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.information -> {
                    openInformation()
                    true
                }
                else -> super.onContextItemSelected(item)
            }
        }
        open_menu.setOnClickListener {
            if(!this.isFinishing){
                dropDownMenu.show()
            }
        }
    }

    private fun openInformation(){
        //TODO open module information
        Toast.makeText(this, "TODO device information", Toast.LENGTH_SHORT).show()
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    private fun setToolbarPadding(){
        heroes_list_toolbar.setPadding(0, getStatusBarHeight(),0,0)
    }
}
