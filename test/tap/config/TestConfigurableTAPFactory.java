package tap.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static tap.config.TAPConfiguration.KEY_DATABASE_ACCESS;
import static tap.config.TAPConfiguration.KEY_DATASOURCE_JNDI_NAME;
import static tap.config.TAPConfiguration.KEY_DB_PASSWORD;
import static tap.config.TAPConfiguration.KEY_DB_USERNAME;
import static tap.config.TAPConfiguration.KEY_JDBC_DRIVER;
import static tap.config.TAPConfiguration.KEY_JDBC_URL;
import static tap.config.TAPConfiguration.KEY_SQL_TRANSLATOR;
import static tap.config.TAPConfiguration.VALUE_JDBC;
import static tap.config.TAPConfiguration.VALUE_JNDI;
import static tap.config.TAPConfiguration.VALUE_PGSPHERE;
import static tap.config.TAPConfiguration.VALUE_POSTGRESQL;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.util.PSQLException;

import tap.ServiceConnection;
import tap.TAPException;
import tap.TAPFactory;
import tap.db.DBConnection;
import tap.db.DBException;
import tap.db.JDBCConnection;
import tap.formatter.OutputFormat;
import tap.log.DefaultTAPLog;
import tap.log.TAPLog;
import tap.metadata.TAPMetadata;
import uws.service.UWSService;
import uws.service.UserIdentifier;
import uws.service.file.LocalUWSFileManager;
import uws.service.file.UWSFileManager;
import adql.db.FunctionDef;

public class TestConfigurableTAPFactory {

	private static Properties validJDBCProp, validJNDIProp,
			incorrectDBAccessProp, missingDBAccessProp,
			missingDatasourceJNDINameProp, wrongDatasourceJNDINameProp,
			noJdbcProp1, noJdbcProp2, noJdbcProp3, badJdbcProp,
			missingTranslatorProp, badTranslatorProp, badDBNameProp,
			badUsernameProp, badPasswordProp;

	private static ServiceConnection serviceConnection = null;

	private static void setJNDIDatasource() throws NamingException{
		// Create an initial JNDI context:
		/* note: this requires that the simple-jndi jar is in the classpath. (https://code.google.com/p/osjava/downloads/detail?name=simple-jndi-0.11.4.1.zip&can=2&q=) */
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.osjava.sj.memory.MemoryContextFactory");
		System.setProperty("org.osjava.sj.jndi.shared", "true");	// memory shared between all instances of InitialContext

		// Context initialization:
		InitialContext ic = new InitialContext();

		// Creation of a reference on a DataSource:
		PGSimpleDataSource datasource = new PGSimpleDataSource();
		datasource.setServerName("localhost");
		datasource.setDatabaseName("gmantele");

		// Link the datasource with the context:
		ic.rebind("jdbc/MyDataSource", datasource);
	}

