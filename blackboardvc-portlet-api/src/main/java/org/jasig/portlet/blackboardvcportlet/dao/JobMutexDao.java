package org.jasig.portlet.blackboardvcportlet.dao;

import org.jasig.portlet.blackboardvcportlet.data.JobMutex;

public interface JobMutexDao {
  
  JobMutex getJobMutex(String id);
  /**
   * checks if its in use, then puts it in use if not
   * @param id the job id
   * @param serverName the current servername
   * @return returns true if started successfully, false if it failed
   */
  boolean startMutex(String id, String serverName);
  
  /**
   * Stops the mutex. Puts an end date on the job id
   * @param id the job to stop
   * @return true if stopped successfully
   */
  boolean stopMutex(String id);
}
