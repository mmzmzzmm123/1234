export function getProcessMenuData(menuData) {
  // const igdPlanData = {};

  const menuList = menuData.reduce((arr, cur) => {
    if (
      cur.dishesId > -1 &&
      cur.name &&
      cur.igdList.length > 0 &&
      cur.type !== "0"
    ) {
      arr.push({
        id: cur.id,
        dishesId: cur.dishesId,
        name: cur.name,
        menuId: cur.menuId,
        methods: cur.methods,
        remark: cur.remark,
        type: cur.type,
        isMain: cur.isMain,
        igdList: cur.igdList.reduce((igdArr, igdData) => {
          if (igdData.id > 0) {
            const tarDetail = cur.detail.find(obj => obj.id === igdData.id);
            if (tarDetail) {
              igdArr.push({
                id: igdData.id,
                name: igdData.name,
                carbonRatio: igdData.carbonRatio,
                fatRatio: igdData.fatRatio,
                proteinRatio: igdData.proteinRatio,
                cusUnit: tarDetail.cus_unit,
                cusWeight: tarDetail.cus_weight,
                weight: parseFloat(tarDetail.weight),
                notRec: igdData.notRec,
                rec: igdData.rec,
                type: igdData.type
              });
            }
          }
          return igdArr;
        }, [])
      });
    }
    return arr;
  }, []);
  return menuList;
}
