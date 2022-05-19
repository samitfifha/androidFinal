package tn.esprit.myapplication.utils

import java.text.FieldPosition

interface ClickHandler {
    fun ClickItem(position: Int)
}

interface ClickUserHandler {
    fun ClickItem(position: Int, prod: Int)

}