package msc.fooxer.studdatabase

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import java.lang.Math.random
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object NAMES {
        val firstName : Array<String> = arrayOf("Амирхон", "Радмир", "Александр", "Григорий", "Никита", "Руслан",
            "Керолос", "Иван", "Кирилл", "Константин", "Куок Ань",
            "Андрей", "Даниил", "Павел", "Парвиз", "Дмитрий", "Эльвек", "Савелий")
        val secondName: Array <String> = arrayOf("Абддуалимов", "Акжигитов", "Артемов", "Болдырев", "Гриценко", "Гарянин", "Зекирьяев",
            "Исхак", "Коватьев", "Кузьмин", "Миночкин", "Нгуен", "Рабочих",
            "Сторожук", "Терентьев", "Турсунов", "Флоря", "Чимидов", "Шатров")
        val thirdName: Array<String> = arrayOf("Рустамович", "Руланович", "Андреевич", "Михайлович", "Сергеевич", "Тимурович",
            "Вильям Гиргис","Дмитриевич", "Даниилович", "", "Юрьевич", "Вадимович", "Бахоралиевич", "Эренценович", "Иванович")

        // Форматирование времени как "день.месяц.год"
        var dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    }
    lateinit var db: SQLiteDatabase
    lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = DBHelper(this)
        db = dbHelper.writableDatabase
        val contentValues = ContentValues()
        val random = Random()
        var dateText = dateFormat.format(Date())

        for(i in 0..5){
            val FIO: String = "${secondName[random.nextInt(secondName.size)]} ${firstName[random.nextInt(firstName.size)]}" +
                    " ${thirdName[random.nextInt(thirdName.size)]}"
            contentValues.put(KEY_NAME,FIO)
            contentValues.put(KEY_TIME, dateText)
            db.insert(TABLE_NAME,null,contentValues)
        }
    }

    fun getColumns(view: View) {
        val cursor: Cursor = db.query(TABLE_NAME,null,null,null,null,null,null)
        if(cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(KEY_INDEX)
            val nameIndex = cursor.getColumnIndex(KEY_NAME)
            val dateIndex = cursor.getColumnIndex(KEY_TIME)
            do {
                Log.d("mLog", "ID =  ${cursor.getInt(idIndex)} NAME = ${cursor.getString(nameIndex)}" +
                        " DATE = ${cursor.getString(dateIndex)}")
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
}
