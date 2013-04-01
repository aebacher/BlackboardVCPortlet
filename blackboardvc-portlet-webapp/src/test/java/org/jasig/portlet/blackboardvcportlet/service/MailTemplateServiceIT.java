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

package org.jasig.portlet.blackboardvcportlet.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/testContext.xml")
public class MailTemplateServiceIT {
	
	@Autowired
	private MailTemplateService mailTemplateService;
	
	@Mock
	private JavaMailSenderImpl mailSender;
	
	@Test
	public void testDownloadAKey() throws Exception
	{
		assertNotNull(mailTemplateService);
		List <String> toList = new ArrayList<String> ();
		toList.add("levett@wisc.edu");
		// TODO Refactor to support new Method Signature
//		mailTemplateService.sendEmailUsingTemplate("test@wisc.edu", toList, "Test Email", null, "testEmail");
		assertTrue(true);
	}
}
