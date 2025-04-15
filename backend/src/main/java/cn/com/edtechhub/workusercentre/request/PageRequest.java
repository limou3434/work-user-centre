package cn.com.edtechhub.workusercentre.request;

import lombok.Data;

/**
 * 分页请求
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private int pageCurrent = 1;

    /**
     * 页面大小
     */
    private int pageSize = Integer.MAX_VALUE;

    /**
     * 排序字段
     */
    private String sortField = "account";

    /**
     * 排序顺序
     */
    private String sortOrder = "ascend"; // "descend"

}
