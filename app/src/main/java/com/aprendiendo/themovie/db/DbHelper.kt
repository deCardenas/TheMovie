package com.aprendiendo.themovie.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable

class DbHelper(context: Context) : ManagedSQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){
    companion object {
        const val DB_NAME = "dbTheMovie"
        const val DB_VERSION = 1
        private var instance: DbHelper? = null
        @Synchronized
        fun getInstance(context: Context): DbHelper {
            if (instance == null) DbHelper(context)
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(ListTable.TABLE_NAME,  true,
                ListTable.TYPE to INTEGER,
                ListTable.TOTAL_PAGES to INTEGER)
        db.createTable(MovieTable.TABLE_NAME, true,
                MovieTable.ID to INTEGER,
                MovieTable.TITLE to TEXT,
                MovieTable.OVERVIEW to TEXT,
                MovieTable.POSTER_PATH to TEXT,
                MovieTable.POPULARITY to INTEGER)
        db.createTable(TvShowTable.TABLE_NAME, true,
                TvShowTable.ID to INTEGER,
                TvShowTable.NAME to TEXT,
                TvShowTable.OVERVIEW to TEXT,
                TvShowTable.POSTER_PATH to TEXT,
                TvShowTable.POPULARITY to INTEGER)
        db.createTable(PersonTable.TABLE_NAME, true,
                PersonTable.ID to INTEGER,
                PersonTable.NAME to TEXT,
                PersonTable.PROFILE_PATH to TEXT,
                PersonTable.POPULARITY to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

val Context.database: DbHelper
    get() = DbHelper.getInstance(applicationContext)