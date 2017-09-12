package test

import cn.yzl.mvp.MyUtils

/**
 * Created by YZL on 2017/9/12.
 */
object Test {
    @JvmStatic fun main(args: Array<String>) {
        val properties = MyUtils.getProperties(null);
        properties.forEach {
            println(it)
        }
    }
}
