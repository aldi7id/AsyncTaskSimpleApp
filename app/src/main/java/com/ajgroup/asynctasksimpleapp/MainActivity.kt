package com.ajgroup.asynctasksimpleapp

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.loader.content.AsyncTaskLoader
import com.ajgroup.asynctasksimpleapp.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var arr: ArrayList<String> = ArrayList()
    private var adapter: ArrayAdapter<String>? = null
    private lateinit var binding: ActivityMainBinding
    lateinit var context : Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = "https://khoapham.vn/KhoaPhamTraining/json/tien/demo2.json"
        ReadJSON().execute(url)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arr)
        binding.listview.adapter = adapter

        context = this
    }
    inner class ReadJSON : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String {
            var content: StringBuffer = StringBuffer()
            val url: URL = URL(p0[0])
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            val inputStream: InputStreamReader = InputStreamReader(urlConnection.inputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStream)
            var line: String = ""
            try {
                do {
                    line = bufferedReader.readLine()
                    if (line != null) {
                        content.append(line)
                    }
                } while (line != null)

            } catch (e: Exception) {
                Log.d("AAA", e.toString())
            }
            return content.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val jsonObject: JSONObject = JSONObject(result)
            val jsonArray: JSONArray = jsonObject.getJSONArray("danhsach")
            for (i in 0..jsonArray.length() - 1) {
                var objecti: JSONObject = jsonArray.get(i) as JSONObject
                var name = objecti.getString("khoahoc")
                arr.add(name)
            }
            adapter?.notifyDataSetChanged()
        }

    }

}