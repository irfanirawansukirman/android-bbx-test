package com.irfanirawansukirman.featuredashboard.data

import android.content.Context
import com.irfanirawansukirman.featuredashboard.data.model.PodsPlace

object DataFactory {

    fun getPodsPlaces() = arrayListOf<PodsPlace>().apply {
        add(PodsPlace("Pods A"))
        add(PodsPlace("Pods B"))
        add(PodsPlace("Pods C"))
        add(PodsPlace("Pods D"))
        add(PodsPlace("Pods E"))
        add(PodsPlace("Pods F"))
        add(PodsPlace("Pods G"))
        add(PodsPlace("Pods H"))
    }

    fun getEmptyPods(): String {
        return "{\n" +
                "  \"status\": 400,\n" +
                "  \"code\": 0,\n" +
                "  \"message\": \"success\",\n" +
                "  \"data\": null,\n" +
                "  \"params\": []\n" +
                "}"
    }

    fun getJsonDataFromAsset(context: Context): String? {
        return context.assets.open("pod_list.json").bufferedReader().use { it.readText() }
    }
}