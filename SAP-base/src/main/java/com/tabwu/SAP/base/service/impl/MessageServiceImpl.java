package com.tabwu.SAP.base.service.impl;

import com.tabwu.SAP.base.entity.Message;
import com.tabwu.SAP.base.mapper.MessageMapper;
import com.tabwu.SAP.base.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tabwu
 * @since 2022-06-19
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
