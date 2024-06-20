package com.portalgmpy.y_trackcomercial.data.model.entities.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1to2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE AuditTrail ADD COLUMN estado TEXT NOT NULL DEFAULT 'P'")
    }
}
