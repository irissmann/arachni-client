/*
 * Copyright 2017 Ingo Rissmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.irissmann.arachni.client.rest;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.irissmann.arachni.client.ArachniClientException;

public class ArachniUtils {
    
    private ArachniUtils() {
        throw new IllegalAccessError("Utility class");
    }

    public enum MergeConflictStrategy {
        THROW_EXCEPTION, PREFER_OBJECT, PREFER_STRING, PREFER_NON_NULL;
    }

    static void merge(JsonObject leftObj, JsonObject rightObj, MergeConflictStrategy conflictStrategy) {
        for (Map.Entry<String, JsonElement> rightEntry : rightObj.entrySet()) {
            String rightKey = rightEntry.getKey();
            JsonElement rightVal = rightEntry.getValue();
            if (leftObj.has(rightKey)) {
                // conflict
                JsonElement leftVal = leftObj.get(rightKey);
                if (leftVal.isJsonArray() && rightVal.isJsonArray()) {
                    JsonArray leftArr = leftVal.getAsJsonArray();
                    JsonArray rightArr = rightVal.getAsJsonArray();
                    // concat the arrays -- there cannot be a conflict in an
                    // array, it's just a collection of stuff
                    for (int i = 0; i < rightArr.size(); i++) {
                        leftArr.add(rightArr.get(i));
                    }
                } else if (leftVal.isJsonObject() && rightVal.isJsonObject()) {
                    // recursive merging
                    merge(leftVal.getAsJsonObject(), rightVal.getAsJsonObject(), conflictStrategy);
                } else {// not both arrays or objects, normal merge with
                        // conflict resolution
                    handleMergeConflict(rightKey, leftObj, leftVal, rightVal, conflictStrategy);
                }
            } else {// no conflict, add to the object
                leftObj.add(rightKey, rightVal);
            }
        }
    }

    private static void handleMergeConflict(String key, JsonObject leftObj, JsonElement leftVal, JsonElement rightVal,
            MergeConflictStrategy conflictStrategy) {
        {
            switch (conflictStrategy) {
            case PREFER_OBJECT:
                break;// do nothing, the right val gets thrown out
            case PREFER_STRING:
                leftObj.add(key, rightVal);// right side auto-wins, replace left
                                           // val with its val
                break;
            case PREFER_NON_NULL:
                // check if right side is not null, and left side is null, in
                // which case we use the right val
                if (leftVal.isJsonNull() && !rightVal.isJsonNull()) {
                    leftObj.add(key, rightVal);
                } // else do nothing since either the left value is non-null or
                  // the right value is null
                break;
            case THROW_EXCEPTION:
                throw new ArachniClientException("Key " + key
                        + " exists in both objects and the conflict resolution strategy is " + conflictStrategy);
            default:
                throw new UnsupportedOperationException(
                        "The conflict strategy " + conflictStrategy + " is unknown and cannot be processed");
            }
        }
    }
}
