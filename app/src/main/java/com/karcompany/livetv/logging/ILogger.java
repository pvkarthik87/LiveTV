/**
 * pvkarthik
 */
package com.karcompany.livetv.logging;

public interface ILogger {
    void log(int priority, String tag, String msg);

    void log(int priority, String tag, String msg, Throwable tr);
}
