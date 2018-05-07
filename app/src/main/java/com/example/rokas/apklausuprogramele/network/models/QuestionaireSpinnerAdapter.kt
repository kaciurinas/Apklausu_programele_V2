package com.example.rokas.apklausuprogramele.network.models

import android.content.Context
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Spinner
import android.os.Bundle
import android.view.View
import com.example.rokas.apklausuprogramele.R
import android.view.ViewGroup
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.support.annotation.LayoutRes
import android.widget.ArrayAdapter


class QuestionaireSpinnerAdapterArrayAdapter(private val mContext: Context, @param:LayoutRes private val mResource: Int,
                         objects: List<Questionaire>) : ArrayAdapter<Questionaire>(mContext, mResource, 0, objects) {

    private val mInflater: LayoutInflater
    private val items: List<Questionaire>

    init {
        mInflater = LayoutInflater.from(mContext)
        items = objects
    }

    override fun getDropDownView(position: Int, convertView: View?,
                                 parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = mInflater.inflate(mResource, parent, false)

        val surveyId :TextView = view.findViewById(R.id.survey_id)
        val surveyName :TextView = view.findViewById(R.id.survey_name)

        val offerData = items[position]

        surveyId.setText(offerData.id)
        surveyName.setText(offerData.name)

        return view
    }
}