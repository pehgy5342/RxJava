package com.example.rxjava

data class Book(val name: String, val pageList: List<Page>)

data class Page(val wordList: List<String>)
