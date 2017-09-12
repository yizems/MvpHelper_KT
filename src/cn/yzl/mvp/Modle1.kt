package cn.yzl.mvp

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiClass

/**
 * 测试数据类
 * Created by YZL on 2017/9/11.
 */
data class Modle1(var type: ClassTypeEnum?, var path: String?, var name: String?, var pkg: String?
                  , var vFile: VirtualFile?, var clz: PsiClass?)