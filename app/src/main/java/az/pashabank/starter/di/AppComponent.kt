package az.pashabank.starter.di

import az.pashabank.starter.appinitializers.di.initModule
import az.pashabank.data.di.dataModule
import az.pashabank.domain.di.domainModule
import az.pashabank.presentation.di.presentationModule

val appComponent = listOf(
    initModule,
    dataModule,
    domainModule,
    presentationModule
)