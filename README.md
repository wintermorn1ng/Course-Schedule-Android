# 开发中

# 功能
- [x] 课表视图
- [x] 添加课程view
- [x] 添加课表视图逻辑
- [ ] 添加课表存储逻辑
- [ ] 课表数据持久化
- [ ] 教务在线获取

# 框架
1. 加入Butter Knife

# TODO
自定义课程的删除

# 自定义视图
尝试创建 CourseView 和CoursesView ，前者提供单格的布局，后者提供整体布局并且负责相应的信息处理。
传入 周课表(WeekCourses) 对象。
对于课表解析成 WeekCourses 对象单独建一个对象来处理。

CoursesView 依靠 RecycleView 的 GridLayoutManager实现

