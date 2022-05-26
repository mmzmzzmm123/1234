package com.ruoyi.system.domain.model.credit;
import java.util.List;

public class BlackRecord {

    private List<ColumnHeadList> columnHeadList;
    private ColumnShowList columnShowList;
    public void setColumnHeadList(List<ColumnHeadList> columnHeadList) {
         this.columnHeadList = columnHeadList;
     }
     public List<ColumnHeadList> getColumnHeadList() {
         return columnHeadList;
     }

    public void setColumnShowList(ColumnShowList columnShowList) {
         this.columnShowList = columnShowList;
     }
     public ColumnShowList getColumnShowList() {
         return columnShowList;
     }

}