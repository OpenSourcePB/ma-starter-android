package digital.pashabank.starter.di

import digital.pashabank.starter.appinitializers.di.initModule
import digital.pashabank.data.di.dataModule
import digital.pashabank.domain.di.domainModule
import digital.pashabank.presentation.di.presentationModule

val appComponent = listOf(
    initModule,
    dataModule,
    domainModule,
    presentationModule
)