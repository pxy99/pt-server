package icu.resip.service.impl;

import icu.resip.entity.TaskVo;
import icu.resip.mapper.TaskMapper;
import icu.resip.qo.TaskQo;
import icu.resip.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Peng
 * @Date 2022/4/16
 */
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private TaskMapper taskMapper;

    @Autowired
    public void setTaskMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskVo> list(TaskQo taskQo) {
        // 拼接order by子句
        StringBuilder sb = new StringBuilder("order by ");

        if (taskQo.getPrice() != null && taskQo.getTime() != null) {
            if (taskQo.getPrice() && taskQo.getTime()) {
                sb.append("ta.money asc, ta.create_time asc");
            } else if (taskQo.getPrice() && !taskQo.getTime()) {
                sb.append("ta.money asc, ta.create_time desc");
            } else if (!taskQo.getPrice() && taskQo.getTime()){
                sb.append("ta.money desc, ta.create_time asc");
            } else if (!taskQo.getPrice() && !taskQo.getTime()){
                sb.append("ta.money desc, ta.create_time desc");
            }
        } else if (taskQo.getPrice() != null || taskQo.getTime() != null) {
            if (taskQo.getPrice() != null) {
                if (taskQo.getPrice()) {
                    sb.append("ta.money asc");
                } else {
                    sb.append("ta.money desc");
                }
            } else if (taskQo.getTime() != null) {
                if (taskQo.getTime()) {
                    sb.append("ta.create_time asc");
                } else {
                    sb.append("ta.create_time desc");
                }
            }
        } else {
            sb.append(" ta.create_time desc");
        }
        taskQo.setOrderBy(sb.toString());

        log.info("order by 子句---{}", sb);

        return taskMapper.listAll(taskQo);
    }

}
