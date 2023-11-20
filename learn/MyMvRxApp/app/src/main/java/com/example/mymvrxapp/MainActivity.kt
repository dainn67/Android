package com.example.mymvrxapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.airbnb.mvrx.MvRxViewModel
import android.widget.TextView
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized

data class MyState(val listing: Async<Listing> = Uninitialized) : MvRxState
class MyViewModel(override val initialState: MyState) : MvRxViewModel<MyState>() {
    init {
        fetchListing()
    }

    private fun fetchListing() {
        ListingRequest.forId(1234).execute { copy(listing = it) }
    }
}

class MyFragment : MvRxFragment() {
    private val viewModel: MyViewModel by fragmentViewModel()

    override fun invalidate() = withState(viewModel) { state ->
        loadingView.isVisible = state.listing is Loading
        titleView.text = listing()?.title
    }
}