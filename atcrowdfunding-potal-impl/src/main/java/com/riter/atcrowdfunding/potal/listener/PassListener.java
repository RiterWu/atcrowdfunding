package com.riter.atcrowdfunding.potal.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * 实名认证审核通过执行的操作
 */
public class PassListener implements ExecutionListener {

    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("PassListener");
    }
}
