package az.pashabank.starter.appinitializers.di

import az.pashabank.starter.appinitializers.AppInitializers
import az.pashabank.starter.appinitializers.TimberInitializer
import org.koin.dsl.module

val initModule = module {
    single { AppInitializers(TimberInitializer()) }
}