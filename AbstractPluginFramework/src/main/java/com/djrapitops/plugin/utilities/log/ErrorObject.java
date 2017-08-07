package com.djrapitops.plugin.utilities.log;

import com.djrapitops.plugin.utilities.Verify;

import java.util.List;

public class ErrorObject {

    private final String exception;
    private List<String> stackTrace;

    public ErrorObject(List<String> stackTrace) throws IllegalStateException {
        if (Verify.isEmpty(stackTrace)) {
            throw new IllegalStateException("Stack Trace was null");
        }
        this.exception = stackTrace.get(0);
        this.stackTrace = stackTrace;
    }

    public String getException() {
        return exception;
    }

    public List<String> getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorObject that = (ErrorObject) o;

        if (!exception.equals(that.exception)) return false;
        return stackTrace.equals(that.stackTrace);
    }

    @Override
    public int hashCode() {
        int result = exception.hashCode();
        result = 31 * result + stackTrace.hashCode();
        return result;
    }
}
