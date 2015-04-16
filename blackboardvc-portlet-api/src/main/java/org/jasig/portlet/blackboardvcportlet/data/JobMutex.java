package org.jasig.portlet.blackboardvcportlet.data;

import org.joda.time.DateTime;

public interface JobMutex {
  //string id
  String getId();
  //string server name
  String getServerName();
  //date start time
  DateTime getStartTime();
  //date end time
  DateTime getEndTime();
}
