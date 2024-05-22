package com.jjpt.business.mapper;

import java.util.List;
import com.jjpt.business.domain.ElUserBook;

/**
 * 错题本Mapper接口
 * 
 * @author 卢亚峰
 * @date 2024-05-22
 */
public interface ElUserBookMapper 
{
    /**
     * 查询错题本
     * 
     * @param id 错题本主键
     * @return 错题本
     */
    public ElUserBook selectElUserBookById(String id);

    /**
     * 查询错题本列表
     * 
     * @param elUserBook 错题本
     * @return 错题本集合
     */
    public List<ElUserBook> selectElUserBookList(ElUserBook elUserBook);

    /**
     * 新增错题本
     * 
     * @param elUserBook 错题本
     * @return 结果
     */
    public int insertElUserBook(ElUserBook elUserBook);

    /**
     * 修改错题本
     * 
     * @param elUserBook 错题本
     * @return 结果
     */
    public int updateElUserBook(ElUserBook elUserBook);

    /**
     * 删除错题本
     * 
     * @param id 错题本主键
     * @return 结果
     */
    public int deleteElUserBookById(String id);

    /**
     * 批量删除错题本
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteElUserBookByIds(String[] ids);

    ElUserBook selectElUserBookInfo(ElUserBook queryElUserBook);
}
