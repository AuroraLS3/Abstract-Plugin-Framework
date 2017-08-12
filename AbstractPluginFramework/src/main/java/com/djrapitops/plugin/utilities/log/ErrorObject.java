package com.djrapitops.plugin.utilities.log;

import com.djrapitops.plugin.utilities.Verify;

import java.util.List;

public class ErrorObject {

    private final String exception;
    private List<String> stackTrace;

    public ErrorObject(List<String> stackTrace) throws IllegalStateException {
        if (stackTrace == null) {
            throw new NullPointerException("Stack trace is null");
        }
        if (stackTrace.isEmpty()) {
            throw new IllegalStateException("Stack Trace was empty");
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

    @Override
    public String toString() {
        return "ErrorObject{\n" +
                "exception='" + exception + "\',\n" +
                "stackTrace=" + stackTrace.toString().replace(",", "\n") +
                '}';
    }
}
