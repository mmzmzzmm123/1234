import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                   // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true               // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect           // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'             // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * meta : {
    roles: ['admin','editor']    // 设置该路由进入的权限，支持多个权限叠加
    title: 'title'               // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'             // 设置该路由的图标，对应路径src/icons/svg
    breadcrumb: false            // 如果设置为false，则不会在breadcrumb面包屑中显示
  }
 */

// 公共路由
export const constantRoutes = [{
        path: '/redirect',
        component: Layout,
        hidden: true,
        children: [{
            path: '/redirect/:path(.*)',
            component: () =>
                import ('@/views/redirect')
        }]
    },
    {
        path: '/login',
        component: () =>
            import ('@/views/login'),
        hidden: true
    },
    {
        path: '/404',
        component: () =>
            import ('@/views/error/404'),
        hidden: true
    },
    {
        path: '/401',
        component: () =>
            import ('@/views/error/401'),
        hidden: true
    },
    {
        path: '/experience/content/:id',
        component: () =>
            import ('@/views/benyi/experience/content'),
        hidden: true
    },
    {
        path: '/activity',
        component: () =>
            import ('@/views/benyi/activity'),
        hidden: true
    },
    {
        path: '/week',
        component: () =>
            import ('@/views/benyi/themeweekplan/table'),
        hidden: true
    },
    {
        path: '/month',
        component: () =>
            import ('@/views/benyi/thememonthplan/table'),
        hidden: true
    },
    {
        path: '/term',
        component: () =>
            import ('@/views/benyi/themetermplan/table'),
        hidden: true
    },
    {
        path: '/play',
        component: () =>
            import ('@/views/benyi/planweek/table'),
        hidden: true
    },
    {
        path: '/benyi_child/child_preserve',
        component: () =>
            import ('@/views/benyi/child_preserve/index'),
        hidden: true,
        children: [{
            path: '/benyi_child/child_preserve',
            component: () =>
                import ('@/views/benyi/child_preserve/index'),
            hidden: true
        }, ]
    },
    {
        path: '/experience/apply/:id(\\d+)',
        component: () =>
            import ('@/views/benyi/experience/choose'),
        hidden: true,
        children: [{
                path: '/experience/apply/:id(\\d+)',
                component: () =>
                    import ('@/views/benyi/experience/apply'),
                hidden: true
            },
            {
                path: '/experience/result/:id(\\d+)',
                component: () =>
                    import ('@/views/benyi/experience/result'),
                hidden: true
            }
        ]
    },
    {
        path: '',
        component: Layout,
        redirect: 'index',
        children: [{
            path: 'index',
            component: () =>
                import ('@/views/index'),
            name: '首页',
            meta: {
                title: '首页',
                icon: 'dashboard',
                noCache: true,
                affix: true
            }
        }]
    },
    {
        path: '/user',
        component: Layout,
        hidden: true,
        redirect: 'noredirect',
        children: [{
            path: 'profile',
            component: () =>
                import ('@/views/system/user/profile/index'),
            name: 'Profile',
            meta: {
                title: '个人中心',
                icon: 'user'
            }
        }]
    },
    {
        path: '/user/change',
        component: Layout,
        hidden: true,
        redirect: 'noredirect',
        children: [{
            path: 'dept',
            component: () =>
                import ('@/views/system/user/change/dept/index'),
            name: 'Changedept',
            meta: {
                title: '切换岗位',
                icon: 'user'
            }
        }]
    },
    {
        path: '/dict',
        component: Layout,
        hidden: true,
        children: [{
            path: 'type/data/:dictId(\\d+)',
            component: () =>
                import ('@/views/system/dict/data'),
            name: 'Data',
            meta: {
                title: '字典数据',
                icon: ''
            }
        }]
    },
    {
        path: '/dayflow',
        component: Layout,
        hidden: true,
        children: [{
            path: 'dayflowmanger/dayflowtask/:id(\\d+)',
            component: () =>
                import ('@/views/benyi/dayflow/task'),
            name: 'Task',
            meta: {
                title: '一日流程任务',
                icon: ''
            }
        }]
    },
    {
        path: '/dayflow/dayflowmanger',
        component: Layout,
        hidden: true,
        children: [{
            path: 'dayflowtask/standard/:code(\\d+)',
            component: () =>
                import ('@/views/benyi/dayflow/standard'),
            name: 'Standard',
            meta: {
                title: '一日流程标准',
                icon: ''
            }
        }]
    },
    {
        path: '/dayflow/dayflowmanger/dayflowtask',
        component: Layout,
        hidden: true,
        children: [{
            path: 'standard/unscramble/:id(\\d+)',
            component: () =>
                import ('@/views/benyi/dayflow/unscramble'),
            name: 'unscramble',
            meta: {
                title: '一日流程标准解读',
                icon: ''
            }
        }]
    },
    {
        path: '/video_study',
        component: Layout,
        hidden: true,
        children: [{
            path: 'study/detail/:id(\\d+)',
            component: () =>
                import ('@/views/benyi_train/video_study/detail'),
            name: 'Detail',
            meta: {
                title: '培训视频详情',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi_course/planweek',
        component: Layout,
        hidden: true,
        children: [{
            path: 'data/:id',
            component: () =>
                import ('@/views/benyi/planweek/data'),
            name: 'planweek1',
            meta: {
                title: '周计划(明细)',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi_course/theme',
        component: Layout,
        hidden: true,
        children: [{
            path: 'activity/:id(\\d+)',
            component: () =>
                import ('@/views/benyi/theme/activity'),
            name: 'Theme1',
            meta: {
                title: '主题整合内容',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi_course/mathtermplan',
        component: Layout,
        hidden: true,
        children: [{
            path: 'data/:id',
            component: () =>
                import ('@/views/benyi/mathtermplan/data'),
            name: 'Mathtermplan1',
            meta: {
                title: '游戏数学学期计划(明细)',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi_course/mathtermplanprint',
        component: Layout,
        hidden: true,
        children: [{
            path: 'table/:id',
            component: () =>
                import ('@/views/benyi/mathtermplan/table'),
            name: 'Mathtermplan2',
            meta: {
                title: '游戏数学学期计划(表格)',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi_course/themetermplan',
        component: Layout,
        hidden: true,
        children: [{
            path: 'data/:id',
            component: () =>
                import ('@/views/benyi/themetermplan/data'),
            name: 'Themetermplan1',
            meta: {
                title: '主题整合学期计划(明细)',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi_course/thememonthplan',
        component: Layout,
        hidden: true,
        children: [{
            path: 'data/:id',
            component: () =>
                import ('@/views/benyi/thememonthplan/data'),
            name: 'Thememonthplan1',
            meta: {
                title: '主题整合月计划(明细)',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi_course/themeweekplan',
        component: Layout,
        hidden: true,
        children: [{
            path: 'data/:id',
            component: () =>
                import ('@/views/benyi/themeweekplan/data'),
            name: 'Themeweekplan1',
            meta: {
                title: '主题整合周计划(明细)',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi_course/themeweekplanprint',
        component: Layout,
        hidden: true,
        children: [{
            path: 'table/:id',
            component: () =>
                import ('@/views/benyi/themeweekplan/table'),
            name: 'Themeweekplan2',
            meta: {
                title: '主题整合周计划(表格)',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi_course/planweekprint',
        component: Layout,
        hidden: true,
        children: [{
            path: 'table/:id',
            component: () =>
                import ('@/views/benyi/planweek/table'),
            name: 'planweek2',
            meta: {
                title: '主题整合周计划(表格)',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi_course/thememonthplanprint',
        component: Layout,
        hidden: true,
        children: [{
            path: 'table/:id',
            component: () =>
                import ('@/views/benyi/thememonthplan/table'),
            name: 'Thememonthplan2',
            meta: {
                title: '主题整合月计划(表格)',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi_course/themetermplanprint',
        component: Layout,
        hidden: true,
        children: [{
            path: 'table/:id',
            component: () =>
                import ('@/views/benyi/themetermplan/table'),
            name: 'Themetermplan2',
            meta: {
                title: '主题整合学期计划(表格)',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi_course/math',
        component: Layout,
        hidden: true,
        children: [{
            path: 'plan/:id(\\d+)',
            component: () =>
                import ('@/views/benyi/math/plan'),
            name: 'Math1',
            meta: {
                title: '游戏数学方案',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi/assessmentchild',
        component: Layout,
        hidden: true,
        children: [{
            path: 'student/:id(\\d+)',
            component: () =>
                import ('@/views/benyi/assessment/student'),
            name: 'assessmentchild',
            meta: {
                title: '幼儿评估详情',
                icon: ''
            }
        }]
    },
    {
        path: '/benyi/assessmentchildhistory',
        component: Layout,
        hidden: true,
        children: [{
            path: 'student/:id(\\d+)',
            component: () =>
                import ('@/views/benyi/assessment/history'),
            name: 'assessmentchildhistory',
            meta: {
                title: '幼儿评估图表',
                icon: ''
            }
        }]
    },
    {
        path: '/job',
        component: Layout,
        hidden: true,
        children: [{
            path: 'log',
            component: () =>
                import ('@/views/monitor/job/log'),
            name: 'JobLog',
            meta: {
                title: '调度日志'
            }
        }]
    },
    {
        path: '/gen',
        component: Layout,
        hidden: true,
        children: [{
            path: 'edit',
            component: () =>
                import ('@/views/tool/gen/editTable'),
            name: 'GenEdit',
            meta: {
                title: '修改生成配置'
            }
        }]
    }
]

export default new Router({
    mode: 'history', // 去掉url中的#
    scrollBehavior: () => ({
        y: 0
    }),
    routes: constantRoutes
})