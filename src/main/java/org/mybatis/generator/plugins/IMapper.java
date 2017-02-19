package org.mybatis.generator.plugins;

import java.util.List;

/**
 * 通用IMapper<M, E, ID>
 * M:实体类
 * E:Example
 * ID:主键的变量类型
 *
 * @author orange1438
 *         github: github.com/orange1438
 *         data: 2017/02/19 21:39
 */
public interface IMapper<M, E, ID> {
    /**
     * 查询数量
     *
     * @param example 条件对象
     */
    long countByExample(E example);

    /**
     * 根据条件删除
     *
     * @param example 条件对象
     */
    int deleteByExample(E example);

    /**
     * 根据ID删除
     *
     * @param id 主键ID
     */
    int deleteByPrimaryKey(ID id);

    /**
     * 添加对象所有字段
     *
     * @param record 插入字段对象(必须含ID）
     */
    int insert(M record);

    /**
     * 添加对象对应字段
     *
     * @param record 插入字段对象(必须含ID）
     */
    int insertSelective(M record);

    /**
     * 根据条件查询（包含二进制大对象）
     *
     * @param example 条件对象
     */
    List<M> selectByExampleWithBLOBs(E example);

    /**
     * 根据条件查询（二进制大对象）
     *
     * @param example 条件对象
     */
    List<M> selectByExample(E example);

    /**
     * 根据ID查询
     *
     * @param id 主键ID
     */
    M selectByPrimaryKey(ID id);

    /**
     * 根据ID修改对应字段
     *
     * @param record 修改字段对象(必须含ID）
     */
    int updateByPrimaryKeySelective(M record);

    /**
     * 根据ID修改字段（包含二进制大对象）
     *
     * @param record 修改字段对象(必须含ID）
     */
    int updateByPrimaryKeyWithBLOBs(M record);

    /**
     * 根据ID修改所有字段(必须含ID）
     *
     * @param record 修改字段对象(必须含ID）
     */
    int updateByPrimaryKey(M record);
}
