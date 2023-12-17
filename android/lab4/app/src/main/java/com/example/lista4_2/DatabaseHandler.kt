import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.lista4_2.ListItem

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2137
        private const val DATABASE_NAME = "ItemDatabase"
        private const val TABLE_ITEMS = "Items"

        private const val KEY_ID = "id"
        private const val KEY_QUOTE = "quote"
        private const val KEY_AUTHOR = "author"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_IMAGE_URL = "imageUrl"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createItemsTable = ("CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_QUOTE + " TEXT,"
                + KEY_AUTHOR + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_IMAGE_URL + " TEXT" + ")")
        db?.execSQL(createItemsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ITEMS")
        onCreate(db)
    }

    fun addItem(item: ListItem) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(KEY_ID, item.id)
        values.put(KEY_QUOTE, item.quote)
        values.put(KEY_AUTHOR, item.author)
        values.put(KEY_DESCRIPTION, item.description)
        values.put(KEY_IMAGE_URL, item.imageUrl)

        db.insert(TABLE_ITEMS, null, values)
        db.close()
    }

    fun editItem(item: ListItem) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(KEY_QUOTE, item.quote)
        values.put(KEY_AUTHOR, item.author)
        values.put(KEY_DESCRIPTION, item.description)
        values.put(KEY_IMAGE_URL, item.imageUrl)

        db.update(TABLE_ITEMS, values, "$KEY_ID=?", arrayOf(item.id.toString()))
        db.close()
    }

    fun deleteItem(id:Int) {
        val db = this.writableDatabase
        db.delete(TABLE_ITEMS, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun readItems(): MutableList<ListItem> {
        val itemList = ArrayList<ListItem>()
        val selectQuery = "SELECT * FROM $TABLE_ITEMS"

        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val idIndex = cursor.getColumnIndex(KEY_ID)
                val quoteIndex = cursor.getColumnIndex(KEY_QUOTE)
                val authorIndex = cursor.getColumnIndex(KEY_AUTHOR)
                val descriptionIndex = cursor.getColumnIndex(KEY_DESCRIPTION)
                val imageUrlIndex = cursor.getColumnIndex(KEY_IMAGE_URL)

                val id = if (idIndex != -1) cursor.getInt(idIndex) else 0
                val quote = if (quoteIndex != -1) cursor.getString(quoteIndex) else "Default Quote"
                val author = if (authorIndex != -1) cursor.getString(authorIndex) else "Unknown Author"
                val description = if (descriptionIndex != -1) cursor.getString(descriptionIndex) else "No Description"
                val imageUrl = if (imageUrlIndex != -1) cursor.getString(imageUrlIndex) else "No Image URL"

                val item = ListItem(id, quote, author, description, imageUrl)
                itemList.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return itemList
    }

}