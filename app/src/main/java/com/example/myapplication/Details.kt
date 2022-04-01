package com.example.myapplication

import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Array

data class Details(val results: List<Results>,val query: Query?, val bestMatch: String)

data class Query(val project: String?,

                 val images: List<String>,

                 val organs: List<String>?)
