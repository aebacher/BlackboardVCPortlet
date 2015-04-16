package org.jasig.portlet.blackboardvcportlet.dao.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.jasig.portlet.blackboardvcportlet.data.JobMutex;
import org.joda.time.DateTime;

@Entity
@Table(name = "VC2_JOB_MUTEX")
public class JobMutexImpl implements JobMutex, Serializable {
  private static final long serialVersionUID = 1L;
  
  @Id
  @Column(name = "ID", nullable = false, length = 30)
  private String id;
  
  @Column(name="SERVER_NAME", nullable = false)
  private String serverName;
  
  @Column(name="START_DTE", nullable = false)
  @Type(type = "dateTime")
  private DateTime startTime;
  
  @Column(name="END_DTE")
  @Type(type = "dateTime")
  private DateTime endTime;
  
  @Version
  @Column(name = "ENTITY_VERSION")
  private final long entityVersion;
  
  /**
   * needed by hibernate
   */
  @SuppressWarnings("unused")
  private JobMutexImpl() {
    entityVersion = -1;
  }
  
  JobMutexImpl(String id, String serverName) {
    this.id = id;
    entityVersion = -1;
    this.serverName = serverName;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getServerName() {
    return serverName;
  }

  @Override
  public DateTime getStartTime() {
    return startTime;
  }

  @Override
  public DateTime getEndTime() {
    return endTime;
  }

  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setEndTime(DateTime endTime) {
    this.endTime = endTime;
  }
  
  public void setStartTime(DateTime startTime) {
    this.startTime = startTime;
  }

}
