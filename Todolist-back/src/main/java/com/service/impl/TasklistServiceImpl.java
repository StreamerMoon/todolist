package com.service.impl;

import com.entity.Tasklist;
import com.mapper.TasklistMapper;
import com.service.TasklistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2021-12-19
 */
@Service
public class TasklistServiceImpl extends ServiceImpl<TasklistMapper, Tasklist> implements TasklistService {

}
