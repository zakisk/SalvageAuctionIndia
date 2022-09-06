package com.example.salvageauctionindia.di

import com.example.salvageauctionindia.data.remote.salvage_api.network_services.NetworkService
import com.example.salvageauctionindia.data.remote.salvage_api.network_services.SparePartDataService
import com.example.salvageauctionindia.data.remote.salvage_api.network_services.VehicleDataService
import com.example.salvageauctionindia.data.remote.salvage_api.source_implementations.NetworkServiceProviderImpl
import com.example.salvageauctionindia.data.remote.salvage_api.source_implementations.SparePartDataSourceImpl
import com.example.salvageauctionindia.data.remote.salvage_api.source_implementations.VehicleDataSourceImpl
import com.example.salvageauctionindia.domain.interfaces.NetworkServiceProvider
import com.example.salvageauctionindia.domain.interfaces.SparePartDataSource
import com.example.salvageauctionindia.domain.interfaces.VehicleDataSource
import com.example.salvageauctionindia.util.Constants.BASE_URL
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkService() : NetworkService {
        val httpInterceptor = HttpLoggingInterceptor {}
        httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(10, TimeUnit.MINUTES) // write timeout
            .readTimeout(10, TimeUnit.MINUTES) // read timeout
            .addInterceptor(httpInterceptor)
            .build()

        JsonObject()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(NetworkService::class.java)
    }




    @Provides
    @Singleton
    fun provideVehicleDataService() : VehicleDataService {
        val httpInterceptor = HttpLoggingInterceptor {}
        httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(10, TimeUnit.MINUTES) // write timeout
            .readTimeout(10, TimeUnit.MINUTES) // read timeout
            .addInterceptor(httpInterceptor)
            .build()

        JsonObject()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(VehicleDataService::class.java)
    }




    @Provides
    @Singleton
    fun provideSparePartDataService() : SparePartDataService {
        val httpInterceptor = HttpLoggingInterceptor {}
        httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(10, TimeUnit.MINUTES) // write timeout
            .readTimeout(10, TimeUnit.MINUTES) // read timeout
            .addInterceptor(httpInterceptor)
            .build()

        JsonObject()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(SparePartDataService::class.java)
    }


    @Provides
    @Singleton
    fun provideNetworkServiceProvider(networkService: NetworkService) : NetworkServiceProvider {
        return NetworkServiceProviderImpl(networkService)
    }

    @Provides
    @Singleton
    fun providesVehicleDataSource(vehicleDataService: VehicleDataService) : VehicleDataSource {
        return VehicleDataSourceImpl(vehicleDataService)
    }


    @Provides
    @Singleton
    fun providesSparePartDataSource(sparePartDataService: SparePartDataService) : SparePartDataSource {
        return SparePartDataSourceImpl(sparePartDataService)
    }

}