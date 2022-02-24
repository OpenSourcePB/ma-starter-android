package digital.pashabank.data.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import digital.pashabank.data.errors.RemoteErrorMapper
import digital.pashabank.data.local.customer.CustomerDatabase
import digital.pashabank.data.local.customer.CustomerLocalDataSource
import digital.pashabank.data.local.customer.CustomerLocalDataSourceImpl
import digital.pashabank.data.local.settings.AppPreferences
import digital.pashabank.data.local.settings.AppSettings
import digital.pashabank.data.local.settings.AppSettingsDataSourceImpl
import digital.pashabank.data.remote.CustomerApi
import digital.pashabank.data.repository.CustomerRepositoryImpl
import digital.pashabank.data.repository.ErrorConverterRepositoryImpl
import digital.pashabank.domain.di.ERROR_MAPPER_NETWORK
import digital.pashabank.domain.di.IO_CONTEXT
import digital.pashabank.domain.exceptions.ErrorMapper
import digital.pashabank.domain.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import kotlin.coroutines.CoroutineContext

val dataModule = module {

    single<CoroutineContext>(named(IO_CONTEXT)) { Dispatchers.IO }

    //////////////////////////////////// NETWORK ////////////////////////////////////

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(
            if (getProperty("isDebug") == true.toString()) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )
    }

    single {
        val builder = OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())

        builder.build()
    }


    single {
        Json {
            isLenient = true
            encodeDefaults = true
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(getProperty("host"))
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    factory<CustomerApi> { get<Retrofit>().create(CustomerApi::class.java) }


    //////////////////////////////////// REPOSITORY ////////////////////////////////////

    factory<ErrorConverterRepository> {
        ErrorConverterRepositoryImpl(
            jsonSerializer = get<Json>()
        )
    }

    factory<AppSettingsDataSource> {
        AppSettingsDataSourceImpl(
            appSettings = get(),
        )
    }

    factory<CustomerRepository> {
        CustomerRepositoryImpl(
            api = get(),
            customerLocalDataSource = get()
        )
    }

    //////////////////////////////////// LOCAL ////////////////////////////////////
    factory<AppSettings> { AppPreferences(context = androidContext()) }

    single<CustomerLocalDataSource> {
        CustomerLocalDataSourceImpl(
            customerDao = get()
        )
    }

    single { get<CustomerDatabase>().customerDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            CustomerDatabase::class.java,
            "transaction-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    //////////////////////////////////// ERROR MAPPER ////////////////////////////////////
    factory<ErrorMapper>(named(ERROR_MAPPER_NETWORK)) { RemoteErrorMapper() }
}