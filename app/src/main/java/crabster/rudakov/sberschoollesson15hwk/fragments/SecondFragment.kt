package crabster.rudakov.sberschoollesson15hwk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import crabster.rudakov.sberschoollesson15hwk.Constants.COUNT_OF_FRAGMENTS_IN_BACKSTACK
import crabster.rudakov.sberschoollesson15hwk.R

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.second_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val countFragments = requireArguments().getInt(COUNT_OF_FRAGMENTS_IN_BACKSTACK)
        view.findViewById<TextView>(R.id.second_fragment_text_view).apply {
            text = resources.getString(R.string.count_of_fragments_in_backstack, countFragments)
        }
    }

}