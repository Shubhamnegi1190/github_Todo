package com.example.todo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDatabaseHelper(context: Context):SQLiteOpenHelper(context , DATABASE_NAME ,null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "ID"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"

    }

    // below is the method for creating a database by sqlite query
    override fun onCreate(db: SQLiteDatabase?) {
        //sqlite query along thier data type are given

        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY , $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        // method for executing our query
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // this method is used to check if table already exists
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }


    // this method is for adding data in our database
    fun insertNote(note: Note) {
        // here we are creating a writable variable of our database
        val db = writableDatabase
        //below we create a content value variable
        val values = ContentValues().apply {
            // we are inserting our value in form of key value pair
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
        }
        // all values are inserted in database
        db.insert(TABLE_NAME, null, values)
        // closing of our database
        db.close()
    }

    // here we created a function which extends the list of notes data class
    fun getAllNotes(): List<Note> {
        //here we craeted a  =empty note list called (mutable list)
        val notesList = mutableListOf<Note>()
        // here we want to read data from the list
        val db = this.readableDatabase
        // our main query
        val query = "SELECT*FROM $TABLE_NAME"
        // cursor is used to read data ! using cursor we can iterate over the rows
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val note = Note(id, title, content)
            notesList.add(note)

        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateNote(note: Note) {
        // here we are creating a writable variable of our database
        val db = writableDatabase
        //below we create a content value variable
        val values = ContentValues().apply {
            // we are inserting our value in form of key value pair
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()

    }
    fun getNoteByID(noteID: Int):Note{
        // here we want to read data from the list
        val db = readableDatabase
        // our main query
        val query = "SELECT*FROM $TABLE_NAME WHERE $COLUMN_ID = $noteID"
        // cursor is used to read data ! using cursor we can iterate over the rows
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Note(id , title , content)
    }

    fun deleteNote(noteID: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID=?"
        val whereArgs = arrayOf(noteID.toString())
        db.delete(TABLE_NAME  , whereClause , whereArgs)
        db.close()
    }
}