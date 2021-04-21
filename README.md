# 开发中
![](https://gitee.com/wintermorning/img/raw/master/img/Screenshot_2021-04.jpg)
# 功能
- [x] 课表视图
- [x] 添加课程view
- [x] 添加课表视图逻辑
- [x] 添加课表存储逻辑
- [x] 课表数据持久化
- [x] 教务在线获取
- [ ] 上课提醒
# 框架
1. Butter Knife
2. Gson
3. Rxjava & RxAndroid
4. Okhttp3
5. Jsoup
6. Stepper-Touch
# TODO
- [X] 自定义课程的删除与管理(长按删除)
- [X] 美化ui
- [ ] 显示学期
# 自定义视图
尝试创建 CourseView 和CoursesView ，前者提供单格的布局，后者提供整体布局并且负责相应的信息处理。
传入 周课表(CoursesWeek) 对象。
对于课表解析成 kCoursesWee 对象单独建一个对象来处理。

CoursesView 依靠 RecycleView 的 GridLayoutManager实现

