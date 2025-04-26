package com.pandawork.bookdiscovery.data

import com.pandawork.bookdiscovery.R
import com.pandawork.bookdiscovery.model.Book

object Books {
    val booksList = listOf<Book>(
        Book(R.drawable.book_one,"Individual Differences And Personality", "Michael C ASHTON"),
        Book(R.drawable.book_two,"Personality And Disease", "Christoffer Johansen"),
        Book(R.drawable.book_three,"Carrots Grow Underground", "Gail Saunders, PhD"),
        Book(R.drawable.book_four,"Together We Grow", "Susan Vaught & Kelly Murphy"),
        Book(R.drawable.book_five,"Grow With Words", "Amity University Press"),
        Book(R.drawable.book_six,"Trade And Grow Rich", "Indrazith Shantharaj")
    )
}