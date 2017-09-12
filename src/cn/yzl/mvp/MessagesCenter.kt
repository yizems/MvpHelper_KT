package cn.yzl.mvp

import com.intellij.openapi.ui.Messages

/**
 * Dialog总控
 * Created by wing on 16/7/23.
 */
object MessagesCenter {

    fun showErrorMessage(msg: String, title: String) {
        Messages.showMessageDialog(msg, title, Messages.getErrorIcon())
    }

    fun showMessage(msg: String, title: String) {
        Messages.showMessageDialog(msg, title, Messages.getInformationIcon())
    }

//    fun showDebugMessage(msg: String, title: String) {
//        if (false) {
//            Messages.showMessageDialog(msg, title, Messages.getErrorIcon())
//        }
//    }
}