package com.ruoyi.system.domain.model.credit;

public class Records {

    private String COURT_NAME;
    private String CARDNUMBER;
    private String CARDTYPE;
    private int RN;
    private String INAME;
    private String CASE_CODE;
    public void setCOURT_NAME(String COURT_NAME) {
         this.COURT_NAME = COURT_NAME;
     }
     public String getCOURT_NAME() {
         return COURT_NAME;
     }

    public void setCARDNUMBER(String CARDNUMBER) {
         this.CARDNUMBER = CARDNUMBER;
     }
     public String getCARDNUMBER() {
         return CARDNUMBER;
     }

    public void setCARDTYPE(String CARDTYPE) {
         this.CARDTYPE = CARDTYPE;
     }
     public String getCARDTYPE() {
         return CARDTYPE;
     }

    public void setRN(int RN) {
         this.RN = RN;
     }
     public int getRN() {
         return RN;
     }

    public void setINAME(String INAME) {
         this.INAME = INAME;
     }
     public String getINAME() {
         return INAME;
     }

    public void setCASE_CODE(String CASE_CODE) {
         this.CASE_CODE = CASE_CODE;
     }
     public String getCASE_CODE() {
         return CASE_CODE;
     }

}