package com.ruoyi.system.domain.model.credit;
import java.util.List;

public class ColumnShowList {

    private int current;
    private int total;
    private int size;
    private List<Records> records;
    public void setCurrent(int current) {
         this.current = current;
     }
     public int getCurrent() {
         return current;
     }

    public void setTotal(int total) {
         this.total = total;
     }
     public int getTotal() {
         return total;
     }

    public void setSize(int size) {
         this.size = size;
     }
     public int getSize() {
         return size;
     }

    public void setRecords(List<Records> records) {
         this.records = records;
     }
     public List<Records> getRecords() {
         return records;
     }

}