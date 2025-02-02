/*
 *  Copyright (c) 2025, WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 LLC. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.spectral.functions.core;

import com.google.gson.Gson;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.wso2.spectral.document.LintTarget;
import org.wso2.spectral.functions.LintFunction;

import java.util.Map;

public class Schema extends LintFunction {

    public Schema(Map<String, Object> options) {
        super(options);
    }

    public boolean execute(LintTarget target) {

        String targetString = new Gson().toJson(target.value);
        JSONObject targetObject = new JSONObject(targetString);

        String schema = new Gson().toJson(options.get("schema"));
        JSONObject schemaObject = new JSONObject(schema);
        org.everit.json.schema.Schema everitSchema = SchemaLoader.load(schemaObject);

        try{
            everitSchema.validate(targetObject);
        } catch (org.everit.json.schema.ValidationException e) {
            return false;
        }
        return true;
    }

}
