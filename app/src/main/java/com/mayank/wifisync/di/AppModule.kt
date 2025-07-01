package com.mayank.wifisync.di

import android.content.Context
import androidx.room.Room
import com.mayank.wifisync.data.local.AppDatabase
import com.mayank.wifisync.data.remote.ApiService
import com.mayank.wifisync.data.repository.UserRepositoryImpl
import com.mayank.wifisync.domain.repository.UserRepository
import com.mayank.wifisync.domain.usecase.GetUsersUseCase
import com.mayank.wifisync.presentation.viewmodel.UserViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-API-Key", "reqres-free-v1")
                    .build()
                    chain.proceed(request)
                }
            .addInterceptor(loggingInterceptor)
            .build()
        Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "user-db").build() }
    single { get<AppDatabase>().userDao() }
    single<UserRepository> {
        UserRepositoryImpl(
            userDao = get(),
            api = get(),
            context = get<Context>()
        )
    }
    factory { GetUsersUseCase(repository = get()) }
    viewModel { UserViewModel(get()) }
}