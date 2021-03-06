package org.jasig.portlet.blackboardvcportlet.dao.ws.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;

import org.jasig.portlet.blackboardvcportlet.dao.ws.MultimediaWSDao;
import org.jasig.portlet.blackboardvcportlet.dao.ws.SessionWSDao;
import org.jasig.portlet.blackboardvcportlet.security.SecurityExpressionEvaluator;
import org.jasig.springframework.mockito.MockitoFactoryBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.elluminate.sas.BlackboardMultimediaResponse;
import com.elluminate.sas.BlackboardSessionResponse;

public class MultimediaWSDaoTestBase extends AbstractWSIT {
	
	@Autowired
	SecurityExpressionEvaluator securityExpressionEvaluator;

	MultimediaWSDao dao;
	
	SessionWSDao sessionDao;
	
	List <Long> multimedias = new ArrayList<Long>() ;
	
	@Autowired
	public void setSessionDao(SessionWSDao dao) {
		this.sessionDao = dao;
	}
	
	@Autowired
	public void setMMWSDao(MultimediaWSDao dao) {
		this.dao = dao;
	}
	
	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		MockitoFactoryBean.resetAllMocks();
		when(securityExpressionEvaluator.authorize(any(String.class))).thenReturn(true);
		when(securityExpressionEvaluator.authorize(any(String.class),any(Map.class))).thenReturn(true);
		form = buildSession();
		user = buildUser();
		session = sessionDao.createSession(user, form);
	}
	
	@After
	public void after() {
		List<BlackboardSessionResponse> sessions = sessionDao.getSessions(null, null, null, user.getEmail(), null, null, null);
		for(BlackboardSessionResponse session : sessions ) {
			List<BlackboardMultimediaResponse> repositoryMultimedias = dao.getSessionMultimedias(session.getSessionId());
			for(BlackboardMultimediaResponse multimedia : repositoryMultimedias) {
				dao.removeSessionMultimedia(session.getSessionId(),multimedia.getMultimediaId());
			}
			sessionDao.deleteSession(session.getSessionId());			
		}
		
		for(Long multimediaId : multimedias) {
			dao.removeRepositoryMultimedia(multimediaId);
		}
		
	}
	
	@Test
	public void getRepositoryMultimediasTest () throws Exception {
		List<BlackboardMultimediaResponse> repositoryMultimedias = dao.getRepositoryMultimedias(user.getUniqueId(), null, null);
		assertEquals(repositoryMultimedias.size(),0);
	}
    

	@Test
	public void getSessionRepositoryMultimediasTest()  throws Exception {
		List<BlackboardMultimediaResponse> sessionRepositoryMultimedias = dao.getSessionMultimedias(session.getSessionId());
		assertTrue(sessionRepositoryMultimedias.size() == 0);
	}
	
	@Test
	public void uploadRepositoryMultimediaTest()  throws Exception {
		BlackboardMultimediaResponse uploadRepositoryMultimedia = createRepoMultimedia();
		assertNotNull(uploadRepositoryMultimedia);
	}
	
	@Test
	public void createSessionMultimediaTest() throws Exception {
		InputStream is = new ByteArrayInputStream("TEST2".getBytes());
        ByteArrayDataSource rawData = new ByteArrayDataSource(is,"video/mpeg");
		DataHandler dataHandler = new DataHandler(rawData);
		BlackboardMultimediaResponse createSessionMultimedia = dao.createSessionMultimedia(session.getSessionId(), user.getEmail(), "test.mpeg", "aliens",dataHandler);
		
		multimedias.add(createSessionMultimedia.getMultimediaId());
		
		List<BlackboardMultimediaResponse> repositoryMultimedias = dao.getSessionMultimedias(session.getSessionId());
		assertNotNull(repositoryMultimedias);
		assertTrue(repositoryMultimedias.size() == 1);
	}
	
	@Test
	public void linkSessionToMultimediaTest() throws Exception {
		BlackboardMultimediaResponse uploadRepositoryMultimedia = createRepoMultimedia();
		assertNotNull(uploadRepositoryMultimedia);
		
		boolean linkSessionToMultimedia = dao.linkSessionToMultimedia(session.getSessionId(), uploadRepositoryMultimedia.getMultimediaId());
		
		assertTrue(linkSessionToMultimedia);
		
		List<BlackboardMultimediaResponse> repositoryMultimedias = dao.getSessionMultimedias(session.getSessionId());
		assertNotNull(repositoryMultimedias);
		assertTrue(repositoryMultimedias.size() == 1);
		
	}
	
	@Test
	public void removeRepositoryMultimediaTest() throws Exception {
		BlackboardMultimediaResponse createRepoMultimedia = createRepoMultimedia();
		assertNotNull(createRepoMultimedia);
		
		boolean removeRepositoryMultimedia = dao.removeRepositoryMultimedia(createRepoMultimedia.getMultimediaId());
		
		assertTrue(removeRepositoryMultimedia);
	}
	
	@Test
	public void removeSessionMultimediaTest() throws Exception {
		BlackboardMultimediaResponse multimedia = createRepoMultimedia();
		assertNotNull(multimedia);
		
		assertTrue(dao.linkSessionToMultimedia(session.getSessionId(), multimedia.getMultimediaId()));
		
		dao.removeSessionMultimedia(session.getSessionId(), multimedia.getMultimediaId());
		
	}
	
	protected BlackboardMultimediaResponse createRepoMultimedia() throws Exception {
		InputStream is = new ByteArrayInputStream("fdsdfsfsdadsfasfda".getBytes());
        ByteArrayDataSource rawData = new ByteArrayDataSource(is,"video/mpeg");
		DataHandler dataHandler = new DataHandler(rawData);

        BlackboardMultimediaResponse uploadRepositoryMultimedia = dao.uploadRepositoryMultimedia(user.getEmail(), "test.mpeg", "aliens",dataHandler);
        multimedias.add(uploadRepositoryMultimedia.getMultimediaId());
        return uploadRepositoryMultimedia;
	}
}
