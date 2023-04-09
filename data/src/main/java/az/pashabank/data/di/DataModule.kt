package az.pashabank.data.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import az.pashabank.data.errors.RemoteErrorMapper
import az.pashabank.data.local.card.CardDatabase
import az.pashabank.data.local.card.CardLocalDataSource
import az.pashabank.data.local.card.CardLocalDataSourceImpl
import az.pashabank.data.local.customer.CustomerDatabase
import az.pashabank.data.local.customer.CustomerLocalDataSource
import az.pashabank.data.local.customer.CustomerLocalDataSourceImpl
import az.pashabank.data.local.settings.AppPreferences
import az.pashabank.data.local.settings.AppSettings
import az.pashabank.data.local.settings.AppSettingsDataSourceImpl
import az.pashabank.data.local.transaction.TransactionDatabase
import az.pashabank.data.local.transaction.TransactionLocalDataSource
import az.pashabank.data.local.transaction.TransactionLocalDataSourceImpl
import az.pashabank.data.remote.CardApi
import az.pashabank.data.remote.CustomerApi
import az.pashabank.data.remote.TransactionApi
import az.pashabank.data.repository.*
import az.pashabank.domain.di.ERROR_MAPPER_NETWORK
import az.pashabank.domain.di.IO_CONTEXT
import az.pashabank.domain.exceptions.ErrorMapper
import az.pashabank.domain.repository.*
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
    factory<CardApi> { get<Retrofit>().create(CardApi::class.java) }
    factory<TransactionApi> { get<Retrofit>().create(TransactionApi::class.java) }


    //////////////////////////////////// REPOSITORY ////////////////////////////////////

    factory<ErrorConverterRepository> {
        ErrorConverterRepositoryImpl(
            jsonSerializer = get()
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

    factory<CardRepository> {
        CardRepositoryImpl(
            api = get(),
            customerLocalDataSource = get(),
            cardLocalDataSource = get()
        )
    }

    factory<AuthRepository> {
        AuthRepositoryImpl()
    }

    factory<TransactionRepository> {
        TransactionRepositoryImpl(
            api = get(),
            customerLocalDataSource = get(),
            transactionLocalDataSource = get()
        )
    }

    //////////////////////////////////// LOCAL ////////////////////////////////////
    factory<AppSettings> { AppPreferences(context = androidContext()) }

    single<CustomerLocalDataSource> {
        CustomerLocalDataSourceImpl(
            customerDao = get()
        )
    }

    single<CardLocalDataSource> {
        CardLocalDataSourceImpl(
            cardDao = get()
        )
    }

    single<TransactionLocalDataSource> {
        TransactionLocalDataSourceImpl(
            transactionDao = get()
        )
    }

    single { get<CustomerDatabase>().customerDao() }
    single { get<CardDatabase>().cardDao() }
    single { get<TransactionDatabase>().transactionDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            CustomerDatabase::class.java,
            "customer-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            CardDatabase::class.java,
            "card-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            TransactionDatabase::class.java,
            "transaction-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    //////////////////////////////////// ERROR MAPPER ////////////////////////////////////
    factory<ErrorMapper>(named(ERROR_MAPPER_NETWORK)) { RemoteErrorMapper() }
}