/**
 * Copyright (C) 2018 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kotlin.trivialdrive.billingrepo.localdb


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CachedPurchase::class, GasTank::class, PremiumCar::class, GoldStatus::class, AugmentedSkuDetails::class],
        version = 1, exportSchema = false)
abstract class LocalBillingDb : RoomDatabase() {
    abstract fun purchaseDao(): PurchaseDao
    abstract fun entitlementsDao(): EntitlementsDao
    abstract fun skuDetailsDao(): AugmentedSkuDetailsDao

    companion object {
        @Volatile
        private var INSTANCE: LocalBillingDb? = null

        fun getInstance(context: Context): LocalBillingDb = INSTANCE?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    LocalBillingDb::class.java,
                    "purchase_db")
                    .fallbackToDestructiveMigration()//remote sources more reliable
                    .build().also { INSTANCE=it }
        }
    }
}
