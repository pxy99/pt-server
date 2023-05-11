package icu.resip.service;

import icu.resip.entity.TaskVo;
import icu.resip.qo.TaskQo;

import java.util.List;

/**
 * @Author Peng
 * @Date 2022/4/16
 */
public interface TaskService {

    /**
     * 按条件查询任务列表
     * @param taskQo 查询条件
     * @return 符合条件的任务列表数据
     */
    List<TaskVo> list(TaskQo taskQo);
}