	@BeforeClass
	public static void beforeClass() throws Exception{
		// BUILD A FAKE SERVICE CONNECTION:
		serviceConnection = new ServiceConnectionTest();

		// LOAD ALL PROPERTIES FILES NEEDED FOR ALL THE TESTS:
		validJDBCProp = AllTAPConfigTests.getValidProperties();

		setJNDIDatasource();
		validJNDIProp = (Properties)validJDBCProp.clone();
		validJNDIProp.setProperty(KEY_DATABASE_ACCESS, "jndi");
		validJNDIProp.setProperty(KEY_DATASOURCE_JNDI_NAME, "jdbc/MyDataSource");
		validJNDIProp.remove(KEY_JDBC_URL);
		validJNDIProp.remove(KEY_JDBC_DRIVER);
		validJNDIProp.remove(KEY_DB_USERNAME);
		validJNDIProp.remove(KEY_DB_PASSWORD);

		incorrectDBAccessProp = (Properties)validJDBCProp.clone();
		incorrectDBAccessProp.setProperty(KEY_DATABASE_ACCESS, "foo");

		missingDBAccessProp = (Properties)validJDBCProp.clone();
		missingDBAccessProp.remove(KEY_DATABASE_ACCESS);

		missingDatasourceJNDINameProp = (Properties)validJNDIProp.clone();
		missingDatasourceJNDINameProp.remove(KEY_DATASOURCE_JNDI_NAME);

		wrongDatasourceJNDINameProp = (Properties)validJNDIProp.clone();
		wrongDatasourceJNDINameProp.setProperty(KEY_DATASOURCE_JNDI_NAME, "foo");

		noJdbcProp1 = (Properties)validJDBCProp.clone();
		noJdbcProp1.remove(KEY_JDBC_DRIVER);

		noJdbcProp2 = (Properties)noJdbcProp1.clone();
		noJdbcProp2.setProperty(KEY_JDBC_URL, "jdbc:foo:gmantele");

		noJdbcProp3 = (Properties)noJdbcProp1.clone();
		noJdbcProp3.remove(KEY_JDBC_URL);

		badJdbcProp = (Properties)validJDBCProp.clone();
		badJdbcProp.setProperty(KEY_JDBC_DRIVER, "foo");
		badJdbcProp.setProperty(KEY_JDBC_URL, "jdbc:foo:gmantele");

		missingTranslatorProp = (Properties)validJDBCProp.clone();
		missingTranslatorProp.remove(KEY_SQL_TRANSLATOR);

		badTranslatorProp = (Properties)validJDBCProp.clone();
		badTranslatorProp.setProperty(KEY_SQL_TRANSLATOR, "foo");

		badDBNameProp = (Properties)validJDBCProp.clone();
		badDBNameProp.setProperty(KEY_JDBC_URL, "jdbc:postgresql:foo");

		badUsernameProp = (Properties)validJDBCProp.clone();
		badUsernameProp.setProperty(KEY_DB_USERNAME, "foo");

		badPasswordProp = (Properties)validJDBCProp.clone();
		badPasswordProp.setProperty(KEY_DB_PASSWORD, "foo");
	}

