/**
 * Copyright (c) 2013 Oculus Info Inc.
 * http://www.oculusinfo.com/
 *
 * Released under the MIT License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package influent.bitcoin.server.data;

import influent.server.dataaccess.MSSQLDataNamespaceHandler;

import java.util.Map;

import org.json.JSONException;

/**
 * The namespace handler handles mapping of table names and entity ids to and from the
 * application database.
 * 
 * @author djonker
 */
public class BitcoinNamespaceHandler extends MSSQLDataNamespaceHandler {

	/**
	 * Constructs a handler which uses standard table names.
	 */
	public BitcoinNamespaceHandler() {
	}
	
	/**
	 * Creates a new handler based on a configuration defined as a JSON object string
	 * mapping standard names to localized names.
	 * 
	 * @param jsonTableNames
	 * @throws JSONException
	 */
	public BitcoinNamespaceHandler(String jsonTableNames) throws JSONException {
		super(jsonTableNames);
	}

	/**
	 * Constructs a namespace handler from a
	 * map of standard names to localized names.
	 * 
	 * @param tableNames
	 */
	public BitcoinNamespaceHandler(Map<String, String> tableNames) {
		super(tableNames);
	}

	
	/**
	 * Creates a globally unique entity id given a local entity id and a namespace.
	 * The default implementation here simply returns the local entity id.
	 * An example override might look like this:
	 * <code>
	 *     return namespace + "." + localEntityId;
	 * </code>
	 * 
	 * @param localEntityId
	 * 		the entity id as it exists in the data store
	 * 
	 * @return
	 * 		a globally unique id used for reference in the system
	 */
	@Override
	public String globalFromLocalEntityId(String namespace, String localEntityId) {
		return super.globalFromLocalEntityId(namespace, localEntityId);
	}

	/**
	 * Extracts and returns a namespace from a global entity id, typically formed
	 * by a call to createGlobalEntityId. The default implementation here returns
	 * null.
	 * 
	 * An example override might look like this:
	 * <code>
	 *     final int i = globalEntityId.indexOf('.');
	 *
	 *     if (i >= 0) {
	 *         return globalEntityId.substring(0, i);
	 *     }
	 *     return namespace + "." + localEntityId;
	 * </code>
	 * 
	 * @param globalEntityId
	 * 		the globally unique entity id
	 * 
	 * @return
	 */
	@Override
	public String namespaceFromGlobalEntityId(String globalEntityId) {
		return super.namespaceFromGlobalEntityId(globalEntityId);
	}

	/**
	 * Translates an entity id from global to local namespace for querying in
	 * the Data View. The default implementation returns the global id, since
	 * by default namespaces are not used.
	 * 
	 * An example override might look like this:
	 * <code>
	 *     final int i = globalEntityId.indexOf('.');
	 *
	 *     if (i >= 0) {
	 *         return globalEntityId.substring(i+1);
	 *     }
	 *     return super.localFromGlobalEntityId(globalEntityId);
	 * </code>
	 * 
	 * @param globalEntityId
	 * 		the globally unique entity id
	 * 
	 * @return
	 * 		the entity id as it exists in the data store
	 */
	@Override
	public String localFromGlobalEntityId(String globalEntityId) {
		return super.localFromGlobalEntityId(globalEntityId);
	}

	/**
	 * Returns a data view table name, given a namespace to consider. The
	 * default implementation here first maps the name using any mappings defined
	 * in construction, then prepends the namespace as a db schema in the form
	 * namespace.tableName if a namespace is provided. 
	 * @param standardTableName
	 * 		The standard data view table name.
	 * 
	 * @return
	 * 		The localized data view table name.
	 */
	@Override
	public String tableName(String namespace, /*String namespace,*/ String standardTableName) {
		return super.tableName(namespace, /*namespace,*/ standardTableName);
	}

	
}