/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.portlet.blackboardvcportlet.dao.impl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.Type;
import org.jasig.portlet.blackboardvcportlet.data.BlackboardSession;
import org.jasig.portlet.blackboardvcportlet.data.BlackboardUser;
import org.joda.time.DateTime;

@Entity
@Table(name = "VC2_SESSION")
@SequenceGenerator(
        name="VC2_SESSION_GEN",
        sequenceName="VC2_SESSION_SEQ",
        allocationSize=10
    )
@TableGenerator(
        name="VC2_SESSION_GEN",
        pkColumnValue="VC2_SESSION",
        allocationSize=10
    )
@NaturalIdCache
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BlackboardSessionImpl implements BlackboardSession {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "VC2_SESSION_GEN")
    @Column(name = "SESSION_ID", nullable = false)
    private final long sessionId;
    
    @Version
    @Column(name = "ENTITY_VERSION", nullable = false)
    private final long entityVersion;
    
    @NaturalId
    @Column(name="BB_SESSION_ID", nullable = false)
    private final long bbSessionId;
    
    @ManyToOne(targetEntity = BlackboardUserImpl.class, optional = false)
    @JoinColumn(name = "CREATOR", nullable = false)
    private final BlackboardUser creator;
    
    @Column(name="SESSION_NAME", nullable = false, length = 1000)
    private String sessionName;
    
    @Column(name="START_TIME", nullable = false)
    @Type(type="dateTime")
    private DateTime startTime;
    
    @Column(name="END_TIME", nullable = false)
    @Type(type="dateTime")
    private DateTime endTime;
    
    @Column(name="BOUNDARY_TIME", nullable = false)
    private int boundaryTime;
    
    @Column(name="ACCESS_TYPE", nullable = false)
    private long accessType;
    
    @Column(name="RECORDINGS", nullable = false)
    private boolean recordings;
    
    @Column(name="CHAIR_NOTES", length = 4000)
    private String chairNotes;
    
    @Column(name="NON_CHAIR_NOTES", length = 4000)
    private String nonChairNotes;
    
    @ManyToMany(targetEntity = BlackboardUserImpl.class, fetch = FetchType.LAZY)
    @JoinTable(name="VC2_SESSION_CHAIRS",
        joinColumns= @JoinColumn(name="SESSION_ID"),
        inverseJoinColumns=@JoinColumn(name="USER_ID")
    )
    private Set<BlackboardUser> chairs = new HashSet<BlackboardUser>(0);
    
    @ManyToMany(targetEntity = BlackboardUserImpl.class, fetch = FetchType.LAZY)
    @JoinTable(name="VC2_SESSION_NONCHAIRS",
        joinColumns= @JoinColumn(name="SESSION_ID"),
        inverseJoinColumns=@JoinColumn(name="USER_ID")
    )
    private Set<BlackboardUser> nonChairs = new HashSet<BlackboardUser>(0);
    
    @Column(name="OPEN_CHAIR", nullable = false)
    private boolean openChair;
    
    @Column(name="MUST_BE_SUPERVISED", nullable = false)
    private boolean mustBeSupervised;
    
    @Column(name="RECORDING_MODE", nullable = false)
    private long recordingMode;

    @Column(name="MAX_TALKERS", nullable = false)
    private int maxTalkers;
    
    @Column(name="MAX_CAMERAS", nullable = false)
    private int maxCameras;
    
    @Column(name="RAISE_HAND_ON_ENTER", nullable = false)
    private boolean raiseHandOnEnter;
    
    @Column(name="RESERVE_SEATS", nullable = false)
    private int reserveSeats;
    
    @Column(name="VERSION_ID", nullable = false)
    private long versionId;
    
    @Column(name="ALLOW_IN_SESSION_INVITES", nullable = false)
    private boolean allowInSessionInvites = true;
    
    @Column(name="HIDE_PARTICIPANT_NAMES", nullable = false)
    private boolean hideParticipantNames = true;
    
    @Column(name="LAST_UPDATED", nullable = false)
    @Type(type = "dateTime")
    private DateTime lastUpdated;
    
    /**
     * needed by hibernate
     */
    @SuppressWarnings("unused")
    private BlackboardSessionImpl() {
        this.sessionId = -1;
        this.entityVersion = -1;
        this.bbSessionId = -1;
        this.creator = null;
    }
    
    BlackboardSessionImpl(long bbSessionId, BlackboardUser creator) {
        Validate.notNull(creator, "creator cannot be null");
            
        this.sessionId = -1;
        this.entityVersion = -1;
        this.bbSessionId = bbSessionId;
        this.creator = creator;
    }

    /**
     * Used to keep lastUpdated up to date
     */
    @PreUpdate
    @PrePersist
    protected final void onUpdate() {
        lastUpdated = DateTime.now();
    }
    
    @Override
    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    @Override
    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public int getBoundaryTime() {
        return boundaryTime;
    }

    public void setBoundaryTime(int boundaryTime) {
        this.boundaryTime = boundaryTime;
    }

    @Override
    public long getAccessType() {
        return accessType;
    }

    public void setAccessType(long accessType) {
        this.accessType = accessType;
    }

    @Override
    public boolean isRecordings() {
        return recordings;
    }

    public void setRecordings(boolean recordings) {
        this.recordings = recordings;
    }

    @Override
    public String getChairNotes() {
        return chairNotes;
    }

    public void setChairNotes(String chairNotes) {
        this.chairNotes = chairNotes;
    }

    @Override
    public String getNonChairNotes() {
        return nonChairNotes;
    }

    public void setNonChairNotes(String nonChairNotes) {
        this.nonChairNotes = nonChairNotes;
    }

    Set<BlackboardUser> getChairs() {
        return chairs;
    }

    Set<BlackboardUser> getNonChairs() {
        return nonChairs;
    }

    @Override
    public boolean isOpenChair() {
        return openChair;
    }

    public void setOpenChair(boolean openChair) {
        this.openChair = openChair;
    }

    @Override
    public boolean isMustBeSupervised() {
        return mustBeSupervised;
    }

    public void setMustBeSupervised(boolean mustBeSupervised) {
        this.mustBeSupervised = mustBeSupervised;
    }

    @Override
    public long getRecordingMode() {
        return recordingMode;
    }

    public void setRecordingMode(long recordingMode) {
        this.recordingMode = recordingMode;
    }

    @Override
    public int getMaxTalkers() {
        return maxTalkers;
    }

    public void setMaxTalkers(int maxTalkers) {
        this.maxTalkers = maxTalkers;
    }

    public int getMaxCameras() {
        return maxCameras;
    }

    public void setMaxCameras(int maxCameras) {
        this.maxCameras = maxCameras;
    }

    @Override
    public boolean isRaiseHandOnEnter() {
        return raiseHandOnEnter;
    }

    public void setRaiseHandOnEnter(boolean raiseHandOnEnter) {
        this.raiseHandOnEnter = raiseHandOnEnter;
    }

    @Override
    public int getReserveSeats() {
        return reserveSeats;
    }

    public void setReserveSeats(int reserveSeats) {
        this.reserveSeats = reserveSeats;
    }

    @Override
    public long getVersionId() {
        return versionId;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    @Override
    public boolean isAllowInSessionInvites() {
        return allowInSessionInvites;
    }

    public void setAllowInSessionInvites(boolean allowInSessionInvites) {
        this.allowInSessionInvites = allowInSessionInvites;
    }

    @Override
    public boolean isHideParticipantNames() {
        return hideParticipantNames;
    }

    public void setHideParticipantNames(boolean hideParticipantNames) {
        this.hideParticipantNames = hideParticipantNames;
    }

    @Override
    public long getSessionId() {
        return sessionId;
    }

    @Override
    public long getEntityVersion() {
        return entityVersion;
    }

    @Override
    public long getBbSessionId() {
        return bbSessionId;
    }

    @Override
    public BlackboardUser getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return "BlackboardSessionImpl [sessionId=" + sessionId + ", sessionName=" + sessionName + ", startTime=" + startTime + ", endTime=" + endTime + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (bbSessionId ^ (bbSessionId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlackboardSessionImpl other = (BlackboardSessionImpl) obj;
        if (bbSessionId != other.bbSessionId)
            return false;
        return true;
    }
}
