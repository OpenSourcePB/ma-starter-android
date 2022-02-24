package digital.pashabank.starter.appinitializers.di

import digital.pashabank.starter.appinitializers.AppInitializers
import digital.pashabank.starter.appinitializers.TimberInitializer
import org.koin.dsl.module

val initModule = module {
    single { AppInitializers(TimberInitializer()) }
}