export function processMenuData(dishes) {
  return dishes
    .sort((a, b) => a.type - b.type)
    .reduce((arr, cur) => {
      if (cur.dishesId > 0 && cur.type !== "0") {
        cur.igdList.forEach(igd => {
          let lastTypeHit = false,
            lastNameHit = false;
          if (arr.length > 0) {
            // 倒推，找到第一个出现的位置
            lastTypeHit = arr[arr.length - 1].type === cur.type;
            if (lastTypeHit) {
              let typePos = arr.length - 1;
              for (let i = typePos; i >= 0; i--) {
                if (arr[i].type !== cur.type) {
                  break;
                }
                typePos = i;
              }
              arr[typePos].typeSpan.rowspan += 1;
            }
            lastNameHit =
              arr[arr.length - 1].dishesId === cur.dishesId &&
              arr[arr.length - 1].type === cur.type;
            if (lastNameHit) {
              let namePos = arr.length - 1;
              for (let i = namePos; i >= 0; i--) {
                if (arr[i].dishesId !== cur.dishesId) {
                  break;
                }
                namePos = i;
              }
              arr[namePos].nameSpan.rowspan += 1;
            }
          }

          // console.log(cur);
          arr.push({
            id: cur.id,
            dishesId: cur.dishesId,
            menuId: cur.menuId,
            name: cur.name,
            type: cur.type,
            isMain: cur.isMain,
            methods: cur.methods,
            remark: cur.remark,
            igdId: igd.id,
            igdType: igd.type,
            igdName: igd.name,
            proteinRatio: igd.proteinRatio,
            fatRatio: igd.fatRatio,
            carbonRatio: igd.carbonRatio,
            rec: igd.rec,
            notRec: igd.notRec,
            weight: igd.weight,
            cusWeight: igd.cusWeight,
            cusUnit: igd.cusUnit,
            typeSpan: lastTypeHit
              ? {
                  rowspan: 0,
                  colspan: 0
                }
              : {
                  rowspan: 1,
                  colspan: 1
                },
            nameSpan: lastNameHit
              ? {
                  rowspan: 0,
                  colspan: 0
                }
              : {
                  rowspan: 1,
                  colspan: 1
                }
          });
        });
      }
      return arr;
    }, []);
}
