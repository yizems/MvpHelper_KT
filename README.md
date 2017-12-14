## MvpHelper_KT

### 1 说明

该分支为 为kotlin项目做的分支插件分支
- 取消persenter 抽象类,直接写成imp格式
- 生成kotlin编写的 presenter和view类

- 只适用于标准Android Studio项目结构

### 2 使用

![Alt+Insert][1]

**Base配置方法**
目前仅支持3个属性

- 根目录下 添加 mvp_helper_kt.properties文件,可参照本项目中的文件
- base_view_pkg BaseView包路径(cn.yzl.base.BaseView)
- base_presenter_pkg BasePresenter包路径(cn.yzl.base.BasePresenter包)
- base_presenter_imp_pkg BasePresenterImp包路径(cn.yzl.BasePresenterImp)



### 3 一点想法

其实无论mvc也好,mvp也好,适合自己的或者项目的才是最重要的,不一定要完全按照标准的来写,就像Android中的设计模式一样,好多都是经过改造的,并不标准,但很适合

### 4 问题

### 5 参考

- https://github.com/langxuelang/MvpCodeCreator
- https://github.com/langxuelang/MvpCodeCreator


  [1]: https://github.com/yizeliang/MvpHelper_KT/blob/master/screenshots/1.gif