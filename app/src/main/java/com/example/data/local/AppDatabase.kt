package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.core.config.AppConfig
import com.example.data.local.dao.CategoryDao
import com.example.data.local.dao.NoteDao
import com.example.data.local.dao.NoteRevisionDao
import com.example.data.local.entity.CategoryEntity
import com.example.data.local.entity.NoteEntity
import com.example.data.local.entity.NoteRevisionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        NoteEntity::class,
        CategoryEntity::class,
        NoteRevisionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao
    abstract fun noteRevisionDao(): NoteRevisionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    AppConfig.DATABASE_NAME
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Prepopulate default Persian categories
                            CoroutineScope(Dispatchers.IO).launch {
                                getInstance(context).categoryDao().insertAll(
                                    listOf(
                                        CategoryEntity(name = "شخصی", colorHex = "#3B82F6", iconName = "person", isDefault = true),
                                        CategoryEntity(name = "کاری", colorHex = "#10B981", iconName = "work", isDefault = true),
                                        CategoryEntity(name = "خرید", colorHex = "#F59E0B", iconName = "shopping_cart", isDefault = true),
                                        CategoryEntity(name = "درس", colorHex = "#8B5CF6", iconName = "school", isDefault = true),
                                        CategoryEntity(name = "ایده‌ها", colorHex = "#EC4899", iconName = "lightbulb", isDefault = true),
                                        CategoryEntity(name = "خاطرات", colorHex = "#14B8A6", iconName = "favorite", isDefault = true),
                                        CategoryEntity(name = "سفر", colorHex = "#EF4444", iconName = "flight", isDefault = true)
                                    )
                                )
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
