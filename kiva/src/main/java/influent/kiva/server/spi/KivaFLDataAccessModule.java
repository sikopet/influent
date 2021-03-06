/*
 * Copyright 2013-2016 Uncharted Software Inc.
 *
 *  Property of Uncharted(TM), formerly Oculus Info Inc.
 *  https://uncharted.software/
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package influent.kiva.server.spi;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import influent.idl.FL_DataAccess;
import influent.idl.FL_EntitySearch;
import influent.kiva.server.dataaccess.KivaDataAccess;
import influent.server.dataaccess.DataNamespaceHandler;
import influent.server.dataaccess.MSSQLDataNamespaceHandler;
import influent.server.sql.SQLBuilder;
import influent.server.utilities.SQLConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oculus.aperture.spi.common.Properties;

/**
 * This class is used by the Kiva *server* implementation.
 * It binds the new FL_ implementation for that server.
 *
 */
public class KivaFLDataAccessModule extends AbstractModule {

	private static Logger s_logger = LoggerFactory.getLogger(KivaFLDataAccessModule.class);
	
	@Override
	protected void configure() {
		bind(FL_DataAccess.class).to(KivaDataAccess.class);
	}
	
	@Provides @Singleton
	public DataNamespaceHandler namespaceHandler() {
		return new MSSQLDataNamespaceHandler();
	}
	
	/*
	 * Provide the database service
	 */
	@Provides @Singleton
	public KivaDataAccess connect (
		@Named("aperture.server.config") Properties config,
		SQLConnectionPool connectionPool,
		FL_EntitySearch search,
		DataNamespaceHandler namespaceHandler,
		SQLBuilder sqlBuilder
	) {

		try {
			return new KivaDataAccess(
				config,
				connectionPool,
				search,
				namespaceHandler,
				sqlBuilder
			);
		} catch (Exception e) {
			addError("Failed to load Data Access", e);
			return null;
		}
	}
	
	
	
}
