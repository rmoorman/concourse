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
package com.cinchapi.concouse.server.upgrade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import com.cinchapi.common.base.CheckedExceptions;
import com.cinchapi.concourse.Concourse;
import com.cinchapi.concourse.server.io.FileSystem;
import com.cinchapi.concourse.test.UpgradeTest;
import com.cinchapi.concourse.util.Random;

/**
 * Integration test for {@link Upgrade0_7_0_1}.
 * 
 * @author Jeff Nelson
 */
public class UpgradeTask0_7_0_1Test extends UpgradeTest {

    private static final String environment1 = "env1";
    private static final String environment2 = "env2";

    @Override
    protected String getInitialServerVersion() {
        return "0.6.0";
    }

    @Override
    protected void preUpgradeActions() {
        String directory1 = FileSystem.makePath(server.getDatabaseDirectory(),
                environment1, "cpb");
        String directory2 = FileSystem.makePath(server.getDatabaseDirectory(),
                environment2, "cpb");
        Concourse client1 = Concourse.connect("localhost",
                server.getClientPort(), "admin", "admin", environment1);
        Concourse client2 = Concourse.connect("localhost",
                server.getClientPort(), "admin", "admin", environment2);
        client1.add("name", "jeff nelson", 17);
        try {
            while (Files.list(Paths.get(directory1)).count() < 1
                    || Files.list(Paths.get(directory2)).count() < 1) {
                client1.add(Random.getSimpleString(), Random.getObject());
                client2.add(Random.getSimpleString(), Random.getObject());
            }
        }
        catch (IOException e) {
            throw CheckedExceptions.throwAsRuntimeException(e);
        }
    }

    @Test
    public void testPreviouslyExistingDataIsStillReadable() {
        Concourse client1 = Concourse.connect("localhost",
                server.getClientPort(), "admin", "admin", environment1);
        Assert.assertEquals("jeff nelson", client1.get("name", 17));
    }

}
