package com.example.moviesapp.data.api

class UniversalResult<ITEM>(private var code: Int,
                            var message: String, private var item: ITEM?, private var items: List<ITEM>, private var currentPage: Int) {
    private var manualError = false

    fun getCode(): Int {
        return this.code
    }

    fun setCode(i: Int) {
        this.code = i
    }

    fun getItem(): ITEM? {
        return item
    }

    fun setItem(item: ITEM) {
        this.item = item
    }

    fun getItems(): List<ITEM> {
        return items
    }

    fun setItems(list: List<ITEM>) {
        items = list
    }

    fun getCurrentPage(): Int {
        return currentPage
    }

    fun setCurrentPage(page: Int) {
        currentPage = page
    }

    fun getManualError(): Boolean {
        return manualError
    }

    fun setManualError(z: Boolean) {
        manualError = z
    }

    fun isSuccess(): Boolean {
        val i = this.code
        return i in 200..299
    }

    fun isError(): Boolean {
        val i = this.code
        return i < 200 || i >= 300 || manualError
    }

    fun setManualError() {
        manualError = true
    }

    private fun hasItem(): Boolean {
        return item != null
    }

    fun hasNoItem(): Boolean {
        return !hasItem()
    }

    private fun hasItems(): Boolean {
        return items.isNotEmpty()
    }

    fun hasNoItems(): Boolean {
        return !hasItems()
    }

}