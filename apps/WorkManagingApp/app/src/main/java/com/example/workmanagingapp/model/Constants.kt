package com.example.workmanagingapp.model

class Constants {
    companion object{
        const val TAG = "aaa"

        const val DB_NAME = "WORK_DATABASE"
        const val DB_VERSION = 1
        const val KEY_ID = "id"
        const val KEY_TITLE = "title"
        const val KEY_TIME = "time"
        const val KEY_CONTENT = "content"
        const val KEY_STATUS = "status"

        enum class ViewAllType{
            TODAY, UPCOMING
        }
    }
}