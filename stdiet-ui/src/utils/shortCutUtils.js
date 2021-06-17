export function getShortCut(key) {
  return new Promise((res, rej) => {
    try {
      const data = JSON.parse(localStorage.getItem("shortCut") || "[]");
      //关键字检索
      if(key != undefined && key != null && key != ""){
          const resultData = [];
          data.forEach((item,index) => {
              if(item.name.indexOf(key) != -1 || (item.className != undefined && item.className != null && item.className.indexOf(key) != -1)){
                resultData.push(item);
              }
          });
          res(resultData);
      }else{
        res(data);
      }
    } catch (error) {
      rej(error);
    }
  });
}

export async function addShortCut(data) {
  const shortCutList = await getShortCut();
  return new Promise((res, rej) => {
    try {
      shortCutList.splice(0, 0, data);
      localStorage.setItem("shortCut", JSON.stringify(shortCutList));
      res();
    } catch (error) {
      rej(error);
    }
  });
}

export async function removeShortCut(id) {
  const shortCutList = await getShortCut();
  return new Promise((res, rej) => {
    try {
      const newShortCutList = shortCutList.filter(obj => obj.id !== id);
      localStorage.setItem("shortCut", JSON.stringify(newShortCutList));
      res();
    } catch (error) {
      rej(error);
    }
  });
}

export async function removeMuchShortCut(ids) {
  const shortCutList = await getShortCut();
  return new Promise((res, rej) => {
    try {
      const newShortCutList = shortCutList.filter(obj => ids.indexOf(obj.id) == -1);
      localStorage.setItem("shortCut", JSON.stringify(newShortCutList));
      res();
    } catch (error) {
      rej(error);
    }
  });
}

export async function editShortCut(data) {
  const shortCutList = await getShortCut();
  return new Promise((res, rej) => {
    try {
      const tarObj = shortCutList.find(obj => obj.id === data.id);
      Object.keys(data).forEach(key => {
        tarObj[key] = data[key];
      });
      localStorage.setItem("shortCut", JSON.stringify(shortCutList));
      res();
    } catch (error) {
      rej(error);
    }
  });
}
