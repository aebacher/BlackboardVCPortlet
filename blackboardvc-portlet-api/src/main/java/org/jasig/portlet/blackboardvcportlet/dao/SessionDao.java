package org.jasig.portlet.blackboardvcportlet.dao;

import java.util.Set;

import org.jasig.portlet.blackboardvcportlet.data.ConferenceUser;
import org.jasig.portlet.blackboardvcportlet.data.Session;
import org.jasig.portlet.blackboardvcportlet.data.SessionRecording;

import com.elluminate.sas.BlackboardSessionResponse;

public interface SessionDao {
    Set<ConferenceUser> getSessionChairs(Session session);
    
    Set<ConferenceUser> getSessionNonChairs(Session session);
    
    Set<SessionRecording> getSessionRecordings(Session session);
    
    Set<Session> getAllSessions();
    
    Session getSession(long sessionId);
    
    Session getSessionByBlackboardId(long bbSessionId);
    
    Session createSession(BlackboardSessionResponse sessionResponse, String guestUrl);
    
    Session updateSession(BlackboardSessionResponse sessionResponse);
    
    void deleteSession(Session session);
}