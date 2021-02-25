const getters = {
  sidebar: state => state.app.sidebar,
  size: state => state.app.size,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  introduction: state => state.user.introduction,
  roles: state => state.user.roles,
  permissions: state => state.user.permissions,
  userId: state => state.user.userId,
  permission_routes: state => state.permission.routes,
  //
  nutritionistIdOptions: state => state.global.nutritionistIdOptions,
  nutriAssisIdOptions: state => state.global.nutriAssisIdOptions,
  preSaleIdOptions: state => state.global.preSaleIdOptions,
  onSaleIdOptions: state => state.global.onSaleIdOptions,
  afterSaleIdOptions: state => state.global.afterSaleIdOptions,
  plannerIdOptions: state => state.global.plannerIdOptions,
  plannerAssisIdOptions: state => state.global.plannerAssisIdOptions,
  operatorIdOptions: state => state.global.operatorIdOptions,
  operatorAssisIdOptions: state => state.global.operatorAssisIdOptions
};
export default getters;
