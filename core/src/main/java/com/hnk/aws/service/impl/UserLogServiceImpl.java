package com.hnk.aws.service.impl;

import com.hnk.aws.model.UserLog;
import com.hnk.aws.repository.UserLogRepository;
import com.hnk.aws.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserLogServiceImpl implements UserLogService {
    @Autowired
    private UserLogRepository userLogRepository;
    @Override
    public List<UserLog> list() {
        List<UserLog> userLogs = new ArrayList<UserLog>();
        Iterator<UserLog> logIterator = userLogRepository.findAll().iterator();
        while(logIterator.hasNext()) {
            userLogs.add(logIterator.next());
        }
        return userLogs;
    }
}
