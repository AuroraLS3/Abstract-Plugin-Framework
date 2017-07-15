/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.djrapitops.plugin.task;

/**
 *
 * @author Rsl1122
 */
public interface ITask<T> {

    public int getTaskId();

    public boolean isSync();

    public void cancel();
    
    public T getWrappedTask();
}
