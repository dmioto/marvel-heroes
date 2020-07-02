package com.dmioto.comic_most_expensive

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.dmioto.desafio_android_daniel_mioto.util.BaseActivity
import com.dmioto.hero_detail.list.ComicBundle
import com.dmioto.request_api.models.Comic
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_comic_detail.*
import kotlinx.android.synthetic.main.content_image_hero_comic.*

class ComicMostExpensiveActivity : BaseActivity() {

    private lateinit var comic: Comic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_most_expensive)
        backToHero()
        getComic()
        setView()
    }

    private fun getComic(){
        getBundle().run {
            getString(ComicBundle.TAG_COMIC_BUNDLE).let {
                comic = Gson().fromJson(it, Comic::class.java)
            }
        }
    }

    private fun setView(){
        setComicPhoto(comic.thumbnail.fullUrl)
        setComicName(comic.title)
        setComicDescription(comic.description)
        setComicPrice(getMostPrice())
    }

    private fun getMostPrice() : String{
        var a = 0F
        comic.getPrice().forEach {
            if (it >= a){
                a = it
            }
        }
        return a.toString()
    }

    private fun setComicPhoto(photoUrl: String){
        Uri.parse(photoUrl).let {
            comic_photo.setImageURI(it, this)
        }
    }


    private fun setComicName(comicName: String){
        comic_name.text = comicName
    }

    private fun setComicPrice(comicPrice : String){
        label_comics.text = getString(R.string.comic_fisic, comicPrice)
    }

    private fun setComicDescription(comicDescription: String){
        if (comicDescription != null) addReadMore(comicDescription, comic_description)
    }

    private fun backToHero() {
        toolbar.setOnClickListener {
            finish()
        }
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