	@Test
	public void testDefaultServiceConnection(){
		// Correct Parameters (JDBC CASE):
		DBConnection connection = null;
		try{
			TAPFactory factory = new ConfigurableTAPFactory(serviceConnection, validJDBCProp);
			connection = factory.getConnection("0");
			assertNotNull(connection);
			assertNull(factory.createUWSBackupManager(new UWSService(factory, new LocalUWSFileManager(new File(".")))));
		}catch(Exception ex){
			fail(getPertinentMessage(ex));
		}finally{
			if (connection != null){
				try{
					((JDBCConnection)connection).getInnerConnection().close();
					connection = null;
				}catch(SQLException se){}
			}
		}

		// Correct Parameters (JNDI CASE):
		try{
			TAPFactory factory = new ConfigurableTAPFactory(serviceConnection, validJNDIProp);
			connection = factory.getConnection("0");
			assertNotNull(connection);
		}catch(Exception ex){
			fail(getPertinentMessage(ex));
		}finally{
			if (connection != null){
				try{
					((JDBCConnection)connection).getInnerConnection().close();
					connection = null;
				}catch(SQLException se){}
			}
		}

		// Incorrect database access method:
		try{
			new ConfigurableServiceConnection(incorrectDBAccessProp);
			fail("This MUST have failed because the value of the property '" + KEY_DATABASE_ACCESS + "' is incorrect!");
		}catch(Exception e){
			assertEquals(TAPException.class, e.getClass());
			assertEquals("Unsupported value for the property " + KEY_DATABASE_ACCESS + ": \"foo\"! Allowed values: \"" + VALUE_JNDI + "\" or \"" + VALUE_JDBC + "\".", e.getMessage());
		}

		// Missing database access method:
		try{
			new ConfigurableServiceConnection(missingDBAccessProp);
			fail("This MUST have failed because the property '" + KEY_DATABASE_ACCESS + "' is missing!");
		}catch(Exception e){
			assertEquals(TAPException.class, e.getClass());
			assertEquals("The property \"" + KEY_DATABASE_ACCESS + "\" is missing! It is required to connect to the database. Two possible values: \"" + VALUE_JDBC + "\" and \"" + VALUE_JNDI + "\".", e.getMessage());
		}

		// Missing JNDI name:
		try{
			new ConfigurableServiceConnection(missingDatasourceJNDINameProp);
			fail("This MUST have failed because the property '" + KEY_DATASOURCE_JNDI_NAME + "' is missing!");
		}catch(Exception e){
			assertEquals(TAPException.class, e.getClass());
			assertEquals("The property \"" + KEY_DATASOURCE_JNDI_NAME + "\" is missing! Since the choosen database access method is \"" + VALUE_JNDI + "\", this property is required.", e.getMessage());
		}

		// Wrong JNDI name:
		try{
			new ConfigurableServiceConnection(wrongDatasourceJNDINameProp);
			fail("This MUST have failed because the value of the property '" + KEY_DATASOURCE_JNDI_NAME + "' is incorrect!");
		}catch(Exception e){
			assertEquals(TAPException.class, e.getClass());
			assertEquals("No datasource found with the JNDI name \"foo\"!", e.getMessage());
		}

		// No JDBC Driver but the database type is known:
		try{
			new ConfigurableTAPFactory(serviceConnection, noJdbcProp1);
		}catch(Exception ex){
			fail(getPertinentMessage(ex));
		}

		// No JDBC Driver but the database type is UNKNOWN:
		try{
			new ConfigurableTAPFactory(serviceConnection, noJdbcProp2);
			fail("This MUST have failed because no JDBC Driver has been successfully guessed from the database type!");
		}catch(Exception ex){
			assertEquals(TAPException.class, ex.getClass());
			assertTrue(ex.getMessage().matches("No JDBC driver known for the DBMS \"[^\\\"]*\"!"));
		}

		// Missing JDBC URL:
		try{
			new ConfigurableTAPFactory(serviceConnection, noJdbcProp3);
			fail("This MUST have failed because the property \"" + KEY_JDBC_URL + "\" is missing!");
		}catch(Exception ex){
			assertEquals(TAPException.class, ex.getClass());
			assertTrue(ex.getMessage().matches("The property \"" + KEY_JDBC_URL + "\" is missing! Since the choosen database access method is \"" + VALUE_JDBC + "\", this property is required."));
		}

		// Bad JDBC Driver:
		try{
			new ConfigurableTAPFactory(serviceConnection, badJdbcProp);
			fail("This MUST have failed because the provided JDBC Driver doesn't exist!");
		}catch(Exception ex){
			assertEquals(DBException.class, ex.getClass());
			assertTrue(ex.getMessage().matches("Impossible to find the JDBC driver \"[^\\\"]*\" !"));
		}

		// Missing Translator:
		try{
			new ConfigurableTAPFactory(serviceConnection, missingTranslatorProp);
			fail("This MUST have failed because the provided SQL translator is missing!");
		}catch(Exception ex){
			assertEquals(TAPException.class, ex.getClass());
			assertTrue(ex.getMessage().matches("The property \"" + KEY_SQL_TRANSLATOR + "\" is missing! ADQL queries can not be translated without it. Allowed values: \"" + VALUE_POSTGRESQL + "\", \"" + VALUE_PGSPHERE + "\" or a class path of a class implementing SQLTranslator."));
		}

		// Bad Translator:
		try{
			new ConfigurableTAPFactory(serviceConnection, badTranslatorProp);
			fail("This MUST have failed because the provided SQL translator is incorrect!");
		}catch(Exception ex){
			assertEquals(TAPException.class, ex.getClass());
			assertTrue(ex.getMessage().matches("Unsupported value for the property sql_translator: \"[^\\\"]*\" !"));
		}

		// Bad DB Name:
		try{
			new ConfigurableTAPFactory(serviceConnection, badDBNameProp);
			fail("This MUST have failed because the provided database name is incorrect!");
		}catch(Exception ex){
			assertEquals(DBException.class, ex.getClass());
			assertTrue(ex.getMessage().matches("Impossible to establish a connection to the database \"[^\\\"]*\"!"));
			assertEquals(PSQLException.class, ex.getCause().getClass());
			assertTrue(ex.getCause().getMessage().matches("FATAL: password authentication failed for user \"[^\\\"]*\""));
		}

		// Bad DB Username: ABORTED BECAUSE THE BAD USERNAME IS NOT DETECTED FOR THE DB WHICH HAS THE SAME NAME AS THE USERNAME !
		try{
			new ConfigurableTAPFactory(serviceConnection, badUsernameProp);
			fail("This MUST have failed because the provided database username is incorrect!");
		}catch(Exception ex){
			assertEquals(DBException.class, ex.getClass());
			assertTrue(ex.getMessage().matches("Impossible to establish a connection to the database \"[^\\\"]*\"!"));
			assertEquals(PSQLException.class, ex.getCause().getClass());
			assertTrue(ex.getCause().getMessage().matches("FATAL: password authentication failed for user \"[^\\\"]*\""));
		}

		// Bad DB Password:
		try{
			new ConfigurableTAPFactory(serviceConnection, badPasswordProp);
			//fail("This MUST have failed because the provided database password is incorrect!"); // NOTE: In function of the database configuration, a password may be required or not. So this test is not automatic! 
		}catch(Exception ex){
			assertEquals(DBException.class, ex.getClass());
			assertTrue(ex.getMessage().matches("Impossible to establish a connection to the database \"[^\\\"]*\"!"));
			assertEquals(PSQLException.class, ex.getCause().getClass());
			assertTrue(ex.getCause().getMessage().matches("FATAL: password authentication failed for user \"[^\\\"]*\""));
		}
	}

