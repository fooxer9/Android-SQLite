package msc.fooxer.studdatabase


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

const val DATABASE_NAME: String = "StudDB"
const val DATABASE_VERSION: Int = 2
const val TABLE_NAME: String = "Students"
const val KEY_INDEX: String = "_ID"
const val KEY_NAME: String = "FullName"
const val KEY_F = "Surname"
const val KEY_I = "Name"
const val KEY_O = "Patronymic"
const val KEY_TIME: String = "Created"
class DBHelper(context: Context?, name: String? = DATABASE_NAME, factory: SQLiteDatabase.CursorFactory? = null, version: Int = DATABASE_VERSION) :
    SQLiteOpenHelper(context, name, null, version ) {
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL("create table $TABLE_NAME($KEY_INDEX integer primary key, $KEY_F text,$KEY_I text, $KEY_O text,$KEY_TIME text)")
            for (i in 0..4) {
                val contentValues = ContentValues()
                val random = Random()
                var dateText = MainActivity.dateFormat.format(Date())
                val surname = MainActivity.secondName[random.nextInt(MainActivity.secondName.size)]
                val name = MainActivity.firstName[random.nextInt(MainActivity.firstName.size)]
                val patr = MainActivity.thirdName[random.nextInt(MainActivity.thirdName.size)]
                contentValues.put(KEY_F, surname)
                contentValues.put(KEY_I, name)
                contentValues.put(KEY_O, patr)
                contentValues.put(KEY_TIME, dateText)
                db.insert(TABLE_NAME, null, contentValues)
            }
            /*val FIO: String =
                "${secondName[random.nextInt(secondName.size)]} ${firstName[random.nextInt(firstName.size)]}" +
                        " ${thirdName[random.nextInt(thirdName.size)]}"*/

        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if ((oldVersion == 1) && (newVersion == 2) && (db!=null)) {
            /* db.execSQL("create temporary table TMP($KEY_INDEX integer primary key, $KEY_NAME text,$KEY_TIME text)")
             db.execSQL("insert into TMP select * from $TABLE_NAME")
             db.execSQL("drop table $TABLE_NAME")*/
            db.execSQL("alter table $TABLE_NAME rename to TMP")
            db.execSQL("create table $TABLE_NAME($KEY_INDEX integer primary key, $KEY_F text,$KEY_I text, $KEY_O text,$KEY_TIME text)")
            try{
                db.beginTransaction()
                val cursor = db.query("TMP",null,null,null,null,null,null)
                if (cursor.moveToFirst()){
                    val idIndex = cursor.getColumnIndex(KEY_INDEX)
                    val nameIndex = cursor.getColumnIndex(KEY_NAME)
                    val dateIndex = cursor.getColumnIndex(KEY_TIME)
                    val cv = ContentValues()
                    do {
                        cv.clear()
                        cv.put(KEY_INDEX,cursor.getInt(idIndex))
                        cv.put(KEY_TIME, cursor.getString(dateIndex))
                        val list = cursor.getString(nameIndex).split((" "))
                        cv.put(KEY_F, list[0])
                        cv.put(KEY_I, list[1])
                        cv.put(KEY_O, list[2])
                        db.insert(TABLE_NAME,null,cv)
                    } while (cursor.moveToNext())
                }
                db.setTransactionSuccessful()
                db.execSQL("drop table TMP")
            } finally {
                db.endTransaction()
            }
        }
    }

    fun cut (s: String, f: StringBuffer, i: StringBuffer, o: StringBuffer) {
        val position = s.indexOf(" ")
        f.append(s.substring(0,position))
        // s = s.substring(position+1)



    }

}