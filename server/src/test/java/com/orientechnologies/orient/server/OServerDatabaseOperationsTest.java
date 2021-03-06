package com.orientechnologies.orient.server;

import com.orientechnologies.common.io.OIOUtils;
import com.orientechnologies.common.log.OLogManager;
import com.orientechnologies.orient.core.Orient;
import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.db.ODatabaseType;
import com.orientechnologies.orient.core.db.OrientDBConfig;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.server.config.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OServerDatabaseOperationsTest {

  private OServer server;

  @Before
  public void before()
      throws ClassNotFoundException, MalformedObjectNameException, InstanceAlreadyExistsException, NotCompliantMBeanException,
      MBeanRegistrationException, NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException,
      InstantiationException {
    OLogManager.instance().setConsoleLevel(Level.OFF.getName());
    OServerConfiguration conf = new OServerConfiguration();

    conf.handlers = new ArrayList<OServerHandlerConfiguration>();
    OServerUserConfiguration rootUser = new OServerUserConfiguration();
    rootUser.name = "root";
    rootUser.password = "root";
    rootUser.resources = "list";
    conf.users = new OServerUserConfiguration[] { rootUser };
    conf.properties = new OServerEntryConfiguration[] { new OServerEntryConfiguration("server.database.path", "target/databases") };
    server = new OServer();
    server.startup(conf);
    server.activate();
    ODocument securityConfig = new ODocument();
    securityConfig.fromJSON(OIOUtils.readStreamAsString(this.getClass().getClassLoader().getResourceAsStream("security.json")));
    server.getSecurity().reload(securityConfig);
  }

  @After
  public void after() {
    server.shutdown();
    Orient.instance().startup();
  }

  @Test
  public void testServerLoginDatabase() {
    server.serverLogin("root", "root", "list");
  }

  @Test
  public void testCreateOpenDatabase() {
    server.createDatabase("test", ODatabaseType.MEMORY, OrientDBConfig.defaultConfig());
    assertTrue(server.existsDatabase("test"));
    ODatabaseSession session = server.openDatabase("test");
    assertNotNull(session);
    session.close();
  }

}
