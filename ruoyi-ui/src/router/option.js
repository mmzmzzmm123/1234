import Layout from '@/layout'

// export default 的本质是将后面的值，赋值给default --> 所以可以将一个值写在 export default 之后
export default [
  {
    path: '/registrationReaction',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/factory/reactionRegistration/index.vue'),
        name: 'reactionRegistrationIndex',
        meta: { title: '登记反应', icon: 'dashboard', affix: true }
      }
    ]
  },
]