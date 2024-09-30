package com.coral.coral_kiosk.repos

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class KioskModule {

    @Binds
    abstract fun getKioskRepo(repo: KioskRepoImpl): KioskRepo
}