export function getShortCut() {
  return new Promise((res, rej) => {
    try {
      const data = JSON.parse(localStorage.getItem("shortCut") || "[]");
      res(data);
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
