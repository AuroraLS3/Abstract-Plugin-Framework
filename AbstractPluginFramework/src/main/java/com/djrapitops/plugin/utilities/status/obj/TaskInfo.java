package com.djrapitops.plugin.utilities.status.obj;

import java.util.Objects;

/**
 * Class containing info about a Task that is being run.
 *
 * @author Rsl1122
 * @since 2.0.0
 */
public class TaskInfo {

    private int id;
    private final String runType;
    private final String status;
    private final String name;

    public TaskInfo(String name, boolean sync, String status) {
        this.runType = sync ? "Sync." : "Async.";
        this.status = status;
        this.name = name;
    }

    public TaskInfo(String name, boolean sync, String status, int id) {
        this.runType = sync ? "Sync." : "Async.";
        this.status = status;
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRunType() {
        return runType;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.runType);
        hash = 79 * hash + Objects.hashCode(this.status);
        hash = 79 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaskInfo other = (TaskInfo) obj;
        return this.id == other.id
                && Objects.equals(this.runType, other.runType)
                && Objects.equals(this.status, other.status)
                && Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "Task" + (id != -1 ? " " + id : "") + ": " + name + ", " + runType;
    }

}