	public static final String getPertinentMessage(final Exception ex){
		return (ex.getCause() == null || ex.getMessage().equals(ex.getCause().getMessage())) ? ex.getMessage() : ex.getCause().getMessage();
	}

	public static class ServiceConnectionTest implements ServiceConnection {

		private TAPLog logger = new DefaultTAPLog((UWSFileManager)null);
		private boolean isAvailable = true;

		@Override
		public String getProviderName(){
			return null;
		}

		@Override
		public String getProviderDescription(){
			return null;
		}

		@Override
		public boolean isAvailable(){
			return isAvailable;
		}

		@Override
		public String getAvailability(){
			return null;
		}

		@Override
		public int[] getRetentionPeriod(){
			return null;
		}

		@Override
		public int[] getExecutionDuration(){
			return null;
		}

		@Override
		public int[] getOutputLimit(){
			return null;
		}

		@Override
		public tap.ServiceConnection.LimitUnit[] getOutputLimitType(){
			return null;
		}

		@Override
		public UserIdentifier getUserIdentifier(){
			return null;
		}

		@Override
		public boolean uploadEnabled(){
			return false;
		}

		@Override
		public int[] getUploadLimit(){
			return null;
		}

		@Override
		public tap.ServiceConnection.LimitUnit[] getUploadLimitType(){
			return null;
		}

		@Override
		public int getMaxUploadSize(){
			return 0;
		}

		@Override
		public TAPMetadata getTAPMetadata(){
			return null;
		}

		@Override
		public Collection<String> getCoordinateSystems(){
			return null;
		}

		@Override
		public TAPLog getLogger(){
			return logger;
		}

		@Override
		public TAPFactory getFactory(){
			return null;
		}

		@Override
		public UWSFileManager getFileManager(){
			return null;
		}

		@Override
		public Iterator<OutputFormat> getOutputFormats(){
			return null;
		}

		@Override
		public OutputFormat getOutputFormat(String mimeOrAlias){
			return null;
		}

		@Override
		public void setAvailable(boolean isAvailable, String message){
			this.isAvailable = isAvailable;
		}

		@Override
		public Collection<String> getGeometries(){
			return null;
		}

		@Override
		public Collection<FunctionDef> getUDFs(){
			return null;
		}

		@Override
		public int getNbMaxAsyncJobs(){
			return -1;
		}

		@Override
		public int[] getFetchSize(){
			return null;
		}
	}

}
