package com.example.inventory.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): InventoryDatabase {
        println("database created")

        return Room.databaseBuilder(
            appContext,
            InventoryDatabase::class.java,
            "item_database"
        ).build()

    }


    @Provides
    @Singleton
    fun provideItemRepository(appDatabase: InventoryDatabase):ItemsRepository{
        println("repository created")

        return  OfflineItemsRepository(appDatabase.itemDao())
    }
}