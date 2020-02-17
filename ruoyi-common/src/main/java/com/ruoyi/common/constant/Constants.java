package com.ruoyi.common.constant;

/**
 * 通用常量信息
 *
 * @author ruoyi
 */
public class Constants {
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 未删除的枚举值
     */
    public static final Integer DEL_STATUS_OK = 0;

    /**
     * 已删除的枚举值
     */
    public static final Integer DEL_STATUS_NO = 1;

    public static final String SPLIT_FILE_UUID = "2fd872b8933a4ea7bee38e1c7bd96feb";

    //新增业务操作码
    public static final String SERVEICE_OPERATE_ADD = "add" ;

    //修改业务操作码
    public static final String SERVEICE_OPERATE_UPDATE = "update" ;
}
