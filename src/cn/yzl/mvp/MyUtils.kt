package cn.yzl.mvp

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiType
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil


/**
 * Created by YZL on 2017/9/11.
 */
object MyUtils {
    /**
     * 是否是activit
     */
    fun getFileClassType(targetClass: PsiClass?): ClassTypeEnum {
        if (targetClass?.qualifiedName.equals("android.app.Activity")) {
            return ClassTypeEnum.ACTIVITY
        } else if (targetClass?.qualifiedName.equals("android.support.v4.app.Fragment")) {
            return ClassTypeEnum.V4_FRAGMENT
        } else if (targetClass?.qualifiedName.equals("android.app.Fragment")) {
            return ClassTypeEnum.FRAGMENT
        }
        val superClass = targetClass?.supers;
        superClass?.forEach() {
            if (it.qualifiedName.equals("android.app.Activity")) {
                return ClassTypeEnum.ACTIVITY
            } else if (it.qualifiedName.equals("android.support.v4.app.Fragment")) {
                return ClassTypeEnum.V4_FRAGMENT
            } else if (it.qualifiedName.equals("android.app.Fragment")) {
                return ClassTypeEnum.FRAGMENT
            } else {
                val fileClassType = getFileClassType(targetClass);
                if (fileClassType != ClassTypeEnum.NONE) {
                    return fileClassType;
                }
            }
        }
        return ClassTypeEnum.NONE
    }

    /**
     * 获取一个文件的路径

     * @param e
     * *
     * @param classFullName ex:nihao.java
     * *
     * @return
     */
    fun getCurrentPath(e: AnActionEvent, classFullName: String): String {
        val currentFile = DataKeys.VIRTUAL_FILE.getData(e.dataContext)
        val path = currentFile!!.path.replace(classFullName, "")
        return path
    }
    /**
     * @param psiFile
     * @param editor
     * @return
     */
    fun getPsiClassFromContext(psiFile: PsiFile?, editor: Editor?): PsiClass? {
        if (psiFile == null || editor == null) {
            return null
        }
        val offset = editor.caretModel.offset
        val element = psiFile.findElementAt(offset)
        return PsiTreeUtil.getParentOfType(element, PsiClass::class.java)
    }
}