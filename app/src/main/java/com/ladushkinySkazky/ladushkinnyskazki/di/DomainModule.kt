package com.ladushkinySkazky.ladushkinnyskazki.di

import com.ladushkinySkazky.ladushkinnyskazki.data.SkazkyRepositoryImpl
import com.ladushkinySkazky.ladushkinnyskazki.domian.SkazkyRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideRepository(): SkazkyRepository {
        return SkazkyRepositoryImpl
    }
}