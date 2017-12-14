package cn.yzl.mvp.`for`.kotlin

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import java.io.File
import java.io.FileInputStream
import java.util.*


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
                val fileClassType = getFileClassType(it);
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


//    private fun getPsiClassFromEvent(e: AnActionEvent): KtClass? {
//        val editor = e.getData(PlatformDataKeys.EDITOR) ?: return null
//
//        val project = editor.project ?: return null
//
//        val psiFile = e.getData(LangDataKeys.PSI_FILE)
//        if (psiFile == null || psiFile.language !== KotlinLanguage.INSTANCE) return null
//
//        val location = Location.fromEditor(editor, project)
//        val psiElement = psiFile.findElementAt(location.getStartOffset()) ?: return null
//
//        return KtClassHelper.getKtClassForElement(psiElement)
//    }

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

    fun getProperties(project: Project?): ArrayList<String> {
        var result: ArrayList<String> = ArrayList<String>()
        val proPath = project?.baseDir?.path  + "/mvp_helper_kt.properties";
        val pro = Properties()
        if(!File(proPath).exists()){
            result.add("")
            result.add("")
            result.add("")
            return result
        }
        pro.load(FileInputStream(proPath))
        result.add(pro?.getProperty("base_presenter_pkg"))
        result.add(pro?.getProperty("base_presenter_imp_pkg"))
        result.add(pro?.getProperty("base_view_pkg"))
        return result
    }
}