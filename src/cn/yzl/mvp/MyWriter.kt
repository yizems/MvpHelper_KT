package cn.yzl.mvp

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import okio.Okio
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.util.*

/**
 * Created by YZL on 2017/9/11.
 */
class MyWriter(project: Project?, modle: Modle?, vararg files: PsiFile?) : WriteCommandAction.Simple<Boolean>(project, *files) {
    var mProject: Project? = null
    var mModle: Modle? = null
    var baseDir: String = "";
    //获取的时候只能获取当前目录下的,如果跨目录进行查询,会返回空指针
    val templateDirPath = "template";
    var basePck: ArrayList<String>? = null;

    constructor() : this(null!!, null!!) {

    }

    init {
        mProject = project
        mModle = modle
        baseDir = mModle?.vFile?.parent?.path ?: ""
        if (project != null) basePck = MyUtils.getProperties(project)
    }

    fun generateFiles() {

        if (project == null
                || mModle == null
                || mModle?.vFile == null
                || baseDir.length <= 0) {
            return
        }
//        var flag = false;
//        basePck?.forEach {
//            if (it.isEmpty()) flag = true
//        }
//        if (flag || basePck?.size==0) {
//            MessagesCenter.showErrorMessage("mvp_helper_kt.properties没有配置或者属性缺少", "错误信息")
//            return
//        }

        mModle?.name = mModle?.psiFile?.name?.replace(".kt", "")
                ?.replace("Activity", "")
                ?.replace("Fragment", "")
        mModle?.pkg = mModle?.psiFile?.containingDirectory.toString()?.substringAfter("src")
                ?.replace("\\",".")
                .replaceFirst(".","")
        println(mModle?.name)
        println(mModle?.pkg)

        createViewInterface()
//        createPresenterInterface()
        createPresenterInterfaceImp()
    }

    override fun run() {
        generateFiles()
    }

    private fun createViewInterface() {
        val dir = createrDir(baseDir + "/view")
        val viewFile = File(dir, mModle?.name + "View.kt")

        if (viewFile.exists()) {
            println(mModle?.name + "View.kt" + " 已经存在")
            return
        } else {
            viewFile.createNewFile()
        }
        var path = templateDirPath
        if (basePck?.size!! >= 2
                && !basePck?.get(2).isNullOrEmpty()) {
            path += "/ViewTemp.txt"
        } else {
            path += "/ViewTempNoBase.txt"
        }
        val ins: InputStream = this.javaClass.getResourceAsStream(path)

        val bufferSource = Okio.buffer(Okio.source(ins))

        val bufferSink = Okio.buffer(Okio.sink(viewFile))

        var content = bufferSource.readString(Charset.forName("UTF-8"))

        content = content.replace("#name", mModle?.name!!, false);
        content = content.replace("#pkg", mModle?.pkg!! + ".view", false);
        content = content.replace("#base_view_pkg", basePck?.get(2)!!, false);
        content = content.replace("#base_view_name", basePck?.get(2)?.substringAfterLast(".")!!, false);
        bufferSink.writeUtf8(content)
        bufferSink.flush()
        bufferSink.close()
        bufferSource.close()
        ins.close()
    }

    private fun createPresenterInterfaceImp() {
        val dir = createrDir(baseDir + "/presenter")

        val pFile = File(dir, mModle?.name + "Presenter.kt")

        if (pFile.exists()) {
            println(mModle?.name + "Presenter" + " 已经存在")
            return
        } else {
            pFile.createNewFile()
        }

        var path = templateDirPath
        if (basePck?.size!! >= 1
                && !basePck?.get(1).isNullOrEmpty()) {
            path += "/PresenterTemp.txt"
        } else {
            path += "/PresenterTempNoBase.txt"
        }

        val ins: InputStream = this.javaClass.getResourceAsStream(path)

        val bufferSource = Okio.buffer(Okio.source(ins))

        val bufferSink = Okio.buffer(Okio.sink(pFile))

        var content = bufferSource.readString(Charset.forName("UTF-8"))

        content = content.replace("#name", mModle?.name!!, false);
        content = content.replace("#pkg", "${mModle?.pkg!!}.presenter", false);
        content = content.replace("#view_pkg", "${mModle?.pkg!!}.view.${mModle?.name!!}View", false);
        content = content.replace("#base_presenter_imp_pkg", basePck?.get(1) ?: "", false);
        content = content.replace("#base_presenter_imp_name", basePck?.get(1)?.substringAfterLast(".") ?: "", false);
        bufferSink.writeUtf8(content)
        bufferSink.flush()
        bufferSink.close()
        bufferSource.close()
        ins.close()
    }

    private fun createrDir(path: String): File {
        val file = File(path);
        if (!file.exists()) {
            file.mkdir()
        }
        return file
    }
}
