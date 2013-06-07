package com.hnk.aws.service;

import com.hnk.aws.model.UserLog;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional("logTx")
public interface UserLogService {
    public List<UserLog> list();
}
