package com.freepath.freepath.di

import com.freepath.freepath.data.plan.PlanDataSourceRemote
import com.freepath.freepath.data.plan.PlanDataSourceRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsPlanDataSource(planDataSource: PlanDataSourceRemoteImpl): PlanDataSourceRemote
}