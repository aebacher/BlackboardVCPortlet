package org.jasig.portlet.blackboardvcportlet.dao.impl;

import org.jasig.jpa.BaseJpaDao;
import org.jasig.portlet.blackboardvcportlet.dao.JobMutexDao;
import org.jasig.portlet.blackboardvcportlet.data.JobMutex;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JobMutexDaoImpl  extends BaseJpaDao implements JobMutexDao {

  @Override
  public JobMutex getJobMutex(String id) {
    return this.getEntityManager().find(JobMutexImpl.class, id);
  }

  private boolean createOrUpdateMutex(JobMutex jm) {
    boolean created = false;
    JobMutexImpl obj = this.getEntityManager().find(JobMutexImpl.class, jm.getId());
    if(obj == null) {
      created = true;
      obj = new JobMutexImpl(jm.getId(), jm.getServerName());
    }
    obj.setServerName(jm.getServerName());
    obj.setEndTime(jm.getEndTime());
    obj.setStartTime(jm.getStartTime());
    
    this.getEntityManager().persist(obj);
    return created;
  }

  private boolean createOrUpdateMutex(String id, String serverName) {
    JobMutexImpl jm = new JobMutexImpl(id, serverName);
    jm.setStartTime(DateTime.now());
    return createOrUpdateMutex(jm);
  }

  @Override
  @Transactional
  public boolean startMutex(String id, String serverName) {
    JobMutexImpl obj = this.getEntityManager().find(JobMutexImpl.class, id);
    if(obj != null && obj.getEndTime() == null) {
      return false; //already in use
    } else {
      //take it
      createOrUpdateMutex(id, serverName);
      return true;
    }
  }

  @Override
  @Transactional
  public boolean stopMutex(String id) {
    JobMutexImpl obj = this.getEntityManager().find(JobMutexImpl.class, id);
    if(obj == null) {
      logger.warn("Tried to end a mutex that didn't exist");
      return false;
    }
    obj.setEndTime(DateTime.now());
    this.getEntityManager().persist(obj);
    return true;
  }

}
