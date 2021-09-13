function DictTableColumnFormatter(labelDict) {
  return function(row, column, cellValue, index) {
    return labelDict[cellValue]
  }
}

export {
  DictTableColumnFormatter,
}
