/*
 * Copyright (c) 2013-2017 Cinchapi Inc.
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
package com.cinchapi.concourse.server.plugin.io;

/**
 * 
 * 
 * @author Jeff Nelson
 */
public class MessageQueueTest extends InterProcessCommunicationTest {

    @Override
    protected InterProcessCommunication getInterProcessCommunication() {
        return new MessageQueue();
    }

    /* (non-Javadoc)
     * @see com.cinchapi.concourse.server.plugin.io.InterProcessCommunicationTest#getInterProcessCommunication(java.lang.String)
     */
    @Override
    protected InterProcessCommunication getInterProcessCommunication(
            String file) {
        return new MessageQueue(file);
    }


    @Override
    protected InterProcessCommunication getInterProcessCommunication(
            String file, int capacity) {
        return getInterProcessCommunication(file);
    }

}
