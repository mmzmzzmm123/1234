package com.ruoyi.fin.domain.old;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户信息对象 userinfo
 * 
 * @author ruoyi
 * @date 2023-09-15
 */
public class Userinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 微信openid */
    private String openid;

    /** 用户账号(预留，用于以后脱离微信使用) */
    @Excel(name = "用户账号(预留，用于以后脱离微信使用)")
    private String loginid;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String uname;

    /** 微信昵称 */
    @Excel(name = "微信昵称")
    private String wxname;

    /** 登录密码 */
    @Excel(name = "登录密码")
    private String loginpwd;

    /** 手机号 */
    @Excel(name = "手机号")
    private String uphone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 记账提醒：0，1 */
    @Excel(name = "记账提醒：0，1")
    private String jztx;

    /** 自动备份：0，1 */
    @Excel(name = "自动备份：0，1")
    private String zdbf;

    /** 4位数字密码 */
    @Excel(name = "4位数字密码")
    private String pwd;

    /** 所有记账项目 */
    @Excel(name = "所有记账项目")
    private String proitems;

    /** 所有记账账号 */
    @Excel(name = "所有记账账号")
    private String accitems;

    /** 所有账本 */
    @Excel(name = "所有账本")
    private String bookitems;

    /** 所有记账分类 */
    @Excel(name = "所有记账分类")
    private String cateitems;

    /** 过期时间 年月日-创建日期加31天 */
    @Excel(name = "过期时间 年月日-创建日期加31天")
    private String expiredate;

    /** 最后记账日期 */
    @Excel(name = "最后记账日期")
    private String lastupdate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String wxicon;

    /** 个人独享数据库表key，findetail和findetail_all就合并在一起了 */
    @Excel(name = "个人独享数据库表key，findetail和findetail_all就合并在一起了")
    private String selfdbkey;

    /** 当前账本 */
    @Excel(name = "当前账本")
    private String currbookid;

    public void setOpenid(String openid) 
    {
        this.openid = openid;
    }

    public String getOpenid() 
    {
        return openid;
    }
    public void setLoginid(String loginid) 
    {
        this.loginid = loginid;
    }

    public String getLoginid() 
    {
        return loginid;
    }
    public void setUname(String uname) 
    {
        this.uname = uname;
    }

    public String getUname() 
    {
        return uname;
    }
    public void setWxname(String wxname) 
    {
        this.wxname = wxname;
    }

    public String getWxname() 
    {
        return wxname;
    }
    public void setLoginpwd(String loginpwd) 
    {
        this.loginpwd = loginpwd;
    }

    public String getLoginpwd() 
    {
        return loginpwd;
    }
    public void setUphone(String uphone) 
    {
        this.uphone = uphone;
    }

    public String getUphone() 
    {
        return uphone;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setJztx(String jztx) 
    {
        this.jztx = jztx;
    }

    public String getJztx() 
    {
        return jztx;
    }
    public void setZdbf(String zdbf) 
    {
        this.zdbf = zdbf;
    }

    public String getZdbf() 
    {
        return zdbf;
    }
    public void setPwd(String pwd) 
    {
        this.pwd = pwd;
    }

    public String getPwd() 
    {
        return pwd;
    }
    public void setProitems(String proitems) 
    {
        this.proitems = proitems;
    }

    public String getProitems() 
    {
        return proitems;
    }
    public void setAccitems(String accitems) 
    {
        this.accitems = accitems;
    }

    public String getAccitems() 
    {
        return accitems;
    }
    public void setBookitems(String bookitems) 
    {
        this.bookitems = bookitems;
    }

    public String getBookitems() 
    {
        return bookitems;
    }
    public void setCateitems(String cateitems) 
    {
        this.cateitems = cateitems;
    }

    public String getCateitems() 
    {
        return cateitems;
    }
    public void setExpiredate(String expiredate) 
    {
        this.expiredate = expiredate;
    }

    public String getExpiredate() 
    {
        return expiredate;
    }
    public void setLastupdate(String lastupdate) 
    {
        this.lastupdate = lastupdate;
    }

    public String getLastupdate() 
    {
        return lastupdate;
    }
    public void setWxicon(String wxicon) 
    {
        this.wxicon = wxicon;
    }

    public String getWxicon() 
    {
        return wxicon;
    }
    public void setSelfdbkey(String selfdbkey) 
    {
        this.selfdbkey = selfdbkey;
    }

    public String getSelfdbkey() 
    {
        return selfdbkey;
    }
    public void setCurrbookid(String currbookid) 
    {
        this.currbookid = currbookid;
    }

    public String getCurrbookid() 
    {
        return currbookid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("openid", getOpenid())
            .append("loginid", getLoginid())
            .append("uname", getUname())
            .append("wxname", getWxname())
            .append("loginpwd", getLoginpwd())
            .append("uphone", getUphone())
            .append("email", getEmail())
            .append("jztx", getJztx())
            .append("zdbf", getZdbf())
            .append("pwd", getPwd())
            .append("proitems", getProitems())
            .append("accitems", getAccitems())
            .append("bookitems", getBookitems())
            .append("cateitems", getCateitems())
            .append("expiredate", getExpiredate())
            .append("lastupdate", getLastupdate())
            .append("wxicon", getWxicon())
            .append("selfdbkey", getSelfdbkey())
            .append("currbookid", getCurrbookid())
            .toString();
    }
}
