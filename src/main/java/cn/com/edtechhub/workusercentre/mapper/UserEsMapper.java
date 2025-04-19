package cn.com.edtechhub.workusercentre.mapper;

import cn.com.edtechhub.workusercentre.model.entity.UserEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 用户映射(ES)
 *
 * @author <a href="https://github.com/limou3434">limou3434</a>
 */
public interface UserEsMapper extends ElasticsearchRepository<UserEs, Long> {

}
