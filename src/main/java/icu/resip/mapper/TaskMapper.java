package icu.resip.mapper;

import icu.resip.entity.TaskVo;
import icu.resip.qo.TaskQo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Peng
 * @Date 2022/4/16
 */
@Repository
public interface TaskMapper {

    /**
     * 按条件查询take_order表数据
     * @param taskQo
     * @return
     */
    List<TaskVo> listAll(TaskQo taskQo);

}
