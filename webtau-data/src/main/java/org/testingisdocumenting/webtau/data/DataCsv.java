/*
 * Copyright 2020 webtau maintainers
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

package org.testingisdocumenting.webtau.data;

import org.testingisdocumenting.webtau.data.table.TableData;
import org.testingisdocumenting.webtau.utils.CsvUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.testingisdocumenting.webtau.data.DataContentUtils.dataTextContent;

public class DataCsv {
    /**
     * Use <code>data.csv.table</code> to read data as {@link TableData} from CSV file.
     * Passed path is either relative based on working dir or absolute path. Or it can be a resource class path.
     * @param fileOrResourcePath relative path, absolute path or classpath resource path
     * @return table data with CSV content
     */
    public TableData table(String fileOrResourcePath) {
        return tableFromListOfMaps(CsvUtils.parse(textContent(fileOrResourcePath)));
    }

    /**
     * Use <code>data.csv.tableAutoConverted</code> to read data as {@link TableData} from CSV file. Numeric values become values of Numeric type instead of String type.
     * Passed path is either relative based on working dir or absolute path. Or it can be a resource class path.
     *
     * @param fileOrResourcePath relative path, absolute path or classpath resource path
     * @return table data with CSV content
     */
    public TableData tableAutoConverted(String fileOrResourcePath) {
        return tableFromListOfMaps(CsvUtils.parseWithAutoConversion(textContent(fileOrResourcePath)));
    }

    /**
     * Use <code>data.csv.listOfMaps</code> to read data as {@link java.util.List} of {@link java.util.Map} from CSV file.
     * Passed path is either relative based on working dir or absolute path. Or it can be a resource class path.
     *
     * @param fileOrResourcePath relative path, absolute path or classpath resource path
     * @return list of maps
     */
    public List<Map<String, String>> listOfMaps(String fileOrResourcePath) {
        return CsvUtils.parse(textContent(fileOrResourcePath));
    }

    /**
     * Use <code>data.csv.listOfMaps</code> to read data as {@link java.util.List} of {@link java.util.Map} from CSV file.
     * Numeric values become values of Numeric type instead of String type.
     * Passed path is either relative based on working dir or absolute path. Or it can be a resource class path.
     *
     * @param fileOrResourcePath relative path, absolute path or classpath resource path
     * @return list of maps
     */
    public List<Map<String, Object>> listOfMapsAutoConverted(String fileOrResourcePath) {
        return CsvUtils.parseWithAutoConversion(textContent(fileOrResourcePath));
    }

    public List<Map<String, String>> listOfMaps(List<String> header, String fileOrResourcePath) {
        return CsvUtils.parse(header, textContent(fileOrResourcePath));
    }

    public List<Map<String, Object>> listOfMapsAutoConverted(List<String> header, String fileOrResourcePath) {
        return CsvUtils.parseWithAutoConversion(header, textContent(fileOrResourcePath));
    }

    private static String textContent(String fileOrResourcePath) {
        return dataTextContent("csv", fileOrResourcePath).content;
    }

    @SuppressWarnings("unchecked")
    private TableData tableFromListOfMaps(List<?> listOfMaps) {
        if (listOfMaps.isEmpty()) {
            return new TableData(Collections.emptyList());
        }

        Map<String, Object> firstRow = (Map<String, Object>) listOfMaps.get(0);

        TableData result = new TableData(firstRow.keySet().stream());
        listOfMaps.forEach((row) -> {
            Map<String, Object> asMap = (Map<String, Object>) row;
            result.addRow(asMap.values().stream());
        });

        return result;
    }
}
