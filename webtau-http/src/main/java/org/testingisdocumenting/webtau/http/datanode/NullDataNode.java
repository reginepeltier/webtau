/*
 * Copyright 2020 webtau maintainers
 * Copyright 2019 TWO SIGMA OPEN SOURCE, LLC
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

package org.testingisdocumenting.webtau.http.datanode;

import org.testingisdocumenting.webtau.data.traceable.TraceableValue;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class NullDataNode implements DataNode {
    private final DataNodeId id;
    private final TraceableValue value = new TraceableValue(null);
    private final Map<DataNodeId, DataNode> queriedNonExistentNodes = new LinkedHashMap<>();

    NullDataNode(DataNodeId id) {
        this.id = id;
    }

    @Override
    public DataNodeId id() {
        return id;
    }

    @Override
    public DataNode get(String pathOrName) {
        return queriedNonExistentNodes.computeIfAbsent(id.child(pathOrName), NullDataNode::new);
    }

    @Override
    public DataNode get(int idx) {
        return queriedNonExistentNodes.computeIfAbsent(id.peer(idx), NullDataNode::new);
    }

    @Override
    public TraceableValue getTraceableValue() {
        return value;
    }

    @Override
    public <E> E get() {
        return null;
    }

    @Override
    public boolean isSingleValue() {
        return true;
    }

    @Override
    public List<DataNode> elements() {
        return Collections.singletonList(queriedNonExistentNodes.computeIfAbsent(id.peer(0), NullDataNode::new));
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public String toString() {
        return "[null node]@" + id;
    }
}
