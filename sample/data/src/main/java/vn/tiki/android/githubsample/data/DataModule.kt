package vn.tiki.android.githubsample.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.tiki.android.di.Module
import vn.tiki.android.githubsample.data.api.GithubService

class DataModule : Module() {

  init {
    provide<GithubService> {
      Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(GithubService::class.java)
    }
  }
}
