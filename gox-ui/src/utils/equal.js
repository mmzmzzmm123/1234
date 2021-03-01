export function isObjEqual(o1, o2) {
  let props1 = Object.getOwnPropertyNames(o1);
  let props2 = Object.getOwnPropertyNames(o2);
  if (props1.length !== props2.length) {
    return false;
  }
  for (let i = 0, max = props1.length; i < max; i++) {
    let propName = props1[i];
    if (o1[propName] !== o2[propName]) {
      return false;
    }
  }
  return true;
}

export function arrayContainObj(array, obj) {
  array.forEach(i => {
    return isObjEqual(i, obj);
  })
}
