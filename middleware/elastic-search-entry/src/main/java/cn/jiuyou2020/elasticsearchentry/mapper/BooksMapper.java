package cn.jiuyou2020.elasticsearchentry.mapper;

import cn.jiuyou2020.elasticsearchentry.domain.Books;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 金木
 * @description 针对表【books】的数据库操作Mapper
 * @createDate 2024-03-02 16:40:27
 * @Entity cn.jiuyou2020.elasticsearchentry.domain.Books
 */
@Mapper
public interface BooksMapper extends BaseMapper<Books> {

    void insertBooksBatch(List<Books> booksList);
}




