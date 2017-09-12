package cn.yzl.mvp

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import okio.Okio
import java.io.File
import java.nio.charset.Charset
/**
 * Created by YZL on 2017/9/11.
 */
class MyWriter(project: Project?, modle: Modle?, vararg files: PsiFile?) : WriteCommandAction.Simple<Boolean>(project, *files) {
    var mProject: Project? = null
    var mModle: Modle? = null
    var baseDir: String = "";
    //获取的时候只能获取当前目录下的,如果跨目录进行查询,会返回空指针
    val templateDirPath = this.javaClass.getResource("").file + "/template";

    constructor() : this(null!!, null!!) {

    }

    init {
        mProject = project
        mModle = modle
        baseDir = mModle?.vFile?.parent?.path ?: ""
    }
    fun generateFiles(){
        if (project == null
                || mModle == null
                || mModle?.vFile == null
                || baseDir.length <= 0) {
            return
        }
        mModle?.name = mModle?.clz?.name?.replace(".java", "")
                ?.replace("Activity", "")
                ?.replace("Fragment", "")
        mModle?.pkg = mModle?.clz?.qualifiedName?.substringBeforeLast(".")

        createViewInterface()
        createPresenterInterface()
        createPresenterInterfaceImp()
    }
    override fun run() {
        generateFiles()
    }

    private fun createViewInterface() {
        val dir = createrDir(baseDir + "/view")
        val viewFile = File(dir, mModle?.name + "View.java")

        if (viewFile.exists()) {
            println(mModle?.name + "View.java" + " 已经存在")
            return
        }else{
            viewFile.createNewFile()
        }
        val path = templateDirPath + "/ViewTemp.txt"
        val vTem = File(path)

        val bufferSource = Okio.buffer(Okio.source(vTem))

        val bufferSink = Okio.buffer(Okio.sink(viewFile))

        var content = bufferSource.readString(Charset.forName("UTF-8"))

        content = content.replace("#name", mModle?.name!!, false);
        content = content.replace("#pkg", mModle?.pkg!! + ".view", false);
        content = content.replace("#base_view_pkg", "cn.yzl.app.mvp.base.BaseView", false);
        content = content.replace("#base_view_name", "BaseView", false);
        bufferSink.writeUtf8(content)
        bufferSink.flush()
        bufferSink.close()
        bufferSource.close()
    }

    private fun createPresenterInterface() {
        val dir = createrDir(baseDir + "/presenter")

        val pFile = File(dir, mModle?.name + "Presenter.java")

        if (pFile.exists()) {
            println(mModle?.name + "Presenter.java" + " 已经存在")
            return
        }else{
            pFile.createNewFile()
        }
        val path = templateDirPath + "/PresenterTemp.txt"
        val vTem = File(path)

        val bufferSource = Okio.buffer(Okio.source(vTem))

        val bufferSink = Okio.buffer(Okio.sink(pFile))

        var content = bufferSource.readString(Charset.forName("UTF-8"))

        content = content.replace("#name", mModle?.name!!, false);
        content = content.replace("#pkg", mModle?.pkg!! + ".presenter", false);
        content = content.replace("#base_presenter_pkg", "cn.yzl.app.mvp.base.BasePresenter", false);
        content = content.replace("#base_presenter_name", "BasePresenter", false);
        bufferSink.writeUtf8(content)
        bufferSink.flush()
        bufferSink.close()
        bufferSource.close()
    }

    private fun createPresenterInterfaceImp() {
        val dir = createrDir(baseDir + "/presenter")

        val pFile = File(dir, mModle?.name + "PresenterImp.java")

        if (pFile.exists()) {
            println(mModle?.name + "PresenterImp" + " 已经存在")
            return
        }else{
            pFile.createNewFile()
        }
        val path = templateDirPath + "/PresenterImpTemp.txt"
        val vTem = File(path)

        val bufferSource = Okio.buffer(Okio.source(vTem))

        val bufferSink = Okio.buffer(Okio.sink(pFile))

        var content = bufferSource.readString(Charset.forName("UTF-8"))

        content = content.replace("#name", mModle?.name!!, false);
        content = content.replace("#pkg", "${mModle?.pkg!!}.presenter", false);
        content = content.replace("#view_pkg", "${mModle?.pkg!!}.view.${mModle?.name!!}View", false);
        content = content.replace("#base_presenter_imp_pkg", "cn.yzl.app.mvp.base.BasePresenterImp", false);
        content = content.replace("#base_presenter_imp_name", "BasePresenterImp", false);
        bufferSink.writeUtf8(content)
        bufferSink.flush()
        bufferSink.close()
        bufferSource.close()
    }

    private fun createrDir(path: String): File {
        val file = File(path);
        if (!file.exists()) {
            file.mkdir()
        }
        return file
    }
}
