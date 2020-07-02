package com.dmioto.hero_detail

import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmioto.desafio_android_daniel_mioto.heroes_list.list.HeroBundle
import com.dmioto.desafio_android_daniel_mioto.util.BaseActivity
import com.dmioto.desafio_android_daniel_mioto.util.ModuleHelper
import com.dmioto.hero_detail.list.ComicBundle
import com.dmioto.hero_detail.list.ComicListAdapter
import com.dmioto.request_api.ConnectionListener
import com.dmioto.request_api.models.Character
import com.dmioto.request_api.models.Comic
import com.dmioto.request_api.services.comics.ComicsService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_hero_detail.*
import kotlinx.android.synthetic.main.content_image_hero_detail.*

class HeroDetailActivity : BaseActivity() {

    private var mostPrice : Float = 0F
    private lateinit var comicL: Comic
    private lateinit var character: Character
    private lateinit var comicAdapter: ComicListAdapter
    private val comicList: MutableList<Comic> = mutableListOf()
    private var offsetDownloadIndex = 0
    private var isLoadingComics = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)
        backToHeroesList()
        getCharacter()
        setView()
        openMostExpensive()
    }

    private fun bde( a : Comic ) : Bundle {
        val bundle = Bundle()
        bundle.apply {
            bundle.putString(ComicBundle.TAG_COMIC_BUNDLE, Gson().toJson(a))
        }
        return bundle
    }

    private fun openMostExpensive() {
        most_expensive_comic.setOnClickListener {
            if (comic_progress.visibility == View.GONE) {
                val b = bde(comicL)
                openModule(ModuleHelper.COMIC_MOST_EXPENSIVE, b)
            }
        }
    }

    private fun addMostExpensiveComic() {
        comicList.let { listOfComic ->
            mostPrice = 0F
            listOfComic.forEach { comic ->
                comic.getPrice().forEach {
                    if (it >= mostPrice) {
                        mostPrice = it
                        comicL = comic
                    }
                }
            }
        }
    }

    private fun getCharacter(){
        getBundle().run {
            getString(HeroBundle.TAG_HERO_BUNDLE).let {
                character = Gson().fromJson(it, Character::class.java)
            }
        }
    }

    private fun setView(){
        setHeroPhoto(character.thumbnail.fullUrl)
        setHeroName(character.name)
        setHeroDescription(character.description)
        if(character.comics.available > 0){
            haveComic(true)
            setComics()
        }else {
            haveComic(false)
        }
    }

    private fun setHeroPhoto(photoUrl: String){
        Uri.parse(photoUrl).let {
            hero_photo.setImageURI(it, this)
        }
    }


    private fun setHeroName(heroName: String){
        hero_name.text = heroName
    }


    private fun setHeroDescription(heroDescription: String){
        if(heroDescription.isNotEmpty()) addReadMore(heroDescription, hero_description)
    }

    private fun backToHeroesList() {
        toolbar.setOnClickListener {
            finish()
        }
    }

    private fun setComics(){
        hero_no_comics.visibility = View.GONE
        getMoreComics()

        comicAdapter = ComicListAdapter(this, comicList)
        val gridLayoutManager = GridLayoutManager(this, 2)
        comics_list.apply {
            visibility = View.VISIBLE
            layoutManager = gridLayoutManager
            adapter = comicAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    checkIfNeedToGetMoreComics(gridLayoutManager.findLastVisibleItemPosition())
                }
            })
        }
    }


    private fun checkIfNeedToGetMoreComics(position: Int){
        if(!isLoadingComics && position > offsetDownloadIndex - (ComicsService.requestQtyLimit/2)){
            getMoreComics()
        }
    }

    private fun getMoreComics(){
        isLoading(true)
        ComicsService(this).getComics(character.id, offsetDownloadIndex,
            object : ConnectionListener<Comic> {
                override fun onSuccess(response: List<Comic>) {
                    comicList.addAll(response)
                    offsetDownloadIndex += ComicsService.requestQtyLimit // update offset value
                    comicAdapter.notifyDataSetChanged()
                    addMostExpensiveComic()
                    isLoading(false)
                }

                override fun onFail(error: String?) {
                    isLoading(false)
                }

                override fun noInternet() {
                    isLoading(false)
                }
            })
    }

    private fun isLoading(isLoading: Boolean){
        comic_progress.visibility = if(isLoading) View.VISIBLE else View.GONE
        isLoadingComics = isLoading
    }

    private fun haveComic(haveComic: Boolean) {
        most_expensive_comic.visibility = if (haveComic) View.VISIBLE else View.GONE
    }

    private fun addReadMore(text: String, textView: TextView) {
        val ss = SpannableString(text.substring(0, 120) + "... read more")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                addReadLess(text, textView)
            }
        }
        ss.setSpan(clickableSpan, ss.length - 10, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun addReadLess(text: String, textView: TextView) {
        val ss = SpannableString("$text read less")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                addReadMore(text, textView)
            }
        }
        ss.setSpan(clickableSpan, ss.length - 10, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}
