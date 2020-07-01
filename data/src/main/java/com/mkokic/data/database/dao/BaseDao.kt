package com.mkokic.data.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Completable

interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param type the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(type: T): Completable

    /**
     * Insert an array of objects in the database.
     *
     * @param type the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg type: T): Completable

    /**
     * Update an object from the database.
     *
     * @param type the object to be updated
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(type: T)

    /**
     * Delete an object from the database
     *
     * @param type the object to be deleted
     */
    @Delete
    fun delete(type: T)
}