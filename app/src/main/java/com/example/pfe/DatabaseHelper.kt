package com.example.pfe

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // create table sql query
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")

    private val CREATE_CLIENT_TABLE=("CREATE TABLE " + TABLE_CLIENT + "("
            + COLUMN_CLIENT_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CLIENT_NAME + " TEXT," + COLUMN_CLIENT_PRENOM + " TEXT,"
            + COLUMN_CLIENT_VILLE + " TEXT,"+ COLUMN_CLIENT_TELEPHONE + " TEXT,"
            + COLUMN_CLIENT_EMAIL + " TEXT," + COLUMN_CLIENT_PASSWORD + " TEXT" + ")")

    private val CREATE_PROFESSIONNEL_TABLE=("CREATE TABLE " + TABLE_PROFESSIONNEL + "("
            + COLUMN_PROFESSIONNEL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PROFESSIONNEL_NAME + " TEXT," + COLUMN_PROFESSIONNEL_PRENOM + " TEXT,"
            + COLUMN_PROFESSIONNEL_VILLE + " TEXT,"+ COLUMN_PROFESSIONNEL_TELEPHONE + " TEXT,"
            + COLUMN_PROFESSIONNEL_EMAIL + " TEXT," + COLUMN_PROFESSIONNEL_PASSWORD + " TEXT" + ")")

    private val CREATE_HABITS_TABLE=("CREATE TABLE" + TABLE_HABITS + "("
            + COLUMN_HABITS_TITRE + " TEXT UNIQUE, " + COLUMN_HABITS_VILLE + " Text, "
            + COLUMN_HABITS_NOM +" TEXT, " + COLUMN_HABITS_PRENOM + " TEXT, "
            + COLUMN_HABITS_EMAIL + " TEXT, " + COLUMN_HABITS_TELEPHONE+ " TEXT " +")")
    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"
    private val DROP_CLIENT_TABLE="DROP TABLE IF EXISTS $TABLE_CLIENT"
    private val DROP_PROFESSIONNEL_TABLE="DROP TABLE IF EXISTS $TABLE_PROFESSIONNEL"
    private val DROP_HABITS_TABLE= "DROP TABLE IF EXISTS $TABLE_HABITS"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
        db.execSQL(CREATE_CLIENT_TABLE)
        db.execSQL(CREATE_PROFESSIONNEL_TABLE)
        db.execSQL(CREATE_HABITS_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE)
        db.execSQL(DROP_CLIENT_TABLE)
        db.execSQL(DROP_PROFESSIONNEL_TABLE)
        db.execSQL(DROP_HABITS_TABLE)
        // Create tables again
        onCreate(db)
    }
    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
  /* fun getAllUser(): list {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD)
        // sorting orders
        val sortOrder = "$COLUMN_USER_NAME ASC"
        val userList = arrayList()
        val db = this.readableDatabase
        // query the user table
        val cursor = db.query(TABLE_USER, //Table to query
            columns,            //columns to return
            null,     //columns for the WHERE clause
            null,  //The values for the WHERE clause
            null,      //group the rows
            null,       //filter by row groups
            sortOrder)         //The sort order
        if (cursor.moveToFirst()) {
            do {
                val user = User(id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)))
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }*/
    /**
     * This method is to create user record
     *
     * @param user
     */


    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)
        // Inserting Row
        db.insert(TABLE_USER, null, values)
        db.close()
    }
    fun addClient(client: Client)
    {
        val db=this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_CLIENT_NAME, client.name)
        values.put(COLUMN_CLIENT_PRENOM, client.prenom)
        values.put(COLUMN_CLIENT_VILLE, client.ville)
        values.put(COLUMN_CLIENT_TELEPHONE, client.telephone)
        values.put(COLUMN_CLIENT_EMAIL, client.email)
        values.put(COLUMN_CLIENT_PASSWORD, client.password)
        db.insert(TABLE_CLIENT,null,values)
        db.close()
    }
    fun addProfessionnel(professionnel: Professionnel)
    {
        val db=this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_PROFESSIONNEL_NAME, professionnel.name)
        values.put(COLUMN_PROFESSIONNEL_PRENOM, professionnel.prenom)
        values.put(COLUMN_PROFESSIONNEL_VILLE, professionnel.ville)
        values.put(COLUMN_PROFESSIONNEL_TELEPHONE, professionnel.telephone)
        values.put(COLUMN_PROFESSIONNEL_EMAIL, professionnel.email)
        values.put(COLUMN_PROFESSIONNEL_PASSWORD, professionnel.password)
        db.insert(TABLE_PROFESSIONNEL,null,values)
        db.close()
    }
    fun addHabits(habits: Habits)
    {
        val db=this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_HABITS_TITRE, habits.titre)
        values.put(COLUMN_HABITS_VILLE, habits.ville)
        values.put(COLUMN_HABITS_NOM, habits.nom)
        values.put(COLUMN_HABITS_PRENOM, habits.prenom)
        values.put(COLUMN_HABITS_EMAIL, habits.email)
        values.put(COLUMN_HABITS_TELEPHONE, habits.telephone)
        db.insert(TABLE_HABITS, null,values)
        db.close()
    }
    /**
     * This method to update user record
     *
     * @param user
     */
    fun updateUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)
        // updating row
        db.update(TABLE_USER, values, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
        db.close()
    }
    fun updateClient(client:Client)
    {
        val db=this.writableDatabase
        val values=ContentValues()
        values.put(COLUMN_CLIENT_NAME, client.name)
        values.put(COLUMN_CLIENT_PRENOM, client.prenom)
        values.put(COLUMN_CLIENT_VILLE, client.ville)
        values.put(COLUMN_CLIENT_TELEPHONE, client.telephone)
        values.put(COLUMN_CLIENT_EMAIL, client.email)
        values.put(COLUMN_CLIENT_PASSWORD, client.password)
        db.update(TABLE_CLIENT, values,"$COLUMN_CLIENT_ID=?",
             arrayOf(client.id.toString()))
        db.close()
    }
    fun updateProfessionnel(professionnel: Professionnel)
    {
        val db=this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_PROFESSIONNEL_NAME, professionnel.name)
        values.put(COLUMN_PROFESSIONNEL_PRENOM, professionnel.prenom)
        values.put(COLUMN_PROFESSIONNEL_VILLE, professionnel.ville)
        values.put(COLUMN_PROFESSIONNEL_TELEPHONE, professionnel.telephone)
        values.put(COLUMN_PROFESSIONNEL_EMAIL, professionnel.email)
        values.put(COLUMN_PROFESSIONNEL_PASSWORD, professionnel.password)
        db.insert(TABLE_PROFESSIONNEL,null,values)
        db.update(
            TABLE_PROFESSIONNEL, values,"$COLUMN_PROFESSIONNEL_ID=?",
            arrayOf(professionnel.id.toString()))
        db.close()
    }
    fun updateHabits(habits: Habits)
    {
        val db=this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_HABITS_TITRE, habits.titre)
        values.put(COLUMN_HABITS_VILLE, habits.ville)
        values.put(COLUMN_HABITS_NOM, habits.nom)
        values.put(COLUMN_HABITS_PRENOM, habits.prenom)
        values.put(COLUMN_HABITS_EMAIL, habits.email)
        values.put(COLUMN_HABITS_TELEPHONE, habits.telephone)
        db.update(
            TABLE_HABITS, values,"$COLUMN_HABITS_TITRE=?",
        arrayOf(habits.titre.toString()))
        db.close()
    }
    /**
     * This method is to delete user record
     *
     * @param user
     */
    fun deleteUser(user: User) {
        val db = this.writableDatabase
        // delete user record by id
        db.delete(TABLE_USER, "$COLUMN_USER_ID = ?",
            arrayOf(user.id.toString()))
        db.close()
    }
    fun deleteClient(client: Client)
    {
        val db=this.writableDatabase
        db.delete(TABLE_CLIENT,"$COLUMN_CLIENT_ID=?",
        arrayOf(client.id.toString()))
        db.close()
    }
    fun deleteProfessionnel(professionnel: Professionnel)
    {
        val db=this.writableDatabase
        db.delete(
            TABLE_PROFESSIONNEL,"$COLUMN_PROFESSIONNEL_ID?",
            arrayOf(professionnel.id.toString()))
        db.close()
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    fun checkUser(email: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COLUMN_USER_EMAIL = ?"
        // selection argument
        val selectionArgs = arrayOf(email)
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(TABLE_USER, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0) {
            return true
        }
        return false
    }
    fun checkClient(email: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(
            COLUMN_CLIENT_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COLUMN_CLIENT_EMAIL = ?"
        // selection argument
        val selectionArgs = arrayOf(email)
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(
            TABLE_CLIENT, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0) {
            return true
        }
        return false
    }
    fun checkProfessionnel(email: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_PROFESSIONNEL_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COLUMN_PROFESSIONNEL_EMAIL = ?"
        // selection argument
        val selectionArgs = arrayOf(email)
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(
            TABLE_PROFESSIONNEL, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0) {
            return true
        }
        return false
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    fun checkClient(email: String, password: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_CLIENT_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COLUMN_CLIENT_EMAIL = ? AND $COLUMN_CLIENT_PASSWORD = ?"
        // selection arguments
        val selectionArgs = arrayOf(email, password)
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(
            TABLE_CLIENT, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }
    fun checkUser(email: String, password: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"
        // selection arguments
        val selectionArgs = arrayOf(email, password)
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(TABLE_USER, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }
    fun checkProfessionnel(email: String, password: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_PROFESSIONNEL_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COLUMN_PROFESSIONNEL_EMAIL = ? AND $COLUMN_PROFESSIONNEL_PASSWORD = ?"
        // selection arguments
        val selectionArgs = arrayOf(email, password)
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(
            TABLE_PROFESSIONNEL, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0)
            return true
        return false
    }
    companion object {
        // Database Version
        private val DATABASE_VERSION = 1
        // Database Name
        private val DATABASE_NAME = "UserManager.db"
        // User table name
        private val TABLE_USER = "user"
        private val TABLE_CLIENT= "client"
        private val TABLE_PROFESSIONNEL="professionnel"
        private val TABLE_HABITS="habits"
        // User Table Columns names
        // User Table Columns names
        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_USER_PASSWORD = "user_password"

        private val COLUMN_CLIENT_ID = "client_id"
        val COLUMN_CLIENT_NAME = "client_name"
        val COLUMN_CLIENT_PRENOM="client_prénom"
        val COLUMN_CLIENT_EMAIL = "client_email"
        val COLUMN_CLIENT_VILLE= "client_ville"
        val COLUMN_CLIENT_TELEPHONE="client_telephone"
        val COLUMN_CLIENT_PASSWORD = "client_password"

        private val COLUMN_PROFESSIONNEL_ID = "professionnel_id"
        val COLUMN_PROFESSIONNEL_NAME = "professionnel_name"
        val COLUMN_PROFESSIONNEL_PRENOM="professionnel_prénom"
        val COLUMN_PROFESSIONNEL_EMAIL= "professionnel_email"
        val COLUMN_PROFESSIONNEL_VILLE= "professionnel_ville"
        val COLUMN_PROFESSIONNEL_TELEPHONE="professionnel_telephone"
        val COLUMN_PROFESSIONNEL_PASSWORD= "professionnel_password"

        private val COLUMN_HABITS_TITRE= "habits_titre"
        private val COLUMN_HABITS_VILLE= "habits_ville"
        private val COLUMN_HABITS_NOM= "habits_nom"
        private val COLUMN_HABITS_PRENOM= "habits_prenom"
        private val COLUMN_HABITS_EMAIL= "habits_email"
        private val COLUMN_HABITS_TELEPHONE= "habits_telephone"
    }

}