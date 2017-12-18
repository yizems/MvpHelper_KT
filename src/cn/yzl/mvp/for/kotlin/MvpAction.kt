package cn.yzl.mvp.`for`.kotlin

import com.intellij.codeInsight.CodeInsightActionHandler
import com.intellij.codeInsight.generation.actions.BaseGenerateAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.psi.KtClass


/**
 * action 入口
 * Created by YZL on 2017/9/11.
 */
class MvpAction(handler: CodeInsightActionHandler?) : BaseGenerateAction(handler) {

    var project: Project? = null

    var editor: Editor? = null

    var event: AnActionEvent? = null

    @SuppressWarnings("unused")
    constructor() : this(null) {
    }

    init {

    }

    /**
     * 校验是否为java文件和kotlin文件
     */
    override fun isValidForFile(project: Project, editor: Editor, file: PsiFile): Boolean {
        return file.name.endsWith(".kt")// || file.name.endsWith(".kt")
    }

    /**
     * 校验是否为 activity,fragment,v4.fragment的子类
     */
    override fun isValidForClass(targetClass: PsiClass?): Boolean {
        return true
    }

    override fun actionPerformed(e: AnActionEvent?) {
        event = e
        project = e?.getData(PlatformDataKeys.PROJECT)
        editor = e?.getData(PlatformDataKeys.EDITOR)
        actionPerformedImpl(project!!, editor)
    }

    override fun actionPerformedImpl(project: Project, editor: Editor?) {
        var modle = Modle()
        modle.psiFile = event?.getData(PlatformDataKeys.PSI_FILE);
        println(modle.psiFile?.containingDirectory)
        println(modle.psiFile?.name)
        modle.vFile = DataKeys.VIRTUAL_FILE.getData(event?.dataContext!!)
        modle.path = MyUtils.getCurrentPath(event!!, modle.psiFile?.name!!);
        modle.ktClass = MyUtils.getPsiClassFromEvent(editor);
        if (modle.ktClass == null) {
            return
        }
        if (!(isValidForFile(project, editor!!, modle.psiFile!!))) {
            MessagesCenter.showErrorMessage("只支持Kotlin文件", "不受支持的文件类型")
            return;
        }
//        if (modle.type == ClassTypeEnum.NONE) {
//            MessagesCenter.showErrorMessage("只支持Activity/Fragment/v4包下的Fragment", "不受支持的类型")
//            return
//        }
        MyWriter(project, modle).generateFiles()
        modle.vFile?.parent?.refresh(true, true, null)
    }

}